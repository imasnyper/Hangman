import java.util.ArrayList;

public class Word {
    String word;
    int totalCorrectChars = 0;
    ArrayList<Character> guessedChars = new ArrayList<>();
    ArrayList<Character> correctChars = new ArrayList<>();
    ArrayList<Character> incorrectChars = new ArrayList<>();
    int guessCount = 0;

    public Word(String inputWord) {
        word = inputWord;
    }

    public void setTotalCorrectChars(char c) {
        int count = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == c) {
                count++;
            }
        }
        totalCorrectChars += count;
    }

    public boolean charGuessed(char guess) {
        for (char correctChar : correctChars) {
            if (guess == correctChar) {
                return true;
            }
        }
        for (char incorrectChar : incorrectChars) {
            if (guess == incorrectChar) {
                return true;
            }
        }
        return false;
    }
}
