package org.example.rest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.example.binarytree.BinaryTreeImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Server<T> {

    private final BinaryTreeImpl<T> tree;
    private HttpServer server;

    public Server(BinaryTreeImpl<T> tree) throws IOException {
        this.tree = tree;
        startServer();
    }

    private void startServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/insert", new InsertHandler());
        server.createContext("/delete", new deleteHandler());

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

    class InsertHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String requestBody = getDataFromBody(exchange);
                tree.insertNode((T) requestBody.trim());
                sendResponse(exchange, "Inserted: " + requestBody, 200);
            } else {
                sendMethodNotAllowed(exchange);
            }
        }
    }

    class deleteHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("DELETE".equals(exchange.getRequestMethod())) {
                String requestBody = getDataFromBody(exchange);
                tree.deleteNode((T) requestBody.trim());
                sendResponse(exchange, "Deleted: " + requestBody, 200);
            } else {
                sendMethodNotAllowed(exchange);
            }
        }
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
}
