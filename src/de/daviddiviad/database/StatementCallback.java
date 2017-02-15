package de.daviddiviad.database;

import java.sql.ResultSet;

/**
 * Created by David on 09.02.2017.
 */

public interface StatementCallback {

    void onResult (String query, ResultSet resultSet);

}
