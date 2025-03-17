import java.util.Scanner;
import java.util.HashSet;

public class Hangman {
    private static final String[] WORDS = {"java", "programming", "hangman", "developer", "computer"};
    private static final int MAX_ATTEMPTS = 6;
    private static String wordToGuess;
    private static char[] guessedWord;
    private static HashSet<Character> guessedLetters;
    private static int attemptsLeft;

    public static void main(String[] args) {
        initializeGame();
        playGame();
    }

    private static void initializeGame() {
        wordToGuess = WORDS[(int) (Math.random() * WORDS.length)];
        guessedWord = new char[wordToGuess.length()];
        for (int i = 0; i < guessedWord.length; i++) {
            guessedWord[i] = '_';
        }
        guessedLetters = new HashSet<>();
        attemptsLeft = MAX_ATTEMPTS;
    }

    private static void playGame() {
        Scanner scanner = new Scanner(System.in);

        while (attemptsLeft > 0 && !isWordGuessed()) {
            System.out.println("Word to guess: " + new String(guessedWord));
            System.out.println("Attempts left: " + attemptsLeft);
            System.out.print("Enter a letter: ");
            char guess = scanner.nextLine().toLowerCase().charAt(0);

            if (guessedLetters.contains(guess)) {
                System.out.println("You already guessed that letter.");
            } else {
                guessedLetters.add(guess);
                if (wordToGuess.indexOf(guess) >= 0) {
                    updateGuessedWord(guess);
                } else {
                    attemptsLeft--;
                    System.out.println("Incorrect guess.");
                }
            }
        }

        if (isWordGuessed()) {
            System.out.println("Congratulations! You guessed the word: " + wordToGuess);
        } else {
            System.out.println("Game Over! The word was: " + wordToGuess);
        }

        scanner.close();
    }

    private static void updateGuessedWord(char guess) {
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == guess) {
                guessedWord[i] = guess;
            }
        }
    }

    private static boolean isWordGuessed() {
        for (char c : guessedWord) {
            if (c == '_') {
                return false;
            }
        }
        return true;
    }
}