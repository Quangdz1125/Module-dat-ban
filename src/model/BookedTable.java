package model;
import java.io.Serializable;

public class BookedTable implements Serializable {
    private int id;
    private Table table;
    private float price;
    
    public BookedTable() {
        super();
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Table getTable() { return table; }
    public void setTable(Table table) { this.table = table; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
}
