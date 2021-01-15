package sortingAlgorithm.selectionSort;

import java.util.Arrays;

public class selectionSort {
    public static void main(String[] args) {
        int[] arr = {4, 2, 1, 5, 3};
        selectStort(arr);
        System.out.println(Arrays.toString(arr));
    }

    //将数组中较小的数与arr[i]交换
    private static void selectStort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if(arr[i]>arr[j]) {
                    int t = arr[j];
                    arr[j] = arr[i];
                    arr[i] = t;
                }
            }
        }
    }
}

