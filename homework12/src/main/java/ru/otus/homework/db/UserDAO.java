package ru.otus.homework.db;

import ru.otus.homework.dataSets.User;
import ru.otus.homework.exception.DBException;
import ru.otus.homework.executors.ExecutorImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAO {

    private ExecutorImpl executor;

    public UserDAO(Connection connection) {
        this.executor = new ExecutorImpl(connection);
    }

    public long addUser(User user) throws DBException {
        try {
            executor.createTable(User.class);
            executor.save(user);
            return executor.getId(user);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public User getUser(long id) throws DBException {
        try {
            return executor.load(id, User.class);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void clear() throws DBException {
        try {
            executor.clear(User.class);
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
