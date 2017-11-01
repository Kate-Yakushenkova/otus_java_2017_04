package ru.otus.homework15.messageSystem.message;

import ru.otus.homework15.messageSystem.Address;

public class CreateUserResponse extends Response<Long> {

    public CreateUserResponse(Address from, Address to, Long id) {
        super(from, to, id);
    }

}
