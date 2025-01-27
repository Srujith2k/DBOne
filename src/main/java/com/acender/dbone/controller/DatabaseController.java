package com.acender.dbone.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/db")
public class DatabaseController {

    @Operation(summary = "Test MySQL Connection")
    @PostMapping("/mysql/test")
    public ResponseEntity<String> testMySQLConnection(@RequestBody ConnectionDetails connectionDetails) {
        String url = String.format("jdbc:mysql://%s:%d/%s", connectionDetails.getHost(), connectionDetails.getPort(), connectionDetails.getDatabase());
        return testConnection(url, connectionDetails, "com.mysql.cj.jdbc.Driver");
    }

    @Operation(summary = "Test PostgreSQL Connection")
    @PostMapping("/postgresql/test")
    public ResponseEntity<String> testPostgreSQLConnection(@RequestBody ConnectionDetails connectionDetails) {
        String url = String.format("jdbc:postgresql://%s:%d/%s", connectionDetails.getHost(), connectionDetails.getPort(), connectionDetails.getDatabase());
        return testConnection(url, connectionDetails, "org.postgresql.Driver");
    }

    @Operation(summary = "Test SQLite Connection")
    @PostMapping("/sqlite/test")
    public ResponseEntity<String> testSQLiteConnection(@RequestBody ConnectionDetails connectionDetails) {
        String url = String.format("jdbc:sqlite:%s.db", connectionDetails.getDatabase());
        return testConnection(url, connectionDetails, "org.sqlite.JDBC");
    }

    @Operation(summary = "Test H2 Connection")
    @PostMapping("/h2/test")
    public ResponseEntity<String> testH2Connection(@RequestBody ConnectionDetails connectionDetails) {
        String url = String.format("jdbc:h2:tcp://%s:%d/%s", connectionDetails.getHost(), connectionDetails.getPort(), connectionDetails.getDatabase());
        return testConnection(url, connectionDetails, "org.h2.Driver");
    }

    @PostMapping("/mysql/query")
    public ResponseEntity<Map<String, Object>> executeMySQLQuery(@RequestBody MySQLQueryDetails details) {
        String url = String.format("jdbc:mysql://%s:%d/%s", details.getHost(), details.getPort(), details.getDatabase());
        return executeQuery(url, details.getUsername(), details.getPassword(), details.getQuery(), "com.mysql.cj.jdbc.Driver");
    }

    @PostMapping("/postgresql/query")
    public ResponseEntity<Map<String, Object>> executePostgreSQLQuery(@RequestBody PostgreSQLQueryDetails details) {
        String url = String.format("jdbc:postgresql://%s:%d/%s", details.getHost(), details.getPort(), details.getDatabase());
        return executeQuery(url, details.getUsername(), details.getPassword(), details.getQuery(), "org.postgresql.Driver");
    }

    @PostMapping("/sqlite/query")
    public ResponseEntity<Map<String, Object>> executeSQLiteQuery(@RequestBody SQLiteQueryDetails details) {
        String url = String.format("jdbc:sqlite:%s.db", details.getDatabase());
        return executeQuery(url, null, null, details.getQuery(), "org.sqlite.JDBC");
    }

    @PostMapping("/h2/query")
    public ResponseEntity<Map<String, Object>> executeH2Query(@RequestBody H2QueryDetails details) {
        String url = String.format("jdbc:h2:tcp://%s:%d/%s", details.getHost(), details.getPort(), details.getDatabase());
        return executeQuery(url, details.getUsername(), details.getPassword(), details.getQuery(), "org.h2.Driver");
    }
    
    private ResponseEntity<Map<String, Object>> executeQuery(String url, String username, String password, String query, String driverClass) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            Class.forName(driverClass);
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                List<Map<String, Object>> rows = new ArrayList<>();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(metaData.getColumnName(i), resultSet.getObject(i));
                    }
                    rows.add(row);
                }

                response.put("status", "success");
                response.put("data", rows);
                return ResponseEntity.ok(response);

            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", e.getLocalizedMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    private ResponseEntity<String> testConnection(String url, ConnectionDetails connectionDetails, String driverClass) {
        try {
            Class.forName(driverClass);
            try (Connection connection = DriverManager.getConnection(url, connectionDetails.getUsername(), connectionDetails.getPassword())) {
                return ResponseEntity.ok("Connection Successful: " + connection.getMetaData().getDatabaseProductName());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Connection Failed: " + e.getLocalizedMessage());
        }
    }
}
