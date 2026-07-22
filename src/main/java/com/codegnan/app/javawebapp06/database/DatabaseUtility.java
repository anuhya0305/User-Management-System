package com.codegnan.app.javawebapp06.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtility {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/sql_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Srilalitha";

    static {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException cnfEx) {
            cnfEx.printStackTrace();
        }
    }

    public static Connection getDatabaseConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(CONNECTION_URL, USERNAME, PASSWORD);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return connection;
    }
}