package tree;

import org.example.binarytree.BinaryTreeImpl;
import org.example.binarytree.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

public class BinaryTreeTests {



    @Test
    void InsertionTwentyOneInt(){
        Comparator<Integer> intComparator = Integer::compare;
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10, intComparator);
        tree.insertNode( 12, intComparator);
        tree.insertNode( 13, intComparator);
        tree.insertNode( 14, intComparator);
        tree.insertNode( 15, intComparator);
        tree.insertNode( 16, intComparator);
        tree.insertNode( 21, intComparator);
        Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(14, root.data);
        Assertions.assertEquals(21, root.right.right.data);
    }

    @Test
    void InsertionEightInt(){
        Comparator<Integer> intComparator = Integer::compare;
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10, intComparator);
        tree.insertNode( 12, intComparator);
        tree.insertNode( 13, intComparator);
        tree.insertNode( 14, intComparator);
        tree.insertNode( 15, intComparator);
        tree.insertNode( 16, intComparator);
        tree.insertNode( 9, intComparator);
        tree.insertNode( 8, intComparator);
        Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(14, root.data);
        Assertions.assertEquals(9, root.left.left.data);
        Assertions.assertEquals(8, root.left.left.left.data);
    }

    @Test
    void DeleteNode(){
        Comparator<Integer> intComparator = Integer::compare;
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10, intComparator);
        tree.insertNode( 12, intComparator);
        tree.insertNode( 13, intComparator);
        tree.insertNode( 14, intComparator);
        tree.insertNode( 15, intComparator);
        tree.insertNode( 16, intComparator);
        tree.deleteNode( 12, intComparator);
        Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(13, root.left.data);
    }

    @Test
    void DeleteNodeWithBalance(){
        Comparator<Integer> intComparator = Integer::compare;
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10, intComparator);
        tree.insertNode( 12, intComparator);
        tree.insertNode( 13, intComparator);
        tree.insertNode( 14, intComparator);
        tree.insertNode( 15, intComparator);
        tree.insertNode( 16, intComparator);
        tree.deleteNode( 13, intComparator);
        tree.deleteNode( 10, intComparator);
        tree.deleteNode( 12, intComparator);
        tree.insertNode( 17, intComparator);
        Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(16, root.data);
        Assertions.assertEquals(14, root.left.data);
        Assertions.assertEquals(17, root.right.data);
    }

    @Test
    void InsertDoubleNode(){
        Comparator<Double> doubleComparator = Double::compare;
        BinaryTreeImpl<Double> tree = new BinaryTreeImpl<>();
        tree.insertNode(10.2, doubleComparator);
        tree.insertNode(12.2, doubleComparator);
        tree.insertNode( 13.2, doubleComparator);
        tree.insertNode(12.0, doubleComparator);
        tree.insertNode(11.2, doubleComparator);
        tree.insertNode(15.2, doubleComparator);
        Node<Double> root = tree.getRoot();
        Assertions.assertEquals(12.2, root.data);
        Assertions.assertEquals(11.2, root.left.data);
        Assertions.assertEquals(13.2, root.right.data);
    }

    @Test
    void DeleteDoubleNode(){
        Comparator<Double> doubleComparator = Double::compare;
        BinaryTreeImpl<Double> tree = new BinaryTreeImpl<>();
        tree.insertNode(10.2, doubleComparator);
        tree.insertNode( 12.2, doubleComparator);
        tree.insertNode( 13.2, doubleComparator);
        tree.insertNode( 12.0, doubleComparator);
        tree.insertNode( 11.2, doubleComparator);
        tree.insertNode( 15.2, doubleComparator);
        tree.deleteNode( 12.2, doubleComparator);
        Node<Double> root = tree.getRoot();
        Assertions.assertEquals(13.2, root.data);
        Assertions.assertEquals(11.2, root.left.data);
        Assertions.assertEquals(15.2, root.right.data);
    }

    @Test
    void InsertStringNode(){
        Comparator<String> stringComparator = Comparator.naturalOrder();
        BinaryTreeImpl<String> tree = new BinaryTreeImpl<>();
        tree.insertNode("Hi", stringComparator);
        tree.insertNode( "Change", stringComparator);
        tree.insertNode( "Delete", stringComparator);
        tree.insertNode( "Notch", stringComparator);
        tree.insertNode( "Abra Kadabra", stringComparator);
        Node<String> root = tree.getRoot();
        Assertions.assertEquals("Delete", root.data);
        Assertions.assertEquals("Change", root.left.data);
        Assertions.assertEquals("Hi", root.right.data);
    }

    @Test
    void DeleteStringNode(){
        Comparator<String> stringComparator = Comparator.naturalOrder();
        BinaryTreeImpl<String> tree = new BinaryTreeImpl<>();
        tree.insertNode("Hi", stringComparator);
        tree.insertNode( "Change", stringComparator);
        tree.insertNode( "Delete", stringComparator);
        tree.insertNode( "Notch", stringComparator);
        tree.insertNode( "Abra Kadabra", stringComparator);
        tree.insertNode( "Why", stringComparator);
        tree.deleteNode( "Why", stringComparator);
        Node<String> root = tree.getRoot();
        Assertions.assertEquals("Delete", root.data);
        Assertions.assertEquals("Change", root.left.data);
        Assertions.assertEquals("Notch", root.right.data);
    }

    @Test
    void AddNullNode(){
        Comparator<Integer> intComparator = Integer::compare;
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10, intComparator);
        tree.insertNode( 12, intComparator);
        tree.insertNode( 13, intComparator);
        tree.insertNode( 14, intComparator);
        tree.insertNode( 15, intComparator);
        tree.insertNode( 16, intComparator);
        tree.insertNode( 9, intComparator);
        tree.insertNode( null, intComparator);
        Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(14, root.data);
    }

    @Test
    void deleteNonExistNode(){
        Comparator<Integer> intComparator = Integer::compare;
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10, intComparator);
        tree.insertNode( 12, intComparator);
        tree.insertNode( 13, intComparator);
        tree.insertNode( 14, intComparator);
        tree.insertNode( 15, intComparator);
        tree.insertNode( 16, intComparator);
        tree.insertNode( 9, intComparator);
        tree.deleteNode( 1, intComparator);
        Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(14, root.data);
    }

    @Test
    void addExistNode(){
        Comparator<Integer> intComparator = Integer::compare;
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10, intComparator);
        tree.insertNode( 12, intComparator);
        tree.insertNode( 13, intComparator);
        tree.insertNode( 14, intComparator);
        tree.insertNode( 15, intComparator);
        tree.insertNode( 16, intComparator);
        tree.insertNode( 9, intComparator);
        tree.insertNode( 10, intComparator);
        Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(14, root.data);
    }

    @Test
    void deleteFromEmptyTree(){
        Comparator<Integer> intComparator = Integer::compare;
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();

        tree.deleteNode(10, intComparator);
        Node<Integer> root = tree.getRoot();
        Assertions.assertNull(root);
    }
}
