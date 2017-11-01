package ru.otus.homework15.messageSystem.message;

import ru.otus.homework15.messageSystem.Address;

import java.util.List;


public class GetCacheResponse extends Response<List> {

    public GetCacheResponse(Address from, Address to, List result) {
        super(from, to, result);
    }

}
