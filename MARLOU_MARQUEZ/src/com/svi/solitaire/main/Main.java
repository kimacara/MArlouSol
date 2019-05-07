package com.svi.solitaire.main;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.svi.solitaire.objects.Card;
import com.svi.solitaire.objects.Deck;

public class Main {

	public static Deck deck = SolitaireMethods.createDeck();
	public static int draw = 0;

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		boolean falseInput = true;
		String choice;

		do {
			System.out.println("\nDo you want to shuffle the deck?");
			System.out.println("Type yes: If you want  to shuffle the deck");
			System.out.println("Type no: If you don't want to shuffle the deck");

			choice = input.nextLine();

			if (choice.equals("yes")) {
				deck.shuffle();
				System.out.println("\nHere is the Shuffled Deck of Cards\n");
				System.out.println(deck.getDeck().toString());
				falseInput = false;

			} else if (choice.equals("no")) {
				System.out.println("\nHere is the Game Deck of cards\n");
				System.out.println(deck.getDeck().toString());
				falseInput = false;

			} else {
				System.out.println("Type YES or NO only");
				falseInput = true;
			}

		} while (falseInput);

		falseInput = true;

		do {
			try {
				System.out.println("\nDRAW(S): \n");

				draw = input.nextInt();

				if (draw == 1 || draw == 3) {
					System.out.println(draw + " DRAW(S)");
					falseInput = false;
				} else {
					System.out.println("Only 1 or 3 draw is allowed.");
					falseInput = true;
				}
			} catch (InputMismatchException e) {
				System.out.println("\nERROR! \nOnly valid integer is allowed.");

				input.nextLine();

			}

		} while (falseInput);

		input.close();

		Main game = new Main();
		game.gamePlay();
	}

	public void gamePlay() {
		ArrayList<ArrayList<Card>> foundationTableau = SolitaireMethods.createFoundation();
		ArrayList<ArrayList<Card>> maneouvereTableau = SolitaireMethods.createManeouverStacks(deck);
		ArrayList<Card> talon = new ArrayList<Card>();
		SolitaireMethods.drawCard(deck, draw, talon);
		SolitaireMethods.sendCardToFoundation(foundationTableau, maneouvereTableau, talon);
		SolitaireMethods.sendCardFromTalonToLine(foundationTableau, maneouvereTableau, talon);
		SolitaireMethods.sendKingfromLineToEmptyLine(foundationTableau, maneouvereTableau, talon);
	}
}
