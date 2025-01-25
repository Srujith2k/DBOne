package com.acender.dbone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/db")
public class DatabaseController {

    @PostMapping("/test")
    public ResponseEntity<String> testConnection(@RequestBody Map<String, String> connectionDetails) {
        String url = connectionDetails.get("url");
        String username = connectionDetails.get("username");
        String password = connectionDetails.get("password");

        try {
            if (url.startsWith("jdbc:mysql:")) {
                Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL Driver
            } else if (url.startsWith("jdbc:postgresql:")) {
                Class.forName("org.postgresql.Driver"); // PostgreSQL Driver
            } else {
                return ResponseEntity.badRequest().body("Unsupported database type!");
            }

            java.sql.Connection conn = java.sql.DriverManager.getConnection(url, username, password);
            return ResponseEntity.ok("Connection successful: " + conn.getMetaData().getDatabaseProductName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Connection failed: " + e.getMessage());
        }
    }
}