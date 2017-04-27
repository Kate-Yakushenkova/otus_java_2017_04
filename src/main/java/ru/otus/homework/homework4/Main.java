package ru.otus.homework.homework4;

import javax.management.*;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        int size = 5000;

        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");
        Benchmark mbean = new Benchmark();
        mbs.registerMBean(mbean, name);

        mbean.setSize(size);
        mbean.run();

    }

}
