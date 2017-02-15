package de.daviddiviad.database;

import java.sql.*;
import java.util.Arrays;

/**
 * Created by David on 09.02.2017.
 */

public class DatabaseConnection {

    private final Connection connection;

    public DatabaseConnection(Connection connection) {
        this.connection = connection;
    }

    public void closeConnection() {
        if (this.connection != null) {
            try {
                this.connection.rollback();
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                System.gc();
            }
        }
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public StatementBatch prepareBatchStatement(StatementCallback callback, String... queries) throws SQLException {
        Statement statement = this.connection.createStatement();
        StatementBatch statementBatch = new StatementBatch(statement, callback);
        Arrays.asList(queries).forEach(statementBatch::query);
        return statementBatch;
    }

    public EquatiomorphStatement prepareEquatiomorphStatement () throws SQLException {
        return new EquatiomorphStatement(this.connection.createStatement());
    }

    public static DatabaseConnection establishConnection(String url, String username, String password) throws SQLException {
        if (url == null)
            throw new NullPointerException();
        return new DatabaseConnection(DriverManager.getConnection(url, username, password));
    }
}