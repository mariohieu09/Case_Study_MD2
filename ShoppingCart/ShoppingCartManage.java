package ShoppingCart;

import Account.Account;

public interface ShoppingCartManage {
    boolean removeProduct(Account acc , String name);
    boolean payment(Account acc, String name);

    void addProductToCart(Account acc, String name);
    void SortList(Account acc);


}
