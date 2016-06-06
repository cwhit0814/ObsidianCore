package com.ObsidianReach.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {
    public Connection connection;
    private final String username;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;

    public MySQL(String username, String database, String password, String port, String hostname) {
        this.username = username;
        this.database = database;
        this.password = password;
        this.port = port;
        this.hostname = hostname;
    }

    public boolean checkConnection() throws SQLException {
        return this.connection != null && !this.connection.isClosed();
    }

    private void updateConnection() throws SQLException, ClassNotFoundException {
        if (!this.checkConnection()) {
            this.openConnection();
        }

    }

    public Connection getConnection() {
        return this.connection;
    }

    public void closeConnection() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }

    }

    public ResultSet query(String query) throws SQLException, ClassNotFoundException {
        this.updateConnection();
        return this.connection.createStatement().executeQuery(query);
    }

    public int update(String query) throws SQLException, ClassNotFoundException {
        this.updateConnection();
        return this.connection.createStatement().executeUpdate(query);
    }

    public Connection openConnection() throws SQLException, ClassNotFoundException {
        if (this.checkConnection()) {
            return this.connection;
        } else {
            this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database, this.username, this.password);
            return this.connection;
        }
    }
}

