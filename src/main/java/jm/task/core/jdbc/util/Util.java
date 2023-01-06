package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String HOST_NAME = "localhost";
    private static final String DB_NAME = "myschemas";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "uGV!hu3.24sdf";
    private static Connection mySQLConn = null;
    private static SessionFactory sessionFactory = null;

    public static Connection getMySQLConnection() {
        return mySQLConn;
    }

    public static void closeMySQLConnection() throws SQLException {
        mySQLConn.close();
    }

    public static void initMySQLConnection() {

        String connectionURL = "jdbc:mysql://" + HOST_NAME + ":3306/" + DB_NAME;

        try {
            mySQLConn =  DriverManager.getConnection(connectionURL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void initHibernateConnection() {
        String connectionURL = "jdbc:mysql://" + HOST_NAME + ":3306/" + DB_NAME;
        Configuration cfg = new Configuration()
//                .addClass(jm.task.core.jdbc.model.User.class)
                //.addClass(org.hibernate.auction.Bid.class)
                .addAnnotatedClass(User.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLInnoDBDialect")
                .setProperty("hibernate.connection.url", connectionURL)
                .setProperty("hibernate.connection.username", USER_NAME)
                .setProperty("hibernate.connection.password", PASSWORD)
                .setProperty("hibernate.hbm2ddl.auto", "none")
                .setProperty("hibernate.order_updates", "true");
        sessionFactory = cfg.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }
}
