package com.codegnan.app.javawebapp06.database;

import com.codegnan.app.javawebapp06.entity.Credentials;
import com.codegnan.app.javawebapp06.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();

        String sqlQuery = "SELECT `u`.user_id, `u`.first_name, `u`.last_name, `c`.username, `c`.login_password FROM users `u` ";
        sqlQuery += "INNER JOIN credentials `c` ON `u`.credentials_id = `c`.credentials_id;";

        try (Connection connection = DatabaseUtility.getDatabaseConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setFirstName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                Credentials credentials = new Credentials();
                credentials.setUsername(resultSet.getString(4));
                credentials.setLoginPassword(resultSet.getString(5));
                user.setCredentials(credentials);

                usersList.add(user);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return usersList;
    }

    public boolean save(User user) {
        boolean isUserSaved = false;

        String sqlQuery1 = "INSERT INTO credentials(username, login_password) VALUES (?,?);";
        String sqlQuery2 = "INSERT INTO users (first_name, last_name, credentials_id) VALUES (?,?,?);";

        try (Connection connection = DatabaseUtility.getDatabaseConnection();
             PreparedStatement preparedStatement1 = connection.prepareStatement(sqlQuery1, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatement2 = connection.prepareStatement(sqlQuery2)) {

            connection.setAutoCommit(false);

            Credentials credentials = user.getCredentials();
            preparedStatement1.setString(1, credentials.getUsername());
            preparedStatement1.setString(2, credentials.getLoginPassword());
            int numOfRows = preparedStatement1.executeUpdate();
            if (numOfRows != 0) {
                ResultSet resultSet = preparedStatement1.getGeneratedKeys();
                resultSet.next();
                int credentials_id = resultSet.getInt(1);

                preparedStatement2.setString(1, user.getFirstName());
                preparedStatement2.setString(2, user.getLastName());
                preparedStatement2.setInt(3, credentials_id);
                numOfRows = preparedStatement2.executeUpdate();
                if (numOfRows != 0) {
                    connection.commit();

                    isUserSaved = true;
                }
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return isUserSaved;
    }

    public User findByUsernameAndLoginPassword(String username, String loginPassword) {
        User user = null;

        String sqlQuery = "SELECT `u`.first_name, `u`.last_name FROM users `u` ";
        sqlQuery += "INNER JOIN credentials `c` ON `u`.credentials_id = `c`.credentials_id ";
        sqlQuery += "WHERE `c`.username = '" + username + "' AND `c`.login_password = '" + loginPassword + "';";

        try (Connection connection = DatabaseUtility.getDatabaseConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            if (resultSet.next()) {
                user = new User();
                user.setFirstName(resultSet.getString(1));
                user.setLastName(resultSet.getString(2));
                Credentials credentials = new Credentials();
                credentials.setUsername(username);
                credentials.setLoginPassword(loginPassword);
                user.setCredentials(credentials);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return user;
    }

    public boolean updateLoginPasswordByUsername(String username, String newLoginPassword) {
        boolean isPasswordUpdated = false;

        String sqlQuery = "UPDATE credentials SET login_password=? WHERE username=?";

        try (Connection connection = DatabaseUtility.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

            preparedStatement.setString(1, newLoginPassword);
            preparedStatement.setString(2, username);
            int numOfRows = preparedStatement.executeUpdate();
            if (numOfRows != 0) {
                isPasswordUpdated = true;
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return isPasswordUpdated;
    }

//    public boolean deleteByUsername(String username) {
//        boolean isUserDeleted = false;
//
//        String sqlQuery = "DELETE FROM credentials WHERE username = ?";
//        try (Connection connection = DatabaseUtility.getDatabaseConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
//            preparedStatement.setString(1, username);
//            int numOfRows = preparedStatement.executeUpdate();
//            if (numOfRows > 0) {
//                isUserDeleted = true;
//            }
//        } catch (SQLException sqlEx) {
//            sqlEx.printStackTrace();
//        }
//        return isUserDeleted;
//    }

    public boolean deleteByUsername(String username) {

        boolean isUserDeleted = false;

        String getCredentialsId = "SELECT credentials_id FROM credentials WHERE username = ?";
        String deleteUser = "DELETE FROM users WHERE credentials_id = ?";
        String deleteCredentials = "DELETE FROM credentials WHERE credentials_id = ?";

        try (Connection connection = DatabaseUtility.getDatabaseConnection()) {

            connection.setAutoCommit(false);

            int credentialsId = -1;

            // Get credentials_id using username
            try (PreparedStatement ps = connection.prepareStatement(getCredentialsId)) {

                ps.setString(1, username);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    credentialsId = rs.getInt("credentials_id");
                } else {
                    return false; // Username not found
                }
            }

            // Delete from users table
            try (PreparedStatement ps = connection.prepareStatement(deleteUser)) {
                ps.setInt(1, credentialsId);
                ps.executeUpdate();
            }

            // Delete from credentials table
            try (PreparedStatement ps = connection.prepareStatement(deleteCredentials)) {
                ps.setInt(1, credentialsId);
                ps.executeUpdate();
            }

            connection.commit();
            isUserDeleted = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isUserDeleted;
    }
}