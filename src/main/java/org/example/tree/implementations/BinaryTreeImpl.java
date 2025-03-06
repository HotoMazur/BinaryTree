package org.example.tree.implementations;

import org.example.tree.interfaces.BinaryTree;

public class BinaryTreeImpl<E extends Comparable<E>> implements BinaryTree {

    @Override
    public <T extends Comparable<T>> void draw(Node<T> root) {
        BTreePrinter.printNode(root);
    }

    private <T extends Comparable<T>> void printTreeRec(Node<T> root, String prefix, boolean isLeft){

    }

    @Override
    public <T extends Comparable<T>> Node<T> insertNode(Node<T> root, T val) {
        if (root == null) {
            root = new Node<>(val);
            return root;
        }

        if (val.compareTo(root.data) < 1) {
            root.left = insertNode(root.left, val);
        } else if (val.compareTo(root.data) > 0) {
            root.right = insertNode(root.right, val);
        } else if (val.compareTo(root.data) == 0) {
            System.out.println("Value already exist");
        }

        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));

        int balance = getBalanced(root);

        if (balance > 1 && val.compareTo(root.left.data) < 0) return rightRotate(root);

        if (balance < -1 && val.compareTo(root.right.data) > 0) return leftRotate(root);

        if (balance > 1 && val.compareTo(root.left.data) > 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && val.compareTo(root.right.data) < 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    @Override
    public <T extends Comparable<T>> Node<T> deleteNode(Node<T> root, T val) {
        if (root == null){
            System.out.println("Root is null. Send correct root");
            return null;
        }

        if (val.compareTo(root.data) < 0){
            root.left = deleteNode(root.left, val);
        } else if (val.compareTo(root.data) > 0){
            root.right = deleteNode(root.right, val);
        } else{
            if ((root.left == null) || root.right == null) {
                Node<T> temp = root.left != null ? root.left : root.right;

                if (temp == null){
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node<T> temp = minValueNode(root.right);
                root.data = temp.data;
                root.right = deleteNode(root.right, temp.data);
            }
        }

        if (root == null) {
            return null;
        }

        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;

        int balance = getBalanced(root);

        if (balance > 1 && getBalanced(root.left) >= 0)
            return rightRotate(root);

        if (balance > 1 && getBalanced(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalanced(root.right) <= 0)
            return leftRotate(root);

        if (balance < -1 && getBalanced(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }


    private <T extends Comparable<T>> Node<T> minValueNode(Node<T> root){
        Node<T> curr = root;

        while (curr.left != null){
            curr = curr.left;
        }
        return curr;
    }

    private <T extends Comparable<T>> int getHeight(Node<T> N) {
        if (N == null) {
            return 0;
        }
        return N.height;
    }

    private <T extends Comparable<T>> Node<T> rightRotate(Node<T> y) {
        Node<T> x = y.left;
        Node<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    private <T extends Comparable<T>> Node<T> leftRotate(Node<T> x) {
        Node<T> y = x.right;
        Node<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));

        return y;
    }

    private <T extends Comparable<T>> int getBalanced(Node<T> N) {
        if (N == null) {
            return 0;
        }
        return getHeight(N.left) - getHeight(N.right);
    }
}
