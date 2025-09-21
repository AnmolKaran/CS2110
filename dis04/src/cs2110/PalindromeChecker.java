package cs2110;

import java.sql.SQLOutput;

public class PalindromeChecker {

    /**
     * Returns whether `s` is a palindrome (ignoring case-sensitivity), meaning it reads the same
     * forwards and backwards. (Bonus challenge: ignore all non-alphanumeric characters)
     */
    static boolean isPalindrome(String s) {
        // TODO 1a-c: Implement isPalindrome() recursively according to its specifications.
        return rangeIsPalindrome(s.toLowerCase(),0,s.length()-1);
    }

    /**
     * Returns whether `s.substring(start, end)` is a palindrome. This method should be recursive and
     * should not construct any new `String` objects during its execution. Requires that `s`
     * consists of only characters from 'a'-'z', and `0 <= start <= end < s.length`.
     */
    static boolean rangeIsPalindrome(String s, int start, int end) {
        // TODO 3: Implement rangeIsPalindrome() according to its specifications.
        //System.out.println(s);
        if (s.isEmpty()){
            return true;
        }
        if(s.charAt(start) == s.charAt(end) && end-start <=1){
            return true;
        }
        if (s.charAt(start) != s.charAt(end)){
            return false;
        }
        if (s.charAt(start) == s.charAt(end)){
            return rangeIsPalindrome(s,start +1, end -1);
        }

        return false;
    }

}