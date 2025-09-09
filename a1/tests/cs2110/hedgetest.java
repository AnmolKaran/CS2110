package cs2110;

public class hedgetest {
    public static void main(String[] args) {
        String correctWord1 = "APPLE";
        String guessWord1 = "HEDGE";
        int[] result1 = Wordell.getColorArray(guessWord1, correctWord1);
        System.out.println("Test 1 (HEDGE vs APPLE): " + java.util.Arrays.toString(result1));

       }

}
