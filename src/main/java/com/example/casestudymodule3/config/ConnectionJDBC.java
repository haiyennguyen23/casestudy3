package com.example.casestudymodule3.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionJDBC {

    private static Connection connection;

    private ConnectionJDBC() {
    }

    public static final String URL = "jdbc:mysql://localhost:3306/book_manager";
    public static final String USER = "nguyenhaiyen2k3@gmail.com";
    public static final String PASSWORD = "Yen123456";

    public static Connection getConnect(){
        if (connection==null){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(URL,USER, PASSWORD);
                System.out.println("Kết nối thành công");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Kết nối không thành công");
                e.printStackTrace();
            }
        }
        return  connection;
    }
}