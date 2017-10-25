package ru.otus.homework13.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.homework13.cache.CacheEngine;
import ru.otus.homework13.cache.MyElement;
import ru.otus.homework13.dataSets.User;
import ru.otus.homework13.db.DBService;
import ru.otus.homework13.exception.DBException;

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
    @Autowired
    @Qualifier("dbService")
    private DBService dbService;

    public CacheServlet() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendError(403);
            return;
        }
        createUsers(NUMBER_USER);
        getUsers(NUMBER_USER);
        getUsers(NUMBER_USER);

        Map<String, Object> parameters = new HashMap<>();
        List<MyElement<Long, User>> cache = convertCacheEngineToList(dbService.cacheEngine);
        parameters.put("cache", cache);
        parameters.put("hits", dbService.cacheEngine.getHitCount());
        parameters.put("miss", dbService.cacheEngine.getMissCount());

        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(TemplateProcessor.instance().getPage(CACHE_PAGE, parameters));

        response.setStatus(HttpServletResponse.SC_OK);
    }

    private boolean isAdmin(HttpServletRequest request) {
        return (request.getSession().getAttribute("admin") != null) && (((Boolean) request.getSession().getAttribute("admin")));
    }

    private void getUsers(int number) {
        for (int i = 1; i <= number; i++) {
            User user = getUser((long) i);
        }
        System.out.println();
    }

    private void createUsers(int number) {
        for (int i = 1; i <= number; i++) {
            User user = new User("Tom" + i, i);
            insertUser(user);
        }
        System.out.println();
    }

    private Long insertUser(User user) {
        try {
            long id = dbService.addUser(user);
            System.out.println("User added with id: " + id);
            return id;
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private User getUser(Long id) {
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

    private List<MyElement<Long, User>> convertCacheEngineToList(CacheEngine<Long, User> cacheEngine) {
        List<MyElement<Long, User>> cache = new ArrayList<>();
        for (long i = 1; i <= NUMBER_USER; i++) {
            MyElement<Long, User> element = cacheEngine.get(i);
            if (element != null) {
                cache.add(element);
            }
        }
        return cache;
    }

    private void clear() {
        try {
            dbService.clear();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

}
