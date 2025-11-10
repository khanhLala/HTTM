package httm.model;

import java.io.*;
import java.time.*;

public class CartDetail implements Serializable{
    private int id, quantity;
    private LocalDateTime createdTime;
    private Product product;
    private Cart cart;

    public CartDetail() {
    }

    public CartDetail(int id, int quantity, LocalDateTime createdTime, Product product, Cart cart) {
        this.id = id;
        this.quantity = quantity;
        this.createdTime = createdTime;
        this.product = product;
        this.cart = cart;
    }

    public CartDetail(int quantity, Product product, Cart cart) {
        this.quantity = quantity;
        this.product = product;
        this.cart = cart;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "CartDetail{" + "id=" + id + ", quantity=" + quantity + ", createdTime=" + createdTime + ", product=" + product + ", cart=" + cart + '}';
    }
}
