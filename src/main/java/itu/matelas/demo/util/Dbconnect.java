package itu.matelas.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconnect {
    public static Connection dbConnect() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/matelas2";
        String user = "postgres";
        String password = "root";
        Connection conn = DriverManager.getConnection(url, user, password);

        return conn;
    }
}
