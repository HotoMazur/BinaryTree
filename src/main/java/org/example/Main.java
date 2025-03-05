package org.example;


import org.example.tree.implementations.BinaryTreeImpl;
import org.example.tree.implementations.Node;

public class Main {
    public static <T extends Comparable<T>> void main(String[] args) {

        Node<Integer> root = new Node<Integer>(10);

        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<Integer>();
        root = tree.insertNode(root, 12);
        root = tree.insertNode(root, 13);
        root = tree.insertNode(root, 14);
        root = tree.insertNode(root, 15);
        root = tree.insertNode(root, 16);
        root = tree.deleteNode(root, 12);
        tree.draw(root);
    }
}