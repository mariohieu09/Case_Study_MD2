package Account;

import FileIO.ReadFile;
import FileIO.WriteFile;
import Product.Product;
import SellerList.SellerListManage;
import SellerList.*;
import eWallet.eWalletManage;
import eWallet.eWalletManage.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SellerManage implements SellerListManage, eWalletManage {
    Scanner sc = new Scanner(System.in);
    ReadFile rf = new ReadFile();
    WriteFile wf = new WriteFile();
    File DataBase = new File("DataBase.txt");
    File ProductFile = new File("Product.txt");
    List<Product> productList;
    List<Account> accounts;
    @Override
    public boolean addNewProductToSell(Account acc) {
        accounts = rf.readFile(DataBase);
        productList = rf.readFile(ProductFile);
        System.out.println("Enter the name of the product: ");
        String name = sc.nextLine();
        boolean isAdded = false;
        boolean isExist = checkifTheProductExist(name);
        if(!isExist){
            System.out.println("Enter the product price: ");
            double price = sc.nextDouble();
            sc.nextLine();
            System.out.println("Enter the product description: ");
            String description = sc.nextLine();
            Product product = new Product(name, price, description, acc.getAccountName());
            productList.add(product);
            List<Product> list;
            Account temp = new Seller();
            temp = accounts.stream().filter(x -> x.getAccountName().equals(acc.getAccountName()))
                    .findAny().get();
            list = ((Seller)temp).getSellerList().getList();
            list.add(product);
            for(Account account : accounts){
                if(account.getAccountName().equals(acc.getAccountName())){
                    ((Seller)account).getSellerList().setList(list);
                    break;
                }
            }
            isAdded = true;
        }
        wf.writeFile(DataBase, accounts);
        wf.writeFile(ProductFile,productList);
        return isAdded;
    }

    @Override
    public boolean increasePrice(Account acc, String productName) {
        accounts = rf.readFile(DataBase);
        productList = rf.readFile(ProductFile);
        boolean isIncrease = false;
        boolean isExist = checkifTheProductExist(productName);
        if(isExist) {
            boolean isInSellList = checkIfTheProductInSellList(acc, productName);
            if (isInSellList) {
                System.out.println("Enter the percent you want to raise: ");
                double percent = sc.nextDouble();
                sc.nextLine();
                double currentAmount;
                double afterRaise = 0;
                List<Product> list;
                for(Product product : productList){
                    if(product.getName().equals(productName)){
                        currentAmount = product.getPrice();
                        afterRaise = currentAmount + (currentAmount * percent/100);
                        product.setPrice(afterRaise);
                        break;
                    }
                }
                for(Account account : accounts){
                    if(account.getAccountName().equals(acc.getAccountName())){
                        list = ((Seller)account).getSellerList().getList();
                        for(Product product : list){
                            if (product.getName().equals(productName)){
                                product.setPrice(afterRaise);
                                break;
                            }
                        }
                        ((Seller)account).getSellerList().setList(list);
                        break;
                    }
                }
                wf.writeFile(DataBase, accounts);
                wf.writeFile(ProductFile, productList);
                isIncrease = true;
            }else{
                System.out.println("The product is not in your sell list!");
            }
        }else{
            System.out.println("Can't find the product!");
        }
        return isIncrease;
    }

    @Override
    public boolean decreasePrice(Account acc, String productName) {
        boolean isDecrease = false;
        boolean isExist = checkifTheProductExist(productName);
        productList = rf.readFile(ProductFile);
        accounts = rf.readFile(DataBase);
        Account temp = accounts.stream()
                .filter(x -> x.getAccountName().equals(acc.getAccountName()))
                .findAny().get();
        if(isExist) {
            boolean isInSellList = checkIfTheProductInSellList(acc, productName);
            if(isInSellList){
                System.out.println("Enter the percent you want to raise: ");
                double percent = sc.nextDouble();
                sc.nextLine();
                double currentAmount;
                double afterDecrease = 0;
                for(Product product : productList){
                    if(product.getName().equals(productName)){
                        currentAmount = product.getPrice();
                        afterDecrease = currentAmount - (currentAmount * percent/100);
                        product.setPrice(afterDecrease);
                        break;
                    }
                }
                List<Product> sellList = new ArrayList<Product>();
                for(Account account : accounts){
                    if(account.getAccountName().equals(acc.getAccountName())){
                        sellList = ((Seller)account).getSellerList().getList();
                        break;
                    }
                }
                for(Product product : sellList){
                    if(product.getName().equals(productName)){
                        product.setPrice(afterDecrease);
                        break;
                    }
                }
                for(Account account : accounts){
                    if(account.getAccountName().equals(acc.getAccountName())){
                        ((Seller)account).getSellerList().setList(sellList);
                        break;
                    }
                }
                wf.writeFile(DataBase, accounts);
                wf.writeFile(ProductFile, productList);
                isDecrease = true;
            }else{
                System.out.println("The product is not in your sell list!");
            }
        }else{
            System.out.println("Can't find the product!");
        }
        return  isDecrease;
    }

    @Override
    public void reduceQuantity(Account acc, String productName) {
        productList = rf.readFile(ProductFile);
        accounts = rf.readFile(DataBase);
        List<Product> list = new ArrayList<Product>();
        boolean isExist =  checkifTheProductExist(productName);
        if(isExist){
          boolean isInSellList =  checkIfTheProductInSellList(acc, productName);
          if(isInSellList){
              System.out.println("Enter the quantity you want to reduce: ");
              int quantity = sc.nextInt();
              sc.nextLine();
              for(Product product : productList){
                  if(product.getName().equals(productName)){
                      product.setQuantity(quantity);
                      break;
                  }
              }
              for(Account account : accounts){
                  if(account.getAccountName().equals(acc.getAccountName())){
                      list =((Seller)account).getSellerList().getList();
                      for(Product product : list){
                          if(product.getName().equals(productName)){
                              product.setQuantity(quantity);
                              break;
                          }
                      }
                      ((Seller)account).getSellerList().setList(list);
                      break;
                  }
              }
              wf.writeFile(ProductFile, productList);
              wf.writeFile(DataBase, accounts);
          }else {
              System.out.println("The product is not in your cart!");
          }
        }else{
            System.out.println("Can't find the product!");
        }
    }

    @Override
    public boolean deposit(Account acc, double money) {
        return false;
    }

    @Override
    public boolean exchange(Account p, String name) {
        return false;
    }

    public boolean checkifTheProductExist(String productName){
        productList = rf.readFile(ProductFile);
        boolean isExist = false;
        for(Product product : productList){
            if(product.getName().equals(productName)){
                isExist = true;
                break;
            }
        }
        return isExist;
    }
    public boolean checkIfTheProductInSellList(Account acc ,String productName){
        accounts = rf.readFile(DataBase);
        Account temp = new Seller();
        temp = accounts.stream()
                .filter(x -> x.getAccountName().equals(acc.getAccountName()))
                .findAny().get();
        List<Product> list = ((Seller)temp).getSellerList().getList();
        boolean isExist = false;
        for(Product product : list){
            if(product.getName().equals(productName)){
                isExist = true;
                break;
            }
        }
        return isExist;
    }
    public void displayTheSellList(Account acc){
        accounts = rf.readFile(DataBase);
        Account seller = accounts.stream().
                filter(x -> x.getAccountName().equals(acc.getAccountName()))
                .findAny().get();
        List<Product> list = ((Seller)seller).getSellerList().getList();
        System.out.println("Here is your sell list: ");
        for(Product product : list){
            System.out.println(product);
            System.out.println("Quantity: " + product.getQuantity());
        }
    }

    public void displayTheAmount(Account acc){
        accounts = rf.readFile(DataBase);
        Account seller = accounts.stream()
                .filter(x -> x.getAccountName().equals(acc.getAccountName()))
                .findAny().get();
        System.out.println(((Seller)seller).geteWallet().getAmount());
    }
    public String getAccountName(Account acc){
        accounts = rf.readFile(DataBase);
        Account account = accounts.stream()
                .filter(x -> x.getAccountName().equals(acc.getAccountName()))
                .findAny().get();
        return account.getAccountName();
    }
}
