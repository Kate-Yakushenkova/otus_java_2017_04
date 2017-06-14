package ru.otus.homework;

public class StringFactory extends ObjectFactory {

    @Override
    public Object create() {
        return new String("");
    }

    @Override
    public Object[] createArray(int size) {
        return new String[size];
    }

}
