package org.example.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.example.binarytree.BinaryTreeImpl;

public class AVLTreePrinter {
    public static <T> void printNode(BinaryTreeImpl.Node<T> root) {
        int maxLevel = AVLTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T> void printNodeInternal(List<BinaryTreeImpl.Node<T>> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || AVLTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        AVLTreePrinter.printWhitespaces(firstSpaces);

        List<BinaryTreeImpl.Node<T>> newNodes = new ArrayList<BinaryTreeImpl.Node<T>>();
        for (BinaryTreeImpl.Node<T> node : nodes) {
            if (node != null) {
                System.out.print(node.data);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            AVLTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                AVLTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    AVLTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    AVLTreePrinter.printWhitespaces(1);

                AVLTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    AVLTreePrinter.printWhitespaces(1);

                AVLTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T> int maxLevel(BinaryTreeImpl.Node<T> node) {
        if (node == null)
            return 0;

        return Math.max(AVLTreePrinter.maxLevel(node.left), AVLTreePrinter.maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }
}
