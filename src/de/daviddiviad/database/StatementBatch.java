package de.daviddiviad.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by David on 09.02.2017.
 */

public class StatementBatch {

    private final Statement statement;
    private final StatementCallback callback;

    private Queue<String> queries;

    public StatementBatch(Statement statement, StatementCallback callback) {
        this.statement = statement;
        this.callback  = callback;
        this.queries = new LinkedList<>();
    }

    Statement getStatement() {
        return statement;
    }

    public void query (String query) {
        this.queries.add(query);
    }

    public void execute () throws SQLException {
        while (!queries.isEmpty()) {
            String query = queries.poll();
            ResultSet resultSet = statement.executeQuery(query);
            callback.onResult(query, resultSet);
            resultSet.close();
        }
    }

    public void execute (String sql, StatementCallback callback) throws SQLException {
        ResultSet resultSet = statement.executeQuery(sql);
        callback.onResult(sql, resultSet);
        resultSet.close();
    }

    public void close () {
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            queries.clear();
            System.gc();
        }
    }
}
