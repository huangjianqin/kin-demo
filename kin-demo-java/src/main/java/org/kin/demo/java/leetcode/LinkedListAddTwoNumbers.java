package org.kin.demo.java.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 两链表求和, 数值正向存储
 * 输入：(6 -> 1 -> 7) + (2 -> 9 -> 5)，即617 + 295 输出：9 -> 1 -> 2，即912
 *
 * @author huangjianqin
 * @date 2022/3/27
 */
public class LinkedListAddTwoNumbers {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(6, new ListNode(1, new ListNode(7, new ListNode(5))));
        ListNode l2 = new ListNode(2, new ListNode(9, new ListNode(5)));
        ListNode ans = addTwoNumbers(l1, l2);
        while (ans != null) {
            System.out.println(ans.val);
            ans = ans.next;
        }
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Deque<Integer> s1 = new ArrayDeque<>();
        push(s1, l1);

        Deque<Integer> s2 = new ArrayDeque<>();
        push(s2, l2);

        ListNode ans = null;
        int up = 0;
        while (!s1.isEmpty() || !s2.isEmpty()) {
            Integer v1 = !s1.isEmpty() ? s1.pop() : 0;
            Integer v2 = !s2.isEmpty() ? s2.pop() : 0;
            int sum = v1 + v2 + up;
            up = sum / 10;
            ans = new ListNode(sum % 10, ans);
        }

        if (up > 0) {
            ans = new ListNode(up, ans);
        }

        return ans;
    }

    public static void push(Deque<Integer> stack, ListNode node) {
        while (node != null) {
            stack.push(node.val);
            node = node.next;
        }
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
