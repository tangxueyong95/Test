package sortingAlgorithm.insertionSort;

import java.util.Arrays;

public class dividedInsertionSort {
    public static void main(String[] args) {
        int[] arr = {4, 2, 1, 5, 3};
        binInsertSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void binInsertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                int m = i - 1, n = 0;
                //找到数插入有序数中的位置
                while (n <= m) {
                    int mid = (n + m) / 2;
                    if (arr[i] < arr[mid]) {
                        m = mid - 1;
                    } else {
                        n = mid + 1;
                    }
                }
                //将数插入有序数中
                for (int j = i; j > n; j--) {
                    int t = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = t;
                }
            }
        }
    }
}
