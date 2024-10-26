import java.util.ArrayList;
import java.util.List;

public class ArrayExercises {

    /** Returns an array [1, 2, 3, 4, 5, 6] */
    public static int[] makeDice() {
        // TODO: Fill in this function.
        int[] dice = {1, 2, 3, 4, 5, 6};
        return dice;
    }

    /** Returns the order depending on the customer.
     *  If the customer is Circle, return ["beyti", "pizza", "hamburger", "tea"].
     *  If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     *  In any other case, return an empty String[] of size 3. */
    public static String[] takeOrder(String customer) {
        // TODO: Fill in this function.
        String[] order;
        if (customer.equals("Circle")) {
            order = new String[]{"beyti", "pizza", "hamburger", "tea"};
        } else if (customer.equals("Erik")) {
            order = new String[]{"sushi", "pasta", "avocado", "coffee"};
        } else {
            order = new String[3];
        }
        return order;
    }

    /** Returns the positive difference between the maximum element and minimum element of the given array.
     *  Assumes array is nonempty. */
    public static int findMinMax(int[] array) {
        // TODO: Fill in this function.
        int n = array.length;
        int minIndex;
        int nextSmallest;
        for (int unsortedStart = 0; unsortedStart < n-1; unsortedStart++) {
            minIndex = unsortedStart;
            for (int currentIndex = unsortedStart+1; currentIndex < n; currentIndex++) {
                if (array[currentIndex] < array[minIndex]) {
                    minIndex = currentIndex;
                }
            }
            nextSmallest = array[minIndex];
            array[minIndex] = array[unsortedStart];
            array[unsortedStart] = nextSmallest;
        }
        return array[n-1] - array[0];
    }

}
