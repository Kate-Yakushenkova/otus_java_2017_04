package ru.otus.homework.homework9.executors;

import ru.otus.homework.homework9.dataSets.DataSet;

import javax.persistence.Column;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserExecutor implements Executor {

    private final Connection connection;

    public UserExecutor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void save(T dataSet) throws SQLException {
//        Вариант, если известен тип
//        User user = (User) dataSet;
//        stmt.execute("insert into users (name, age) values ('" + user.getName() + "', '" + user.getAge() + "');");

//        Вариант работы с дженериком через reflection
        StringBuilder sql = new StringBuilder("insert into users (");
        Field[] fields = dataSet.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                sql.append(field.getName()).append(",");
            }
        }
        sql.deleteCharAt(sql.length() - 1);

        sql.append(") values (");
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Column.class)) {
                try {
                    sql.append("'").append(field.get(dataSet).toString()).append("'").append(",");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(");");

        Statement stmt = connection.createStatement();
        stmt.execute(sql.toString());

        stmt.close();
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("select * from users where id='" + id + "';");
        ResultSet result = stmt.getResultSet();
        T user = getObjectFromResult(result, clazz);
        result.close();
        stmt.close();
        return user;
    }

    @Override
    public <T extends DataSet> long getId(T dataSet) throws SQLException {
//        Вариант работы, если известен тип
//        User user = (User) dataSet;
//        stmt.execute("select * from users where name='" + user.getName() + "' and age='" + user.getAge() + "';");

//        Вариант работы с дженериком
        Statement stmt = connection.createStatement();
        StringBuilder sql = new StringBuilder("select * from users where ");
        Field[] fields = dataSet.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                try {
                    sql.append(field.getName()).append("='").append(field.get(dataSet)).append("' and ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        sql.delete(sql.length() - 5, sql.length());
        stmt.execute(sql.toString());
        ResultSet result = stmt.getResultSet();
        long id = getIdFromResultSet(result);
        result.close();
        stmt.close();
        return id;
    }

    @Override
    public void createTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("create table if not exists users (id bigint(20) not null auto_increment default null, name varchar(255) null default null, age int(3) not null default 0, primary key (id))");
    }

    @Override
    public void clear() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("drop table users");
    }

    private long getIdFromResultSet(ResultSet result) throws SQLException {
        result.next();
        return result.getLong(1);
    }

    private <T extends DataSet> T getObjectFromResult(ResultSet result, Class<T> clazz) throws SQLException {
        result.next();
        try {
//            Вариант работы, если известен тип
//            User user = new User(result.getString(2), result.getInt(3));

//            Вариант работы с дженериком
            Object user = clazz.newInstance();
            Field[] fields = user.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(user, result.getObject(field.getName()));
            }
            return (T) user;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
