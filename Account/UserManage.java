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
        if(exist){
            boolean canPaid = checkingTheAmount(acc, productName);
            Product product = productList.stream()
                    .filter(x -> x.getName().equals(productName))
                    .findAny().get();
            if(canPaid){
                if(checkQuantity(productName)){
                    double price = product.getPrice();
                    List<Invoice> invoiceList = new ArrayList<>();
                    for (Account account : accounts) {
                        if (account.getAccountName().equals(acc.getAccountName())) {
                            double currentAmount = account.eWallet.getAmount();
                            double afterPaid = currentAmount - price;
                            account.eWallet.setAmount(afterPaid);
                            Invoice iv = createInvoice(acc, productName, price);
                            display(iv);
                            invoiceList = ((User) account).getInvoiceList();
                            invoiceList.add(iv);
                            List<Product> list = ((User)account).getCart().getList();
                            list.remove(product);
                            ((User)account).getCart().setList(list);
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
        wf.writeFile(DataBase, accounts);
        wf.writeFile(ProductFile, productList);
        return beenPaid;
    }

    @Override
    public void addProductToCart(Account acc, String name) {
        accounts = rf.readFile(DataBase);
        productList = rf.readFile(ProductFile);
        Product product = new Product();
        boolean existence = checkifTheproductExist(acc, name);
        if(existence){
            product = productList.stream()
                    .filter(x -> x.getName().equals(name))
                    .findAny().get();
            Account user = new User();
            user = accounts.stream()
                    .filter(x -> x.getAccountName().equals(name))
                    .findAny().orElse(user);
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
    public void displayThePaidHistory(Account acc) {
        accounts = rf.readFile(DataBase);
        List<Invoice> invoiceList;
        Account account = accounts.stream().
                filter(x -> x.getAccountName().equals(acc.getAccountName()))
                .findAny().get();
        invoiceList = ((User)account).getInvoiceList();
        invoiceList.forEach(System.out::println);
    }
}
