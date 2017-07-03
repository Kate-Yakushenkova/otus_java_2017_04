package ru.otus.homework.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CacheEngineImpl<K, V> implements CacheEngine<K, V> {

    private final int LIFE_TIME_MS = 1000;
    private final int ACCESS_TIME_MS = 500;

    private int max_size;

    private Map<K, MyElement<K, V>> cache = new HashMap<>();
    private int hit;
    private int miss;

    private Timer timer = new Timer();

    public CacheEngineImpl(int size) {
        this.max_size = size;
    }

    @Override
    public void put(MyElement<K, V> element) {
        if (cache.size() >= max_size) {
            K firstKey = cache.keySet().iterator().next();
            cache.remove(firstKey);
        }
        K key = element.getKey();
        cache.put(key, element);

        TimerTask lifeTimerTask = createLifeTimerTask(key);
        timer.schedule(lifeTimerTask, LIFE_TIME_MS);
        TimerTask accessTimerTask = createAccessTimerTask(key);
        timer.schedule(accessTimerTask, ACCESS_TIME_MS);
    }

    @Override
    public MyElement<K, V> get(K key) {
        MyElement<K, V> element = cache.get(key);
        if (element != null) {
            hit++;
            element.setAccessed();
        } else {
            miss++;
        }
        return element;
    }

    @Override
    public int getHitCount() {
        return hit;
    }

    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {
        timer.cancel();
    }

    private TimerTask createLifeTimerTask(final K key) {
        return new TimerTask() {
            @Override
            public void run() {
                MyElement<K, V> e = cache.get(key);
                if (e == null || e.getCreationTime() + LIFE_TIME_MS > System.currentTimeMillis()) {
                    cache.remove(key);
                }
            }
        };
    }

    private TimerTask createAccessTimerTask(final K key) {
        return new TimerTask() {
            @Override
            public void run() {
                MyElement<K, V> e = cache.get(key);
                if (e == null || e.getLastAccessTime() + ACCESS_TIME_MS > System.currentTimeMillis()) {
                    cache.remove(key);
                }
            }
        };
    }
}
