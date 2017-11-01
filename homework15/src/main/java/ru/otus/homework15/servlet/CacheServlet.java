package ru.otus.homework15.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import ru.otus.homework15.cache.CacheDecorator;
import ru.otus.homework15.cache.MyElement;
import ru.otus.homework15.dataSets.User;
import ru.otus.homework15.messageSystem.*;
import ru.otus.homework15.messageSystem.message.CreateUserRequest;
import ru.otus.homework15.messageSystem.message.GetCacheRequest;
import ru.otus.homework15.messageSystem.message.LoadUserRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class CacheServlet extends HttpServlet {

    private static final int NUMBER_USER = 10;
    private static final String CACHE_PAGE = "cache.html";
    private static final Address address = new Address("cacheServlet");

    /**
     * mysql> CREATE USER 'kate'@'localhost' IDENTIFIED BY 'Pa$$w0rd';
     * mysql> create database db_kate;
     * mysql> GRANT ALL PRIVILEGES ON db_kate.* TO 'kate'@'localhost';
     * mysql> FLUSH PRIVILEGES;
     */

    @Autowired
    @Qualifier("messageSystemContext")
    private MessageSystemContext messageSystemContext;

    @Autowired
    @Qualifier("cacheDecorator")
    private CacheDecorator cacheDecorator;

    public CacheServlet() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!isAdmin(request)) {
            response.sendError(403);
            return;
        }

        try {
            createUsers(NUMBER_USER);
            getUsers(NUMBER_USER);
            getUsers(NUMBER_USER);
            Map<String, Object> parameters = new HashMap<>();
            long id = messageSystemContext.getMessageSystem().sendMessage(new GetCacheRequest(address, messageSystemContext.getDbAddress(), NUMBER_USER));
            List<MyElement<Long, User>> cache = (List<MyElement<Long, User>>) messageSystemContext.getMessageSystem().getResponse(id).getResult();
            parameters.put("cache", cache);
            parameters.put("hits", cacheDecorator.getHitCount());
            parameters.put("miss", cacheDecorator.getMissCount());

            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(TemplateProcessor.instance().getPage(CACHE_PAGE, parameters));

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }

    }

    private boolean isAdmin(HttpServletRequest request) {
        return (request.getSession().getAttribute("admin") != null) && (((Boolean) request.getSession().getAttribute("admin")));
    }

    private void getUsers(int number) throws InterruptedException, ExecutionException, TimeoutException {
        for (long i = 1; i <= number; i++) {
            long id = messageSystemContext.getMessageSystem().sendMessage(new LoadUserRequest(address, messageSystemContext.getDbAddress(), i));
            System.out.println(messageSystemContext.getMessageSystem().getResponse(id).getResult());
        }
        System.out.println();
    }

    private void createUsers(int number) throws InterruptedException, ExecutionException, TimeoutException {
        for (int i = 1; i <= number; i++) {
            User user = new User("Tom" + i, i);
            long id = messageSystemContext.getMessageSystem().sendMessage(new CreateUserRequest(address, messageSystemContext.getDbAddress(), user));
            System.out.println(messageSystemContext.getMessageSystem().getResponse(id).getResult());
        }
        System.out.println();
    }



}
