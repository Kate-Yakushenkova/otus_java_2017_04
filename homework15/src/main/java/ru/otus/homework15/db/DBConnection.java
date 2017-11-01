package ru.otus.homework15.db;

import ru.otus.homework15.exception.DBException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private final Connection connection;

    public DBConnection() throws DBException {
        this.connection = getMySQLConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public void printConnectInfo() {
        try {
            System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("Driver: " + connection.getMetaData().getDriverName());
            System.out.println("Autocommit: " + connection.getAutoCommit());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getMySQLConnection() throws DBException {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            StringBuilder url = new StringBuilder();
            url.
                    append("jdbc:mysql://").
                    append("localhost:").
                    append("3306/").
                    append("db_kate?").
                    append("user=kate&").
                    append("password=Pa$$w0rd&").
                    append("useLegacyDatetimeCode=false&").
                    append("amp&").
                    append("serverTimezone=UTC");

            System.out.println("URL: " + url + "\n");

            return DriverManager.getConnection(url.toString());
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

}
