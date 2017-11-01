
import atm.ATM;
import atm.Cell;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ATMDepartment atmDepartment = new ATMDepartment();

        System.out.println("Установка ATM:");

        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell(10, 1000));
        cells.add(new Cell(5, 500));
        cells.add(new Cell(7, 100));
        cells.add(new Cell(20, 50));
        cells.add(new Cell(20, 10));
        ATM atm1 = new ATM(cells);
        System.out.println("Начальный баланс ATM 1: " + atm1.getBalance());
        atmDepartment.setATM(atm1);

        cells = new ArrayList<>();
        cells.add(new Cell(5, 5000));
        cells.add(new Cell(5, 1000));
        cells.add(new Cell(5, 500));
        cells.add(new Cell(10, 100));
        cells.add(new Cell(10, 50));

        ATM atm2 = new ATM(cells);
        System.out.println("Начальный баланс ATM 2: " + atm2.getBalance());
        atmDepartment.setATM(atm2);

        System.out.println("Начальный баланс ATMDepartment: " + atmDepartment.getBalance());

        atm1.withdraw(1680);
        atm2.withdraw(1350);

        System.out.println("Конечный баланс ATM 1: " + atm1.getBalance());
        System.out.println("Конечный баланс ATM 2: " + atm2.getBalance());
        System.out.println("Конечный баланс ATMDepartment: " + atmDepartment.getBalance());

        atmDepartment.setInitialState();
        System.out.println("Восстановление первоначального состояния ATMDepartment:" + atmDepartment.getBalance());

        atmDepartment.removeATM(atm1);
        System.out.println("Баланс ATMDepartment после удаления ATM 1: " + atmDepartment.getBalance());
    }

}
