package ru.otus.homework15.messageSystem.message;

import ru.otus.homework15.messageSystem.Address;

public abstract class Response<T> extends Message {

    private T result;

    public Response(Address from, Address to, T result) {
        super(from, to);
        this.result = result;
    }

    public T getResult() {
        return result;
    }
}
