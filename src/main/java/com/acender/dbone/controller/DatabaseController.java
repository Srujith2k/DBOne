package com.acender.dbone.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

@RestController
@RequestMapping("/api/db")
public class DatabaseController{
	@PostMapping("/mysql/test")
	public ResponseEntity<String> testMySQLConnection(@RequestBody Map<String, String> connectionDetails){
		return testConnection(connectionDetails, "com.mysql.cj.jdbc.Driver");
	}
	@PostMapping("/postgresql/test")
    public ResponseEntity<String> testPostgreSQLConnection(@RequestBody Map<String, String> connectionDetails) {
        return testConnection(connectionDetails, "org.postgresql.Driver");
    }

    @PostMapping("/sqlite/test")
    public ResponseEntity<String> testSQLiteConnection(@RequestBody Map<String, String> connectionDetails) {
        return testConnection(connectionDetails, "org.sqlite.JDBC");
    }

    @PostMapping("/h2/test")
    public ResponseEntity<String> testH2Connection(@RequestBody Map<String, String> connectionDetails) {
        return testConnection(connectionDetails, "org.h2.Driver");
    }
    
    private ResponseEntity<String> testConnection(Map<String, String> connectionDetails, String driverClass){
    	String url = connectionDetails.get("url");
        String username = connectionDetails.getOrDefault("username", "");
        String password = connectionDetails.getOrDefault("password", "");
        
        try {
        	Class.forName(driverClass); //Loading the driver
        	try(Connection connection = DriverManager.getConnection(url, username, password)){
        		return ResponseEntity.ok("Connection Successful: "+ connection.getMetaData().getDatabaseProductName());
        	}
        } catch(Exception e){
        	return ResponseEntity.badRequest().body("Connection Failed: "+ e.getLocalizedMessage());
        }
    }
}