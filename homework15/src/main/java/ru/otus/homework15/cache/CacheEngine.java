package ru.otus.homework15.cache;

import java.util.List;

public interface CacheEngine<K, V> {

    void put(MyElement<K, V> element);

    MyElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();

    public List getAllElements();
}
