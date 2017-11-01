package atm;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class ATMTest {

    private static final int CAPACITY = 10;
    private static final int NOMINAL_10 = 10;
    private static final int NOMINAL_5 = 5;
    private ATM atm;
    private Cell[] cells;

    @BeforeMethod
    public void init() {
        cells = new Cell[]{
                new Cell(CAPACITY, NOMINAL_5),
                new Cell(CAPACITY, NOMINAL_10)
        };
        atm = new ATM(Arrays.asList(cells));
    }

    @Test
    public void testGetBalance() {
        assertEquals(CAPACITY*NOMINAL_5 + CAPACITY * NOMINAL_10, atm.getBalance());
    }

    @Test
    public void testSetInitialState() {
        cells[0].withdraw(5);
        cells[1].withdraw(5);
        assertEquals((CAPACITY - 5) * NOMINAL_5 + (CAPACITY - 5) * NOMINAL_10, atm.getBalance());
        atm.setInitialState();
        assertEquals(CAPACITY*NOMINAL_5 + CAPACITY * NOMINAL_10, atm.getBalance());
    }

    @Test
    public void testWithdraw() {
        assertTrue(atm.withdraw(10));
        int balance = CAPACITY*NOMINAL_5 + (CAPACITY - 1) * NOMINAL_10;
        Assert.assertEquals(balance, atm.getBalance());

        Assert.assertFalse(atm.withdraw(4));
        Assert.assertEquals(balance, atm.getBalance());

        Assert.assertFalse(atm.withdraw(702));
        Assert.assertEquals(balance, atm.getBalance());

        assertTrue(atm.withdraw(5));
        balance -= 5;
        Assert.assertEquals(balance, atm.getBalance());
    }

}
