package ru.otus.homework.asserts;

public class Assert {

    private Assert() {}

    public static void assertTrue(Boolean actual) {
        if (!actual) {
            throw new AssertException("Your value is \"False\", but \"True\" expected");
        }
    }

    public static void assertFalse(Boolean actual) {
        if (actual) {
            throw new AssertException("Your value is \"True\", but \"False\" expected");
        }
    }

    public static void assertNotNull(Object actual) {
        if (actual == null) {
            throw new AssertException("Your value is \"null\", but \"not null\" expected");
        }
    }

    public static void assertNull(Object actual) {
        if (actual != null) {
            throw new AssertException("Your value is \"not null\", but \"null\" expected");
        }
    }

    public static void assertFail() {
        throw new AssertException("Fail");
    }

}
