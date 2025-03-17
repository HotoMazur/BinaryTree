package org.example.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.binarytree.BinaryTree;
import org.example.binarytree.BinaryTreeImpl;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Server<T> {

    private final BinaryTree<T> tree;
    private HttpServer server;

    public Server(BinaryTree<T> tree) throws IOException {
        this.tree = tree;
        startServer();
    }

    private void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/api/v1/tree", new TreeHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("Server stopped.");
        }
    }

    class TreeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
                if (contentType != null && contentType.startsWith("multipart/form-data")) {
                    nodeInsertByFile(exchange);
                }
                insertNodeRest(exchange);
            } else if ("DELETE".equals(exchange.getRequestMethod())) {
                deleteNodeRest(exchange);
            } else {
                sendMethodNotAllowed(exchange);
            }
        }

        private void insertNodeRest(HttpExchange exchange) throws IOException {
            String requestBody = getDataFromBody(exchange);
            tree.insertNode((T) requestBody.trim());
            sendResponse(exchange, "Inserted: " + requestBody, 200);
        }

        private void deleteNodeRest(HttpExchange exchange) throws IOException {
            String requestBody = getDataFromBody(exchange);
            tree.deleteNode((T) requestBody.trim());
            sendResponse(exchange, "Deleted: " + requestBody, 200);
        }
    }

    private void nodeInsertByFile(HttpExchange exchange) throws IOException {
        StringBuilder responseBuilder = new StringBuilder();
        InputStream is = exchange.getRequestBody();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder fileContent = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            fileContent.append(line).append("\n");
        }

        String fileData = extractFileContent(fileContent.toString());
        Scanner scanner = new Scanner(fileData);
        while (scanner.hasNextLine()) {
            tryHandleFileData(scanner, responseBuilder);
        }
        scanner.close();
        String requestBody = responseBuilder.length() > 0 ? responseBuilder.toString() : "No operations performed";
        sendResponse(exchange, "Uploaded from file: \n" + requestBody, 200);
    }

    private String extractFileContent(String rawContent) {
        String[] lines = rawContent.split("\n");
        StringBuilder fileContent = new StringBuilder();
        boolean contentStarted = false;
        for (String line : lines) {
            if (line.startsWith("--") && contentStarted) {
                break;
            }
            if (contentStarted) {
                fileContent.append(line).append("\n");
            }
            if (line.trim().isEmpty()) {
                contentStarted = true;
            }
        }
        return fileContent.toString().trim();
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void sendMethodNotAllowed(HttpExchange exchange) throws IOException {
        sendResponse(exchange, "Method Not Allowed", 405);
    }

    private String getDataFromBody(HttpExchange exchange) throws IOException {
        return new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
    }

    private void tryHandleFileData(Scanner scanner, StringBuilder responseBuilder){
        String fileLine = scanner.nextLine().trim();
        if (fileLine.isEmpty()) return;
        String[] data = fileLine.split(" ");
        if (data.length < 2) {
            responseBuilder.append("Invalid line: ").append(fileLine).append("\n");
            return;
        }
        try {
            Integer value = Integer.parseInt(data[1]);
            if ("1".equals(data[0])) {
                tree.insertNode((T) value);
                responseBuilder.append("Inserted: ").append(value).append("\n");
            } else if ("2".equals(data[0])) {
                tree.deleteNode((T) value);
                responseBuilder.append("Deleted: ").append(value).append("\n");
            } else {
                responseBuilder.append("Unknown operation: ").append(fileLine).append("\n");
            }
        } catch (NumberFormatException e) {
            responseBuilder.append("Invalid number in line: ").append(fileLine).append("\n");
        }
    }
}
