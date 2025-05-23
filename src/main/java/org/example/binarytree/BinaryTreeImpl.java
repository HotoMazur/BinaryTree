package org.example.binarytree;

import org.example.comparator.ComparatorFactory;
import org.example.comparator.GenericComparatorFactory;
import org.example.util.database.DatabaseManager;
import org.example.util.printer.AVLTreePrinter;
import org.example.util.Annotations.LogOperation;
import org.example.util.Annotations.TrackPerformance;
import org.example.util.Annotations.ValidateInput;

import java.util.Comparator;

public class BinaryTreeImpl<T> implements BinaryTree<T> {
    private Node<T> root = null;
    private Comparator<T> comparator = null;
    private Class<?> expectedType = null;
    private String treeType = "AVL";

    public static class Node<T> {
        public T data;
        public Node<T> left, right;
        public int height;
        public long id;

        public Node(T data) {
            this.data = data;
            left = right = null;
            height = 1;
        }
    }

    @Override
    public void draw() {
        AVLTreePrinter.printNode(this.root);
    }

    @ValidateInput(maxValue = 100)
    @TrackPerformance(unit = "ns", logExecution = true)
    @LogOperation(operation = "insert", logResult = true)
    @Override
    public void insertNode(T val) {
        if (val == null) {
            throw new IllegalArgumentException("Null values are not allowed");
        }
        this.root = insertNodeRec(this.root, val);
    }

    @LogOperation(operation = "insert_recursive")
    public Node<T> insertNodeRec(Node<T> root, T val) {
        root = handleInsertBaseCase(root, val);
        if (val == null) return root;
        if (expectedType == null) {
            expectedType = val.getClass();
        }

        ensureComparator(val);

        int comparison = comparator.compare(val, root.data);
        if (comparison < 0) {
            root.left = insertNodeRec(root.left, val);
        } else if (comparison > 0) {
            root.right = insertNodeRec(root.right, val);
        }

        root = makeBalance(root, val);

        Long leftId = root.left != null ? root.left.id : null;
        Long rightId = root.right != null ? root.right.id : null;
        DatabaseManager.updateNode(root.id, root.height, null, null, leftId, rightId);

        return root;
    }

    @ValidateInput()
    @TrackPerformance(unit = "ns", logExecution = true)
    @LogOperation(operation = "delete", logResult = true)
    @Override
    public void deleteNode(T val) {
        this.root = deleteNodeRec(this.root, val);
    }

    @LogOperation(operation = "delete_recursive")
    public Node<T> deleteNodeRec(Node<T> root, T val) {
        if (root == null) {
            return root;
        }

        root = performDelete(root, val);
        if (root == null) {
//            DatabaseManager.deleteNode(root.id);
        } else {
            Long leftId = root.left != null ? root.left.id : null;
            Long rightId = root.right != null ? root.right.id : null;
            DatabaseManager.updateNode(root.id, root.height, null, null, leftId, rightId);
        }

        return root != null ? makeBalance(root, val) : null;
    }

    private Node<T> minValueNode(Node<T> root) {
        Node<T> curr = root;

        while (curr.left != null) {
            curr = curr.left;
        }
        return curr;
    }

    private int getHeight(Node<T> N) {
        if (N == null) {
            return 0;
        }
        return N.height;
    }

    private Node<T> rightRotate(Node<T> y) {
        if (y == null || y.left == null) {
            return y;
        }
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

    @TrackPerformance(unit = "ns", logExecution = true)
    @LogOperation(operation = "balance")
    private Node<T> makeBalance(Node<T> root, T val) {
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

    public Node<T> getRoot() {
        return this.root;
    }

    private void createComparator(T val) {
        ComparatorFactory<T> factory = GenericComparatorFactory.getFactory(val);
        comparator = factory.createComparator();
    }

    private Node<T> handleInsertBaseCase(Node<T> root, T val) {
        if (root == null) {
            Node<T> newNode = new Node<>(val);
            DatabaseManager.insertNode(treeType, (Integer)val, 1, null, null, null, null);
            return newNode;
        }
        if (val == null) {
            System.out.println("Can't add null");
            return root;
        }
        return root;
    }

    private void ensureComparator(T val) {
        if (comparator == null) {
            createComparator(val);
        }
    }

    private Node<T> performDelete(Node<T> root, T val) {
        int comparison = comparator.compare(val, root.data);

        if (comparison < 0) {
            root.left = deleteNodeRec(root.left, val);
        } else if (comparison > 0) {
            root.right = deleteNodeRec(root.right, val);
        } else {
            root = deleteTargetNode(root);
        }
        return root;
    }

    private Node<T> deleteTargetNode(Node<T> root) {
        if (root.left == null || root.right == null) {
            return root.left != null ? root.left : root.right;
        }

        Node<T> successor = minValueNode(root.right);
        root.data = successor.data;
        root.right = deleteNodeRec(root.right, successor.data);
        return root;
    }

    public Class<?> getExpectedType() {
        return expectedType;
    }
}