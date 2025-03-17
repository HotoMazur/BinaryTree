package org.example;


import org.example.binarytree.BinaryTree;
import org.example.binarytree.BinaryTreeImpl;
import org.example.binarytree.RedBlackTreeImpl;
import org.example.rest.Server;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        Server<Integer> server = null;

        System.out.println("Choose the tree");
        System.out.println("1: AVL Tree\n2: Red Black Tree");
        String chosenTree = r.readLine();

        switch (chosenTree) {
            case "1" -> {
                BinaryTreeImpl<Integer> avlTree = new BinaryTreeImpl<>();
                startBinaryTree(r, server, avlTree);
            }
            case "2" -> {
                RedBlackTreeImpl<Integer> redBlackTree = new RedBlackTreeImpl<>();
                startBinaryTree(r, server, redBlackTree);
            }
        }
    }



    private static void startBinaryTree(BufferedReader r, Server<Integer> server, BinaryTree<Integer> tree) throws IOException {
        while (true) {
            System.out.println("Choose the operation");
            System.out.println("1: Read from file\n2: Read from terminal\n3: Start server\n4: Stop server\n5: Draw Tree\n6: Stop Program");
            String operation = r.readLine();
            switch (operation) {
                case "1" -> nodeFileHandler(r, tree);
                case "2" -> startTerminal(r, tree);
                case "3" -> server = startRestServer(server, tree);
                case "4" -> server = stopRestServer(server);
                case "5" -> tree.draw();
                case "6" -> {
                    return;
                }
            }
        }
    }

    private static void insertNodeTerminal(BufferedReader r, BinaryTree<Integer> tree) throws IOException {
        System.out.println("Write a data to put in");
        String data = r.readLine();
        try {
            Integer intData = Integer.parseInt(data);
            tree.insertNode(intData);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect format");
        }
    }

    private static void deleteNodeTerminal(BufferedReader r, BinaryTree<Integer> tree) throws IOException {
        System.out.println("Write a data to delete");
        String data = r.readLine();
        try {
            Integer intData = Integer.parseInt(data);
            tree.deleteNode(intData);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect format");
        }
    }

    private static void startTerminal(BufferedReader r, BinaryTree<Integer> tree) throws IOException {
        while (true) {
            System.out.println("1: Insert Node\n2: Delete Node\n3: Draw Tree\n4: Stop Program");
            String operation1 = r.readLine();
            switch (operation1) {
                case "1" -> insertNodeTerminal(r, tree);
                case "2" -> deleteNodeTerminal(r, tree);
                case "3" -> tree.draw();
                case "4" -> {
                    return;
                }
            }
        }
    }

    private static void insertNodeFile(String[] data, BinaryTree<Integer> tree) {
        try {
            Integer intData = Integer.parseInt(data[1]);
            tree.insertNode(intData);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect format");
        }
    }

    private static void deleteNodeFile(String[] data, BinaryTree<Integer> tree) {
        try {
            Integer intData = Integer.parseInt(data[1]);
            tree.deleteNode(intData);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect format");
        }
    }

    private static void nodeFileHandler(BufferedReader r, BinaryTree<Integer> tree) throws IOException {
        System.out.println("Write full file name");
        String filename = r.readLine();
        File file = new File(filename);
        try {
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] data = line.split(" ");
                if (data[0].equals("1")) {
                    insertNodeFile(data, tree);
                } else if (data[0].equals("2")) {
                    deleteNodeFile(data, tree);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Can't find file with this name");
        }
    }

    private static Server<Integer> startRestServer(Server<Integer> server, BinaryTree<Integer> tree) throws IOException {
        if (server == null) {
            return new Server<>(tree);
        } else {
            System.out.println("Server is already running.");
        }
        return null;
    }

    private static Server<Integer> stopRestServer(Server<Integer> server) {
        if (server != null) {
            server.stop();
            return null;
        } else {
            System.out.println("Server is not running.");
        }
        return null;
    }
}