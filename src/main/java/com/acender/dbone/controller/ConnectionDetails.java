package com.acender.dbone.controller;

import io.swagger.v3.oas.annotations.media.Schema;

public class ConnectionDetails {

    @Schema(example = "localhost", description = "The hostname or IP address of the database server")
    private String host;

    @Schema(example = "3306", description = "The port number of the database server")
    private int port;

    @Schema(example = "testdb", description = "The name of the database")
    private String database;

    @Schema(example = "root", description = "The username for the database connection")
    private String username;

    @Schema(example = "password123", description = "The password for the database connection")
    private String password;

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
}
