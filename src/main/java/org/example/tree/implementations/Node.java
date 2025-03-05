package org.example.tree.implementations;

public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
    public T data;
    public Node<T> left, right;
    public int height;

    public Node(T data) {
        this.data = data;
        left = right = null;
        height = 1;
    }

    @Override
    public int compareTo(Node<T> o) {
        return this.data.compareTo(o.data);
    }
}

