package model.payment;
import model.User;

import java.io.Serializable;
import java.util.Date;

public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int bookingID;         
    private Date createdDate;      
    private Date exportTime;       
    private float totalAmount;     
    private float tax;             
    private float discount;        
    private float finalAmount;     
    private String paymentMethod;  
    private String status;         
    private User user;             

    public Bill() {}

    public Bill(int id, Date createdDate, float totalAmount, float tax, float discount, 
                String paymentMethod, String status, User user) {
        this.id = id;
        this.createdDate = createdDate;
        this.totalAmount = totalAmount;
        this.tax = tax;
        this.discount = discount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.user = user;
        this.finalAmount = (totalAmount * (1 + tax)) - discount;
    }

    
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBookingID() { return bookingID; }
    public void setBookingID(int bookingID) { this.bookingID = bookingID; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public Date getExportTime() { return exportTime; }
    public void setExportTime(Date exportTime) { this.exportTime = exportTime; }

    public float getTotalAmount() { return totalAmount; }
    public void setTotalAmount(float totalAmount) { this.totalAmount = totalAmount; }

    public float getTax() { return tax; }
    public void setTax(float tax) { this.tax = tax; }

    public float getDiscount() { return discount; }
    public void setDiscount(float discount) { this.discount = discount; }

    public float getFinalAmount() {
        return (this.totalAmount * (1 + this.tax)) - this.discount;
    }
    public void setFinalAmount(float finalAmount) { this.finalAmount = finalAmount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
 
