package org.example.binarytree;

public class Node<T> {
    public T data;
    public Node<T> left, right;
    public int height;

    public Node(T data) {
        this.data = data;
        left = right = null;
        height = 1;
    }
}

