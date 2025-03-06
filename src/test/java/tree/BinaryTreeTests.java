package tree;

import org.example.tree.implementations.BinaryTreeImpl;
import org.example.tree.implementations.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

public class BinaryTreeTests {



    @Test
    void InsertionTwentyOneInt(){
        Comparator<Integer> intComparator = Integer::compare;
        Node<Integer> root = new Node<>(10);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12, intComparator);
        root = tree.insertNode(root, 13, intComparator);
        root = tree.insertNode(root, 14, intComparator);
        root = tree.insertNode(root, 15, intComparator);
        root = tree.insertNode(root, 16, intComparator);
        root = tree.insertNode(root, 21, intComparator);
        Assertions.assertEquals(14, root.data);
        Assertions.assertEquals(21, root.right.right.data);
    }

    @Test
    void InsertionEightInt(){
        Comparator<Integer> intComparator = Integer::compare;
        Node<Integer> root = new Node<>(10);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12, intComparator);
        root = tree.insertNode(root, 13, intComparator);
        root = tree.insertNode(root, 14, intComparator);
        root = tree.insertNode(root, 15, intComparator);
        root = tree.insertNode(root, 16, intComparator);
        root = tree.insertNode(root, 9, intComparator);
        root = tree.insertNode(root, 8, intComparator);
        Assertions.assertEquals(14, root.data);
        Assertions.assertEquals(9, root.left.left.data);
        Assertions.assertEquals(8, root.left.left.left.data);
    }

    @Test
    void DeleteNode(){
        Comparator<Integer> intComparator = Integer::compare;
        Node<Integer> root = new Node<>(10);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12, intComparator);
        root = tree.insertNode(root, 13, intComparator);
        root = tree.insertNode(root, 14, intComparator);
        root = tree.insertNode(root, 15, intComparator);
        root = tree.insertNode(root, 16, intComparator);
        root = tree.deleteNode(root, 12, intComparator);
        Assertions.assertEquals(13, root.left.data);
    }

    @Test
    void DeleteNodeWithBalance(){
        Comparator<Integer> intComparator = Integer::compare;
        Node<Integer> root = new Node<>(10);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12, intComparator);
        root = tree.insertNode(root, 13, intComparator);
        root = tree.insertNode(root, 14, intComparator);
        root = tree.insertNode(root, 15, intComparator);
        root = tree.insertNode(root, 16, intComparator);
        root = tree.deleteNode(root, 13, intComparator);
        root = tree.deleteNode(root, 10, intComparator);
        root = tree.deleteNode(root, 12, intComparator);
        root = tree.insertNode(root, 17, intComparator);
        Assertions.assertEquals(16, root.data);
        Assertions.assertEquals(14, root.left.data);
        Assertions.assertEquals(17, root.right.data);
    }

    @Test
    void InsertDoubleNode(){
        Comparator<Double> doubleComparator = Double::compare;
        Node<Double> root = new Node<>(10.2);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12.2, doubleComparator);
        root = tree.insertNode(root, 13.2, doubleComparator);
        root = tree.insertNode(root, 12.0, doubleComparator);
        root = tree.insertNode(root, 11.2, doubleComparator);
        root = tree.insertNode(root, 15.2, doubleComparator);
        Assertions.assertEquals(12.2, root.data);
        Assertions.assertEquals(11.2, root.left.data);
        Assertions.assertEquals(13.2, root.right.data);
    }

    @Test
    void DeleteDoubleNode(){
        Comparator<Double> doubleComparator = Double::compare;
        Node<Double> root = new Node<>(10.2);
        BinaryTreeImpl<Double> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12.2, doubleComparator);
        root = tree.insertNode(root, 13.2, doubleComparator);
        root = tree.insertNode(root, 12.0, doubleComparator);
        root = tree.insertNode(root, 11.2, doubleComparator);
        root = tree.insertNode(root, 15.2, doubleComparator);
        root = tree.deleteNode(root, 12.2, doubleComparator);
        Assertions.assertEquals(13.2, root.data);
        Assertions.assertEquals(11.2, root.left.data);
        Assertions.assertEquals(15.2, root.right.data);
    }

    @Test
    void InsertStringNode(){
        Comparator<String> stringComparator = Comparator.naturalOrder();
        Node<String> root = new Node<>("Hi");
        BinaryTreeImpl<String> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, "Change", stringComparator);
        root = tree.insertNode(root, "Delete", stringComparator);
        root = tree.insertNode(root, "Notch", stringComparator);
        root = tree.insertNode(root, "Abra Kadabra", stringComparator);
        Assertions.assertEquals("Delete", root.data);
        Assertions.assertEquals("Change", root.left.data);
        Assertions.assertEquals("Hi", root.right.data);
    }

    @Test
    void DeleteStringNode(){
        Comparator<String> stringComparator = Comparator.naturalOrder();
        Node<String> root = new Node<>("Hi");
        BinaryTreeImpl<String> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, "Change", stringComparator);
        root = tree.insertNode(root, "Delete", stringComparator);
        root = tree.insertNode(root, "Notch", stringComparator);
        root = tree.insertNode(root, "Abra Kadabra", stringComparator);
        root = tree.insertNode(root, "Why", stringComparator);
        root = tree.deleteNode(root, "Why", stringComparator);
        Assertions.assertEquals("Delete", root.data);
        Assertions.assertEquals("Change", root.left.data);
        Assertions.assertEquals("Notch", root.right.data);
    }

    @Test
    void AddNullNode(){
        Comparator<Integer> intComparator = Integer::compare;
        Node<Integer> root = new Node<>(10);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12, intComparator);
        root = tree.insertNode(root, 13, intComparator);
        root = tree.insertNode(root, 14, intComparator);
        root = tree.insertNode(root, 15, intComparator);
        root = tree.insertNode(root, 16, intComparator);
        root = tree.insertNode(root, 9, intComparator);
        root = tree.insertNode(root, null, intComparator);
        Assertions.assertEquals(14, root.data);
    }

    @Test
    void deleteNonExistNode(){
        Comparator<Integer> intComparator = Integer::compare;
        Node<Integer> root = new Node<>(10);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12, intComparator);
        root = tree.insertNode(root, 13, intComparator);
        root = tree.insertNode(root, 14, intComparator);
        root = tree.insertNode(root, 15, intComparator);
        root = tree.insertNode(root, 16, intComparator);
        root = tree.insertNode(root, 9, intComparator);
        root = tree.deleteNode(root, 1, intComparator);
        Assertions.assertEquals(14, root.data);
    }

    @Test
    void addExistNode(){
        Comparator<Integer> intComparator = Integer::compare;
        Node<Integer> root = new Node<>(10);
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        root = tree.insertNode(root, 12, intComparator);
        root = tree.insertNode(root, 13, intComparator);
        root = tree.insertNode(root, 14, intComparator);
        root = tree.insertNode(root, 15, intComparator);
        root = tree.insertNode(root, 16, intComparator);
        root = tree.insertNode(root, 9, intComparator);
        root = tree.insertNode(root, 10, intComparator);
        Assertions.assertEquals(14, root.data);
    }

    @Test
    void deleteFromEmptyTree(){
        Comparator<Integer> intComparator = Integer::compare;
        Node<Integer> root = null;
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();

        root = tree.deleteNode(root, 10, intComparator);
        Assertions.assertNull(root);
    }
}
