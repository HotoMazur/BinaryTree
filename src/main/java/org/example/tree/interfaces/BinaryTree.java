package org.example.tree.interfaces;

import org.example.tree.implementations.Node;

import java.util.Comparator;

public interface BinaryTree {

    <T> void draw(Node<T> root);

    <T> Node<T> insertNode(Node<T> root, T val, Comparator<T> comparator);

    <T> Node<T> deleteNode(Node<T> root, T val, Comparator<T> comparator);
}
