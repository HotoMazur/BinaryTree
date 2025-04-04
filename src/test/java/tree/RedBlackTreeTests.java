package tree;

import org.example.binarytree.RedBlackTreeImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RedBlackTreeTests {
    @Test
    void InsertDuplicateNode() {
        RedBlackTreeImpl<Integer> tree = new RedBlackTreeImpl<>();
        tree.insertNode(10);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tree.insertNode(10);
        });
        Assertions.assertEquals("BST already contains a node with value 10", exception.getMessage());
    }

    @Test
    void InsertNullNode() {
        RedBlackTreeImpl<Integer> tree = new RedBlackTreeImpl<>();
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tree.insertNode(null);
        });
        Assertions.assertEquals("Null values are not allowed", exception.getMessage());
    }

    @Test
    void DeleteRootNode() {
        RedBlackTreeImpl<Integer> tree = new RedBlackTreeImpl<>();
        tree.insertNode(10);
        tree.deleteNode(10);
        RedBlackTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertNull(root);
    }

    @Test
    void DeleteNonExistentNode() {
        RedBlackTreeImpl<Integer> tree = new RedBlackTreeImpl<>();
        tree.insertNode(10);
        tree.deleteNode(20);
        RedBlackTreeImpl.Node<Integer> root = tree.getRoot();
        Assertions.assertEquals(10, root.data);
    }
}
