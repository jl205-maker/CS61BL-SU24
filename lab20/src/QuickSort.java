public class QuickSort {

    /**
     * @param arr
     *
     * Sort the array arr using quicksort with the 3-scan partition algorithm.
     * The quicksort algorithm is as follows:
     * 1. Select a pivot, partition array in place around the pivot.
     * 2. Recursively call quicksort on each subsection of the modified array.
     */
    public static int[] sort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
        return arr;
    }

    /**
     * @param arr
     * @param start
     * @param end
     *
     * Helper method for sort: runs quicksort algorithm on array from [start:end)
     */
    private static void quickSort(int[] arr, int start, int end) {
        if (start < end) {
            int[] partitionIndices = partition(arr, start, end);
            quickSort(arr, start, partitionIndices[0]);
            quickSort(arr, partitionIndices[1], end);
        }
    }

    /**
     * @param arr
     * @param start
     * @param end
     *
     * Partition the array in-place following the 3-scan partitioning scheme.
     * You may assume that first item is always selected as the pivot.
     *
     * Returns a length-2 int array of indices:
     * [end index of "less than" section, start index of "greater than" section]
     *
     * Most of the code for quicksort is in this function.
     */
    private static int[] partition(int[] arr, int start, int end) {
        int pivot = arr[start];
        int lt = start;     // end index of "less than" section
        int gt = end;       // start index of "greater than" section
        int i = start + 1;  // current index

        while (i <= gt) {
            if (arr[i] < pivot) {
                swap(arr, lt, i);
                lt++;
                i++;
            } else if (arr[i] > pivot) {
                swap(arr, i, gt);
                gt--;
            } else {
                i++;
            }
        }

        return new int[] { lt - 1, gt + 1 };
    }

    /**
     * Swap two elements in the array.
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {38, 27, 43, 3, 9, 82, 10};
        int[] sortedArr = sort(arr);

        System.out.println("Sorted Array: ");
        for (int num : sortedArr) {
            System.out.print(num + " ");
        }
    }
}
