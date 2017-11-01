package atm;

import java.util.ArrayList;
import java.util.List;

public class ATM {

    private List<Cell> cells = new ArrayList<>();

    public ATM(List<Cell> cells) {
        this.cells.addAll(cells);
    }

    public int getBalance() {
        int sum = 0;
        for (Cell cell : cells) {
            sum += cell.getCount() * cell.getNominal();
        }
        return sum;
    }

    public void setInitialState() {
        for (Cell cell : cells) {
            cell.setInitialState();
        }
    }

    public boolean withdraw(int requested) {
        return withdraw(requested, 0);
    }

    private boolean withdraw(int requested, int index) {
        Cell cell = cells.get(index);
        int expectedCount = Math.min(requested / cell.getNominal(), cell.getCount());
        int expectedCash = expectedCount * cell.getNominal();
        boolean nextCellResult = true;
        if (requested != expectedCash) {
            nextCellResult = index + 1 < cells.size() && withdraw(requested - expectedCash, index + 1);
        }
        if (nextCellResult) {
            cell.withdraw(expectedCount);
            return true;
        }
        return false;
    }
}
