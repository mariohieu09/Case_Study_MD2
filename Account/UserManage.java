package Account;

import FileIO.ReadFile;
import FileIO.WriteFile;
import Invoice.Invoice;
import Invoice.PaidCheckManage;
import Product.Product;
import ShoppingCart.*;

import java.io.File;
import java.util.*;
import Validate.*;
import eWallet.eWalletManage;

import java.util.stream.Collectors;

public class UserManage implements ShoppingCartManage, eWalletManage, PaidCheckManage {
    File DataBase = new File("DataBase.txt");
    File ProductFile = new File("Product.txt");
    List<Product> productList;
    List<Account> accounts;
    Scanner sc = new Scanner(System.in);
    ReadFile rf = new ReadFile();
    WriteFile wf = new WriteFile();

    @Override
    public boolean removeProduct(Account acc, String name) {
        accounts = rf.readFile(DataBase);
        Account user = new User();
        boolean existence = false;
        user = accounts.stream()
                .filter(a -> a.getAccountName().equals(acc.getAccountName()))
                .findAny().orElse(user);
        productList = ((User)user).getCart().getList();
        for(Product product : productList){
            if(product.getName().equals(name)){
                productList.remove(product);
                break;
            }
        }
        for(Account account : accounts){
            if(account.getAccountName().equals(acc)){
                ((User)account).getCart().setList(productList);
                existence = true;
                break;
            }
        }
        wf.writeFile(DataBase, accounts);
        return existence;
    }

    @Override
    public boolean payment(Account acc, String productName) {
        boolean beenPaid = false;
        accounts = rf.readFile(DataBase);
        boolean exist = checkIfTheProductInCart(acc, productName);
        productList = rf.readFile(ProductFile);
        Invoice invoice = new Invoice();
        if(exist){
            boolean canPaid = checkingTheAmount(acc, productName);
            Product product = productList.stream()
                    .filter(x -> x.getName().equals(productName))
                    .findAny().get();
            if(canPaid){
                if(checkQuantity(productName)){
                    double price = product.getPrice();
                    double currentAmount;
                    for(Account account : accounts){
                        if(account.getAccountName().equals(acc.getAccountName())){
                            currentAmount = ((User)account).geteWallet().getAmount();
                            currentAmount -= price;
                            ((User)account).geteWallet().setAmount(currentAmount);
                            break;
                        }
                    }
                    for(Product product1 : productList){
                        if(product1.getName().equals(productName)){
                            int current = product1.getQuantity();
                            int afterPaid = current - 1;
                            product1.setQuantity(afterPaid);
                            break;
                        }
                    }
                    invoice = new Invoice(acc, productName, price);
                    beenPaid = true;
                }else{
                    System.out.println("The quantity is not enough or the product is no longer exist in the list!");
                }
//                removeProduct(acc, productName);

            }else{
                System.out.println("The amount is not enough, please deposit more money!");
            }
        }else{
            System.out.println("The product is not in ur cart!");
        }
        if(beenPaid){
            display(invoice);
            wf.writeFile(DataBase, accounts);
            wf.writeFile(ProductFile, productList);
            addNewInvoice(acc, invoice);
            removeProduct(acc, productName);
        }
        return beenPaid;
    }
    public void addNewInvoice(Account acc, Invoice e){
        accounts = rf.readFile(DataBase);
        List<Invoice> list;
        for(Account account : accounts){
            if(account.getAccountName().equals(acc.getAccountName())){
                list = ((User)account).getInvoiceList();
                list.add(e);
                ((User)account).setInvoiceList(list);
                break;
            }
        }
        wf.writeFile(DataBase, accounts);
    }

    @Override
    public void addProductToCart(Account acc, String name) {
        accounts = rf.readFile(DataBase);
        Product product = new Product();
        boolean existence = checkifTheproductExist(acc, name);
        if(existence){
            product = productList.stream()
                    .filter(x -> x.getName().equals(name))
                    .findAny().get();
            Account user = accounts.stream()
                    .filter(x -> x.getAccountName().equals(acc.getAccountName()))
                    .findAny().get();
            productList = ((User)user).getCart().getList();
            productList.add(product);
            for(Account account : accounts){
                if(account.getAccountName().equals(acc.getAccountName())){
                    ((User)account).getCart().setList(productList);
                    break;
                }
            }
            wf.writeFile(DataBase, accounts);
            System.out.println("The product has been add to your cart!");
        }else{
            System.out.println("Can't find the product!");
        }
    }

    @Override
    public void SortList(Account acc) {
        accounts = rf.readFile(DataBase);
        Account user = new User();
        user = accounts.stream()
                .filter(x -> x.getAccountName().equals(acc.getAccountName()))
                .findAny().orElse(user);
        productList = ((User)user).getCart().getList();
        String temp = "";
        do {
            System.out.println("What order do you want to sort: \n1.Sort by name \n2.Sort by price");
            temp = sc.nextLine();
        }while (Validate.sortValidate(temp));
        int choice = (int)temp.charAt(0) - 48;
        if(choice == 1) {
            Collections.sort(productList, Comparator.comparing(Product::getName));
            System.out.println("Sort by name: ");
            productList.forEach(System.out::println);
        }else{
            Collections.sort(productList, Comparator.comparing(Product :: getPrice));
            System.out.println("Sort by price: ");
            productList.forEach(System.out::println);
        }
    }


