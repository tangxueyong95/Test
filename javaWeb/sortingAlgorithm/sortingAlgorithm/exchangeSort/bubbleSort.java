package sortingAlgorithm.exchangeSort;

import java.util.Arrays;

public class bubbleSort {
    public static void main(String[] args) {
        int[] arr = {4, 2, 1, 3, 5};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    private static void bubbleSort(int[] arr) {
        //动态边界
        int n = arr.length-1;
        //记录数组最后进行排序的位置
        int m = 0;
        for (int i = 0; i < arr.length; i++) {
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (arr[j] > arr[j + 1]) {
                    int max = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = max;
                    flag = false;
                    m = j;
                }
            }
            n = m;
            if (flag) {
                break;
            }
        }
    }
}
