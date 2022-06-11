package com.config;

import com.models.Book;
import com.models.Person;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.EntityManagerFactory;
import java.util.Properties;


public class DatabaseConnection {

    public static EntityManagerFactory createEntityManagerFactory() {

        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "org.postgresql.Driver");
        properties.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
        properties.put(Environment.USER, "postgres");
        properties.put(Environment.PASS, "237");

        properties.put(Environment.HBM2DDL_AUTO, "update");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect");
        properties.put(Environment.SHOW_SQL, "true");

        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(Book.class);
        return configuration.buildSessionFactory().unwrap(EntityManagerFactory.class);
    }

}