package Core;

import UI.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class JDBC {
    public static final String URL = "jdbc:sqlite:database/F1-DATA.db";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
