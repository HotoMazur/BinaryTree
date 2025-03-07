package org.example.binarytree;

import org.example.util.BTreePrinter;

import java.util.Comparator;

public class BinaryTreeImpl<T> implements BinaryTree<T>, Comparator<T> {
    private Node<T> root = null;

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

        int comparison = compare(val, root.data);
        if (comparison < 0) {
            root.left = insertNodeRec(root.left, val);
        } else if (comparison > 0) {
            root.right = insertNodeRec(root.right, val);
        } else {
            System.out.println("Value already exists");
        }

        root.height = 1 + Math.max(getHeight(root.left), getHeight(root.right));
        int balance = getBalanced(root);

        if (balance > 1 && compare(val, root.left.data) < 0) return rightRotate(root);
        if (balance < -1 && compare(val, root.right.data) > 0) return leftRotate(root);
        if (balance > 1 && compare(val, root.left.data) > 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        if (balance < -1 && compare(val, root.right.data) < 0) {
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

        int comparison = compare(val, root.data);
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

            if (balance > 1 && compare(val, root.left.data) < 0) return rightRotate(root);
            if (balance < -1 && compare(val, root.right.data) > 0) return leftRotate(root);
            if (balance > 1 && compare(val, root.left.data) > 0) {
                root.left = leftRotate(root.left);
                return rightRotate(root);
            }
            if (balance < -1 && compare(val, root.right.data) < 0) {
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

    @Override
    public int compare(T o1, T o2) {
        if (o1 == null && o2 == null) return 0;
        if (o1 == null) return -1;
        if (o2 == null) return 1;

        if (o1.getClass() != o2.getClass()) {
            throw new IllegalArgumentException("Can't compare two different classes");
        }

        if (o1 instanceof String){
            return ((String) o1).compareTo((String) o2);
        } else if (o1 instanceof Integer) {
            return ((Integer) o1).compareTo((Integer) o2);
        } else if (o1 instanceof Double){
            return ((Double) o1).compareTo((Double) o2);
        } else {
            throw new IllegalArgumentException("Can't compare " + o1.getClass());
        }
    }
}
