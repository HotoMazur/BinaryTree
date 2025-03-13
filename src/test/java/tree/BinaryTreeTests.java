package tree;

import org.example.binarytree.BinaryTreeImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

public class BinaryTreeTests {



    @Test
    void InsertionTwentyOneInt(){
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10);
        tree.insertNode( 12);
        tree.insertNode( 13);
        tree.insertNode( 14);
        tree.insertNode( 15);
        tree.insertNode( 16);
        tree.insertNode( 21);
        BinaryTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(14, root.data);
        Assertions.assertEquals(21, root.right.right.data);
    }

    @Test
    void InsertionEightInt(){
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10);
        tree.insertNode( 12);
        tree.insertNode( 13);
        tree.insertNode( 14);
        tree.insertNode( 15);
        tree.insertNode( 16);
        tree.insertNode( 9);
        tree.insertNode( 8);
        BinaryTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(14, root.data);
        Assertions.assertEquals(9, root.left.left.data);
        Assertions.assertEquals(8, root.left.left.left.data);
    }

    @Test
    void DeleteNode(){
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10);
        tree.insertNode( 12);
        tree.insertNode( 13);
        tree.insertNode( 14);
        tree.insertNode( 15);
        tree.insertNode( 16);
        tree.deleteNode( 12);
        BinaryTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(13, root.left.data);
    }

    @Test
    void DeleteNodeWithBalance(){
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10);
        tree.insertNode( 12);
        tree.insertNode( 13);
        tree.insertNode( 14);
        tree.insertNode( 15);
        tree.insertNode( 16);
        tree.deleteNode( 13);
        tree.deleteNode( 10);
        tree.deleteNode( 12);
        tree.insertNode( 17);
        BinaryTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(15, root.data);
        Assertions.assertEquals(14, root.left.data);
        Assertions.assertEquals(16, root.right.data);
    }

    @Test
    void InsertDoubleNode(){
        BinaryTreeImpl<Double> tree = new BinaryTreeImpl<>();
        tree.insertNode(10.2);
        tree.insertNode(12.2);
        tree.insertNode( 13.2);
        tree.insertNode(12.0);
        tree.insertNode(11.2);
        tree.insertNode(15.2);
        BinaryTreeImpl.Node<Double> root = tree.getRoot();
        Assertions.assertEquals(12.2, root.data);
        Assertions.assertEquals(11.2, root.left.data);
        Assertions.assertEquals(13.2, root.right.data);
    }

    @Test
    void DeleteDoubleNode(){
        BinaryTreeImpl<Double> tree = new BinaryTreeImpl<>();
        tree.insertNode(10.2);
        tree.insertNode( 12.2);
        tree.insertNode( 13.2);
        tree.insertNode( 12.0);
        tree.insertNode( 11.2);
        tree.insertNode( 15.2);
        tree.deleteNode( 12.2);
        BinaryTreeImpl.Node<Double> root = tree.getRoot();
        Assertions.assertEquals(13.2, root.data);
        Assertions.assertEquals(11.2, root.left.data);
        Assertions.assertEquals(15.2, root.right.data);
    }

    @Test
    void InsertStringNode(){
        BinaryTreeImpl<String> tree = new BinaryTreeImpl<>();
        tree.insertNode("Hi");
        tree.insertNode( "Change");
        tree.insertNode( "Delete");
        tree.insertNode( "Notch");
        tree.insertNode( "Abra Kadabra");
        BinaryTreeImpl.Node<String> root = tree.getRoot();
        Assertions.assertEquals("Delete", root.data);
        Assertions.assertEquals("Change", root.left.data);
        Assertions.assertEquals("Hi", root.right.data);
    }

    @Test
    void DeleteStringNode(){
        BinaryTreeImpl<String> tree = new BinaryTreeImpl<>();
        tree.insertNode("Hi");
        tree.insertNode( "Change");
        tree.insertNode( "Delete");
        tree.insertNode( "Notch");
        tree.insertNode( "Abra Kadabra");
        tree.insertNode( "Why");
        tree.deleteNode( "Why");
        BinaryTreeImpl.Node<String> root = tree.getRoot();
        Assertions.assertEquals("Delete", root.data);
        Assertions.assertEquals("Change", root.left.data);
        Assertions.assertEquals("Notch", root.right.data);
    }

    @Test
    void AddNullNode(){
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10);
        tree.insertNode( 12);
        tree.insertNode( 13);
        tree.insertNode( 14);
        tree.insertNode( 15);
        tree.insertNode( 16);
        tree.insertNode( 9);
        tree.insertNode( null);
        BinaryTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(14, root.data);
    }

    @Test
    void deleteNonExistNode(){
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10);
        tree.insertNode( 12);
        tree.insertNode( 13);
        tree.insertNode( 14);
        tree.insertNode( 15);
        tree.insertNode( 16);
        tree.insertNode( 9);
        tree.deleteNode( 1);
        BinaryTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(14, root.data);
    }

    @Test
    void addExistNode(){
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10);
        tree.insertNode( 12);
        tree.insertNode( 13);
        tree.insertNode( 14);
        tree.insertNode( 15);
        tree.insertNode( 16);
        tree.insertNode( 9);
        tree.insertNode( 10);
        BinaryTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(14, root.data);
    }

    @Test
    void deleteFromEmptyTree(){
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();

        tree.deleteNode(10);
        BinaryTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertNull(root);
    }
}
