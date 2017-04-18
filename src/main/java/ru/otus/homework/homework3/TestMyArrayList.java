package ru.otus.homework.homework3;

import java.util.Collections;
import java.util.Comparator;

public class TestMyArrayList {

    public static void main(String[] args) {
        MyArrayList<Integer> myList = fillList();
        MyArrayList<Integer> newList = addAll();
        copy(myList, newList);
        sort(myList);
    }

    private static MyArrayList<Integer> fillList() {
        System.out.println("Заполнение списка");
        MyArrayList<Integer> myList = new MyArrayList<>();
        for (int i = 0; i < 20; i++) {
            myList.add(i);
        }
        printList(myList);
        System.out.println("Удаление элемента");
        myList.remove(9);
        printList(myList);
        return myList;
    }

    private static MyArrayList<Integer> addAll() {
        System.out.println("Заполнение списка методом Collections.addAll");
        MyArrayList<Integer> newList = new MyArrayList<>();
        Collections.addAll(newList, 510, 120, 315, 132, 546, 378, 264, 64, 58, 90, 110);
        printList(newList);
        return newList;
    }

    private static void copy(MyArrayList<Integer> myList, MyArrayList<Integer> newList) {
        System.out.println("Копирование списка методом Collections.copy");
        Collections.copy(myList, newList);
        printList(myList);
    }

    private static void sort(MyArrayList<Integer> myList) {
        System.out.println("Сортировка списка");
        Collections.sort(myList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -o1.compareTo(o2);
            }
        });
        printList(myList);
    }

    private static void printList(MyArrayList<Integer> newList) {
        for (Integer x : newList) {
            System.out.print(x + " ");
        }
        System.out.println();
        System.out.println();
    }

}
