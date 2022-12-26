package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    public static Connection getMySQLConnection() throws SQLException
            {
        String hostName = "localhost";
        String dbName = "myschemas";
        String userName = "root";
        String password = "uGV!hu3.24sdf";
        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException
             {
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;
                 return DriverManager.getConnection(connectionURL, userName, password);
    }
}
