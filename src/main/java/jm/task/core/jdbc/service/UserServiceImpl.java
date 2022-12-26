package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() throws SQLException {
        Connection conn = Util.getMySQLConnection();


        // Create statement
        Statement statement = conn.createStatement();

        String sql = "create table myschemas.users\n" + "(\n" + "    user_id       int auto_increment,\n" + "    user_name     varchar(30) null,\n" + "    user_lastname varchar(30) null,\n" + "    user_age      int         null,\n" + "    constraint users_pk\n" + "        primary key (user_id)\n" + ");";

        // Execute SQL statement returns a ResultSet object.
        try {
            statement.executeUpdate(sql);
        } catch (SQLSyntaxErrorException e) {
            if (e.getMessage().equals("Table 'users' already exists")) {
                System.out.println(e.getMessage());
            } else {
                throw e;
            }
        }
        statement.close();
        conn.close();
    }

    public void dropUsersTable() throws SQLException {
        Connection conn = Util.getMySQLConnection();
        Statement statement = conn.createStatement();
        String sql = "drop table myschemas.users;";
        try {
            statement.executeUpdate(sql);
        } catch (SQLSyntaxErrorException e) {
            if (e.getMessage().equals("Unknown table 'myschemas.users'")) {
                System.out.println(e.getMessage());
            } else {
                throw e;
            }
        }
        statement.close();
        conn.close();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection conn = Util.getMySQLConnection();
        Statement statement = conn.createStatement();
        String sql = "INSERT INTO myschemas.users (user_name, user_lastname, user_age)\n" +
                "VALUES ('" + name + "', '" + lastName + "', " + age + "); ";
        statement.executeUpdate(sql);
        statement.close();
        conn.close();
        System.out.println("User с именем – " + name + " добавлен в базу данных ");
    }

    public void removeUserById(long id) throws SQLException {
        Connection conn = Util.getMySQLConnection();
        Statement statement = conn.createStatement();
        String sql = "DELETE FROM myschemas.users WHERE user_id = " + id + "; ";
        statement.executeUpdate(sql);
        statement.close();
        conn.close();
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        Connection conn = Util.getMySQLConnection();
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM myschemas.users ;";
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            //Long userID = rs.getLong("user_id");
            String userName = rs.getString("user_name");
            String userLastname = rs.getString("user_lastname");
            Byte userAge = rs.getByte("user_age");

            users.add(new User(userName, userLastname, userAge));
        }
        rs.close();
        statement.close();
        conn.close();
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        Connection conn = Util.getMySQLConnection();
        Statement statement = conn.createStatement();
        String sql = "DELETE FROM myschemas.users;";
        statement.executeUpdate(sql);
        statement.close();
        conn.close();
    }
}
