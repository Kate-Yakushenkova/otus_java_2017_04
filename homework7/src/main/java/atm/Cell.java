package atm;

public class Cell {

    private final int capacity;
    private final int nominal;
    private int count;

    public Cell(int capacity, int nominal) {
        this.capacity = capacity;
        this.nominal = nominal;
        this.count = capacity;
    }

    public int getNominal() {
        return nominal;
    }

    public int getCount() {
        return count;
    }

    public void setInitialState() {
        count = this.capacity;
    }

    public void withdraw(int count){
        this.count -= count;
    }
}
