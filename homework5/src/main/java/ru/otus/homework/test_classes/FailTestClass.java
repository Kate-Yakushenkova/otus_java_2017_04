package ru.otus.homework.test_classes;

import ru.otus.homework.annotations.Test;

import static ru.otus.homework.asserts.Assert.*;

public class FailTestClass {

    @Test
    public void testNotNull() {
        assertNotNull(null);
    }

    @Test
    public void testNull() {
        assertNull(new Object());
    }

    @Test
    public void testTrue() {
        assertTrue(false);
    }

    @Test
    public void testFalse() {
        assertFalse(true);
    }

    @Test
    public void testFail() {
        assertFail();
    }
}
