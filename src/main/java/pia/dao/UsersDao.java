package pia.dao;

import org.apache.commons.dbutils.DbUtils;
import pia.data.DbSettings;
import pia.data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersDao {
    private final DbSettings dbSettings;

    public UsersDao(DbSettings dbSettings) throws ClassNotFoundException {
        super();
        this.dbSettings = dbSettings;

        Class.forName("com.mysql.jdbc.Driver");
    }

    public List<User> getUsers() throws SQLException {
        List<User> users = new ArrayList<User>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(this.dbSettings.getConnectionUrl(), this.dbSettings.getUser(), this.dbSettings.getPassword());

            preparedStatement = connection.prepareStatement("SELECT id, login, name, lastname, password FROM jtichava_users;");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();

                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                user.setLastname(resultSet.getString("lastname"));
                user.setPassword(resultSet.getString("password"));

                users.add(user);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            DbUtils.closeQuietly(resultSet);
            DbUtils.closeQuietly(preparedStatement);
            DbUtils.closeQuietly(connection);
        }

        return users;
    }
}
