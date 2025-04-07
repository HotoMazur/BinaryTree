package tree;

import org.example.binarytree.BinaryTree;
import org.example.util.rest.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

public class ServerTests {
    private Server<String> server;
    @Mock
    private BinaryTree<String> tree;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() throws IOException {
        closeable = MockitoAnnotations.openMocks(this);
        server = new Server<>(tree);
    }

    @AfterEach
    void tearDown() throws Exception {
        server.stop();
        closeable.close();
    }

    @Test
    void insertNodeRest_validInput_shouldInsertNode() throws IOException, InterruptedException {
        String input = "testData";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/tree"))
                .POST(HttpRequest.BodyPublishers.ofString(input))
                .build();
        client.send(request, HttpResponse.BodyHandlers.discarding());
        verify(tree).insertNode(input);
    }

    @Test
    void deleteNodeRest_validInput_shouldDeleteNode() throws IOException, InterruptedException {
        String input = "testData";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/tree"))
                .method("DELETE", HttpRequest.BodyPublishers.ofString(input))
                .build();
        client.send(request, HttpResponse.BodyHandlers.discarding());
        verify(tree).deleteNode(input);
    }

    @Test
    void handle_invalidMethod_shouldReturnMethodNotAllowed() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/tree"))
                .PUT(HttpRequest.BodyPublishers.ofString("test"))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(405, response.statusCode());
        assertEquals("Method Not Allowed", response.body());
    }

    @Test
    void nodeInsertByFile_invalidFileContent_shouldNotInsertNodesAndReturnErrorMessage() throws IOException, InterruptedException {
        String fileContent = "--boundary\n" +
                "Content-Disposition: form-data; name=\"file\"; filename=\"data.txt\"\n" +
                "Content-Type: text/plain\n" +
                "\n" +
                "invalid line\n" +
                "--boundary--\n";

        InputStream inputStream = new ByteArrayInputStream(fileContent.getBytes(StandardCharsets.UTF_8));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/tree"))
                .header("Content-Type", "multipart/form-data; boundary=boundary")
                .POST(HttpRequest.BodyPublishers.ofString(fileContent))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
    }

    @Test
    void nodeInsertByFile_emptyFile_shouldReturnNoOperationsPerformedMessage() throws IOException, InterruptedException {
        String fileContent = "--boundary\n" +
                "Content-Disposition: form-data; name=\"file\"; filename=\"data.txt\"\n" +
                "Content-Type: text/plain\n" +
                "\n" +
                "\n" +
                "--boundary--\n";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/tree"))
                .header("Content-Type", "multipart/form-data; boundary=boundary")
                .POST(HttpRequest.BodyPublishers.ofString(fileContent))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals("Uploaded from file: \nNo operations performed", response.body());
    }
}
