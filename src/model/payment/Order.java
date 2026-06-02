package model.payment;
import model.User;
import model.Table;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private Table table;
    private ArrayList<OrderedDish> orderedDishes;
    private String status;
    private User user;
    private double subTotal;
    private String note;
    private java.util.Date orderTime;

    public Order() {
        this.orderedDishes = new ArrayList<>();
    }

    public Order(int id, Table table, ArrayList<OrderedDish> orderedDishes, String status, String note) {
        this.id = id;
        this.table = table;
        this.orderedDishes = orderedDishes;
        this.status = status;
        this.note = note;
        this.orderTime = new java.util.Date();
    }

    public void addOrderedDish(OrderedDish orderedDish, Dish dish, int quantity, String note) {
        for (OrderedDish od : orderedDishes) {
            if (od.getDish().getId() == dish.getId() && od.getNote().equals(note)) {
                od.setQuantity(od.getQuantity() + quantity);
                return;
            }
        }
        OrderedDish newOrderedDish = new OrderedDish(dish, quantity, note);
        orderedDishes.add(newOrderedDish);
    }

    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public Table getTable() { 
        return table; 
    }
    
    public void setTable(Table table) { 
        this.table = table; 
    }

    public ArrayList<OrderedDish> getOrderedDishes() { 
        return orderedDishes; 
    }
    
    public void setOrderedDishes(ArrayList<OrderedDish> orderedDishes) { 
        this.orderedDishes = orderedDishes; 
    }

    public String getStatus() { 
        return status; 
    }
    
    public void setStatus(String status) { 
        this.status = status; 
    }

    public User getUser() { 
        return user; 
    }
    
    public void setUser(User user) { 
        this.user = user; 
    }

    public double getSubTotal() { 
        return subTotal; 
    }
    
    public void setSubTotal(double subTotal) { 
        this.subTotal = subTotal; 
    }

    public String getNote() { 
        return note; 
    }
    
    public void setNote(String note) { 
        this.note = note; 
    }

    public java.util.Date getOrderTime() { 
        return orderTime; 
    }
    
    public void setOrderTime(java.util.Date orderTime) { 
        this.orderTime = orderTime; 
    }
}
