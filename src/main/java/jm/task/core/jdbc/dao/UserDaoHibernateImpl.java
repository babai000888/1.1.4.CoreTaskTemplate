package jm.task.core.jdbc.dao;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
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
  //      Session session = Util.getSessionFactory().openSession();
        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                "`id` BIGINT NOT NULL AUTO_INCREMENT," +
                "`name` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'," +
                "`lastName` TEXT CHARACTER SET 'utf8' COLLATE 'utf8_general_ci'," +
                "`age` SMALLINT," +
                "PRIMARY KEY (`id`));";
        try (Session session = Util.getSessionFactory().openSession()){
            session.createSQLQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users";
        try (Session session = Util.getSessionFactory().openSession()){
            session.createSQLQuery(sql).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()){
            session.save(new User(name,lastName,age));
        } catch (JDBCException | NullPointerException e) {
            System.out.println("saveUser: Table not available");
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.delete(session.get(User.class, id));
        } catch (Exception e) {
            System.out.println("removeUserById: user id=" + id + " not found");
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List list = null;
        try (Session session = Util.getSessionFactory().openSession()){
            list = session.createQuery("FROM User").list();
        } catch (JDBCException e) {
            System.out.println("getAllUsers: Table not available");
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.createQuery("DELETE User").executeUpdate();
        } catch (JDBCException e) {
            System.out.println("cleanUserTable: Table not available");
            e.printStackTrace();
        }
    }
}

