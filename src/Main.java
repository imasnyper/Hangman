import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        String inputWord;
        boolean playAgain = true;
        Record record = new Record();

        do {

            if (args.length > 0) {
                if (args[0].equals("-r")) {
                    inputWord = randomWord();
                } else {
                    System.out.println("Enter a word for guessing: ");
                    inputWord = scnr.nextLine();
                }
            } else {
                System.out.println("Enter a word for guessing: ");
                inputWord = scnr.nextLine();
            }

            Word word = new Word(inputWord);

            run(word, record);

            System.out.print("Would you like to play again? (y/n): ");
            String input = scnr.nextLine();
            if (!input.equalsIgnoreCase("y")) {
                playAgain = false;
            }
        } while (playAgain);

        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Thank you for playing!");
        System.out.println("Your final score was: ");
        System.out.printf("Wins: %d\tLosses: %d\n", record.wins, record.losses);
        System.out.println("\n\n\n\n\n\n\n");
    }

    public static String randomWord() {
        ArrayList<String> wordList = new ArrayList<>();
        Scanner wlReader = null;

        try {
            File wordListFile = new File("WordList.txt");
            wlReader = new Scanner(wordListFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error: The file could not be found.");
            System.exit(1);
        }

        while (wlReader.hasNextLine()) {
            wordList.add(wlReader.nextLine());
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(0, wordList.size());

        return wordList.get(randomIndex);
    }

    public static void run(Word word, Record record) {
        char guess;
        int lives = 5;
        boolean gameWon;

        do {
            printGameInfo(word, lives, record);

            guess = getGuess(word);

            String strGuess = Character.toString(guess);
            word.guessedChars.add(guess);

            if (word.word.contains(strGuess)) {
                word.correctChars.add(guess);
                word.setTotalCorrectChars(guess);
            } else {
                lives--;
                word.incorrectChars.add(guess);
            }
            gameWon = word.word.length() == word.totalCorrectChars;
        } while (guess != '!' && !gameWon && lives > 0);

        printGameInfo(word, lives, record);
        if (gameWon) {
            System.out.println("Congrats!! You Win!!");
            record.incrementWins();
        } else {
            System.out.println("You lose :(. The word was " + word.word);
            record.incrementLosses();
        }
    }

    public static char getGuess(Word word) {
        Scanner scnr = new Scanner(System.in);
        char guess;

        do {
            System.out.print("Enter a character to guess: ");
            String guessInput = scnr.nextLine();
            guess = guessInput.charAt(0);
            if (word.charGuessed(guess)) {
                System.out.println("Already guessed " + guess + ".");
            }
        } while (word.charGuessed(guess));

        word.guessCount++;

        return guess;
    }

    public static void printGameInfo(Word word, int lives, Record record) {
        System.out.println("\n\n\n\n\n\n\n");
        System.out.printf("Wins: %d\tLosses: %d\n", record.wins, record.losses);
        System.out.println("Word length: " + word.word.length());
        printHangman(lives);
        printBoard(word);
        printGuessedChars(word);
        System.out.println();
    }

    public static void printHangman(int lives) {
        switch (lives) {
            case 5 -> System.out.println(HangmanBoards.FIVE_LIVES);
            case 4 -> System.out.println(HangmanBoards.FOUR_LIVES);
            case 3 -> System.out.println(HangmanBoards.THREE_LIVES);
            case 2 -> System.out.println(HangmanBoards.TWO_LIVES);
            case 1 -> System.out.println(HangmanBoards.ONE_LIFE);
            case 0 -> System.out.println(HangmanBoards.ZERO_LIVES);
        }
    }

    public static void printBoard(Word word) {
        for (int i = 0; i < word.word.length(); i++) {
            boolean charGuessed = false;
            for (int j = 0; j < word.correctChars.size(); j++) {
                if (word.word.charAt(i) == word.correctChars.get(j)) {
                    System.out.print(word.word.charAt(i) + " ");
                    charGuessed = true;
                }
            }
            if (!charGuessed) {
                System.out.print("_ ");
            }
        }
        System.out.println();
    }

    public static void printGuessedChars(Word word) {
        System.out.print("Guesses: ");
        for (int i = 0; i < word.guessedChars.size(); i++) {
            System.out.print(word.guessedChars.get(i));
            if (i < word.guessedChars.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        System.out.print("Incorrect Characters: ");
        int incorrectLength = word.incorrectChars.size();
        for (int i = 0; i < incorrectLength; i++) {
            System.out.print(word.incorrectChars.get(i));
            if (i < incorrectLength - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        System.out.print("Correct Characters: ");
        int correctLength = word.correctChars.size();
        for (int i = 0; i < correctLength; i++) {
            System.out.print(word.correctChars.get(i));
            if (i < correctLength - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
