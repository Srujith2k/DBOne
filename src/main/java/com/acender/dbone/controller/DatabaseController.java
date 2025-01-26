package com.acender.dbone.controller;

import java.sql.Connection;
import java.sql.DriverManager;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/db")
public class DatabaseController{
	
	@Operation(summary = "Test MySQL Connection")
	@PostMapping("/mysql/test")
	public ResponseEntity<String> testMySQLConnection(@RequestBody ConnectionDetails connectionDetails){
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

    
    private ResponseEntity<String> testConnection(String url, ConnectionDetails connectionDetails, String driverClass){
        try {
        	Class.forName(driverClass); //Loading the driver
        	try(Connection connection = DriverManager.getConnection(url, connectionDetails.getUsername(), connectionDetails.getPassword())){
        		return ResponseEntity.ok("Connection Successful: "+ connection.getMetaData().getColumns(driverClass, driverClass, url, driverClass));
        	}
        } catch(Exception e){
        	return ResponseEntity.badRequest().body("Connection Failed: "+ e.getLocalizedMessage());
        }
    }
}