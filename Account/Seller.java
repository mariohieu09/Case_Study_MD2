package Account;

import SellerList.SellerList;
import eWallet.*;
public class Seller extends Account{
    private static final long serialVersionUID = 9045863543269746292L;
    SellerList sellerList;
    eWallet eWallet;


    public Seller() {
        this.Role = "Seller";
    }
    public Seller(String accountName, String password){
        this.AccountName = accountName;
        this.Password = password;
        this.Role = "Seller";
        this.sellerList = new SellerList();
        eWallet = new eWallet();
    }

    public Seller(String name, String accountName, String password) {
        super(name, accountName, password);
        this.sellerList = new SellerList();
        this.Role = "Seller";
        eWallet = new eWallet();
    }

    public SellerList getSellerList() {
        return sellerList;
    }

    public void setSellerList(SellerList list) {
        this.sellerList = list;
    }

    public eWallet geteWallet() {
        return eWallet;
    }

    public void seteWallet(eWallet eWallet) {
        this.eWallet = eWallet;
    }
}
