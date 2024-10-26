public class MergeSort {


    /**
     * @param arr
     *
     * Sort the array arr using merge sort.
     * The merge sort algorithm is as follows:
     * 1. Split the collection to be sorted in half.
     * 2. Recursively call merge sort on each half.
     * 3. Merge the sorted half-lists.
     *
     */
    public static int[] sort(int[] arr) {
        if (arr.length <= 1) {
            return arr;
        }

        int midIndex = arr.length / 2;
        int[] leftHalf = new int[midIndex];
        int[] rightHalf = new int[arr.length - midIndex];

        System.arraycopy(arr, 0, leftHalf, 0, midIndex);
        System.arraycopy(arr, midIndex, rightHalf, 0, arr.length - midIndex);

        leftHalf = sort(leftHalf);
        rightHalf = sort(rightHalf);

        return merge(leftHalf, rightHalf);
    }

    /**
     * @param a
     * @param b
     *
     * Merge the sorted half-lists.
     *
     * Suggested helper method that will make it easier for you to implement merge sort.
     */
    private static int[] merge(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int i = 0; //iterator of a
        int j = 0; //iterator of b
        int k = 0; //iterator of c

        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                c[k] = a[i];
                i++;
            } else {
                c[k] = b[j];
                j++;
            }
            k++;
        }

        while (i < a.length) {
            c[k] = a[i];
            i++;
            k++;
        }

        while (j < b.length) {
            c[k] = b[j];
            j++;
            k++;
        }

        return c;
    }
}

