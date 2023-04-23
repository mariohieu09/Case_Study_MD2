package Account;
import Invoice.Invoice;
import eWallet.*;
import ShoppingCart.*;

import java.util.ArrayList;
import java.util.List;

public class User extends Account {
    private static final long serialVersionUID = 9045863543269746292L;
    ShoppingCart cart = new ShoppingCart();
    List<Invoice> invoiceList;


    public User(String name, String accountName, String password) {
        this.name = name;
        this.AccountName = accountName;
        this.Password = password;
        this.Role = "User";
        this.eWallet = new eWallet();
        this.cart = new ShoppingCart();
        this.invoiceList = new ArrayList<Invoice>();

    }

    public User(String accountName, String password) {
        cart = new ShoppingCart();
        this.Role = "User";
        this.eWallet = new eWallet();
        this.AccountName = accountName;
        this.Password = password;
        this.cart = new ShoppingCart();
        this.invoiceList = new ArrayList<Invoice>();
    }

    public User() {
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public List<Invoice> getInvoiceList() {
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }
    public eWallet geteWallet(){
        return this.eWallet;
    }
}
