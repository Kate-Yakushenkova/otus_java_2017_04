package ru.otus.homework.test_classes;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

import static ru.otus.homework.asserts.Assert.assertNotNull;
import static ru.otus.homework.asserts.Assert.assertNull;

public class TestClass {

    @Before
    public void before() {
        System.out.println("Before TestClass");
    }

    @Test
    public void test1() {
        System.out.println("First test TestClass");
        assertNull(null);
    }

    @Test
    public void test2() {
        System.out.println("Second test TestClass");
        assertNotNull(new Object());
    }

    @After
    public void after() {
        System.out.println("After TestClass");
    }

}
