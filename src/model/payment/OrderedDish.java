package model.payment;

import java.io.Serializable;

public class OrderedDish implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private Dish dish;
    private int quantity;
    private double priceAtTime;
    private double priceAtSale;
    private String note;
    private String status;
    private double subTotal;

    public OrderedDish() {}

    public OrderedDish(int id, Dish dish, int quantity, double priceAtTime, String note, String status) {
        this.id = id;
        this.dish = dish;
        this.quantity = quantity;
        this.priceAtTime = priceAtTime;
        this.priceAtSale = priceAtSale;
        this.note = note;
        this.status = status;
        this.subTotal = priceAtTime * quantity;
    }

  
    public OrderedDish(Dish dish, int quantity, String note) {
        this.dish = dish;
        this.quantity = quantity;
        this.note = note;
        this.priceAtTime = dish.getPrice(); // Lấy giá hiện tại của món ăn
        this.status = "Chưa phục vụ";       // Trạng thái mặc định ban đầu
        this.subTotal = this.priceAtTime * this.quantity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Dish getDish() { return dish; }
    public void setDish(Dish dish) { this.dish = dish; }

    public int getQuantity() { return quantity; }
    // Khi cập nhật lại số lượng món ăn, thành tiền subTotal tự động tính lại
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
        this.subTotal = this.priceAtTime * this.quantity;
    }

    public double getPriceAtTime() { return priceAtTime; }
    public void setPriceAtTime(double priceAtTime) { 
        this.priceAtTime = priceAtTime; 
        this.subTotal = this.priceAtTime * this.quantity; // Tính lại nếu giá thay đổi
    }
    
    public double getPriceAtSale() { return priceAtSale; }
    public void setPriceAtSale(double priceAtSale) { 
        this.priceAtSale = priceAtSale; 
        this.subTotal = this.priceAtSale * this.quantity; // Tính lại nếu giá thay đổi
    }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getSubTotal() { return this.subTotal; }
    public void setSubTotal(double subTotal) { this.subTotal = subTotal; }
}