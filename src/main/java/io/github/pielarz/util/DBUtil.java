package io.github.pielarz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connection to the database.
 *
 * DB_URL holds and address to the database
 *
 * DB_NAME holds database name
 *
 * DB_PARAMS ssl, character encoding, timezone etc.
 *
 * DB_USER holds username/login to database
 *
 * DB_PASSWORD holds password to database
 */

//jdbc:mysql://localhost:3306/db_name?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
public class DBUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "school";
    private static final String DB_PARAMS = "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "12345678";

    //This method will be used in Dao classes
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL + DB_NAME + DB_PARAMS, DB_USER, DB_PASSWORD);
    }
}
