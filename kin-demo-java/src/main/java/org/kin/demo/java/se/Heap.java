package org.kin.demo.java.se;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * @author huangjianqin
 * @date 2022/3/22
 */
@SuppressWarnings("unchecked")
public class Heap<E extends Comparable<E>> {
    private int size;
    private Object[] items;

    public Heap(int capacity) {
        items = new Object[capacity];
    }

    public void add(E e) {
        if (size >= items.length) {
            Object[] items = this.items;
            this.items = Arrays.copyOf(items, items.length * 2);
        }

        if (size == 0) {
            items[0] = e;
        } else {
            siftUp(size, e);
        }
        size++;
    }

    private void siftUp(int idx, E e) {
        while (idx > 0) {
            int parent = (idx - 1) / 2;
            E parentItem = (E) items[parent];
            if (e.compareTo(parentItem) >= 0) {
                break;
            }
            items[idx] = parentItem;
            idx = parent;
        }
        items[idx] = e;
    }

    @Nullable
    public E poll() {
        E ret = (E) items[0];
        size--;
        int s = size;
        E last = (E) items[s];
        items[s] = null;
        if (s != 0) {
            siftdown(0, last);
        }
        return ret;
    }

    private void siftdown(int idx, E e) {
        int half = size / 2;
        while (idx < half) {
            int child = idx * 2 + 1;
            E childItem = (E) items[child];
            int right = child + 1;
            if (right < size && childItem.compareTo((E) items[right]) > 0) {
                child = right;
                childItem = (E) items[right];
            }
            if (e.compareTo(childItem) <= 0) {
                break;
            }

            items[idx] = childItem;
            idx = child;
        }
        items[idx] = e;
    }

    public static void main(String[] args) {
        Heap<Integer> heap = new Heap<>(4);
        heap.add(1);
        heap.add(2);
        heap.add(5);
        heap.add(-1);
        heap.add(100);
        heap.add(164);
        heap.add(-64);
        heap.add(654);
        heap.add(6004);
        heap.add(-640);

        for (int i = 0; i < 10; i++) {
            System.out.println(heap.poll());
        }
    }
}
