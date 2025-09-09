package cs2110;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * A console-based implementation of the popular "Wordle" word game.
 */
public class Wordell {

    /**
     * Returns a random entry from the valid word list in "words.txt".
     */
    static String getRandomValidWord() throws IOException {
        String[] validWords = Files.readString(Path.of("words.txt")).split("\n");
        Random rand = new Random();
        return validWords[rand.nextInt(validWords.length)];
    }

    /**
     * Returns a String that outputs a green tile containing the given character `c` to the console,
     * followed by a trailing space. Requires that `c` is an uppercase letter.
     */
    static String greenTile(char c) {
        assert 'A' <= c && c <= 'Z'; // make sure the parameter is an uppercase letter
        return "\u001B[102m " + c + " \u001B[0m ";
    }

    /**
     * Returns a String that outputs a yellow tile containing the given character `c` to the
     * console, followed by a trailing space. Requires that `c` is an uppercase letter.
     */
    static String yellowTile(char c) {
        assert 'A' <= c && c <= 'Z'; // make sure the parameter is an uppercase letter
        return "\u001B[103m " + c + " \u001B[0m ";
    }

    /**
     * Returns a String that outputs a gray tile containing the given character `c` to the console,
     * followed by a trailing space. Requires that `c` is an uppercase letter.
     */
    static String grayTile(char c) {
        assert 'A' <= c && c <= 'Z'; // make sure the parameter is an uppercase letter
        return "\u001B[47m " + c + " \u001B[0m ";
    }

    /**
     * Returns `false` and prints an explanatory message if the given `guess` is not valid,
     * otherwise returns `true`. A guess is not valid if (1) it contains a number of characters
     * besides 5, in which case the message "Your guess must have 5 letters. Try again.", or (2) it
     * contains the correct number of characters, but one of these is outside 'A'-'Z', in which case
     * the message "Your guess cannot include the character '*'. Try again." with * replaced by the
     * first illegal character should be printed. Both messages should end with a newline. Requires
     * that `guess != null`.
     */
    static boolean isValidGuess(String guess) { //to determine if a guess is even allowed. if not, this will return false.
        assert guess != null;

        // TODO 1: Implement this method according to its specifications.

        //rule 1

        if (guess.length() != 5){ //first checking the basic rule: does guess have 5 letters
            System.out.println("Your guess must have 5 letters. Try again.");
            return false;
        }
        //rule 2: making sure all characters are legal
        char[] legal_chars = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        for (int i = 0; i < guess.length(); i ++) {
            boolean found = false;
            char curr = guess.charAt(i);
            for (int j = 0; j < legal_chars.length; j++) {
                if (curr == legal_chars[j]) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Your guess cannot include the character '" + curr + "'. Try again."); //false condition
                return false;
            }
        }

        //throw new UnsupportedOperationException();
        return true;
    } //done

    /**
     * Prints the output for the given `guess`, which consists of `guess.length()` colored tiles
     * containing the characters in the guess (in order) colored according to the corresponding
     * entries in the given `colors` array (0=gray, 1=yellow, 2=green), followed by a trailing space
     * and a newline.
     */
    static void printGuessOutput(String guess, int[] colors) {
        // TODO 2: Implement this method according to its specifications.
        String fin;
        fin = "";
        for (int i = 0; i <colors.length; i ++){ //adding the correct tile to the string.
            if (colors[i] == 0){
                fin += grayTile(guess.charAt(i)) ;
            }
            else if (colors[i] == 1){
                fin += yellowTile(guess.charAt(i));
            }
            else if (colors[i] == 2){
                fin += greenTile(guess.charAt(i));
            }
        }
        System.out.println(fin);
        //throw new UnsupportedOperationException();
    } //done

    /**
     * Returns an `int[5]` array where the value at index `i` corresponds to the color of the `i`th
     * character in the given `guess` (0=gray, 1=yellow, 2=green). A character in the `guess` is
     * colored green if that character appears in the same position in the actual `word`. A
     * character is colored yellow if it appears in a different position in the actual `word` that
     * is not already associated with another yellow or green tile. Otherwise, a character is
     * colored gray. All yellow tiles with a given letter appear to the left of all gray tiles with
     * that same letter.
     */
    public static int[] getColorArray(String guess, String word) {
        // TODO 3: Implement this method according to its specifications.
        int[] finArr = new int[5]; //will eventually contain array of final colors
        int[] yellow_and_green_tiles_used_up = new int[]{-1,-1,-1,-1,-1}; //this array is meant to store only the yellow and green tiles. grey tiles will still have -1
        //above array is used later. see line 140
        for (int i = 0; i <guess.length(); i ++){
            if (word.charAt(i) == guess.charAt(i)){
                finArr[i] = 2;
                yellow_and_green_tiles_used_up[i] = i;

            }
            else{
                boolean yellow = false;
                for (int j = 0; j < word.length(); j ++){
                    if (guess.charAt(i) == word.charAt(j)&& guess.charAt(j)!=word.charAt(j)&& yellow_and_green_tiles_used_up[j] == -1){ //if the same character at a different index is yellow, checking if that same character has not already been used as a yellow tile.

                        finArr[i] = 1;
                        yellow = true;
                        yellow_and_green_tiles_used_up[j] = j;
                    }
                }
                if (!yellow){
                    finArr[i] = 0;
                }

            }

        }
        //System.out.println(finArr);
        return finArr;
        //throw new UnsupportedOperationException();
    } //done

