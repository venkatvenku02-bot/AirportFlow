package module2;

public class KMP {

    // Creates the LPS (Longest Prefix Suffix) array
    private static int[] computeLPS(String pattern) {

        int m = pattern.length();
        int[] lps = new int[m];

        int len = 0;
        int i = 1;

        while (i < m) {

            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } 
            else {

                if (len != 0) {
                    len = lps[len - 1];
                } 
                else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    // KMP pattern searching
    public static boolean search(String text, String pattern) {

        if (pattern.length() == 0) {
            return true;
        }

        int[] lps = computeLPS(pattern);

        int i = 0;
        int j = 0;

        while (i < text.length()) {

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length()) {
                    return true;
                }
            } 
            else {

                if (j != 0) {
                    j = lps[j - 1];
                } 
                else {
                    i++;
                }
            }
        }

        return false;
    }
}