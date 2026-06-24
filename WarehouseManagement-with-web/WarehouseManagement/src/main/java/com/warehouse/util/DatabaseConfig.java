package com.warehouse.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DatabaseConfig - Manages database connections and configurations
 */
public class DatabaseConfig {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    private static final String CONFIG_FILE = "application.properties";
    private static Properties properties;
    
    static {
        properties = new Properties();
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                logger.warn("Property file not found. Using default settings.");
            } else {
                properties.load(input);
                logger.info("Database configuration loaded successfully");
            }
        } catch (IOException e) {
            logger.error("Failed to load database configuration", e);
        }
    }

    private static final String DRIVER = getProperty("db.driver", "com.mysql.cj.jdbc.Driver");
     private static final String URL =
     System.getenv("DB_URL");

     private static final String USERNAME =
      System.getenv("DB_USER");

     private static final String PASSWORD =
     System.getenv("DB_PASSWORD");

    /**
     * Get property value from configuration
     */
    private static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Initialize JDBC Driver
     */
    static {
        try {
            Class.forName(DRIVER);
            logger.info("JDBC Driver loaded successfully");
        } catch (ClassNotFoundException e) {
            logger.error("Failed to load JDBC driver", e);
        }
    }

    /**
     * Get database connection
     */
    // public static Connection getConnection() throws SQLException {
    //     try {
    //         Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
    //         logger.debug("Database connection established");
    //         return connection;
    //     } catch (SQLException e) {
    //         logger.error("Failed to establish database connection", e);
    //         throw e;
    //     }
    // }

    public static Connection getConnection() throws SQLException {

    System.out.println("DB URL = " + URL);
    System.out.println("DB USER = " + USERNAME);

    Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

    System.out.println("DATABASE CONNECTED SUCCESSFULLY");

    return connection;
}

    /**
     * Test database connectivity
     */
    public static boolean testConnection() {
        try (Connection connection = getConnection()) {
            logger.info("Database connection test successful");
            return true;
        } catch (SQLException e) {
            logger.error("Database connection test failed", e);
            return false;
        }
    }

    /**
     * Get database URL
     */
    public static String getUrl() {
        return URL;
    }

    /**
     * Get database username
     */
    public static String getUsername() {
        return USERNAME;
    }
}
