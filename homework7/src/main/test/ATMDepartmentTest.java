import atm.ATM;
import atm.Cell;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;

import static org.testng.Assert.assertEquals;

public class ATMDepartmentTest {

    private ATMDepartment atmDepartment;
    private ATM atm1;
    private ATM atm2;

    @BeforeMethod
    public void init() {
        atm1 = createATM();
        atm2 = createATM();
        atmDepartment = createATMDepartment(atm1, atm2);
    }

    @Test
    public void testSetATM() {
        atmDepartment = new ATMDepartment();
        atmDepartment.setATM(new ATM(Collections.singletonList(new Cell(1, 1))));
        assertEquals(1, atmDepartment.getBalance());
    }

    @Test
    public void getBalance() {
        assertEquals(atm1.getBalance() + atm2.getBalance(), atmDepartment.getBalance());
    }

    @Test
    public void setInitialState() {
        atm1.withdraw(5);
        atmDepartment.setInitialState();
        assertEquals(200, atmDepartment.getBalance());
    }

    @Test
    public void removeATM() {
        atmDepartment.removeATM(atm1);
        assertEquals(atm2.getBalance(), atmDepartment.getBalance());
    }

    private ATMDepartment createATMDepartment(ATM... atms) {
        ATMDepartment atmDepartment = new ATMDepartment();
        for (ATM atm : atms) {
            atmDepartment.setATM(atm);
        }
        return atmDepartment;
    }

    private ATM createATM() {
        return new ATM(Collections.singletonList(new Cell(10, 10)));
    }

}
