package org.example.binarytree;

import org.example.comparator.ComparatorFactory;
import org.example.comparator.GenericComparatorFactory;
import org.example.comparator.IntegerComparator;
import org.example.comparator.StringComparator;
import org.example.util.BTreePrinter;

import java.util.Comparator;

public class BinaryTreeImpl<T> implements BinaryTree<T> {
    private Node<T> root = null;
    private Comparator<T> comparator = null;

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

    @Override
    public void draw() {
        BTreePrinter.printNode(this.root);
    }

    @Override
    public void insertNode(T val) {
        this.root = insertNodeRec(this.root, val);
    }


    public Node<T> insertNodeRec(Node<T> root, T val) {
        if (root == null) {
            return new Node<>(val);
        }

        if (val == null) {
            System.out.println("Can't add null");
            return root;
        }

        if (comparator == null){
            createComparator(val);
        }

        int comparison = comparator.compare(val, root.data);
        if (comparison < 0) {
            root.left = insertNodeRec(root.left, val);
        } else if (comparison > 0) {
            root.right = insertNodeRec(root.right, val);
        } else {
            System.out.println("Value already exists");
        }

        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));
        int balance = getBalanced(root);

        if (balance > 1 && comparator.compare(val, root.left.data) < 0) return rightRotate(root);
        if (balance < -1 && comparator.compare(val, root.right.data) > 0) return leftRotate(root);
        if (balance > 1 && comparator.compare(val, root.left.data) > 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        if (balance < -1 && comparator.compare(val, root.right.data) < 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    @Override
    public void deleteNode(T val) {
        this.root = deleteNodeRec(this.root, val);
    }

    public Node<T> deleteNodeRec(Node<T> root, T val) {
        if (root == null) {
            return null;
        }

        int comparison = comparator.compare(val, root.data);
        if (comparison < 0) {
            root.left = deleteNodeRec(root.left, val);
        } else if (comparison > 0) {
            root.right = deleteNodeRec(root.right, val);
        } else {
            if ((root.left == null) || root.right == null) {
                Node<T> temp = root.left != null ? root.left : root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                Node<T> temp = minValueNode(root.right);
                root.data = temp.data;
                root.right = deleteNodeRec(root.right, temp.data);
            }


            if (root == null) {
                return null;
            }

            root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;

            int balance = getBalanced(root);

            if (balance > 1 && comparator.compare(val, root.left.data) < 0) return rightRotate(root);
            if (balance < -1 && comparator.compare(val, root.right.data) > 0) return leftRotate(root);
            if (balance > 1 && comparator.compare(val, root.left.data) > 0) {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }
            if (balance < -1 && comparator.compare(val, root.right.data) < 0) {
                root.right = rightRotate(root.right);
                return leftRotate(root);
            }
        }

        return root;
    }


    private Node<T> minValueNode(Node<T> root) {
        Node<T> curr = root;

        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    private <T> int getHeight(Node<T> N) {
        if (N == null) {
            return 0;
        }
        return N.height;
    }

    private Node<T> rightRotate(Node<T> y) {
        Node<T> x = y.left;
        Node<T> T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    private Node<T> leftRotate(Node<T> x) {
        Node<T> y = x.right;
        Node<T> T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));

        return y;
    }

    private int getBalanced(Node<T> N) {
        if (N == null) {
            return 0;
        }
        return getHeight(N.left) - getHeight(N.right);
    }

    public Node<T> getRoot() {
        return this.root;
    }

    private void createComparator(T val){
        ComparatorFactory factory = GenericComparatorFactory.getFactory(val);
        comparator = factory.createComparator();
    }

}
