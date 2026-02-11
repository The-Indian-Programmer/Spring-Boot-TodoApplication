package org.crud.todo.config;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseStartupLogger implements ApplicationRunner {
    private final DataSource dataSource;

    public DatabaseStartupLogger(DataSource dataSource) {
        this.dataSource = dataSource;
        System.out.println("Database connnecttion loaded");
    }

    @Override
    public void run(ApplicationArguments args) {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("Database connected successfully");
            System.out.println("Database URL: " + connection.getMetaData().getURL());
            System.out.println("Database UserName : " + connection.getMetaData().getUserName());
        } catch (Exception e) {
           System.out.println("Database connection failed");
           e.printStackTrace();
        }
    }
    
}