package client.model;

public class Order {
    private int id;
    private Drink drink;
    private String status; // "open" or "served"
   
   


    
   
    public Order(int id, Drink drink, String status) {
        this.id = id;
        this.drink = drink;
        this.status = status;
    }

    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Drink getDrink() {
        return drink;
    }
    public void setDrink(Drink drink) {
        this.drink = drink;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    // Getters, Setters, and Constructor
    
}
