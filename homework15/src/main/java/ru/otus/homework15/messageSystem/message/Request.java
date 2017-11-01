package ru.otus.homework15.messageSystem.message;

import ru.otus.homework15.messageSystem.Address;
import ru.otus.homework15.messageSystem.Addressee;

public abstract class Request extends Message {
    public abstract Response exec(Addressee addressee);

    public Request(Address from, Address to) {
        super(from, to);
    }

}
