
/*
 * Program Name: Guess
 * Purpose:
 * Coder: Daniel Hayes
 * Date: 2020-11-17
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Guess {
    char guess;
    boolean correct;

    public Guess() {

    }

    public void getGuess(String word, ArrayList<Guess> allGuesses) {
        Scanner scnr = new Scanner(System.in);
        boolean wasGuessed;

        do {
            System.out.print("Enter a character to guess: ");
            String guessInput = scnr.nextLine();
            guess = guessInput.charAt(0);
            wasGuessed = guessed(guess, allGuesses);
            if (wasGuessed) {
                System.out.println("Already guessed " + guess + ".");
            }
        } while (wasGuessed);

        String strGuess = Character.toString(guess);

        correct = word.contains(strGuess);
    }

    public boolean guessed(char c, ArrayList<Guess> allGuesses) {
        for (Guess g : allGuesses) {
            if (c == g.guess) {
                return true;
            }
        }
        return false;
    }

    public String strGuess() {
        return Character.toString(guess);
    }
}
