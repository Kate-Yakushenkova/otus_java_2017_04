package ru.otus.homework15.db;

import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.homework15.cache.CacheEngine;
import ru.otus.homework15.cache.MyElement;
import ru.otus.homework15.dataSets.User;
import ru.otus.homework15.exception.DBException;
import ru.otus.homework15.executors.ExecutorImpl;
import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.Addressee;
import ru.otus.homework15.messageSystem.MessageSystemContext;

import java.lang.ref.SoftReference;
import java.sql.SQLException;

public class DBService implements Addressee {

    private final Address address;

    private ExecutorImpl executor;

    public CacheEngine<Long, User> cacheEngine;

    @Autowired
    public DBService(CacheEngine<Long, User> cache, MessageSystemContext messageSystemContext) {
        this.address = messageSystemContext.getDbAddress();
        messageSystemContext.getMessageSystem().addAdressee(this);
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

    @Override
    public Address getAddress() {
        return address;
    }
}
