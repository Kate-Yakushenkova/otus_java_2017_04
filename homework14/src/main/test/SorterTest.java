import org.testng.annotations.Test;
import ru.otus.homework.ArrayGenerator;
import ru.otus.homework.Sorter;

import static org.testng.Assert.assertTrue;

public class SorterTest {

    @Test
    public void testSort() {
        Sorter<Integer> sorter = new Sorter<>();
        Integer[] array = new ArrayGenerator().generateArray(10, 100);

        array = sorter.sort(array);

        for (int i = 0; i < array.length - 1; i++) {
            assertTrue(array[i] <= array[i + 1]);
        }
    }

}
