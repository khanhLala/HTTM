package httm.model;

import java.io.*;

public class Product implements Serializable {

    private int id, quantity;
    private String name, brand, des, categorycode;
    private float price;

    public Product() {
    }

    public Product(int id, int quantity, String name, String brand, String des, float price) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.brand = brand;
        this.des = des;
        this.price = price;
    }

    public Product(int id, int quantity, String name, String brand, String des, String categorycode, float price) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.brand = brand;
        this.des = des;
        this.categorycode = categorycode;
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getCategorycode() {
        return categorycode;
    }

    public void setCategorycode(String categorycode) {
        this.categorycode = categorycode;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", quantity=" + quantity + ", name=" + name + ", brand=" + brand + ", des=" + des + ", price=" + price + '}';
    }
}
