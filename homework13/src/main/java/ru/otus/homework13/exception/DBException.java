package ru.otus.homework13.exception;

public class DBException extends Exception {

    public DBException(Exception e) {
        super(e);
    }

}
