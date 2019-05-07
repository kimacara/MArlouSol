package com.svi.solitaire.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.objects.Card;
import com.svi.solitaire.objects.Deck;

public class SolitaireMethods {

	public static Deck deck = new Deck();

	public static Rank getRank(String rankIdentity) {

		for (Rank ranks : Rank.values()) {
			if (ranks.getRankIdentity().equals(rankIdentity)) {

				return ranks;
			}
		}

		return null;
	}

	public static Suit getSuit(String suitIdentity) {

		for (Suit suits : Suit.values()) {
			if (suits.getSuitIdentity().equals(suitIdentity)) {

				return suits;
			}
		}

		return null;
	}

	public static Deck createDeck() {

		try {
			Scanner scanner = new Scanner(new File("input.txt"));
			String[] lineText = scanner.nextLine().split(",");

			for (String delimeterText : lineText) {
				String token[] = delimeterText.split("-");
				Suit suit = getSuit(token[0]);
				Rank rank = getRank(token[1]);
				Card card = new Card(suit, rank);
				deck.add(card);

			}

			scanner.close();

			System.out.println("KLONDIKE SOLITAIRE");
			System.out.println("\nINPUT FILE LOADED SUCCESSFULLY!!!");
			System.out.println("\nHere is the Initial Deck of Cards: \n");
			System.out.println(deck.getDeck().toString());

		} catch (IOException e) {

			System.out.println("\nFILE NOT FOUND!!!");
		}
		return deck;

	}

	public static ArrayList<ArrayList<Card>> createManeouverStacks(Deck deck) {

		ArrayList<ArrayList<Card>> manoeuvereTableau = new ArrayList<ArrayList<Card>>();

		for (int i = 0; i < 7; i++) {
			ArrayList<Card> manoeuvereStack = new ArrayList<Card>();
			manoeuvereTableau.add(manoeuvereStack);

		}

		for (int j = 0; j < manoeuvereTableau.size(); j++) {
			for (int k = j; k < manoeuvereTableau.size(); k++) {

				Card card = deck.removeCard(0);

				if (k != j) {
					card.setFaceUp(false);

				}

				manoeuvereTableau.get(k).add(card);
			}

		}

		ArrayList<Card> remainingCards = deck.getDeck();
		System.out.println("\nREMAINING CARDS IN DECK: ");
		System.out.println(remainingCards + "\n");

		for (int l = 0; l < manoeuvereTableau.size(); l++) {
			System.out.println(manoeuvereTableau.get(l));

		}

		return manoeuvereTableau;

	}

	public static ArrayList<ArrayList<Card>> createFoundation() {

		ArrayList<ArrayList<Card>> foundationTableau = new ArrayList<ArrayList<Card>>();

		System.out.println("\nFOUNDATION: ");

		for (int x = 0; x < 4; x++) {
			ArrayList<Card> foundationStack = new ArrayList<Card>();
			foundationTableau.add(foundationStack);

		}
		System.out.println(foundationTableau + " ");

		return foundationTableau;
	}

	public static ArrayList<Card> drawCard(Deck deck, int draw, ArrayList<Card> talon) {

		for (int m = 0; m < draw; m++) {
			Card card = deck.removeCard(0);
			talon.add(card);
		}
		System.out.println("\nTALON:");
		System.out.println(talon);
		System.out.println("\nREMAINING CARDS IN DECK:");
		System.out.println(deck.getDeck());

		return talon;
	}

	private static int openCards(ArrayList<Card> maneouvereTableau) {

		int count = 0;
		for (Card card : maneouvereTableau) {
			if (card.isFaceUp() == true)
				count++;
		}
		return count;
	}

