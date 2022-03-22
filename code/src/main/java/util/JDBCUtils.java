package util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {

    private Properties jdbcProperties;

    private static final Logger logger = LogManager.getLogger();

    public JDBCUtils(Properties jdbcProperties) {
        this.jdbcProperties = jdbcProperties;
    }

    private Connection instance = null;

    private Connection getNewConnection() {
        logger.traceEntry();
        String url = jdbcProperties.getProperty("jdbc.url");
        String username = jdbcProperties.getProperty("jdbc.username");
        String password = jdbcProperties.getProperty("jdbc.password");
        logger.info("Trying to connect to database {}...", url);
        Connection connection = null;
        try {
            if (username != null && password != null)
                connection = DriverManager.getConnection(url, username, password);
            else
                connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error getting new connection: " + e);
        }
        logger.traceExit();
        return connection;
    }

    public Connection getConnection() {
        logger.traceEntry();
        try {
            if(instance == null || instance.isClosed())
                instance = getNewConnection();
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error getting connection: " + e);
        }
        logger.traceExit(instance);
        return instance;
    }
}
