import java.util.HashMap;


public class CodingChallenges {

    /**
     * Return the missing number from an array of length N containing all the
     * values from 0 to N except for one missing number.
     */
    public static int missingNumber(int[] values) {
        int n = values.length;
        int[] allValues = new int[n + 1];
        for (int num : values) {
            allValues[num]++;
        }
        int frequency = 1;
        int skipped = 0;
        for (int i = 0; i < allValues.length; i++) {
            if (allValues[i] < frequency) {
                skipped = i;
            }
        }
        return skipped;
    }

    /**
     * Returns true if and only if s1 is a permutation of s2. s1 is a
     * permutation of s2 if it has the same number of each character as s2.
     */
    public static boolean isPermutation(String s1, String s2) {
        char[] s1Char = s1.toCharArray();
        char[] s2Char = s2.toCharArray();
        int s1Length = s1Char.length;
        int s2Length = s2Char.length;
        //if the two strings has different numbers of chars, they are not permutations.
        if (s1Length != s2Length) {
            return false;
        }
        //tracks the frequency of s1 characters
        HashMap<Character, Integer> s1dict = strCharDict(s1);
        //tracks the frequency of s2 characters
        HashMap<Character, Integer> s2dict = strCharDict(s2);
        //compare the two Hashmaps
        boolean isPerm = true;
        for (char k1 : s1dict.keySet()) {
            if (!s2dict.containsKey(k1) || !s1dict.get(k1).equals(s2dict.get(k1))) {
                isPerm = false;
            }
        }
        return isPerm;
    }

    private static HashMap<Character, Integer> strCharDict(String aString) {
        char[] strChar = aString.toCharArray();
        HashMap<Character, Integer> dict = new HashMap<>();
        for (char c : strChar) {
            if (dict.containsKey(c)) {
                dict.put(c, dict.get(c) + 1);
            } else {
                dict.put(c, 1);
            }
        }
        return dict;
    }
}
