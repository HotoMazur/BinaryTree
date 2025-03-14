package org.example.binarytree;

import org.example.comparator.ComparatorFactory;
import org.example.comparator.GenericComparatorFactory;
import org.example.util.RedBlackTreePrinter;
import org.example.constant.TreeColor;

import java.util.Comparator;

public class RedBlackTreeImpl<T> implements BinaryTree<T> {
    private Node<T> root = null;
    private Comparator<T> comparator = null;

    @Override
    public void draw() {
        RedBlackTreePrinter.printNode(this.root);
    }

    @Override
    public void insertNode(T val) {
        Node<T> node = root;
        Node<T> parent = null;

        if (val != null) {
            ensureComparator(val);
        }

        while (node != null) {
            int comparison = comparator.compare(val, node.data);
            parent = node;
            if (comparison < 0) {
                node = node.left;
            } else if (comparison > 0) {
                node = node.right;
            } else {
                throw new IllegalArgumentException("BST already contains a node with value " + val);
            }
        }

        Node<T> newNode = new Node<>(val);
        newNode.color = TreeColor.RED;
        newNode.parent = parent;

        if (parent == null) {
            root = newNode;
            root.color = TreeColor.BLACK;
        } else if (comparator.compare(val, parent.data) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        fixPropertiesAfterInsertion(newNode);
    }

    private void fixPropertiesAfterInsertion(Node<T> node) {
        Node<T> parent = node.parent;

        if (parent == null) {
            return;
        }

        if (parent.color == TreeColor.BLACK) {
            return;
        }

        Node<T> grandparent = parent.parent;

        if (grandparent == null) {
            parent.color = TreeColor.BLACK;
            return;
        }

        Node<T> uncle = getUncle(parent);

        if (uncle != null && uncle.color == TreeColor.RED) {
            parent.color = TreeColor.BLACK;
            grandparent.color = TreeColor.RED;
            uncle.color = TreeColor.BLACK;

            fixPropertiesAfterInsertion(grandparent);
        } else if (parent == grandparent.left) {
            if (node == parent.right) {
                leftRotate(parent);
                parent = node;
            }
            rightRotate(grandparent);

            parent.color = TreeColor.BLACK;
            grandparent.color = TreeColor.RED;
        } else {
            if (node == parent.left) {
                rightRotate(parent);
                parent = node;
            }

            leftRotate(grandparent);

            parent.color = TreeColor.BLACK;
            grandparent.color = TreeColor.RED;
        }
    }

    private Node<T> getUncle(Node<T> parent) {
        Node<T> grandparent = parent.parent;

        if (grandparent.left == parent) {
            return grandparent.right;
        } else if (grandparent.right == parent) {
            return grandparent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    @Override
    public void deleteNode(T val) {
        if (val == null) return;

        Node<T> node = findNode(val);
        if (node == null) return;

        Node<T> movedUpNode;
        TreeColor deletedNodeColor;

        if (node.left != null && node.right != null) {
            Node<T> successor = findMinimum(node.right);
            node.data = successor.data;
            movedUpNode = deleteNodeWithZeroOrOneChild(successor);
            deletedNodeColor = successor.color;
        } else {
            movedUpNode = deleteNodeWithZeroOrOneChild(node);
            deletedNodeColor = node.color;
        }

        if (deletedNodeColor == TreeColor.BLACK && movedUpNode != null) {
            fixPropertiesAfterDelete(movedUpNode);
        }

        if (root != null) {
            root.color = TreeColor.BLACK;
        }
    }

    private Node<T> deleteNodeWithZeroOrOneChild(Node<T> node) {
        Node<T> child = (node.left != null) ? node.left : node.right;
        Node<T> parent = node.parent;

        if (parent == null) {
            root = child;
            if (root != null) {
                root.parent = null;
                root.color = TreeColor.BLACK;
            }
            return child;
        }

        if (child != null) {
            child.parent = parent;
        }

        if (parent.left == node) {
            parent.left = child;
        } else {
            parent.right = child;
        }

        return child;
    }

    private void fixPropertiesAfterDelete(Node<T> node) {
        if (node == null || node == root) {
            return;
        }

        Node<T> sibling = getSibling(node);

        if (sibling == null) return;

        if (sibling.color == TreeColor.RED) {
            handleRedSibling(node, sibling);
            sibling = getSibling(node);
        }

        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            sibling.color = TreeColor.RED;
            if (node.parent.color == TreeColor.RED) {
                node.parent.color = TreeColor.BLACK;
            } else {
                fixPropertiesAfterDelete(node.parent);
            }
        } else {
            handleBlackSiblingWithAtLeastOneRedChild(node, sibling);
        }
    }

    private Node<T> getSibling(Node<T> node) {
        if (node.parent == null) return null;
        return (node == node.parent.left) ? node.parent.right : node.parent.left;
    }

    private boolean isBlack(Node<T> node) {
        return node == null || node.color == TreeColor.BLACK;
    }

    private Node<T> findMinimum(Node<T> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private void handleRedSibling(Node<T> node, Node<T> sibling) {
        sibling.color = TreeColor.BLACK;
        node.parent.color = TreeColor.RED;

        if (node == node.parent.left) {
            leftRotate(node.parent);
        } else {
            rightRotate(node.parent);
        }
    }

    private void handleBlackSiblingWithAtLeastOneRedChild(Node<T> node, Node<T> sibling) {
        boolean nodeIsLeftChild = node == node.parent.left;

        if (nodeIsLeftChild && isBlack(sibling.right)) {
            sibling.left.color = TreeColor.BLACK;
            sibling.color = TreeColor.RED;
            rightRotate(sibling);
            sibling = node.parent.right;
        } else if (!nodeIsLeftChild && isBlack(sibling.left)) {
            sibling.right.color = TreeColor.BLACK;
            sibling.color = TreeColor.RED;
            leftRotate(sibling);
            sibling = node.parent.left;
        }

        sibling.color = node.parent.color;
        node.parent.color = TreeColor.BLACK;
        if (nodeIsLeftChild) {
            sibling.right.color = TreeColor.BLACK;
            leftRotate(node.parent);
        } else {
            sibling.left.color = TreeColor.BLACK;
            rightRotate(node.parent);
        }
    }

    private void rightRotate(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> leftChild = node.left;

        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.right = node;
        node.parent = leftChild;

        replaceParentsChild(parent, node, leftChild);
    }

    private void leftRotate(Node<T> node) {
        Node<T> parent = node.parent;
        Node<T> rightChild = node.right;

        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.left = node;
        node.parent = rightChild;

        replaceParentsChild(parent, node, rightChild);
    }

    private void replaceParentsChild(Node<T> parent, Node<T> oldChild, Node<T> newChild) {
        if (parent == null) {
            root = newChild;
        } else if (parent.left == oldChild) {
            parent.left = newChild;
        } else if (parent.right == oldChild) {
            parent.right = newChild;
        } else {
            throw new IllegalStateException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.parent = parent;
        }
    }

    private Node<T> findNode(T val) {
        if (val == null || root == null) {
            return null;
        }

        Node<T> current = root;
        while (current != null) {
            int comparison = comparator.compare(val, current.data);
            if (comparison == 0) {
                return current;
            } else if (comparison < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    private void createComparator(T val) {
        ComparatorFactory<T> factory = GenericComparatorFactory.getFactory(val);
        comparator = factory.createComparator();
    }

    private void ensureComparator(T val) {
        if (comparator == null) {
            createComparator(val);
        }
    }

    public static class Node<T> {
        public T data;
        public Node<T> left;
        public Node<T> right;
        public Node<T> parent;
        public TreeColor color;

        public Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.parent = null;
            this.color = TreeColor.RED;
        }
    }
}
