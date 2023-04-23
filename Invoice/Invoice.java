package Invoice;

import Account.Account;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Invoice implements Serializable {
    private static final long serialVersionUID = 9045863543269746292L;

    LocalDateTime date;

    String productName;
    double price;
    String Buyer;
    public Invoice(Account acc, String productName, double price) {
        this.productName = productName;
        this.price = price;
        this.date = LocalDateTime.now();
        this.Buyer = acc.getAccountName();
    }

    public Invoice() {
        this.date = LocalDateTime.now();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }



    @Override
    public String toString() {
        return "Paid Check{" +
                " product name= " + productName +
                " price= " + price +
                " Buyer= " + Buyer +
                " date= " + date +
                '}';
    }
}
