import java.util.Random;

public class ArrayGenerator {

    public Integer[] generateArray(int n, int bound) {
        Integer[] array = new Integer[n];
        for (int i = 0; i < n; i++) {
            array[i] = new Random().nextInt(bound);
        }
        return array;
    }

}
