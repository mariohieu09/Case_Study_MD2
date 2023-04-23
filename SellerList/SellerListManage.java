package SellerList;

import Account.Account;

public interface SellerListManage {
    boolean addNewProductToSell(Account acc);

    boolean increasePrice(Account acc, String productName);
    boolean decreasePrice(Account acc, String productName);
    void reduceQuantity(Account acc, String productName);

}
