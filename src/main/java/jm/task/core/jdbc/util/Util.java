package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/CoreTaskTemplate?useSSL=false&useUnicode=true&characterEncoding=utf8",
                    "root", "000888");
        } catch (SQLException e) {
            System.out.println(" getConnection() ERROR");
        }
        return connection;
    }
    // Hibernate
    private static SessionFactory factory = null;
    public static SessionFactory getSessionFactory() {
        if (factory == null) {
            Configuration configuration = new Configuration()
                    .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                    .setProperty("hibernate.connection.driver_class",
                            "com.mysql.jdbc.Driver")
                    .setProperty("hibernate.connection.url",
                            "jdbc:mysql://localhost:3306/CoreTaskTemplate?useSSL=false&useUnicode=true&characterEncoding=utf8")
                    .setProperty("hibernate.connection.username",
                            "root")
                    .setProperty("hibernate.connection.password",
                            "000888")
                    .setProperty("hibernate.dialect",
                            "org.hibernate.dialect.MySQLDialect")
                    .setProperty("hibernate.show_sql", "true")
                    .setProperty("format_sql", "true")
                    .setProperty("use_sql_comments", "true")
                    .setProperty("hibernate.allow_update_outside_transaction", "true");
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            factory = configuration.buildSessionFactory(builder.build());
        }
        return factory;
    }
}

