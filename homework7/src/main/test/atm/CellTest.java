package atm;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CellTest {

    private static final int CAPACITY = 10;
    private static final int NOMINAL = 10;
    private Cell cell;

    @BeforeMethod
    public void init() {
        cell = new Cell(CAPACITY, NOMINAL);
    }

    @Test
    public void testGetNominal() {
        assertEquals(NOMINAL, cell.getNominal());
    }

    @Test
    public void testGetCount() {
        assertEquals(CAPACITY, cell.getCount());
        cell.withdraw(5);
        assertEquals(CAPACITY - 5, cell.getCount());
    }

    @Test
    public void testSetInitialState() {
        cell.withdraw(5);
        assertEquals(CAPACITY - 5, cell.getCount());
        cell.setInitialState();
        assertEquals(CAPACITY, cell.getCount());
    }

    @Test
    public void testWithdraw() {
        cell.withdraw(5);
        assertEquals(CAPACITY - 5, cell.getCount());
    }

}
