package ru.otus.homework.homework9.main;

import ru.otus.homework.homework9.dataSets.User;
import ru.otus.homework.homework9.db.DBConnection;
import ru.otus.homework.homework9.db.UserDAO;
import ru.otus.homework.homework9.exception.DBException;

public class Main {

    public static void main(String[] args) {
        UserDAO userDAO = createUserDAO();
        if (userDAO != null) {
            Long id = insertUser(userDAO);
            getUser(userDAO, id);
            clear(userDAO);
        }
    }

    private static UserDAO createUserDAO() {
        try {
            DBConnection dbConnection = new DBConnection();
            dbConnection.printConnectInfo();
            return new UserDAO(dbConnection.getConnection());
        } catch (DBException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Long insertUser(UserDAO userDAO) {
        try {
            User user = new User("Tom", 25);
            long id = userDAO.addUser(user);
            System.out.println("User added with id: " + id);
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
