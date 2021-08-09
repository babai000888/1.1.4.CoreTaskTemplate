package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import java.util.List;
import java.util.Objects;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    private void closeSession(Session session) {
       if(session != null) { session.close(); }
    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                "`id` BIGINT NOT NULL AUTO_INCREMENT," +
                "`name` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'," +
                "`lastName` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'," +
                "`age` SMALLINT," +
                "PRIMARY KEY (`id`));";
        try {
            session.createSQLQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        String sql = "DROP TABLE IF EXISTS Users";
        try {
            session.createSQLQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        try {
            session.save(new User(name,lastName,age));
        } catch (JDBCException | NullPointerException e) {
            System.out.println("saveUser: Table not available");
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        try {
            session.delete(session.get(User.class, id));
        } catch (Exception e) {
            System.out.println("removeUserById: user id=" + id + " not found");
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List list = null;
        Session session = Util.getSessionFactory().openSession();
        try {
            list = session.createQuery("FROM User").list();
        } catch (JDBCException e) {
            System.out.println("getAllUsers: Table not available");
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        try {
            session.createQuery("DELETE User").executeUpdate();
        } catch (JDBCException e) {
            System.out.println("cleanUserTable: Table not available");
            e.printStackTrace();
        } finally {
            closeSession(session);
        }
    }
}

