package tree;

import org.example.tree.implementations.BinaryTreeImpl;
import org.example.tree.implementations.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BinaryTreeTests {



    @Test
    void InsertionTwentyOneInt(){
        Node<Integer> root = new Node<>(10);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12);
        root = tree.insertNode(root, 13);
        root = tree.insertNode(root, 14);
        root = tree.insertNode(root, 15);
        root = tree.insertNode(root, 16);
        root = tree.insertNode(root, 21);
        Assertions.assertEquals(14, root.data);
        Assertions.assertEquals(21, root.right.right.data);
    }

    @Test
    void InsertionEightInt(){
        Node<Integer> root = new Node<>(10);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12);
        root = tree.insertNode(root, 13);
        root = tree.insertNode(root, 14);
        root = tree.insertNode(root, 15);
        root = tree.insertNode(root, 16);
        root = tree.insertNode(root, 9);
        root = tree.insertNode(root, 8);
        Assertions.assertEquals(14, root.data);
        Assertions.assertEquals(9, root.left.left.data);
        Assertions.assertEquals(8, root.left.left.left.data);
    }

    @Test
    void DeleteNode(){
        Node<Integer> root = new Node<>(10);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12);
        root = tree.insertNode(root, 13);
        root = tree.insertNode(root, 14);
        root = tree.insertNode(root, 15);
        root = tree.insertNode(root, 16);
        root = tree.deleteNode(root, 12);
        Assertions.assertEquals(13, root.left.data);
    }

    @Test
    void DeleteNodeWithBalance(){
        Node<Integer> root = new Node<>(10);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12);
        root = tree.insertNode(root, 13);
        root = tree.insertNode(root, 14);
        root = tree.insertNode(root, 15);
        root = tree.insertNode(root, 16);
        root = tree.deleteNode(root, 13);
        root = tree.deleteNode(root, 10);
        root = tree.deleteNode(root, 12);
        Assertions.assertEquals(15, root.data);
        Assertions.assertEquals(14, root.left.data);
        Assertions.assertEquals(16, root.right.data);
    }

    @Test
    void InsertDoubleNode(){
        Node<Double> root = new Node<>(10.2);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12.2);
        root = tree.insertNode(root, 13.2);
        root = tree.insertNode(root, 12.0);
        root = tree.insertNode(root, 11.2);
        root = tree.insertNode(root, 15.2);
        Assertions.assertEquals(12.2, root.data);
        Assertions.assertEquals(11.2, root.left.data);
        Assertions.assertEquals(13.2, root.right.data);
    }

    @Test
    void DeleteDoubleNode(){
        Node<Double> root = new Node<>(10.2);
        BinaryTreeImpl<Double> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12.2);
        root = tree.insertNode(root, 13.2);
        root = tree.insertNode(root, 12.0);
        root = tree.insertNode(root, 11.2);
        root = tree.insertNode(root, 15.2);
        root = tree.deleteNode(root, 12.2);
        Assertions.assertEquals(13.2, root.data);
        Assertions.assertEquals(11.2, root.left.data);
        Assertions.assertEquals(15.2, root.right.data);
    }

    @Test
    void InsertStringNode(){
        Node<String> root = new Node<>("Hi");
        BinaryTreeImpl<String> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, "Change");
        root = tree.insertNode(root, "Delete");
        root = tree.insertNode(root, "Notch");
        root = tree.insertNode(root, "Abra Kadabra");
        Assertions.assertEquals("Delete", root.data);
        Assertions.assertEquals("Change", root.left.data);
        Assertions.assertEquals("Hi", root.right.data);
    }

    @Test
    void DeleteStringNode(){
        Node<String> root = new Node<>("Hi");
        BinaryTreeImpl<String> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, "Change");
        root = tree.insertNode(root, "Delete");
        root = tree.insertNode(root, "Notch");
        root = tree.insertNode(root, "Abra Kadabra");
        root = tree.insertNode(root, "Why");
        root = tree.deleteNode(root, "Why");
        Assertions.assertEquals("Delete", root.data);
        Assertions.assertEquals("Change", root.left.data);
        Assertions.assertEquals("Notch", root.right.data);
    }

}
