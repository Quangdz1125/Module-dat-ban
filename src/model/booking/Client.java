package model.booking;
import java.io.Serializable;

public class Client implements Serializable {
    private int id;
    private String name;
    private String email;
    private String tel;
    private String address;
    private String note;
    
    public Client() {
        super();
    }
    public Client(String name, String email, String tel, String address, String note) {
        super();
        this.name = name;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.note = note;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTel() { return tel; }
    public void setTel(String tel) { this.tel = tel; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}
