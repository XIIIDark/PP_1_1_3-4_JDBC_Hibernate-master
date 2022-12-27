package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String HOST_NAME = "localhost";
    private static final String DB_NAME = "myschemas";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "uGV!hu3.24sdf";
    private static Connection conn = null;

    public static Connection getMySQLConnection() {
        return conn;
    }

    public static void closeMySQLConnection() throws SQLException {
        conn.close();
    }

    public static void initMySQLConnection() {

        String connectionURL = "jdbc:mysql://" + HOST_NAME + ":3306/" + DB_NAME;

        try {
            conn =  DriverManager.getConnection(connectionURL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
