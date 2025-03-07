package org.example.binarytree;

import java.util.Comparator;

public interface BinaryTree<T> {

    void draw();

    void insertNode(T val, Comparator<T> comparator);

    void deleteNode(T val, Comparator<T> comparator);
}
