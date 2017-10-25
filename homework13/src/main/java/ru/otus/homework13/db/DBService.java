package ru.otus.homework13.db;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework13.cache.CacheEngine;
import ru.otus.homework13.cache.MyElement;
import ru.otus.homework13.dataSets.User;
import ru.otus.homework13.exception.DBException;
import ru.otus.homework13.executors.ExecutorImpl;

import java.lang.ref.SoftReference;
import java.sql.SQLException;

public class DBService {

    private ExecutorImpl executor;

    public CacheEngine<Long, User> cacheEngine;

    @Autowired
    public DBService(CacheEngine<Long, User> cache) {
        try {
            this.cacheEngine = cache;
            DBConnection dbConnection = new DBConnection();
            dbConnection.printConnectInfo();
            this.executor = new ExecutorImpl(dbConnection.getConnection());
        } catch (DBException e) {
            e.printStackTrace();
        }
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
