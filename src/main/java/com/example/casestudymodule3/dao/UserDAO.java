package com.example.casestudymodule3.dao;
import com.example.casestudymodule3.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class UserDAO implements IUserDAO {

        private String jdbcURL = "jdbc:mysql://localhost:3306/casestudymodule3?useSSL=false";
        private String jdbcUsername = "root";
        private String jdbcPassword = "123456";

        private static final String INSERT_USERS_SQL = "INSERT INTO user (id, name, email, address, country) VALUES (?, ?, ?, ?, ?);";
        private static final String SELECT_USER_BY_ID = "select id,name,email,address, country from user where id =?";
        private static final String SELECT_ALL_USERS = "select * from user";
        private static final String DELETE_USERS_SQL = "delete from user where id = ?;";
        private static final String UPDATE_USERS_SQL = "update user set name = ?,email= ?, address=?, country =? where id = ?;";

        public UserDAO() {
        }

        protected Connection getConnection() {
            //Thay doi gia tri database cho gia tri database=null
            Connection connection = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return connection;
        }

        public void insertUser(User user) throws SQLException {
            System.out.println(INSERT_USERS_SQL);
            try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getEmail());
                preparedStatement.setString(3,user.getAddress());
                preparedStatement.setString(4, user.getCountry());
                System.out.println(preparedStatement);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                printSQLException(e);
            }
        }

        public User selectUser(int id) {
            User user = null;
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
                preparedStatement.setInt(1, id);
                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String address = rs.getString("address");
                    String country = rs.getString("country");
                    user = new User(id, name, email, country);
                }
            } catch (SQLException e) {
                printSQLException(e);
            }
            return user;
        }

        public List<User> selectAllUsers() {
            List<User> users = new ArrayList<>();
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
                System.out.println(preparedStatement);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    String address=rs.getString("address");
                    String country = rs.getString("country");
                    users.add(new User(id, name, email, country));
                }
            } catch (SQLException e) {
                printSQLException(e);
            }
            return users;
        }

        public boolean deleteUser(int id) throws SQLException {
            boolean rowDeleted;
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
                statement.setInt(1, id);
                rowDeleted = statement.executeUpdate() > 0;
            }
            return rowDeleted;
        }

        public boolean updateUser(User user) throws SQLException {
            boolean rowUpdated;
            try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getEmail());
                statement.setString(3, user.getAddress());
                statement.setString(3, user.getCountry());
                statement.setInt(4, user.getId());

                rowUpdated = statement.executeUpdate() > 0;
            }
            return rowUpdated;
        }

        private void printSQLException(SQLException ex) {
            for (Throwable e : ex) {
                if (e instanceof SQLException) {
                    e.printStackTrace(System.err);
                    System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                    System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                    System.err.println("Message: " + e.getMessage());
                    Throwable t = ex.getCause();
                    while (t != null) {
                        System.out.println("Cause: " + t);
                        t = t.getCause();
                    }
                }
            }
        }
}
