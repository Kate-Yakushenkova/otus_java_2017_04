package ru.otus.homework;

import ru.otus.homework.test_classes.AnotherTestClass;
import ru.otus.homework.test_classes.FailTestClass;
import ru.otus.homework.test_classes.TestClass;

public class Main {

    public static void main(String[] args) {
        System.out.println("Тесты для массива классов:");
        TestUnit classTestUnit = new TestUnit(TestClass.class, AnotherTestClass.class, FailTestClass.class);
        classTestUnit.run();

        System.out.println("Тесты для пакета:");
        TestUnit packageTestUnit = new TestUnit("ru.otus.homework.test_classes");
        packageTestUnit.run();
    }

}
