package ru.otus.homework.homework2;

public class ObjectMeasuring {

    private static final int SIZE = 10_000_000;
    private static final int TESTS = 10;

    public static void main(String[] args) {

        System.out.println("\n\nИзмерение размера Object\n");
        measureSizeObject();
        System.gc();
        sleep();
        System.out.println("\n\nИзмерение размера String\n");
        measureSizeString();
        System.gc();
        sleep();
        System.out.println("\n\nЗависимость размера пустого массива от количества элементов\n");
        measureSizeObjectEmptyArray();

    }

    private static void measureSizeObject() {
        Object[] arrayObject;
        long sizeObject = 0;
        long sizeEmptyArray = 0;
        for (int n = 1; n <= TESTS; n++) {
            System.out.println("\n\tТест: " + n);
            sleep();
            long withoutArraySize = Runtime.getRuntime().freeMemory();
            arrayObject = new Object[SIZE];
            System.out.println("Создали массив Object размера: " + SIZE);
            long emptyArraySize = withoutArraySize - Runtime.getRuntime().freeMemory();
            sizeEmptyArray += emptyArraySize;
            sleep();
            for (int i = 0; i < SIZE; i++) {
                arrayObject[i] = new Object();
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
        System.out.println();
        System.out.println("Размер пустого массива из Object: " + sizeEmptyArray / TESTS + " байт");
        System.out.println("Размер объекта Object: " + Math.round(1. * sizeObject / TESTS) + " байт");
    }

    private static void measureSizeString() {
        String[] arrayString;
        long sizeString = 0;
        long sizeEmptyArray = 0;
        for (int n = 1; n <= TESTS; n++) {
            System.out.println("\n\tТест: " + n);
            sleep();
            long withoutArrayMemory = Runtime.getRuntime().freeMemory();
            arrayString = new String[SIZE];
            System.out.println("Создали массив String размера: " + SIZE);
            long emptyArraySize = withoutArrayMemory - Runtime.getRuntime().freeMemory();
            sizeEmptyArray += emptyArraySize;
            sleep();
            for (int i = 0; i < SIZE; i++) {
                arrayString[i] = new String("");
            }
            System.out.println("Заполнили массив");
            System.out.println("(Оставшаяся память: " + Runtime.getRuntime().freeMemory() + " байт)");
            long fullArraySize = withoutArrayMemory - Runtime.getRuntime().freeMemory() - emptyArraySize;
            sleep();
            arrayString = null;
            Runtime.getRuntime().gc();
            sleep();
            System.out.println("Освободили память");
            System.out.println("(Освободившаяся память: " + Runtime.getRuntime().freeMemory() + " байт)");
            sleep();
            sizeString += Math.round(1. * fullArraySize / SIZE);
        }
        System.out.println();
        System.out.println("Размер пустого массива из String: " + Math.round(1. * sizeEmptyArray / TESTS) + " байт");
        System.out.println("Размер объекта String: " + Math.round(1. * sizeString / TESTS) + " байт");
    }

    private static void measureSizeObjectEmptyArray() {
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
