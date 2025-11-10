package httm.model;

import java.sql.*;
import java.io.*;
import java.time.LocalDateTime;

public class Cart implements Serializable{
    private int id;
    private LocalDateTime lastModifiedTime;
    private User user;

    public Cart(int id, LocalDateTime lastModifiedTime, User user) {
        this.id = id;
        this.lastModifiedTime = lastModifiedTime;
        this.user = user;
    }

    public Cart() {
    }

    public Cart(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Cart{" + "id=" + id + ", lastModifiedTime=" + lastModifiedTime + ", user=" + user + '}';
    }
}
