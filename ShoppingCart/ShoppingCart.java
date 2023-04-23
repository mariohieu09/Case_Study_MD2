package ShoppingCart;

import Product.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 9045863543269746292L;
    List<Product> list;

    public ShoppingCart() {
        this.list = new ArrayList<>();
    }

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }

}
