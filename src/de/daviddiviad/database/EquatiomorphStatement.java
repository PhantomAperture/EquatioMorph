package de.daviddiviad.database;

import de.daviddiviad.search.Tag;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by David on 09.02.2017.
 */
public class EquatiomorphStatement {

    private final Statement statement;

    public EquatiomorphStatement(Statement statement) {
        this.statement = statement;
    }

    public boolean insertTag (Tag tag) {
        try {
            statement.execute("INSERT INTO TAGS VALUES ('" + tag.hashCode() + "', '" + tag.getValue() + "')");
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getTags () {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT DISTINCT VALUE FROM TAGS");
            List<String> tags = new ArrayList<>();
            while(resultSet.next())
                tags.add(resultSet.getString("VALUE"));
            return tags;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    public Statement getStatement() {
        return statement;
    }
}
