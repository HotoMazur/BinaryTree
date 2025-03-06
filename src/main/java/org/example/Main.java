package org.example;


import org.example.tree.implementations.BinaryTreeImpl;
import org.example.tree.implementations.Node;

import java.io.*;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static <T> void main(String[] args) throws IOException {

        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        Node<Integer> root = null;
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<Integer>();
        Comparator<Integer> intComparator = Integer::compare;
        
        while (true) {
            System.out.println("Choose the operation");
            System.out.println("1: Insert Node\n2: Delete Node\n3: Draw Tree\n4: Read from file\n5: Stop Program");
            String operation = r.readLine();
            switch (operation) {
                case "1" -> {
                    System.out.println("Write a data to put in");
                    String data = r.readLine();
                    try {
                        Integer intData = Integer.parseInt(data);
                        root = tree.insertNode(root, intData, intComparator);
                    } catch (NumberFormatException e) {
                        System.out.println("Incorrect format");
                    }
                }
                case "2" -> {
                    System.out.println("Write a data to delete");
                    String data = r.readLine();
                    try {
                        Integer intData = Integer.parseInt(data);
                        root = tree.deleteNode(root, intData, intComparator);
                    } catch (NumberFormatException e) {
                        System.out.println("Incorrect format");
                    }
                }
                case "3" -> tree.draw(root);
                case "4" -> {
                    System.out.println("Write file name");
                    String filename = r.readLine();
                    File file = new File("src/main/resources/" + filename);
                    try {
                        Scanner myReader = new Scanner(file);
                        while (myReader.hasNextLine()) {
                            String line = myReader.nextLine();
                            String[] data = line.split(" ");
                            if (data[0].equals("1")) {
                                try {
                                    Integer intData = Integer.parseInt(data[1]);
                                    root = tree.insertNode(root, intData, intComparator);
                                } catch (NumberFormatException e) {
                                    System.out.println("Incorrect format");
                                }
                            } else if (data[0].equals("2")) {
                                try {
                                    Integer intData = Integer.parseInt(data[1]);
                                    root = tree.deleteNode(root, intData, intComparator);
                                } catch (NumberFormatException e) {
                                    System.out.println("Incorrect format");
                                }
                            }
                        }
                    } catch (FileNotFoundException e){
                        System.out.println("Can't find file with this name");
                    }
                }
                case "5"-> {return;}
            }
        }


        
    }
}