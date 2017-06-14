package ru.otus.homework;

public class Measure {

    private static final int SIZE = 10_000_000;
    private static final int TESTS = 10;

    public static long measureSize(ObjectFactory factory) {
        Object[] arrayObject;
        long sizeObject = 0;
        for (int n = 1; n <= TESTS; n++) {
            System.out.println("\n\tТест: " + n);
            sleep();
            long withoutArraySize = Runtime.getRuntime().freeMemory();
            arrayObject = factory.createArray(SIZE);
            System.out.println("Создали массив размера: " + SIZE);
            long emptyArraySize = withoutArraySize - Runtime.getRuntime().freeMemory();
            sleep();
            for (int i = 0; i < SIZE; i++) {
                arrayObject[i] = factory.create();
            }
            System.out.println("Заполнили массив");
            System.out.println("(Оставшаяся память: " + Runtime.getRuntime().freeMemory() + " байт)");
            long fullArraySize = withoutArraySize - emptyArraySize - Runtime.getRuntime().freeMemory();
            sleep();
            arrayObject = null;
            Runtime.getRuntime().gc();
            sleep();
            System.out.println("Освободили память");
            System.out.println("(Освободившаяся память: " + Runtime.getRuntime().freeMemory() + " байт)");
            sleep();
            sizeObject += Math.round(1. * fullArraySize / SIZE);
        }
        return Math.round(1. * sizeObject / TESTS);
    }

    public static void measureSizeObjectEmptyArray() {
        Object[] arrayObject;
        for (int i = 1; i < SIZE; i *= 10) {
            long memoryWithoutArray = Runtime.getRuntime().freeMemory();
            arrayObject = new Object[i];
            long sizeEmptyArray = memoryWithoutArray - Runtime.getRuntime().freeMemory();
            System.out.println("Массив из " + i + " элементов занимает " + sizeEmptyArray + " байт");
            arrayObject = null;
            sleep();
            System.gc();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
