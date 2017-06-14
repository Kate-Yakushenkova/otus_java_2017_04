package ru.otus.homework;

public class ObjectMeasuring {

    public static void main(String[] args) {
        System.out.println("\nИзмерение размера Object\n");
        System.out.println("\nРазмер Object: " + Measure.measureSize(new ObjectFactory()) + " байт\n");
        System.gc();
        System.out.println("\nИзмерение размера String\n");
        System.out.println("\nРазмер String: " + Measure.measureSize(new StringFactory()) + " байт\n");
        System.gc();
        System.out.println("\nЗависимость размера пустого массива от количества элементов\n");
        Measure.measureSizeObjectEmptyArray();
    }

}
