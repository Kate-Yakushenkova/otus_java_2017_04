package ru.otus.homework.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.otus.homework.servlet.CacheServlet;
import ru.otus.homework.servlet.LoginServlet;

public class Main {

    private static final int PORT = 8090;
    private static final String PUBLIC_HTML = "src/main/resources/public_html";

    public static void main(String[] args) throws Exception {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(LoginServlet.class, "/login");
        servletContextHandler.addServlet(CacheServlet.class, "/cache");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, servletContextHandler));

        server.start();
        server.join();
    }

}