	public static void sendCardToFoundation(ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> maneouvereTableau, ArrayList<Card> talon) {

		for (ArrayList<Card> maneouvereLine : maneouvereTableau) {
			if (!maneouvereLine.isEmpty()) {
				Card lastCardOfTheLine = maneouvereLine.get(maneouvereLine.size() - 1);
				if (lastCardOfTheLine.getRank() == Rank.ACE) {
					System.out.println("\n" + lastCardOfTheLine.toString() + " moved to the Foundation\n");
					foundationTableau.get(lastCardOfTheLine.getSuit().getSuitValue())
							.add(maneouvereLine.remove(maneouvereLine.size() - 1));
					if (!maneouvereLine.isEmpty()) {
						maneouvereLine.get(maneouvereLine.size() - 1).setFaceUp(true);
					}
					System.out.print("\nTALON: ");
					System.out.println(talon + "\n");
					System.out.print("\n");
					System.out.println(foundationTableau + "\n");
					for (int l = 0; l < maneouvereTableau.size(); l++) {
						System.out.println(maneouvereTableau.get(l));
					}

					sendCardToFoundation(foundationTableau, maneouvereTableau, talon);
					return;

				} else {
					for (ArrayList<Card> suitFoundation : foundationTableau) {
						if (!suitFoundation.isEmpty()) {
							Card lastCardOfTheFoundation = suitFoundation.get(suitFoundation.size() - 1);
							if (lastCardOfTheLine.getRank().getRankValue() - 1 == lastCardOfTheFoundation.getRank()
									.getRankValue()
									&& lastCardOfTheFoundation.getSuit() == lastCardOfTheLine.getSuit()) {
								System.out.println("\n" + lastCardOfTheLine.toString() + " moved to the Foundation\n");
								suitFoundation.add(maneouvereLine.remove(maneouvereLine.size() - 1));
								if (!maneouvereLine.isEmpty()) {
									maneouvereLine.get(maneouvereLine.size() - 1).setFaceUp(true);
								}
								System.out.print("\nTALON: ");
								System.out.println(talon + "\n");
								System.out.print("\n");
								System.out.println(foundationTableau + "\n");
								for (int l = 0; l < maneouvereTableau.size(); l++) {
									System.out.println(maneouvereTableau.get(l));
								}

								sendCardToFoundation(foundationTableau, maneouvereTableau, talon);
								return;
							}
						}
					}
				}
			}
		}

		if (!talon.isEmpty()) {
			Card lastCardOfTalon = talon.get(talon.size() - 1);
			if (lastCardOfTalon.getRank() == Rank.ACE) {
				System.out.println("\n" + lastCardOfTalon.toString() + " moved to the Foundation\n");
				foundationTableau.get(lastCardOfTalon.getSuit().getSuitValue()).add(talon.remove(talon.size() - 1));
				System.out.print("\nTALON: ");
				System.out.println(talon + "\n");
				System.out.print("\n");
				System.out.println(foundationTableau + "\n");
				for (int l = 0; l < maneouvereTableau.size(); l++) {
					System.out.println(maneouvereTableau.get(l));
				}

				sendCardToFoundation(foundationTableau, maneouvereTableau, talon);
				return;

			} else {
				for (ArrayList<Card> suitFoundation : foundationTableau) {
					if (!suitFoundation.isEmpty()) {
						Card lastCardOfTheFoundation = suitFoundation.get(suitFoundation.size() - 1);
						if (lastCardOfTalon.getRank().getRankValue() - 1 == lastCardOfTheFoundation.getRank()
								.getRankValue() && lastCardOfTalon.getSuit() == lastCardOfTheFoundation.getSuit()) {
							System.out.println("\n" + lastCardOfTalon.toString() + " moved to the Foundation\n");
							suitFoundation.add(talon.remove(talon.size() - 1));

							System.out.print("\nTALON: ");
							System.out.println(talon + "\n");
							System.out.print("\n");
							System.out.println(foundationTableau + "\n");
							for (int l = 0; l < maneouvereTableau.size(); l++) {
								System.out.println(maneouvereTableau.get(l));
							}

							sendCardToFoundation(foundationTableau, maneouvereTableau, talon);
							return;
						}
					}
				}
			}
		}
	}

	public static void sendCardFromTalonToLine(ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> maneouvereTableau, ArrayList<Card> talon) {

		for (ArrayList<Card> maneouvereLine : maneouvereTableau) {
			if (!maneouvereLine.isEmpty()) {
				Card lastCardOfLine = maneouvereLine.get(maneouvereLine.size() - 1);
				Card lastCardOfTalon = talon.get(talon.size() - 1);
				if (lastCardOfLine.getRank().getRankValue() - 1 == lastCardOfTalon.getRank().getRankValue()
						&& lastCardOfTalon.getColor() != lastCardOfLine.getColor()) {
					System.out.println("\n" + lastCardOfTalon.toString() + " moved to the maneouvere line\n");
					maneouvereLine.add(talon.remove(talon.size() - 1));

					System.out.print("\nTALON: ");
					System.out.println(talon + "\n");
					System.out.print("\n");
					System.out.println(foundationTableau + "\n");
					for (int l = 0; l < maneouvereTableau.size(); l++) {
						System.out.println(maneouvereTableau.get(l));
					}

					sendCardFromTalonToLine(foundationTableau, maneouvereTableau, talon);
					return;
				}
			}
		}

	}

	public static void sendKingfromLineToEmptyLine(ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> maneouvereTableau, ArrayList<Card> talon) {

		for (ArrayList<Card> sourceManeouvereLine : maneouvereTableau) {
			for (ArrayList<Card> destinationManeouvereLine : maneouvereTableau) {
				if (!sourceManeouvereLine.isEmpty()) {
					Card lastCardOfLine = sourceManeouvereLine.get(sourceManeouvereLine.size() - 1);
					if (destinationManeouvereLine.isEmpty()) {
						if (lastCardOfLine.getRank() == Rank.KING) {
								destinationManeouvereLine
										.add(sourceManeouvereLine.remove(sourceManeouvereLine.size() - 1));
								if (!sourceManeouvereLine.isEmpty()) {
									sourceManeouvereLine.get(sourceManeouvereLine.size() - 1).setFaceUp(true);
								}
							System.out.print("\nTALON: ");
							System.out.println(talon + "\n");
							System.out.print("\n");
							System.out.println(foundationTableau + "\n");
							for (int l = 0; l < maneouvereTableau.size(); l++) {
								System.out.println(maneouvereTableau.get(l));
							}

							sendKingfromLineToEmptyLine(foundationTableau, maneouvereTableau, talon);
							return;

						} else {
							Card firstFaceUpCard = sourceManeouvereLine
									.get(sourceManeouvereLine.size() - openCards(sourceManeouvereLine));
							if (firstFaceUpCard.getRank() == Rank.KING) {
								destinationManeouvereLine.add(sourceManeouvereLine
										.remove(sourceManeouvereLine.size() - openCards(sourceManeouvereLine)));
								if (!sourceManeouvereLine.isEmpty()) {
									sourceManeouvereLine.get(sourceManeouvereLine.size() - 1).setFaceUp(true);
								}
								System.out.print("\nTALON: ");
								System.out.println(talon + "\n");
								System.out.print("\n");
								System.out.println(foundationTableau + "\n");
								for (int l = 0; l < maneouvereTableau.size(); l++) {
									System.out.println(maneouvereTableau.get(l));
								}

								sendKingfromLineToEmptyLine(foundationTableau, maneouvereTableau, talon);
								return;
							}
						}
					}
				}
			}
		}
		
		// king from talon to empty line 
	}

}
