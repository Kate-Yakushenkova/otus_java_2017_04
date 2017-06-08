package ru.otus.homework.homework9.main;

import ru.otus.homework.homework9.dataSets.User;
import ru.otus.homework.homework9.db.DBConnection;
import ru.otus.homework.homework9.db.UserDAO;
import ru.otus.homework.homework9.exception.DBException;

public class Main {

    /**
     * mysql> CREATE USER 'kate'@'localhost' IDENTIFIED BY 'Pa$$w0rd';
     * mysql> create database db_kate;
     * mysql> GRANT ALL PRIVILEGES ON db_kate.* TO 'kate'@'localhost';
     * mysql> FLUSH PRIVILEGES;
     */
    
    public static void main(String[] args) {
        UserDAO userDAO = createUserDAO();
        if (userDAO != null) {
            Long id = insertUser(new User("Tom", 25), userDAO);
            getUser(userDAO, id);
            Long id2 = insertUser(new User("Jerry", 5), userDAO);
            getUser(userDAO, id2);
            clear(userDAO);
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

    private static Long insertUser(User user, UserDAO userDAO) {
        try {
            long id = userDAO.addUser(user);
            System.out.println("User added with id: " + id);
            System.out.println();
            return id;
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void getUser(UserDAO userDAO, Long id) {
        try {
            if (id == null) {
                System.out.println("Пользователя с таким id не существует");
                return;
            }
            User savedUser = userDAO.getUser(id);
            System.out.println(id + ": " + savedUser);
            System.out.println();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

    private static void clear(UserDAO userDAO) {
        try {
            userDAO.clear();
        } catch (DBException e) {
            e.printStackTrace();
        }
    }

}
