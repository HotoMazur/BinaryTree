package tree;

import org.example.binarytree.BinaryTreeImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AVLTreeTests {

    @Test
    void InsertDuplicateNode() {
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10);
        tree.insertNode(10);
        BinaryTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(10, root.data);
        Assertions.assertNull(root.left);
        Assertions.assertNull(root.right);
    }

    @Test
    void InsertNullNode() {
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tree.insertNode(null);
        });
        Assertions.assertEquals("Null values are not allowed", exception.getMessage());
    }

    @Test
    void DeleteRootNode() {
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10);
        tree.deleteNode(10);
        BinaryTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertNull(root);
    }

    @Test
    void DeleteNonExistentNode() {
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10);
        tree.deleteNode(20);
        BinaryTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(10, root.data);
    }

    @Test
    void InsertAndDeleteMultipleNodes() {
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        tree.insertNode(10);
        tree.insertNode(20);
        tree.insertNode(5);
        tree.deleteNode(10);
        BinaryTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(20, root.data);
        Assertions.assertEquals(5, root.left.data);
    }
}
