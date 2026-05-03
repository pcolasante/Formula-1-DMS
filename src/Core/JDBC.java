package Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Author: Paulina Flores Colasante
 * CEN 3024 - Software Development 1
 * Date: 4/8/2026
 * JDBC.java: This class will connect the F1App to the SQLite F1-DATA, the default database.
 */

public class JDBC {
    public static final String URL = "jdbc:sqlite:database/F1-DATA.db";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
