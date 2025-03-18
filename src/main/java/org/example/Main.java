package org.example;


import org.example.binarytree.BinaryTree;
import org.example.binarytree.BinaryTreeImpl;
import org.example.util.InputValidator;
import org.example.util.LogOperation;
import org.example.binarytree.RedBlackTreeImpl;
import org.example.rest.Server;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            Method method = tree.getClass().getMethod("insertNode", Object.class);
            InputValidator.validateInput(tree, method, new Object[]{intData});
//            tree.insertNode(intData);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect format");
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteNodeTerminal(BufferedReader r, BinaryTree<Integer> tree) throws IOException {
        System.out.println("Write a data to delete");
        String data = r.readLine();
        try {
            Integer intData = Integer.parseInt(data);
            Method method = tree.getClass().getMethod("deleteNode", Object.class);
            InputValidator.validateInput(tree, method, new Object[]{intData});
//            tree.deleteNode(intData);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect format");
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
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

    private static void insertNodeFile(Integer data, BinaryTree<Integer> tree) {
        try {
            Method method = tree.getClass().getMethod("insertNode", Object.class);
            InputValidator.validateInput(tree, method, new Object[]{data});
        } catch (NumberFormatException e) {
            System.out.println("Incorrect format");
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteNodeFile(Integer data, BinaryTree<Integer> tree) {
        try {
            Method method = tree.getClass().getMethod("deleteNode", Object.class);
            InputValidator.validateInput(tree, method, new Object[]{data});
        } catch (NumberFormatException e) {
            System.out.println("Incorrect format");
        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @LogOperation(operation = "read_file")
    private static void nodeFileHandler(BufferedReader r, BinaryTree<Integer> tree) throws IOException {
        System.out.println("Write full file name");
        String filename = r.readLine();
        if (filename.endsWith(".json")){
            handleJsonFile(filename, tree);
        } else {
            File file = new File(filename);
            try {
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    String line = myReader.nextLine();
                    String[] data = line.split(" ");
                    if (data[0].equals("1")) {
                        insertNodeFile(Integer.valueOf(data[1]), tree);
                    } else if (data[0].equals("2")) {
                        deleteNodeFile(Integer.valueOf(data[1]), tree);
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Can't find file with this name");
            }
        }
    }

    @LogOperation(operation = "read_file")
    private static void handleJsonFile(String filename, BinaryTree<Integer> tree) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                jsonContent.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonContent.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                int method = obj.getInt("method");
                int value = obj.getInt("value");

                if (method == 1) {
                    insertNodeFile(value, tree);
                } else {
                    deleteNodeFile(value, tree);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @LogOperation(operation = "start_server")
    private static Server<Integer> startRestServer(Server<Integer> server, BinaryTree<Integer> tree) throws IOException {
        if (server == null) {
            return new Server<>(tree);
        } else {
            System.out.println("Server is already running.");
        }
        return null;
    }

    @LogOperation(operation = "stop_server")
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