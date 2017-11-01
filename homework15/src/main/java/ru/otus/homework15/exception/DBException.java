package ru.otus.homework15.exception;

public class DBException extends Exception {

    public DBException(Exception e) {
        super(e);
    }

}
