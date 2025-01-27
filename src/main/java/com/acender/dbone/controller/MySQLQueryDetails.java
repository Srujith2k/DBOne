package com.acender.dbone.controller;

import io.swagger.v3.oas.annotations.media.Schema;

public class MySQLQueryDetails {

    @Schema(example = "localhost", description = "The hostname or IP address of the MySQL server.")
    private String host;

    @Schema(example = "3306", description = "The port number of the MySQL server.")
    private int port;

    @Schema(example = "testdb", description = "The name of the MySQL database.")
    private String database;

    @Schema(example = "root", description = "The username for the MySQL connection.")
    private String username;

    @Schema(example = "password123", description = "The password for the MySQL connection.")
    private String password;

    @Schema(example = "SELECT * FROM users", description = "The SQL query to execute.")
    private String query;

    // Getters and Setters
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
