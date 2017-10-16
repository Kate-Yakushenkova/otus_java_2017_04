package ru.otus.homework.test_classes;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

import static ru.otus.homework.asserts.Assert.assertFalse;
import static ru.otus.homework.asserts.Assert.assertTrue;

public class AnotherTestClass {

    @Before
    public void before() {
        System.out.println("Before AnotherTestClass");
    }

    @Test
    public void test1() {
        System.out.println("First test AnotherTestClass");
        assertTrue(true);
    }

    @Test
    public void test2() {
        System.out.println("Second test AnotherTestClass");
        assertFalse(false);
    }

    @After
    public void after() {
        System.out.println("After AnotherTestClass");
    }

}
