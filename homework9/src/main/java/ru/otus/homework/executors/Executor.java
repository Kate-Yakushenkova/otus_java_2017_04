package ru.otus.homework.executors;

import ru.otus.homework.dataSets.DataSet;

import java.sql.SQLException;

public interface Executor {

    <T extends DataSet> void save(T dataSet) throws SQLException;

    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;

    <T extends DataSet> long getId(T dataSet) throws SQLException;

    <T extends DataSet> void createTable(Class<T> clazz) throws SQLException;

    <T extends DataSet> void clear(Class<T> clazz) throws SQLException;

}
