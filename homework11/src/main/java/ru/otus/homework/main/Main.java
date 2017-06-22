package ru.otus.homework.main;

import ru.otus.homework.cache.CacheEngineImpl;
import ru.otus.homework.cache.MyElement;
import ru.otus.homework.dataSets.User;
import ru.otus.homework.db.DBConnection;
import ru.otus.homework.db.DBService;
import ru.otus.homework.exception.DBException;

public class Main {

    private static final int NUMBER_USER = 10;

    /**
     * mysql> CREATE USER 'kate'@'localhost' IDENTIFIED BY 'Pa$$w0rd';
     * mysql> create database db_kate;
     * mysql> GRANT ALL PRIVILEGES ON db_kate.* TO 'kate'@'localhost';
     * mysql> FLUSH PRIVILEGES;
     */

    public static void main(String[] args) {
        DBService dbService = createUserDAO();
        if (dbService != null) {
            createUsers(NUMBER_USER, dbService);
            getUsers(NUMBER_USER, dbService);
            System.out.println("Hits: " + dbService.cacheEngine.getHitCount());
            System.out.println("Miss: " + dbService.cacheEngine.getMissCount());
            System.out.println();

            printCache(dbService.cacheEngine);
            System.out.println("Hits: " + dbService.cacheEngine.getHitCount());
            System.out.println("Miss: " + dbService.cacheEngine.getMissCount());

            clear(dbService);
        }
    }

    private static void getUsers(int number, DBService dbService) {
        for (int i = 1; i <= number; i++) {
            User user = getUser(dbService, (long) i);
        }
        System.out.println();
    }

    private static DBService createUserDAO() {
        try {
            DBConnection dbConnection = new DBConnection();
            dbConnection.printConnectInfo();
            System.out.println();
            return new DBService(dbConnection.getConnection());
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void createUsers(int number, DBService dbService) {
        for (int i = 1; i <= number; i++) {
            User user = new User("Tom" + i, i);
            insertUser(user, dbService);
        }
        System.out.println();
    }

    private static Long insertUser(User user, DBService dbService) {
        try {
            long id = dbService.addUser(user);
            System.out.println("User added with id: " + id);
            return id;
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static User getUser(DBService dbService, Long id) {
        try {
            if (id == null) {
                System.out.println("Пользователя с таким id не существует");
                return null;
            }
            User savedUser = dbService.getUser(id);
            System.out.println(id + ": " + savedUser);
            return savedUser;
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void printCache(CacheEngineImpl<Long, User> cacheEngine) {
        for (long i = 1; i <= NUMBER_USER; i++) {
            MyElement element = cacheEngine.get(i);
            System.out.println("User " + i + " in cache: " + (element == null ? null : element.getValue().get()));
        }
        System.out.println();
    }

    private static void clear(DBService dbService) {
        try {
            dbService.clear();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

}
