import atm.ATM;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartment {

    private List<ATM> atms = new ArrayList<>();

    public void setATM(ATM atm) {
        atms.add(atm);
    }

    public int getBalance() {
        int sum = 0;
        for (ATM atm : atms) {
            sum += atm.getBalance();
        }
        return sum;
    }

    public void setInitialState() {
        for (ATM atm : atms) {
            atm.setInitialState();
        }
    }

    public void removeATM(ATM atm) {
        atms.remove(atm);
    }
}
