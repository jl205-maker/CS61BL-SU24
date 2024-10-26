import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DistributionSorts {

    /* Destructively sorts ARR using counting sort. Assumes that ARR contains
       only 0, 1, ..., 9. */
    public static void countingSort(int[] arr) {
        int[] size = new int[10];
        for (int i : arr) {
            size[i]++;
        }
        int index = 0;
        for (int item = 0; item < size.length; item++) {
            for (int count = 0; count < size[item]; count++) {
                arr[index++] = item;
            }
        }
    }


    /* Destructively sorts ARR using LSD radix sort. */
    public static void lsdRadixSort(int[] arr) {
        int maxDigit = mostDigitsIn(arr);
        for (int d = 0; d < maxDigit; d++) {
            countingSortOnDigit(arr, d);
        }
    }

    /* A helper method for radix sort. Modifies ARR to be sorted according to
       DIGIT-th digit. When DIGIT is equal to 0, sort the numbers by the
       rightmost digit of each number. */
    private static void countingSortOnDigit(int[] arr, int digit) {
        ArrayList<Integer>[] buckets = new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (int n : arr) {
            String numString = Integer.toString(n);
            int digitToSort;
            if (digit >= numString.length()) {
                digitToSort = 0;
            } else {
                digitToSort = Character.getNumericValue(numString.charAt(numString.length() - 1 - digit));
            }
            buckets[digitToSort].add(n);
        }

        int index = 0;
        for (int i = 0; i < 10; i++) {
            for (int num : buckets[i]) {
                arr[index++] = num;
            }
        }
    }

    /* Returns the largest number of digits that any integer in ARR has. */
    private static int mostDigitsIn(int[] arr) {
        int maxDigitsSoFar = 0;
        for (int num : arr) {
            int numDigits = (int) (Math.log10(num) + 1);
            if (numDigits > maxDigitsSoFar) {
                maxDigitsSoFar = numDigits;
            }
        }
        return maxDigitsSoFar;
    }

    /* Returns a random integer between 0 and 9999. */
    private static int randomInt() {
        return (int) (10000 * Math.random());
    }

    /* Returns a random integer between 0 and 9. */
    private static int randomDigit() {
        return (int) (10 * Math.random());
    }

    private static void runCountingSort(int len) {
        int[] arr1 = new int[len];
        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = randomDigit();
        }
        System.out.println("Original array: " + Arrays.toString(arr1));
        countingSort(arr1);
        if (arr1 != null) {
            System.out.println("Should be sorted: " + Arrays.toString(arr1));
        }
    }

    public static void runLSDRadixSort(int len) {
        int[] arr2 = new int[len];
        for (int i = 0; i < arr2.length; i++) {
            arr2[i] = randomInt();
        }
        System.out.println("Original array: " + Arrays.toString(arr2));
        lsdRadixSort(arr2);
        System.out.println("Should be sorted: " + Arrays.toString(arr2));

    }

//    public static void runLSDRadixSort2(int[] arr2) {
//        System.out.println("Original array: " + Arrays.toString(arr2));
//        lsdRadixSort(arr2);
//        System.out.println("Should be sorted: " + Arrays.toString(arr2));
//
//    }

    public static void main(String[] args) {
        runCountingSort(20);
        runLSDRadixSort(3);
        runLSDRadixSort(30);
//        int[] arr2 = new int[] {13, 2, 43, 71, 72, 77, 92, 98, 9};
//        runLSDRadixSort2(arr2);
    }
}