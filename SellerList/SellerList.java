package SellerList;

import Product.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SellerList implements Serializable {
    private static final long serialVersionUID = 9045863543269746292L;
    List<Product> list;

    public SellerList() {
        list = new ArrayList<Product>();
    }

    public List<Product> getList() {
        return list;
    }

    public void setList(List<Product> list) {
        this.list = list;
    }
}
