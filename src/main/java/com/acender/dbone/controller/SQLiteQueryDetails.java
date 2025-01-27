package com.acender.dbone.controller;

import io.swagger.v3.oas.annotations.media.Schema;

public class SQLiteQueryDetails {

    @Schema(example = "testdb", description = "The name of the SQLite database file.")
    private String database;

    @Schema(example = "SELECT * FROM products", description = "The SQL query to execute.")
    private String query;

    // Getters and Setters
    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
