package ru.otus.homework.homework3;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class MyArrayList<T> implements List<T>, Iterable<T> {

    private Object[] array = new Object[10];
    private int size;

    public MyArrayList() {
        array = new Object[10];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return (indexOf(o) >= 0);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            int index;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                if (index >= size) {
                    throw new NoSuchElementException();
                }
                T element = (T) array[index];
                index++;
                return element;
            }
        };
    }

    @Override
    public ListIterator<T> listIterator() {
        return new ListIterator<T>() {

            int index;
            int lastReturn = -1;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public T next() {
                if (index >= size) {
                    throw new NoSuchElementException();
                }
                T element = (T) array[index];
                lastReturn = index;
                index++;
                return element;
            }

            @Override
            public boolean hasPrevious() {
                return index >= 0;
            }

            @Override
            public T previous() {
                if (index < 0) {
                    throw new NoSuchElementException();
                }
                T element = (T) array[index];
                lastReturn = index;
                index--;
                return element;
            }

            @Override
            public int nextIndex() {
                return index;
            }

            @Override
            public int previousIndex() {
                return index - 1;
            }

            @Override
            public void remove() {
                MyArrayList.this.remove(index);
                index = lastReturn;
                lastReturn = -1;
            }

            @Override
            public void set(T t) {
                MyArrayList.this.set(lastReturn, t);
            }

            @Override
            public void add(T t) {
                MyArrayList.this.add(index, t);
                index++;
                lastReturn = -1;
            }
        };
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }

    @Override
    public boolean add(T t) {
        if (size == array.length) {
            array = Arrays.copyOf(array, 2 * size);
        }
        array[size] = t;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return (T) array[index];
    }

    @Override
    public T set(int index, T element) {
        if (index > array.length) {
            array = Arrays.copyOf(array, 2 * array.length);
        }
        T old = (T) array[index];
        array[index] = element;
        return old;
    }

    @Override
    public void add(int index, T element) {
        if (index > array.length) {
            array = Arrays.copyOf(array, 2 * array.length);
        }
        array[index] = element;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Object removed = array[index];
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        return (T) removed;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void sort(Comparator<? super T> c) {
        Arrays.sort((T[]) this.array, 0, size, c);
    }

    //Unsupported operations

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Spliterator<T> spliterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeIf(Predicate<? super T> filter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<T> stream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<T> parallelStream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        throw new UnsupportedOperationException();
    }
}
