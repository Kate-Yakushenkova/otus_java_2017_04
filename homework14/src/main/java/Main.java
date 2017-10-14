import java.util.Arrays;
import java.util.Random;

public class Main {

    private static final int N = 1_000_000;

    public static void main(String[] args) {
        int[] array = generateArray(N);
//        System.out.println(Arrays.toString(array));

        long start = System.currentTimeMillis();
        Sorter sorter = new Sorter();
        int[] array2 = null;
//        for (int i = 0; i < 20; i++) {
            array2 = sorter.sort(array);
//        }
        long end = System.currentTimeMillis();

//        System.out.println(Arrays.toString(array2));

        System.out.println(end - start);

    }

    private static int[] generateArray(int n) {
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = new Random().nextInt(100);
        }
        return array;
    }

}
