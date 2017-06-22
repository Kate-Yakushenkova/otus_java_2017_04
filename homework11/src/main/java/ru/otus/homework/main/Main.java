package ru.otus.homework.main;

import ru.otus.homework.cache.CacheEngineImpl;
import ru.otus.homework.cache.MyElement;
import ru.otus.homework.dataSets.User;
import ru.otus.homework.db.DBConnection;
import ru.otus.homework.db.UserDAO;
import ru.otus.homework.exception.DBException;

import java.lang.ref.SoftReference;

public class Main {

    private static final int NUMBER_USER = 10000;

    /**
     * mysql> CREATE USER 'kate'@'localhost' IDENTIFIED BY 'Pa$$w0rd';
     * mysql> create database db_kate;
     * mysql> GRANT ALL PRIVILEGES ON db_kate.* TO 'kate'@'localhost';
     * mysql> FLUSH PRIVILEGES;
     */
    
    public static void main(String[] args) {
        UserDAO userDAO = createUserDAO();
        if (userDAO != null) {
            CacheEngineImpl<Integer, User> cacheEngine = new CacheEngineImpl<>(5);

            createUsers(NUMBER_USER, userDAO);

            for (int i = 1; i <= NUMBER_USER; i++) {
                User user = getUser(userDAO, (long)i);
                cacheEngine.put(new MyElement<>(i, new SoftReference<>(user)));
            }
            System.out.println();

            printCache(cacheEngine);

            System.out.println("Hits: " + cacheEngine.getHitCount());
            System.out.println("Miss: " + cacheEngine.getMissCount());

            clear(userDAO);
            cacheEngine.dispose();
        }
    }

    private static UserDAO createUserDAO() {
        try {
            DBConnection dbConnection = new DBConnection();
            dbConnection.printConnectInfo();
            System.out.println();
            return new UserDAO(dbConnection.getConnection());
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void createUsers(int number, UserDAO userDAO) {
        for (int i = 1; i <= number; i++) {
            User user = new User("Tom" + i, i);
            insertUser(user, userDAO);
        }
        System.out.println();
    }

    private static Long insertUser(User user, UserDAO userDAO) {
        try {
            long id = userDAO.addUser(user);
            System.out.println("User added with id: " + id);
            return id;
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static User getUser(UserDAO userDAO, Long id) {
        try {
            if (id == null) {
                System.out.println("Пользователя с таким id не существует");
                return null;
            }
            User savedUser = userDAO.getUser(id);
            System.out.println(id + ": " + savedUser);
            return savedUser;
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void printCache(CacheEngineImpl<Integer, User> cacheEngine) {
        for (int i = 1; i <= NUMBER_USER; i++) {
            MyElement element = cacheEngine.get(i);
            System.out.println("User " + i + " in cache: " + (element == null ? null : element.getValue().get()));
        }
        System.out.println();
    }

    private static void clear(UserDAO userDAO) {
        try {
            userDAO.clear();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

}
