package tree;

import org.example.tree.implementations.BinaryTreeImpl;
import org.example.tree.implementations.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinaryTreeTests {

    private Node<Integer> root;
    private BinaryTreeImpl<Integer> tree;

    @BeforeEach
    void setup(){
        this.root = new Node<Integer>(10);
        this.tree = new BinaryTreeImpl<Integer>();

        this.root = tree.insertNode(root, 12);
        this.root = tree.insertNode(root, 13);
        this.root = tree.insertNode(root, 14);
        this.root = tree.insertNode(root, 15);
        this.root = tree.insertNode(root, 16);
    }

    @Test
    void InsertionTwentyOneInt(){
        this.root = tree.insertNode(root, 21);
        Assertions.assertEquals(14, this.root.data);
        Assertions.assertEquals(21, this.root.right.right.data);
    }

    @Test
    void InsertionEightInt(){
        this.root = tree.insertNode(root, 9);
        this.root = tree.insertNode(root, 8);
        Assertions.assertEquals(14, this.root.data);
        Assertions.assertEquals(9, this.root.left.left.data);
        Assertions.assertEquals(8, this.root.left.left.left.data);
    }

    @Test
    void DeleteNode(){
        this.root = tree.deleteNode(root, 12);
        Assertions.assertEquals(13, this.root.left.data);
    }

    @Test
    void DeleteNodeWithBalance(){
        this.root = tree.deleteNode(root, 13);
        this.root = tree.deleteNode(root, 10);
        this.root = tree.deleteNode(root, 12);
        Assertions.assertEquals(15, this.root.data);
        Assertions.assertEquals(14, this.root.left.data);
        Assertions.assertEquals(16, this.root.right.data);
    }
}
