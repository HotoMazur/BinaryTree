package org.example.binarytree;

import java.util.Comparator;

public interface BinaryTree<T> {

    void draw();

    void insertNode(T val);

    void deleteNode(T val);
}
