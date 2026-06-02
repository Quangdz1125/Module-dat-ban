package model.payment;

import java.io.Serializable;

public class Dish implements Serializable {
    private int id;
    private String name;
    private double price;
    private String type;
    private String status;
    

    public Dish() { super(); }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
}
