package model.booking;
import model.User;
import model.BookedTable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Booking implements Serializable {
    private int id;
    private Client client;
    private User user;
    private Date bookDate;
    private String time;
    private String note;
    private String status;
    private float totalAmount;
    private List<BookedTable> bookedTables;
    
    public Booking() {
        super();
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public Date getBookDate() { return bookDate; }
    public void setBookDate(Date bookDate) { this.bookDate = bookDate; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public float getTotalAmount() { return totalAmount; }
    public void setTotalAmount(float totalAmount) { this.totalAmount = totalAmount; }
    public List<BookedTable> getBookedTables() { return bookedTables; }
    public void setBookedTables(List<BookedTable> bookedTables) { this.bookedTables = bookedTables; }
}
