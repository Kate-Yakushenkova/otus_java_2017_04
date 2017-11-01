package ru.otus.homework15.messageSystem.message;

import ru.otus.homework15.cache.CacheEngine;
import ru.otus.homework15.cache.MyElement;
import ru.otus.homework15.dataSets.User;
import ru.otus.homework15.db.DBService;
import ru.otus.homework15.messageSystem.Address;

import java.util.ArrayList;
import java.util.List;


public class GetCacheRequest extends RequestToDb {

    long count;

    public GetCacheRequest(Address from, Address to, int count) {
        super(from, to);
        this.count = count;
    }

    @Override
    public Response exec(DBService dbService) {
        return new GetCacheResponse(getTo(), getFrom(), convertCacheEngineToList(dbService.cacheEngine));
    }

    private List<MyElement<Long, User>> convertCacheEngineToList(CacheEngine<Long, User> cacheEngine) {
        List<MyElement<Long, User>> cache = new ArrayList<>();
        for (long i = 1; i <= count; i++) {
            MyElement<Long, User> element = cacheEngine.get(i);
            if (element != null) {
                cache.add(element);
            }
        }
        return cache;
    }

}
