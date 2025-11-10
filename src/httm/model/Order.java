package httm.model;

import java.io.*;
import java.time.*;

public class Order implements Serializable{
    private int id;
    private LocalDateTime createdTime;
    private User user;

    public Order() {
    }

    public Order(int id, LocalDateTime createdTime, User user) {
        this.id = id;
        this.createdTime = createdTime;
        this.user = user;
    }

    public Order(User user) {
        this.user = user;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", createdTime=" + createdTime + ", user=" + user + '}';
    }
 
}
