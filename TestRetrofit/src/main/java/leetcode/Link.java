package leetcode;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import Gson.Book;

public class Link {
   static Node head = init();
    public static void main(String[] args) throws IOException {

        printNode(head);
    }

    private static Node get(int index) {
        Node node = head;
        for (int i = 0; i < index; ++i) {
            node = node.next;
        }
        return node;
    }

    private static Node addAtHead(Node node) {
        node.next = head;
        head = node;
        return head;
    }

    private static void addAtTail(int index,Node add) {
        Node node = head;
        for (int i = 0; i < index; ++i) {
            node = node.next;
        }
        Node next = node.next;
        node.next = add;
        add.next = next;
    }

    private static void deleteAtTail(int index) {
        Node node = head;
        for (int i = 0; i <= index; ++i) {
            node = node.next;
        }
        Node delete = node.next;
        node.next = delete.next;

    }

    private static Node init() {
        Node head = new Node();
        head.value = 0;
        Node pre = head;
        for (int i = 1; i < 6; ++i) {
            Node next = new Node();
            next.value = i;
            pre.next = next;
            pre = next;
        }

        return head;
    }


    private static void printNode(Node head) {
        Node node = head;
        do {
            System.out.println(node);
        } while ((node = node.next) != null);
    }


    static class Node {
        int value;
        Node next;

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }
}
