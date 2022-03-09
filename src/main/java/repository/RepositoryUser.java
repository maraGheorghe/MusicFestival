package repository;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.interfaces.RepositoryInterfaceUser;
import util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class RepositoryUser implements RepositoryInterfaceUser {

    private JDBCUtils jdbcUtils;

    private static final Logger logger = LogManager.getLogger();

    public RepositoryUser(Properties properties) {
        logger.info("Initializing RepositoryUser with properties: {} ", properties);
        jdbcUtils = new JDBCUtils(properties);
    }

    @Override
    public Optional<User> save(User user) {
        logger.traceEntry("Saving user: {} ", user);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("insert into users (username, password) values (?, ?)")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            int result = preparedStatement.executeUpdate();
            logger.trace("Saved {} instances.", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
            return Optional.empty();
        }
        logger.traceExit();
        return Optional.of(user);
    }

    @Override
    public Optional<User> find(Long ID) {
        logger.traceEntry("Finding user with ID: {}.", ID);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from users where user_id = ?")) {
            preparedStatement.setLong(1, ID);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    User user = new User(ID, username, password);
                    logger.traceExit(user);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        logger.traceExit();
        return Optional.empty();
    }

    @Override
    public Optional<User> delete(User user) {
        logger.traceEntry("Deleting user: {} ", user);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from users where user_id = ?")) {
            preparedStatement.setLong(1, user.getID());
            int result = preparedStatement.executeUpdate();
            logger.trace("Deleted {} instances.", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
            return Optional.empty();
        }
        logger.traceExit();
        return Optional.of(user);
    }

    @Override
    public Optional<User> update(User user) {
        logger.traceEntry("Updating user: {} ", user);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("update users set username = ?,  password = ? where user_id = ?")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getID());
            int result = preparedStatement.executeUpdate();
            logger.trace("Updated {} instances.", result);
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
            return Optional.empty();
        }
        logger.traceExit();
        return Optional.of(user);
    }

    @Override
    public List<User> findAll() {
        logger.traceEntry("Finding all users.");
        Connection connection = jdbcUtils.getConnection();
        List<User> users = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from users")) {
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while(resultSet.next()) {
                    Long user_id = resultSet.getLong("user_id");
                    String username = resultSet.getString("username");
                    String password = resultSet.getString("password");
                    User user = new User(user_id, username, password);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        logger.traceExit();
        return users;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        logger.traceEntry("Finding user with username: {}", username);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username = ?")) {
            preparedStatement.setString(1, username);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    Long user_id = resultSet.getLong("user_id");
                    String password = resultSet.getString("password");
                    User user = new User(user_id, username, password);
                    logger.traceExit(user);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        logger.traceExit();
        return Optional.empty();
    }

    @Override
    public Optional<User> checkConnection(String username, String password) {
        logger.traceEntry("Check connection for user with username: {}", username);
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username = ? and password = ?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    Long user_id = resultSet.getLong("user_id");
                    User user = new User(user_id, username, password);
                    logger.traceExit(user);
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.err.println("DB error: " + e);
        }
        logger.traceExit();
        return Optional.empty();
    }
}
