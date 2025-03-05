package org.example.tree.interfaces;

import org.example.tree.implementations.Node;

public interface BinaryTreeInterface {

    <T extends Comparable<T>> void draw(Node<T> root);

    <T extends Comparable<T>> Node<T> insertNode(Node<T> root, T val);

    <T extends Comparable<T>> Node<T> deleteNode(Node<T> root, T val);
}
