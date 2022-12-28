package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDaoHibernateImpl implements UserDao {

    private Session session;

    public UserDaoHibernateImpl() {
        session = Util.getHibernateSession();

    }

    private void executeSQL(String sql) {

        Transaction transaction = session.beginTransaction();
        session.createSQLQuery(sql).executeUpdate();
        transaction.commit();

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users\n"
                + "(\n" + "    user_id       int auto_increment,\n"
                + "    user_name     varchar(30) null,\n"
                + "    user_lastname varchar(30) null,\n"
                + "    user_age      int         null,\n"
                + "    constraint users_pk\n"
                + "        primary key (user_id)\n" + ");";
        executeSQL(sql);
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users;";
        executeSQL(sql);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = session.beginTransaction();
        String sql = "INSERT INTO users (user_name, user_lastname, user_age)\n" +
                " VALUES (?, ?, ?); ";
        session.createSQLQuery(sql)
                .setString(1, name)
                .setString(2, lastName)
                .setByte(3, age)
                .executeUpdate();
        transaction.commit();
        System.out.println("User с именем – " + name + " добавлен в базу данных ");
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = session.beginTransaction();
        String sql = "DELETE FROM users WHERE user_id = ?; ";
        session.createSQLQuery(sql)
                .setLong(1, id)
                .executeUpdate();
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT * FROM users ;";
        List<Object[]> query = (List<Object[]>) session.createSQLQuery(sql).list();
        for (Object[] object : query) {
            User user = new User();
            user.setName(object[1].toString());
            user.setLastName(object[2].toString());
            user.setAge(Byte.parseByte(object[3].toString()));
            users.add(user);
        }
        transaction.commit();
        System.out.println(users);
        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users;";
        executeSQL(sql);
    }
}
