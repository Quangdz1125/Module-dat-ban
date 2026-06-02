package model;
import java.io.Serializable;
import java.util.Date;

public class BookedTable implements Serializable {
    private int id;
    private Table table;
    private float price;
    private Date bookingDate;
    private String bookTime;
    private int numberOfPeople;
    private int amount; // Tiền đặt cọc giữ bàn
    private String note;
    
    public BookedTable() {
        super();
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Table getTable() { return table; }
    public void setTable(Table table) { this.table = table; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }

    public BookedTable(int id, Date bookingDate, String bookTime, int numberOfPeople,
                       int amount, String note, Table table) {
        this.id = id;
        this.bookingDate = bookingDate;
        this.bookTime = bookTime;
        this.numberOfPeople = numberOfPeople;
        this.amount = amount;
        this.note = note;
        this.table = table;
    }

    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }

    public String getBookTime() { return bookTime; }
    public void setBookTime(String bookTime) { this.bookTime = bookTime; }

    public int getNumberOfPeople() { return numberOfPeople; }
    public void setNumberOfPeople(int numberOfPeople) { this.numberOfPeople = numberOfPeople; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
