package ru.otus.homework;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;

public class JsonWriter {

    public String toJsonString(Object object) {
        try {
            return serializeFieldValue(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    private String serializeObject(Object object) throws IllegalAccessException {
        if (object == null) {
            return "null";
        }
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("{");
        Class objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.get(object) != null) {
                jsonString.append(serializeFieldName(field));
                jsonString.append(":");
                jsonString.append(serializeFieldValue(field.get(object)));
                jsonString.append(",");
            }
        }
        if (jsonString.length() > 0) {
            jsonString.deleteCharAt(jsonString.length() - 1);
        }
        jsonString.append("}");
        return jsonString.toString();
    }

    private String serializeFieldName(Field field) {
        return "\"" + field.getName() + "\"";
    }

    private String serializeFieldValue(Object fieldValue) throws IllegalAccessException {
        StringBuilder jsonString = new StringBuilder();
        if (fieldValue == null) {
            jsonString.append("null");
            return jsonString.toString();
        }
        Class type = fieldValue.getClass();
        if (type.equals(String.class) || type.equals(char.class) || type.equals(Character.class)) {
            jsonString.append("\"").append(fieldValue).append("\"");
        } else if (type.isPrimitive() || Number.class.isInstance(fieldValue)) {
            jsonString.append(fieldValue);
        } else if (type.isArray()) {
            jsonString.append(serializeArray(fieldValue));
        } else if (Collection.class.isInstance(fieldValue)) {
            jsonString.append(serializeCollection(fieldValue));
        } else {
            jsonString.append(serializeObject(fieldValue));
        }
        return jsonString.toString();
    }

    private String serializeCollection(Object fieldValue) throws IllegalAccessException {
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("[");
        Collection collection = (Collection) fieldValue;
        for (Object object : collection) {
            jsonString.append(serializeFieldValue(object));
            jsonString.append(",");
        }
        jsonString.deleteCharAt(jsonString.length() - 1);
        jsonString.append("]");
        return jsonString.toString();
    }

    private String serializeArray(Object fieldValue) throws IllegalAccessException {
        StringBuilder jsonString = new StringBuilder();
        jsonString.append("[");
        int length = Array.getLength(fieldValue);
        for (int i = 0; i < length; i++) {
            Object element = Array.get(fieldValue, i);
            jsonString.append(serializeFieldValue(element));
            jsonString.append(",");
        }
        jsonString.deleteCharAt(jsonString.length() - 1);
        jsonString.append("]");
        return jsonString.toString();
    }

}
