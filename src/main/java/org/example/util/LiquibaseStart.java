package org.example.util;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

public class LiquibaseStart {
    public static void runLiquibase() {
        try {
            String url = "jdbc:postgresql://localhost:5432/BinaryTree";
            String username = "postgres";
            String password = "postgres";

            Connection connection = DriverManager.getConnection(url, username, password);
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(
                    "db/changelog/db-changelog-master.yaml",
                    new ClassLoaderResourceAccessor(),
                    database
            );

            liquibase.update("");
            System.out.println("Liquibase migration completed successfully");

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LiquibaseStart.runLiquibase();
    }
}
