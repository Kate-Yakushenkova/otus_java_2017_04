package ru.otus.homework.db;

import ru.otus.homework.cache.CacheEngineImpl;
import ru.otus.homework.cache.MyElement;
import ru.otus.homework.dataSets.User;
import ru.otus.homework.exception.DBException;
import ru.otus.homework.executors.ExecutorImpl;

import java.lang.ref.SoftReference;
import java.sql.Connection;
import java.sql.SQLException;

public class DBService {

    private ExecutorImpl executor;

    public CacheEngineImpl<Long, User> cacheEngine = new CacheEngineImpl<>(5);

    public DBService(Connection connection) {
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
            MyElement<Long, User> element = cacheEngine.get(id);
            User user = null;
            if (element != null) {
                user = element.getValue().get();
            }
            if (user == null){
                user = executor.load(id, User.class);
                cacheEngine.put(new MyElement<>(id, new SoftReference<>(user)));
            }
            return user;
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }

    public void clear() throws DBException {
        try {
            executor.clear(User.class);
            cacheEngine.dispose();
        } catch (SQLException e) {
            throw new DBException(e);
        }
    }
}
