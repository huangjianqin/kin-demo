package org.kin.demo.java.se;

import java.util.Arrays;

/**
 * @author huangjianqin
 * @date 2022/3/26
 */
public class HeapSort {
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void heap(int[] array, int lastIdx) {
        //从非叶子节点开始遍历, 构建大顶堆
        for (int i = (lastIdx + 1) / 2 - 1; i >= 0; i--) {
            //left
            int child = i * 2 + 1;
            if (child <= lastIdx && array[i] < array[child]) {
                swap(array, i, child);
            }

            //right
            child++;
            if (child <= lastIdx && array[i] < array[child]) {
                swap(array, i, child);
            }
        }
    }

    public static void sort(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            heap(array, i);
            swap(array, 0, i);
        }
    }

    public static void main(String[] args) {
        int[] array = {1, 0, 2};
        sort(array);
        System.out.println(Arrays.toString(array));

        array = new int[]{1, 0, 2, 4, 5, 6};
        sort(array);
        System.out.println(Arrays.toString(array));
    }
}