    @Override
    public boolean deposit(Account acc, double money) {
        accounts = rf.readFile(DataBase);
        boolean isDeposed = false;
        for(Account account : accounts){
            if(account.getAccountName().equals(acc.getAccountName())){
                double currentAmount = account.eWallet.getAmount();
                currentAmount += money;
                account.eWallet.setAmount(currentAmount);
                isDeposed = true;
                break;
            }
        }
        wf.writeFile(DataBase, accounts);
        return  isDeposed;
    }

    @Override
    public boolean exchange(Account acc, String productName) {
        accounts = rf.readFile(DataBase);
        productList = rf.readFile(ProductFile);
        boolean isExchange = false;
        Product product = productList.stream()
                .filter(x -> x.getName().equals(productName))
                .findAny().get();
        for(Account account : accounts){
            if(account.getAccountName().equals(product.getSellerName())){
                double current = ((Seller)account).geteWallet().getAmount();
                double after = current + product.getPrice();
                ((Seller)account).geteWallet().setAmount(after);
                isExchange = true;
                List<Product> list = ((Seller)account).getSellerList().getList();
                for(Product product1 : list){
                    if(product1.getName().equals(productName)){
                        int currentQ = product1.getQuantity();
                        int afterQ = currentQ - 1;
                        product1.setQuantity(afterQ);
                        break;
                    }
                }
                break;
            }
        }

        wf.writeFile(DataBase, accounts);
        return isExchange;
    }
    public boolean checkifTheproductExist(Account acc , String name){
        accounts = rf.readFile(DataBase);
        boolean existed = false;
        productList = rf.readFile(ProductFile);
        List<Product> list  = new ArrayList<Product>();
        Product product = new Product();
        product = productList.stream().filter(p -> p.getName().equals(name))
                .findAny().orElse(product);
        if(!(product.getName() == null)){
             existed = true;
        }
        return existed;
    }

    public boolean checkingTheAmount(Account acc, String productName){
        accounts = rf.readFile(DataBase);
        productList = rf.readFile(ProductFile);
        boolean canPaid = false;
        boolean existed = checkIfTheProductInCart(acc, productName);
        Account temp = new User();
        temp = accounts.stream().filter(x -> x.getAccountName().equals(acc.getAccountName()))
                .findAny().get();
        double currentAmount = ((User)temp).eWallet.getAmount();
        if(existed){
            Product product = new Product();
            product = productList.stream().filter(x -> x.getName().equals(productName)).findAny().get();
            if(product.getPrice() > currentAmount){
                canPaid = false;
            }else{
                canPaid = true;
            }
        }
        return canPaid;
    }

    public boolean checkIfTheProductInCart(Account acc, String productName){
        accounts = rf.readFile(DataBase);
        boolean existed = false;
            Account temp = new User();
            temp = accounts.stream()
                    .filter(x -> x.getAccountName().equals(acc.getAccountName()))
                    .findAny().orElse(temp);
            productList = ((User) temp).getCart().getList();
            Product product = new Product();
            product = productList.stream().filter(x -> x.getName().equals(productName)).findAny().orElse(product);
            if(!(product.getName() == null)){
                 existed = true;
            }
        return existed;
    }
    public void displayTheCart(Account acc){
        accounts = rf.readFile(DataBase);
        Account user = new User();
        user = accounts.stream()
                .filter(x -> x.getAccountName().equals(acc.getAccountName()))
                .findAny().get();
        productList = ((User)user).getCart().getList();
        System.out.println("Here is your cart list: ");
        productList.forEach(System.out::println);
        System.out.println("---------------------------------------------");
    }
    public void displayTheAmount(Account acc){
        accounts = rf.readFile(DataBase);
        Account user = accounts.stream()
                .filter(x -> x.getAccountName().equals(acc.getAccountName()))
                .findAny().get();
        System.out.println(((User)user).eWallet.getAmount());
    }
    public boolean checkQuantity(String productName){
        productList = rf.readFile(ProductFile);
        boolean isOkay = false;
        Product product = productList.stream()
                .filter(x -> x.getName().equals(productName))
                .findAny().get();
        if(product.getQuantity() > 0){
            isOkay = true;
        }
        return isOkay;
    }

    @Override
    public Invoice createInvoice(Account acc, String productName, double price) {
        Invoice invoice = new Invoice(acc, productName, price);
        return invoice;
    }

    @Override
    public void display(Invoice invoice) {
        System.out.println(invoice);
    }

    @Override
    public void displayThePaidHistory(String accountName) {
        List<Account> list = rf.readFile(DataBase);
        List<Invoice> invoiceList = new ArrayList<Invoice>();
        for(Account account : list){
            if(account.getAccountName().equals(accountName)){
                invoiceList = ((User)account).getInvoiceList();
                break;
            }
        }
        invoiceList.forEach(System.out::println);
    }
    public void searchProduct(Account acc , String productName){
        productList = rf.readFile(ProductFile);
       boolean exist = checkifTheproductExist(acc, productName);
       if(exist){
           Product product = productList.stream().
                   filter(x -> x.getName().equals(productName))
                   .findAny().get();
           System.out.println(product);
       }else{
           System.out.println("Can't find the product!");
       }
    }
}
