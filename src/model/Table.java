package model;
import java.io.Serializable;

public class Table implements Serializable {
    private int id;
    private String name;
    private int capacity;
    private String des;
    private String type;
    
    public Table() {
        super();
    }
    public Table(String name, int capacity, String des) {
        super();
        this.name = name;
        this.capacity = capacity;
        this.des = des;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public String getDes() { return des; }
    public void setDes(String des) { this.des = des; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return des; }
    public void setDescription(String description) { this.des = description; }
}
