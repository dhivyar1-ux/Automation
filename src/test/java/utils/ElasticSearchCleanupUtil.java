package utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ElasticSearchCleanupUtil {

    private static final String ELASTICSEARCH_URL = "http://localhost:9200"; // Change if using port 9200 mapped via Docker

    /**
     * Deletes matching records from 'student' and 'user' indices in Elasticsearch by name.
     * 
     * @param studentName The student name to delete
     */
    public static void deleteUserFromElastic(String userName,String coll_name) {
        switch(coll_name) {
            case "student":
            case "staffadmin":
            case "contentcreator":
            case "usergroup":
                deleteFromIndex(coll_name, userName);
                break;
        }
        switch(coll_name) {
            case "student":
            case "staffadmin":
                deleteFromIndex("user", userName);
                break;
        }
    }

    private static void deleteFromIndex(String indexName, String userName) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // Constructs the Delete By Query endpoint URL
            String url = ELASTICSEARCH_URL + "/" + indexName + "/_delete_by_query";

            // Build query based on the index
            String jsonPayload = "";

            if ("usergroup".equalsIgnoreCase(indexName)) {
                jsonPayload = String.format(
                    "{\"query\": {\"match\": {\"name\": \"%s\"}}}",
                    userName
                );
            } else {
                jsonPayload = String.format(
                    "{\"query\": {\"match\": {\"login\": \"%s\"}}}",
                    userName
                );
            }

            System.out.println("Elasticsearch delete query: " + jsonPayload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonResponse = objectMapper.readTree(response.body());

                int total = jsonResponse.get("total").asInt();
                int deleted = jsonResponse.get("deleted").asInt();

                if (deleted > 0) {
                    System.out.println(
                        "Elasticsearch: Successfully deleted " +
                        deleted + " document(s) from index '" +
                        indexName + "' for: " + userName
                    );
                } else {
                    System.err.println(
                        "Elasticsearch: No matching document found in index '" +
                        indexName + "' for: " + userName +
                        " (total=" + total +
                        ", deleted=" + deleted + ")"
                    );
                }

            } else {
                System.err.println(
                    "Elasticsearch: Failed to delete from index '" +
                    indexName +
                    "'. HTTP Status: " +
                    response.statusCode() +
                    ", Response: " +
                    response.body()
                );
            }

        } catch (Exception e) {
            System.err.println(
                "Error deleting from Elasticsearch: " +
                e.getMessage()
            );
            e.printStackTrace();
        }
    }
}