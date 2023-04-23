package Product;

import Invoice.Invoice;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 9045863543269746292L;
    private Invoice paidCheck;
    private String name;
    private double price;
    private  int quantity;
    private String description = "No description";
    private String SellerName;

    public Product(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = 1;
    }

    public Product(String name, double price, String description, String sellerName) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.SellerName = sellerName;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product() {
        name = null;
        price = 0;
    }

    public Invoice getPaidCheck() {
        return paidCheck;
    }

    public void setPaidCheck(Invoice paidCheck) {
        this.paidCheck = paidCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSellerName() {
        return SellerName;
    }

    public void setSellerName(String sellerName) {
        SellerName = sellerName;
    }

    @Override
    public String toString() {
        return "Product{" +
                " name='" + name + '\'' +
                ", price=" + price + '\'' +
                ", seller = " + SellerName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
