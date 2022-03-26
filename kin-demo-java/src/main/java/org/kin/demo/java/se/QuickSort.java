package org.kin.demo.java.se;

import java.util.Arrays;

/**
 * @author huangjianqin
 * @date 2022/3/27
 */
public class QuickSort {
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int split(int[] array, int left, int right) {
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (array[i] < array[pivot]) {
                swap(array, i, pivot);
                index++;
            }
        }
        return index - 1;
    }

    public static void sort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        int p = split(array, left, right);
        sort(array, left, p - 1);
        sort(array, p + 1, right);
    }

    public static void main(String[] args) {
        int[] array = {1, 0, 2};
        sort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));

        array = new int[]{1, 0, 2, 4, 5, 6};
        sort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }
}
