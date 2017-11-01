package ru.otus.homework15.cache;

import java.util.List;


public class CacheDecorator {

    private CacheEngine cache;

    public CacheDecorator(CacheEngine cache) {
        this.cache = cache;
    }

    public int getHitCount() {
        return cache.getHitCount();
    }

    public int getMissCount() {
        return cache.getHitCount();
    }

    public List getAllElements() {
        return cache.getAllElements();
    }
}