    /**
     * Simulates a game of "Wordell" with the given target `word`, using the given Scanner `sc` to
     * get inputs (guessed words) from the user. Over the course of 6 rounds, the game should prompt
     * the user for a guess with the console output "#. Enter a guess: " where # is the current
     * valid guess number (starting from 1), read the user's console input, and convert it to
     * uppercase. If the user gives a valid guess, a color array is printed and the prompt is made
     * for the next guess. If the user gives an invalid guess, the application should prompt the
     * user for another guess with the same guess number. If the user guesses the correct word, the
     * game should print "Congratulations! You won in # guesses." with `#` replaced by the number of
     * valid guesses, and the method should return. Otherwise, if the user runs out of guesses, the
     * game should print "Better luck next time. The word was *****.", with `*****` replaced by the
     * `word`.
     */
    static void play(String word, Scanner sc) {
        // TODO 4: Implement this method according to its specifications.
        //  Use the instance method call `sc.next()` to get the guess entered by the user.
        for (int i = 1; i <7; i ++){ //going thru 6 rounds
            boolean is_good_guess = false;
            String user_guess = "";
            while(!is_good_guess){ //making sure user enters valid guess. otherwise, repeat prompt
                System.out.print(String.valueOf(i) + ". Enter a guess: ");

                user_guess = sc.next().toUpperCase();

                if (isValidGuess(user_guess)){
                    is_good_guess = true;
                }

            }
            printGuessOutput(user_guess,getColorArray(user_guess,word)); //displaying color array of word user entered
            if (user_guess.equals(word)){ //if user has reached the word
                System.out.print("Congratulations! You won in " + String.valueOf(i) + " guesses.");
                return;
            }

        }
        System.out.print("Better luck next time. The word was " + word + ".");
        //throw new UnsupportedOperationException();
    }


    /**
     * Simulates a game of "Wordell" in hard mode with the given target `word`, using the given
     * Scanner `sc` to get inputs (guessed words) from the user. Over the course of 6 rounds, the
     * game should prompt * the user for a guess with the console output "#. Enter a guess: " where
     * # is the current * valid guess number (starting from 1), read the user's console input, and
     * convert it to * uppercase. If the user gives a valid guess, a color array is printed and the
     * prompt is made for the next guess. If the user gives an invalid guess, the application should
     * prompt the user for another guess with the same guess number. In the case that a guess
     * conflicts with information from a previous guess, the message "Your guess conflicts with
     * information from the guess *****. Try again." with ***** replaced by the first guessed word
     * that causes a conflict is printed. If the user guesses the correct word, the game should
     * print "Congratulations! You won in # guesses." with the correct number of guesses filled in,
     * and the method should return. Otherwise, if the user runs out of guesses, the game should
     * print "Better luck next time. The word was *****.", with `word` in place of the *s.
     */
    static void playHardMode(String word, Scanner sc) {
        // TODO 6: Implement this method according to its specifications.
        String[] prev_guesses = new String[6]; //to store all guesses to compare color patterns with later guesses
        for (int i = 1; i <7; i ++){
            boolean is_good_guess = false; //is a good guess if it does NOT conflict with the color patterns of any previous guesses
            String user_guess = "";
            while(!is_good_guess){
                System.out.print(String.valueOf(i) + ". Enter a guess: ");

                user_guess = sc.next().toUpperCase();

                if (isValidGuess(user_guess)){
                    int[] colorArr = getColorArray(user_guess,word);
                    boolean isConflicting = false;
                    for (int j= 0; j <prev_guesses.length;j++){//going through all previous guesses

                        String prev_guess = prev_guesses[j];
                        if (prev_guess == null){
                            continue;
                        }
                        int[] prev_colorArr = getColorArray(prev_guess,user_guess); //get the color array of the previous guess compared to the current guess, which is basically treating the current guess as the solution word.
                        int[] prev_og_colorArr = getColorArray(prev_guess,word); //checking the color array of the previous guess compared to the actual word
                        if (!prev_colorArr.equals(prev_og_colorArr))
                        //if (!Arrays.equals(prev_colorArr, prev_og_colorArr)){
                            System.out.println("Your guess conflicts with information from the guess " + prev_guess +". Try again.");
                            isConflicting = true;// if the two color arrays are not equal, then theres a conflict
                        }

                    }
                    if (!isConflicting){
                        is_good_guess = true; //only if there is NOT a conflict, we reassign the value of is_good_guess to true.
                        prev_guesses[i] = user_guess; //adding in the user's guess as a previous guess, since we are done with this iteration
                        printGuessOutput(user_guess,colorArr);
                    }

                }

            }


            if (user_guess.equals(word)){
                System.out.print("Congratulations! You won in " + String.valueOf(i) + " guesses.");
                return;
            }

        }
        System.out.print("Better luck next time. The word was " + word + ".");
        //throw new UnsupportedOperationException();
    }

    /**
     * Creates a new "Wordell" game with a random target word. Uses hard mode if the "hard" command
     * line argument is supplied.
     */


    public static void main(String[] args) throws IOException {
        boolean hardMode = (args.length == 1 && args[0].equals("hard"));

        try (Scanner sc = new Scanner(System.in)) {
            if (hardMode) {
                playHardMode("ALLAY", sc);
            } else {
                //play(getRandomValidWord(), sc);
                //System.out.println("1. Enter a guess: Congratulations! You won in 1 guesses.".split("\u001B")[0] );
                //String inputs = "HELLO!\nHELLO";
                //Wordell.play("HELLO", new Scanner(inputs));
                play(getRandomValidWord(),sc);

            }
        }
    }
}
