package ru.otus.homework15.messageSystem.message;


import ru.otus.homework15.dataSets.User;
import ru.otus.homework15.messageSystem.Address;

public class LoadUserResponse extends Response<User> {

    public LoadUserResponse(Address from, Address to, User user) {
        super(from, to, user);
    }

}
