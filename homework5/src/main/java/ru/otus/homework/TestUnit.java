package ru.otus.homework;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;
import ru.otus.homework.asserts.AssertException;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class TestUnit {

    private Class[] classes;

    private int countTotalTests;
    private int countSuccessTests;
    private int countFailTests;

    public TestUnit(Class... classes) {
        this.classes = classes;
    }

    public TestUnit(String packageName) {
        List<ClassLoader> classLoadersList = new ArrayList<>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));

        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        this.classes = classes.toArray(new Class[classes.size()]);
    }

    public void run() {
        for (Class clazz : classes) {
            runTests(clazz);
        }
        System.out.println();
        System.out.println("Всего тестов: " + countTotalTests);
        System.out.println("Провалившиеся тесты: " + countFailTests);
        System.out.println("Успешные тесты: " + countSuccessTests);
        System.out.println();
    }

    private void runTests(Class clazz) {
        System.out.println();
        System.out.println("Тесты для класса: " + clazz.getName());
        Object testObject = ReflectionHelper.instantiate(clazz);
        Method[] test = getTestMethods(clazz.getMethods());
        for (Method testMethod : test) {
            try {
                System.out.println();
                System.out.println("Метод: " + testMethod.getName());
                Method before = getBeforeMethod(clazz.getMethods());
                if (before != null) {
                    ReflectionHelper.callMethod(testObject, before.getName());
                }
                ReflectionHelper.callMethod(testObject, testMethod.getName());
                Method after = getAfterMethod(clazz.getMethods());
                if (after != null) {
                    ReflectionHelper.callMethod(testObject, after.getName());
                }
                countSuccessTests++;
                System.out.println("SUCCESS");
            } catch (AssertException e) {
                countFailTests++;
                System.out.println("FAIL: " + e.getMessage());
            } catch (Exception e) {
                countFailTests++;
                e.printStackTrace();
            }
            countTotalTests++;
            ;
        }
    }

    private Method getBeforeMethod(Method[] methods) {
        for (Method m : methods) {
            if (m.getAnnotation(Before.class) != null)
                return m;
        }
        return null;
    }

    private Method[] getTestMethods(Method[] methods) {
        List<Method> testMethods = new ArrayList<>();
        for (Method m : methods) {
            if (m.getAnnotation(Test.class) != null)
                testMethods.add(m);
        }
        return testMethods.toArray(new Method[testMethods.size()]);
    }

    private Method getAfterMethod(Method[] methods) {
        for (Method m : methods) {
            if (m.getAnnotation(After.class) != null)
                return m;
        }
        return null;
    }

}
