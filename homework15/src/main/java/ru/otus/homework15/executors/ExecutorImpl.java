package ru.otus.homework15.executors;

import ru.otus.homework15.dataSets.DataSet;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ExecutorImpl implements Executor {

    private final Connection connection;

    public ExecutorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <T extends DataSet> void save(T dataSet) throws SQLException {
        Statement stmt = connection.createStatement();
        StringBuilder sql = createInsertQuery(dataSet);
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
        Statement stmt = connection.createStatement();
        StringBuilder sql = createSelectQueryToFindUserId(dataSet);
        stmt.execute(sql.toString());
        ResultSet result = stmt.getResultSet();
        long id = getIdFromResultSet(result);
        result.close();
        stmt.close();
        return id;
    }

    @Override
    public <T extends DataSet> void createTable(Class<T> clazz) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("create table if not exists " + clazz.getAnnotation(Table.class).name() + " (id bigint(20) not null auto_increment, name varchar(255) null default null, age int(3) not null default 0, primary key (id))");
    }

    @Override
    public <T extends DataSet> void clear(Class<T> clazz) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.execute("drop table " + clazz.getAnnotation(Table.class).name() + ";");
    }

    private <T extends DataSet> StringBuilder createInsertQuery(T dataSet) {
        StringBuilder sql = new StringBuilder("insert into ").append(getTableName(dataSet.getClass())).append(" (");
        List<Field> columns = getColumns(dataSet.getClass());
        for (Field field : columns) {
            sql.append(field.getAnnotation(Column.class).name()).append(",");
        }
        sql.deleteCharAt(sql.length() - 1);

        sql.append(") values (");
        for (Field field : columns) {
            field.setAccessible(true);
            try {
                sql.append("'").append(field.get(dataSet).toString()).append("'").append(",");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append(");");

        return sql;
    }

    private <T extends DataSet> StringBuilder createSelectQueryToFindUserId(T dataSet) {
        StringBuilder sql = new StringBuilder("select * from users where ");
        List<Field> columns = getColumns(dataSet.getClass());
        for (Field field : columns) {
            field.setAccessible(true);
            try {
                sql.append(field.getAnnotation(Column.class).name()).append("='").append(field.get(dataSet)).append("' and ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sql.delete(sql.length() - 5, sql.length());
        return sql;
    }

    private <T extends DataSet> String getTableName(Class<T> clazz) {
        return clazz.getAnnotation(Table.class).name();
    }

    private <T extends DataSet> List<Field> getColumns(Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        List<Field> columns = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                columns.add(field);
            }
        }
        return columns;
    }

    private long getIdFromResultSet(ResultSet result) throws SQLException {
        result.next();
        return result.getLong(1);
    }

    private <T extends DataSet> T getObjectFromResult(ResultSet result, Class<T> clazz) throws SQLException {
        result.next();
        try {
            Object user = clazz.newInstance();
            List<Field> columns = getColumns(clazz);
            for (Field field : columns) {
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
