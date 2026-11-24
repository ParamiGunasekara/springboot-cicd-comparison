
package incident_reporting_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
class IncidentApiTests {

    @LocalServerPort
    private int port;

    private final HttpClient client = HttpClient.newHttpClient();

    @Test
    void healthCheck() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port
                        + "/actuator/health"))
                .GET()
                .build();

        var response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("\"UP\""));
    }

    @Test
    void createIncident() throws Exception {

        String body = """
                {
                  "title": "Server Down",
                  "description": "Server unavailable",
                  "severity": "HIGH"
                }
                """;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port
                        + "/api/incidents"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        var response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        assertEquals(201, response.statusCode());
        assertTrue(response.body().contains("\"OPEN\""));
        assertTrue(response.body().contains("\"HIGH\""));
    }

    @Test
    void rejectInvalidIncident() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port
                        + "/api/incidents"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{}"))
                .build();

        var response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        assertEquals(400, response.statusCode());
    }
}
