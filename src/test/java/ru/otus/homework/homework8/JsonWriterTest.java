package ru.otus.homework.homework8;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JsonWriterTest {

    private JsonWriter writer = new JsonWriter();
    private Gson gson = new Gson();

    @Test
    public void testNullToJsonString() {
        String result = writer.toJsonString(null);
        String expected = gson.toJson(null);

        assertEquals(expected, result);
    }

    @Test
    public void testObjectToJsonString() {
        Person testPerson = createPerson();

        String result = writer.toJsonString(testPerson);
        String expected = gson.toJson(testPerson);

        assertEquals(expected, result);
    }

    @Test
    public void testArrayObjectsToJsonString() {
        Person[] testArrayPersons = createArrayPersons();

        String result = writer.toJsonString(testArrayPersons);
        String expected = gson.toJson(testArrayPersons);

        assertEquals(expected, result);
    }

    @Test
    public void testArrayPrimitivesToJsonString() {
        int[] array = {0, 5, 10};

        String result = writer.toJsonString(array);
        String expected = gson.toJson(array);

        assertEquals(expected, result);
    }

    @Test
    public void testListObjectsToJsonString() {
        List<Person> testListPersons = createListPersons();

        String result = writer.toJsonString(testListPersons);
        String expected = gson.toJson(testListPersons);

        assertEquals(expected, result);
    }

    private Person createPerson() {
        Person person = new Person();
        person.setName("Tom");
        person.setAge(10);
        return person;
    }

    private Person[] createArrayPersons() {
        Person[] persons = new Person[3];
        persons[0] = null;
        persons[1] = new Person();
        persons[2] = createPerson();
        return persons;
    }

    private List<Person> createListPersons() {
        List<Person> persons = new ArrayList<>();
        persons.add(null);
        persons.add(new Person());
        persons.add(createPerson());
        return persons;
    }
}
