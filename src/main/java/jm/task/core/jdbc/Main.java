package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Alex", "Art", (byte) 15);
        userService.saveUser("Alex1", "Art", (byte) 18);
        userService.saveUser("Alex2", "Art", (byte) 21);
        userService.saveUser("Alex3", "Art", (byte) 30);
//        userService.removeUserById(2);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
        //Util.closeMySQLConnection();
        Util.closeHibernateSession();
    }
}
