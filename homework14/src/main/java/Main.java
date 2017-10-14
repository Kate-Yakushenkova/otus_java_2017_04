import java.util.Arrays;
import java.util.Random;

public class Main {

    private static final int N = 10;

    public static void main(String[] args) {
        Integer[] array = new ArrayGenerator().generateArray(N, 100);

        System.out.println("Исходный массив:");
        System.out.println(Arrays.toString(array));
        System.out.println();

        Sorter<Integer> sorter = new Sorter<>();
        array = sorter.sort(array);

        System.out.println("Отсортированный массив:");
        System.out.println(Arrays.toString(array));
    }

}
