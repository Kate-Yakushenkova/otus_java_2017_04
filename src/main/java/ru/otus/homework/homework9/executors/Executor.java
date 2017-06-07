package ru.otus.homework.homework9.executors;

import ru.otus.homework.homework9.dataSets.DataSet;

import java.sql.SQLException;

public interface Executor {

    <T extends DataSet> void save(T dataSet) throws SQLException;

    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;

    <T extends DataSet> long getId(T dataSet) throws SQLException;

    void createTable() throws SQLException;

    void clear() throws SQLException;

}
