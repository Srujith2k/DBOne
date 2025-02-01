package com.acender.dbone.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DBOneClient {

    private final String baseUrl;
    private final RestTemplate restTemplate;

    public DBOneClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.restTemplate = new RestTemplate();
    }

    // Test connection to MySQL
    public String testMySQLConnection(String host, int port, String database, String username, String password) {
        return testConnection("/mysql/test", host, port, database, username, password);
    }

    // Test connection to PostgreSQL
    public String testPostgreSQLConnection(String host, int port, String database, String username, String password) {
        return testConnection("/postgresql/test", host, port, database, username, password);
    }

    // Test connection to SQLite
    public String testSQLiteConnection(String database) {
        Map<String, Object> body = new HashMap<>();
        body.put("database", database);

        return sendPostRequest("/sqlite/test", body);
    }

    // Test connection to H2
    public String testH2Connection(String host, int port, String database, String username, String password) {
        return testConnection("/h2/test", host, port, database, username, password);
    }

    // Run a custom query for MySQL
    public List<Map<String, Object>> executeMySQLQuery(String host, int port, String database, String username, String password, String query) {
        return executeQuery("/mysql/query", host, port, database, username, password, query);
    }

    // Run a custom query for PostgreSQL
    public List<Map<String, Object>> executePostgreSQLQuery(String host, int port, String database, String username, String password, String query) {
        return executeQuery("/postgresql/query", host, port, database, username, password, query);
    }

    // Run a custom query for SQLite
    public List<Map<String, Object>> executeSQLiteQuery(String database, String query) {
        Map<String, Object> body = new HashMap<>();
        body.put("database", database);
        body.put("query", query);
        return sendPostRequestForList("/sqlite/query", body);
    }

    // Run a custom query for H2
    public List<Map<String, Object>> executeH2Query(String host, int port, String database, String username, String password, String query) {
        return executeQuery("/h2/query", host, port, database, username, password, query);
    }

    private String testConnection(String endpoint, String host, int port, String database, String username, String password) {
        Map<String, Object> body = new HashMap<>();
        body.put("host", host);
        body.put("port", port);
        body.put("database", database);
        body.put("username", username);
        body.put("password", password);

        return sendPostRequest(endpoint, body);
    }

    private List<Map<String, Object>> executeQuery(String endpoint, String host, int port, String database, String username, String password, String query) {
        Map<String, Object> body = new HashMap<>();
        body.put("host", host);
        body.put("port", port);
        body.put("database", database);
        body.put("username", username);
        body.put("password", password);
        body.put("query", query);

        return sendPostRequestForList(endpoint, body);
    }

    private String sendPostRequest(String endpoint, Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl + endpoint, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    private List<Map<String, Object>> sendPostRequestForList(String endpoint, Map<String, Object> body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<List> response = restTemplate.exchange(baseUrl + endpoint, HttpMethod.POST, entity, List.class);
        return response.getBody();
    }
}
