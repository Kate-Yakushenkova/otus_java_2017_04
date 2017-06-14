package ru.otus.homework;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Benchmark implements BenchmarkMBean {

    int size;

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void setSize(int size) {
        this.size = size;
    }

    public void run() throws IOException, InterruptedException {
        Map<String, GCProperties> gc = new HashMap<>();
        List<String> list = new LinkedList<>();

        while (true) {
            for (int j = 0; j < size; j++) {
                list.add("num" + j);
            }
            for (int j = 0; j < size / 2; j++) {
                list.remove(0);
            }
            putStatisticIntoMap(gc);
            writeStatistic(gc, list.size());
            Thread.sleep(100);
        }
    }

    private void putStatisticIntoMap(Map<String, GCProperties> gc) {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbageCollector : garbageCollectorMXBeans) {
            if (gc.containsKey(garbageCollector.getName())) {
                GCProperties properties = gc.get(garbageCollector.getName());
                properties.setCount(garbageCollector.getCollectionCount());
                properties.setTime(garbageCollector.getCollectionTime());
                gc.put(garbageCollector.getName(), properties);
            } else {
                gc.put(garbageCollector.getName(), new GCProperties(garbageCollector.getCollectionTime(), garbageCollector.getCollectionCount()));
            }
        }
    }

    private void writeStatistic(Map<String, GCProperties> gc, int size) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter("statistic"));
        out.println("Количество элементов в списке: " + size);
        for (Map.Entry<String, GCProperties> gcProperties : gc.entrySet()) {
            out.println("Сборщик мусора: " + gcProperties.getKey());
            out.println("Общее число сборок: " + gcProperties.getValue().getCount());
            out.println("Общее время работы: " + gcProperties.getValue().getTime() + " миллисекунд");
        }
        out.flush();
        out.close();
    }

    private class GCProperties {

        private Long time;
        private Long count;

        public GCProperties(Long time, Long count) {
            this.time = time;
            this.count = count;
        }

        public Long getTime() {
            return time;
        }

        public void setTime(Long time) {
            this.time = time;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }

    }
}
