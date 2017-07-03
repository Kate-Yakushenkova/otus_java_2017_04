package ru.otus.homework.servlet;

import ru.otus.homework.cache.CacheEngineImpl;
import ru.otus.homework.cache.MyElement;
import ru.otus.homework.dataSets.User;
import ru.otus.homework.db.DBConnection;
import ru.otus.homework.db.DBService;
import ru.otus.homework.exception.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheServlet extends HttpServlet {

    private static final int NUMBER_USER = 10;
    private static final String CACHE_PAGE = "cache.html";

    /**
     * mysql> CREATE USER 'kate'@'localhost' IDENTIFIED BY 'Pa$$w0rd';
     * mysql> create database db_kate;
     * mysql> GRANT ALL PRIVILEGES ON db_kate.* TO 'kate'@'localhost';
     * mysql> FLUSH PRIVILEGES;
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBService dbService = createUserDAO();
        if (dbService != null) {
            createUsers(NUMBER_USER, dbService);
            getUsers(NUMBER_USER, dbService);

            Map<String, Object> parameters = new HashMap<>();
            List<MyElement<Long, User>> cache = convertCacheEngineToList(dbService.cacheEngine);
            parameters.put("cache", cache);
            parameters.put("hits", dbService.cacheEngine.getHitCount());
            parameters.put("miss", dbService.cacheEngine.getMissCount());

            response.getWriter().println(TemplateProcessor.instance().getPage(CACHE_PAGE, parameters));

            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);

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

    private List<MyElement<Long, User>> convertCacheEngineToList(CacheEngineImpl<Long, User> cacheEngine) {
        List<MyElement<Long, User>> cache = new ArrayList<>();
        for (long i = 1; i <= NUMBER_USER; i++) {
            MyElement<Long, User> element = cacheEngine.get(i);
            if (element != null) {
                cache.add(element);
            }
        }
        return cache;
    }

    private static void clear(DBService dbService) {
        try {
            dbService.clear();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

}
