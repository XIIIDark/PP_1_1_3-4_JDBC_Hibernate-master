package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        sessionFactory = Util.getSessionFactory();
    }

    private void executeSQL(String sql) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
        }

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
        Transaction transaction = null;
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных ");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
                throw e;
            }

        }

    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            User user = session.get(User.class, id);

            if(user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {

            assert transaction != null;
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
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

        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users;";
        executeSQL(sql);
    }
}
