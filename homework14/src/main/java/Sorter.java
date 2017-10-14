import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Sorter {

    private static final int THREAD_NUMBER = 10;
    private AtomicInteger threadCount;

    public int[] sort(int[] array) {
        threadCount = new AtomicInteger(1);
        Helper helper = new Helper(array);
        return helper.getResult();
    }

    private static int[] merge(int[] array1, int[] array2) {
        int[] newArray = new int[array1.length + array2.length];
        int i = 0, j = 0, k = 0;
        for (; i < array1.length && j < array2.length; ) {
            if (array1[i] < array2[j]) {
                newArray[k] = array1[i];
                i++;
            } else {
                newArray[k] = array2[j];
                j++;
            }
            k++;
        }
        for (; i < array1.length; i++, k++) {
            newArray[k] = array1[i];
        }
        for (; j < array2.length; j++, k++) {
            newArray[k] = array2[j];
        }
        return newArray;
    }

    private class Helper implements Runnable {

        Thread thread;
        int[] array;
        int[] result;

        public Helper(int[] array) {
            this.array = array;
            thread = new Thread(this);
            thread.start();
        }

        public int[] getResult() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public void run() {
            System.out.println(threadCount);
            if (array.length > 1) {
                int[] left = Arrays.copyOfRange(array, 0, array.length / 2);
                int[] right = Arrays.copyOfRange(array, array.length / 2, array.length);
                if (threadCount.addAndGet(2) < THREAD_NUMBER) {
                    Helper h1 = new Helper(left);
                    Helper h2 = new Helper(right);
                    result = merge(h1.getResult(), h2.getResult());
                } else {
                    Arrays.sort(left);
                    Arrays.sort(right);
                    result = merge(left, right);
                }
            } else {
                result = array;
            }
        }
    }
}
