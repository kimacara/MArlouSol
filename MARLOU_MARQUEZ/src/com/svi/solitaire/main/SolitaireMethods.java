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

	public static ArrayList<ArrayList<Card>> createManeouverLine(Deck deck) {

		ArrayList<ArrayList<Card>> manoeuvereTableau = new ArrayList<ArrayList<Card>>();

		for (int i = 0; i < 7; i++) {
			ArrayList<Card> manoeuvereLine = new ArrayList<Card>();
			manoeuvereTableau.add(manoeuvereLine);

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
		System.out.println("\nWASTE: ");
		System.out.println(remainingCards + "\n");
		System.out.println("Maneouvere Tableau: ");
		for (int l = 0; l < manoeuvereTableau.size(); l++) {
			System.out.println(manoeuvereTableau.get(l));

		}

		return manoeuvereTableau;

	}

	public static ArrayList<ArrayList<Card>> createFoundation() {

		ArrayList<ArrayList<Card>> foundationTableau = new ArrayList<ArrayList<Card>>();

		System.out.println("\nFOUNDATION: ");

		for (int x = 0; x < 4; x++) {
			ArrayList<Card> foundationLine = new ArrayList<Card>();
			foundationTableau.add(foundationLine);

		}
		System.out.println(foundationTableau + " ");

		return foundationTableau;
	}

	public static ArrayList<Card> drawCard(Deck deck, int draw, ArrayList<Card> talon) {

		if (deck.getDeck().isEmpty()) {
			if (deck.getDeck().isEmpty()) { return null;}
			deck.getDeck().addAll(talon);
			talon.clear();
		}

		for (int m = 0; m < draw; m++) {
			Card card = deck.removeCard(0);
			talon.add(card);
			if (deck.getDeck().isEmpty()) {
				break;
			}

		}
		System.out.println("\nTALON:");
		System.out.println(talon);

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

	public static void moveCardToFoundation(ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> maneouvereTableau, ArrayList<Card> talon) {

		for (ArrayList<Card> maneouvereLine : maneouvereTableau) {
			if (!maneouvereLine.isEmpty()) {
				Card lastCardOfTheLine = maneouvereLine.get(maneouvereLine.size() - 1);
				if (lastCardOfTheLine.getRank() == Rank.ACE) {
					System.out.println("\n" + lastCardOfTheLine.toString() + " moved to the Foundation");
					System.out.println(
							"__________________________________________________________________________________________________________");
					foundationTableau.get(lastCardOfTheLine.getSuit().getSuitValue())
							.add(maneouvereLine.remove(maneouvereLine.size() - 1));
					if (!maneouvereLine.isEmpty()) {
						maneouvereLine.get(maneouvereLine.size() - 1).setFaceUp(true);
					}
					System.out.print("\nTALON: ");
					System.out.println(talon + "\n");
					System.out.println("REMAINING CARDS IN WASTE:");
					System.out.println(deck.getDeck() + "\n");

					System.out.println(foundationTableau + "\n");
					System.out.println("Maneouvere Tableau: ");
					for (int l = 0; l < maneouvereTableau.size(); l++) {
						System.out.println(maneouvereTableau.get(l));
					}
					checkOtherMoves(maneouvereTableau, maneouvereTableau, talon);
					moveCardToFoundation(foundationTableau, maneouvereTableau, talon);
					return;

				} else {
					for (ArrayList<Card> suitFoundation : foundationTableau) {
						if (!suitFoundation.isEmpty()) {
							Card lastCardOfTheFoundation = suitFoundation.get(suitFoundation.size() - 1);
							if (lastCardOfTheLine.getRank().getRankValue() - 1 == lastCardOfTheFoundation.getRank()
									.getRankValue()
									&& lastCardOfTheFoundation.getSuit() == lastCardOfTheLine.getSuit()) {
								System.out.println("\n" + lastCardOfTheLine.toString() + " moved to the Foundation");
								System.out.println(
										"__________________________________________________________________________________________________________");
								suitFoundation.add(maneouvereLine.remove(maneouvereLine.size() - 1));
								if (!maneouvereLine.isEmpty()) {
									maneouvereLine.get(maneouvereLine.size() - 1).setFaceUp(true);
								}
								System.out.print("\nTALON: ");
								System.out.println(talon + "\n");
								System.out.println("REMAINING CARDS IN WASTE:");
								System.out.println(deck.getDeck() + "\n");

								System.out.println(foundationTableau + "\n");
								System.out.println("Maneouvere Tableau: ");
								for (int l = 0; l < maneouvereTableau.size(); l++) {
									System.out.println(maneouvereTableau.get(l));
								}
								checkOtherMoves(maneouvereTableau, maneouvereTableau, talon);
								moveCardToFoundation(foundationTableau, maneouvereTableau, talon);
								return;
							}
						}
					}
				}
			}
		}

		if (!talon.isEmpty()) {
			Card lastCardOfTheTalon = talon.get(talon.size() - 1);
			if (lastCardOfTheTalon.getRank() == Rank.ACE) {
				System.out.println("\n" + lastCardOfTheTalon.toString() + " moved to the Foundation");
				System.out.println(
						"__________________________________________________________________________________________________________");
				foundationTableau.get(lastCardOfTheTalon.getSuit().getSuitValue()).add(talon.remove(talon.size() - 1));
				System.out.print("\nTALON: ");
				System.out.println(talon + "\n");
				System.out.println("REMAINING CARDS IN WASTE:");
				System.out.println(deck.getDeck() + "\n");

				System.out.println(foundationTableau + "\n");
				System.out.println("Maneouvere Tableau: ");
				for (int l = 0; l < maneouvereTableau.size(); l++) {
					System.out.println(maneouvereTableau.get(l));
				}
				checkOtherMoves(maneouvereTableau, maneouvereTableau, talon);
				moveCardToFoundation(foundationTableau, maneouvereTableau, talon);
				return;

			} else {
				for (ArrayList<Card> suitFoundation : foundationTableau) {
					if (!suitFoundation.isEmpty()) {
						Card lastCardOfTheFoundation = suitFoundation.get(suitFoundation.size() - 1);
						if (lastCardOfTheTalon.getRank().getRankValue() - 1 == lastCardOfTheFoundation.getRank()
								.getRankValue() && lastCardOfTheTalon.getSuit() == lastCardOfTheFoundation.getSuit()) {
							System.out.println("\n" + lastCardOfTheTalon.toString() + " moved to the Foundation");
							System.out.println(
									"__________________________________________________________________________________________________________");
							suitFoundation.add(talon.remove(talon.size() - 1));

							System.out.print("\nTALON: ");
							System.out.println(talon + "\n");
							System.out.println("REMAINING CARDS IN WASTE:");
							System.out.println(deck.getDeck() + "\n");

							System.out.println(foundationTableau + "\n");
							System.out.println("MANEOUVERE TABLEAU: ");
							for (int l = 0; l < maneouvereTableau.size(); l++) {
								System.out.println(maneouvereTableau.get(l));
							}
							checkOtherMoves(maneouvereTableau, maneouvereTableau, talon);
							moveCardToFoundation(foundationTableau, maneouvereTableau, talon);
							return;
						}
					}
				}
			}
		}
	}

	public static void moveCardFromTalonToLine(ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> maneouvereTableau, ArrayList<Card> talon) {

		for (ArrayList<Card> maneouvereLine : maneouvereTableau) {
			if (!maneouvereLine.isEmpty()) {
				Card lastCardOfTheLine = maneouvereLine.get(maneouvereLine.size() - 1);
				if (!talon.isEmpty()) {
					Card lastCardOfTheTalon = talon.get(talon.size() - 1);
					if (lastCardOfTheLine.getRank().getRankValue() - 1 == lastCardOfTheTalon.getRank().getRankValue()
							&& lastCardOfTheTalon.getColor() != lastCardOfTheLine.getColor()) {
						System.out.println("\n" + lastCardOfTheTalon.toString() + " moved to the Maneouvere Line");
						System.out.println(
								"__________________________________________________________________________________________________________");
						maneouvereLine.add(talon.remove(talon.size() - 1));

						System.out.print("\nTALON: ");
						System.out.println(talon + "\n");
						System.out.println("REMAINING CARDS IN WASTE:");
						System.out.println(deck.getDeck() + "\n");

						System.out.println(foundationTableau + "\n");
						System.out.println("Maneouvere Tableau: ");
						for (int l = 0; l < maneouvereTableau.size(); l++) {
							System.out.println(maneouvereTableau.get(l));
						}
						checkOtherMoves(maneouvereTableau, maneouvereTableau, talon);
						moveCardFromTalonToLine(foundationTableau, maneouvereTableau, talon);
						return;
					}

				}
			}
		}

	}

	public static void moveKingToEmptyLine(ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> maneouvereTableau, ArrayList<Card> talon) {

		for (ArrayList<Card> sourceManeouvereLine : maneouvereTableau) {
			for (ArrayList<Card> destinationManeouvereLine : maneouvereTableau) {
				if (!sourceManeouvereLine.isEmpty() && destinationManeouvereLine.isEmpty()) {
					Card firstFaceUpCardOfTheLine = sourceManeouvereLine
							.get(sourceManeouvereLine.size() - openCards(sourceManeouvereLine));
					if (firstFaceUpCardOfTheLine.getRank() == Rank.KING
							&& sourceManeouvereLine.get(0).getRank() != Rank.KING) {
						int indexToBeMove = (sourceManeouvereLine.size() - openCards(sourceManeouvereLine));
						for (int x = 0; x < openCards(sourceManeouvereLine); x++) {
							System.out.print("\n" + sourceManeouvereLine.get(indexToBeMove) + " ");
							destinationManeouvereLine.add(sourceManeouvereLine.remove(indexToBeMove));
						}
						System.out.println(" moved to the Empty Line");
						System.out.println(
								"__________________________________________________________________________________________________________");
						if (!sourceManeouvereLine.isEmpty()) {
							sourceManeouvereLine.get(sourceManeouvereLine.size() - 1).setFaceUp(true);
						}
						System.out.print("\nTALON: ");
						System.out.println(talon + "\n");
						System.out.println("REMAINING CARDS IN WASTE:");
						System.out.println(deck.getDeck() + "\n");

						System.out.println(foundationTableau + "\n");
						for (int l = 0; l < maneouvereTableau.size(); l++) {
							System.out.println(maneouvereTableau.get(l));
						}
						checkOtherMoves(maneouvereTableau, maneouvereTableau, talon);
						moveKingToEmptyLine(foundationTableau, maneouvereTableau, talon);
						return;

					}
				}

				if (!talon.isEmpty()) {
					if (destinationManeouvereLine.isEmpty()) {
						Card lastCardOfTheTalon = talon.get(talon.size() - 1);
						if (lastCardOfTheTalon.getRank() == Rank.KING) {
							System.out.println("\n" + lastCardOfTheTalon.toString() + " moved to the Empty Line");
							System.out.println(
									"__________________________________________________________________________________________________________");
							destinationManeouvereLine.add(talon.remove(talon.size() - 1));
							System.out.print("\nTALON: ");
							System.out.println(talon + "\n");
							System.out.println("REMAINING CARDS IN WASTE:");
							System.out.println(deck.getDeck() + "\n");

							System.out.println(foundationTableau + "\n");
							for (int l = 0; l < maneouvereTableau.size(); l++) {
								System.out.println(maneouvereTableau.get(l));
							}
							checkOtherMoves(maneouvereTableau, maneouvereTableau, talon);
							moveKingToEmptyLine(foundationTableau, maneouvereTableau, talon);
							return;
						}

					}
				}
			}

		}
	}

	public static void moveCardFromLineToLine(ArrayList<ArrayList<Card>> foundationTableau,
			ArrayList<ArrayList<Card>> maneouvereTableau, ArrayList<Card> talon) {

		for (ArrayList<Card> sourceManeouvereLine : maneouvereTableau) {
			for (ArrayList<Card> destinationManeouvereLine : maneouvereTableau) {
				if (!sourceManeouvereLine.isEmpty() && !destinationManeouvereLine.isEmpty()) {
					Card firstFaceUpCardOfTheLine = sourceManeouvereLine
							.get(sourceManeouvereLine.size() - openCards(sourceManeouvereLine));
					Card lastCardOfTheLine = destinationManeouvereLine.get(destinationManeouvereLine.size() - 1);
					if (firstFaceUpCardOfTheLine.getRank().getRankValue() == lastCardOfTheLine.getRank().getRankValue()
							- 1 && firstFaceUpCardOfTheLine.getColor() != lastCardOfTheLine.getColor()) {
						int indexToBeMove = (sourceManeouvereLine.size() - openCards(sourceManeouvereLine));
						for (int x = 0; x < openCards(sourceManeouvereLine); x++) {
							System.out.print("\n" + sourceManeouvereLine.get(indexToBeMove) + " ");
							destinationManeouvereLine.add(sourceManeouvereLine.remove(indexToBeMove));
						}
						System.out.println(" moved to Destination Line");
						System.out.println(
								"__________________________________________________________________________________________________________");
						if (!sourceManeouvereLine.isEmpty()) {
							sourceManeouvereLine.get(sourceManeouvereLine.size() - 1).setFaceUp(true);
						}
						System.out.print("\nTALON: ");
						System.out.println(talon + "\n");
						System.out.println("REMAINING CARDS IN WASTE:");
						System.out.println(deck.getDeck() + "\n");

						System.out.println(foundationTableau + "\n");
						for (int l = 0; l < maneouvereTableau.size(); l++) {
							System.out.println(maneouvereTableau.get(l));
						}
						checkOtherMoves(maneouvereTableau, maneouvereTableau, talon);
						moveCardFromLineToLine(foundationTableau, maneouvereTableau, talon);
						return;
					}
				}
			}
		}
	}

	private static void checkOtherMoves(ArrayList<ArrayList<Card>> foundationTableau, ArrayList<ArrayList<Card>> maneouvereTableau, ArrayList<Card> talon) {
		
		moveCardToFoundation(maneouvereTableau, maneouvereTableau, talon);
		moveCardFromTalonToLine(maneouvereTableau, maneouvereTableau, talon);
		moveKingToEmptyLine(maneouvereTableau, maneouvereTableau, talon);
		moveCardFromLineToLine(maneouvereTableau, maneouvereTableau, talon);
	}
		
}
	
