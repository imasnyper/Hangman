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
                    inputWord = args[0];
                }
            } else {
                System.out.println("Enter a word for guessing: ");
                inputWord = scnr.nextLine();
            }

            String word = inputWord;

            run(word, record);

            System.out.print("Would you like to play again? (y/n): ");
            String input = scnr.nextLine();
            if (!input.equalsIgnoreCase("y")) {
                playAgain = false;
            }
        } while (playAgain);

        System.out.println("\n\n\n\n");
        System.out.println("Thank you for playing!");
        System.out.println("Your final score was: ");
        System.out.printf("Wins: %d\tLosses: %d\n", record.wins, record.losses);
        System.out.println("\n\n\n");
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

    public static void run(String word, Record record) {
        int lives = 5;
        boolean gameWon;
        ArrayList<Guess> allGuesses = new ArrayList<>();

        do {
            Guess guess = new Guess();
            printGameInfo(word, allGuesses, lives, record);

            guess.getGuess(word, allGuesses);
            allGuesses.add(guess);

            if (!word.contains(guess.strGuess())) {
                lives--;
            }

            gameWon = word.length() == getCorrectChars(word, allGuesses);
        } while (!gameWon && lives > 0);

        printGameInfo(word, allGuesses, lives, record);
        if (gameWon) {
            System.out.println("Congrats!! You Win!!");
            record.incrementWins();
        } else {
            System.out.println("You lose :(. The word was " + word);
            record.incrementLosses();
        }
    }

    public static int getCorrectChars(String word, ArrayList<Guess> allGuesses) {
        int correctChars = 0;

        for (Guess g : getGuesses(true, allGuesses)) {
            for (char c : word.toCharArray()) {
                if (g.guess == c) {
                    correctChars++;
                }
            }
        }

        return correctChars;
    }

    public static void printGameInfo(String word, ArrayList<Guess> allGuesses, int lives, Record record) {
        System.out.println("\n\n\n\n");
        System.out.printf("Wins: %d\tLosses: %d\n", record.wins, record.losses);
        printHangman(lives);
        printBoard(word, allGuesses);
        printGuessedChars(allGuesses);
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

    public static void printBoard(String word, ArrayList<Guess> allGuesses) {
        for (int i = 0; i < word.length(); i++) {
            boolean charGuessed = false;
            for (int j = 0; j < getGuesses(true, allGuesses).size(); j++) {
                if (word.charAt(i) == getGuesses(true, allGuesses).get(j).guess) {
                    System.out.print(word.charAt(i) + " ");
                    charGuessed = true;
                }
            }
            if (!charGuessed) {
                System.out.print("_ ");
            }
        }
        System.out.println();
    }

    public static ArrayList<Guess> getGuesses(boolean correct, ArrayList<Guess> guesses) {
        ArrayList<Guess> vals = new ArrayList<>();
        if (correct) {
            for (Guess guess : guesses) {
                if (guess.correct) {
                    vals.add(guess);
                }
            }
        } else {
            for (Guess guess : guesses) {
                if (!guess.correct) {
                    vals.add(guess);
                }
            }
        }
        return vals;
    }

    public static void printGuessedChars(ArrayList<Guess> allGuesses) {
        System.out.print("Guesses: ");
        for (int i = 0; i < allGuesses.size(); i++) {
            Guess guess = allGuesses.get(i);
            if (guess.correct) {
                System.out.print(ConsoleColor.Color.BLUE_BOLD);
                System.out.print(guess.guess);
                System.out.print(ConsoleColor.Color.RESET);
            } else {
                System.out.print(ConsoleColor.Color.RED_BOLD);
                System.out.print(guess.guess);
                System.out.print(ConsoleColor.Color.RESET);
            }
            if (i < allGuesses.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }
}
