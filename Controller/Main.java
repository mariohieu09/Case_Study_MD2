package Controller;

import Account.Account;
import Account.Seller;
import Account.*;
import Product.Product;
import Product.ProductDisplay;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AccountManage accountManage  = new AccountManage();
        while (true){
            ProductDisplay.display();
            String menuChoice;
            do {
                System.out.println("1.Sign up            2.Sign in              3.Forget Password         0.Exit");
                menuChoice = sc.nextLine();
            }while (!(ValidateChoice.menuChoice(menuChoice)));
            int choice = (int)menuChoice.charAt(0) - 48;
            switch (choice){
                case 1 -> {
                    boolean isExist =  accountManage.SignUp();
                    if(isExist){
                        System.out.println("Success!");
                    }else{
                        System.out.println("The account name is exist!");
                    }
                }
                case 2 -> {
//                    String name;
                    System.out.println("Enter account name: ");
                    String Acountname = sc.nextLine();
                    System.out.println("Enter password: ");
                    String password = sc.nextLine();
                    int Role = accountManage.SignIn(Acountname, password);
                    if(Role == 1){
                        int sellerCheck = 0;
                        while(sellerCheck != 1){
                        SellerManage sellerManage = new SellerManage();
                        Account seller = new Seller(Acountname, password);
                        ProductDisplay.display();
                        System.out.println("1.Check the sell list           2.Sort the list and display        3.Exit");
                        int n = sc.nextInt();
                        sc.nextLine();
                        String choose;
                        switch (n) {
                            case 1 -> {
                                int check = 0;
                                while (check != 1) {
                                    sellerManage.displayTheAmount(seller);
                                    sellerManage.displayTheSellList(seller);
                                    do {
                                        System.out.println("1.Adding new product      2.Increase the product price       3.Decrease the product price      4.Reduce the quantity      0.Exit");
                                        choose = sc.nextLine();
                                    }while (!(ValidateChoice.Choice(choose)));
                                    int SellerChoice = (int)choose.charAt(0) - 48;
                                    switch (SellerChoice) {
                                        case 1 -> sellerManage.addNewProductToSell(seller);
                                        case 2 -> {
                                            System.out.println("Enter the product name: ");
                                            String Productname = sc.nextLine();
                                            sellerManage.increasePrice(seller, Productname);
                                        }
                                        case 3 -> {
                                            System.out.println("Enter the product name: ");
                                            String Productname = sc.nextLine();
                                            sellerManage.decreasePrice(seller, Productname);
                                        }
                                        case 4 -> {
                                            System.out.println("Enter the product you want to reduce the quantity: ");
                                            String Productname = sc.nextLine();
                                            sellerManage.reduceQuantity(seller, Productname);
                                        }
                                        case 0 -> check = 1;
                                    }
                                }
                            }
                                    case 2 -> ProductDisplay.SortTheList();
                            case 3 -> sellerCheck = 1;
                        }
                        }
                    }else if(Role == 0){
                        int UserCheck = 0;
                        String choose;
                        while(UserCheck != 1){
                            System.out.println("Welcome!");
                            UserManage userManage = new UserManage();
                            Account user = new User(Acountname, password);
                            ProductDisplay.display();
                            do {
                                System.out.println("1.Check your shopping cart        2.Search the product        3.Sort the list and display       4.Display invoice history          0.Exit");
                                choose = sc.nextLine();
                            }while (!(ValidateChoice.Choice(choose)));
                            int userChoice = (int)choose.charAt(0) - 48;
                            switch (userChoice){
                                case 1 -> {
                                    int cartCheck = 0;
                                    while (cartCheck != 1){
                                        userManage.displayTheAmount(user);
                                        userManage.displayTheCart(user);
                                        ProductDisplay.display();
                                        do {
                                            System.out.println("1.Adding new product to your cart      2.Remove the product        3.Payment          4.Deposit the amount           0.Back to main menu");
                                            choose = sc.nextLine();
                                        }while (!(ValidateChoice.Choice(choose)));
                                        int cartChoice = (int)choose.charAt(0) - 48;
                                        switch (cartChoice){
                                            case 1 -> {
                                                System.out.println("Enter the product name: ");
                                                String name = sc.nextLine();
                                                userManage.addProductToCart(user, name);
                                            }
                                            case 2 -> {
                                                System.out.println("Enter the product name: ");
                                                String name = sc.nextLine();
                                                boolean isRemove = userManage.removeProduct(user, name);
                                                if(isRemove){
                                                    System.out.println("Success remove!");
                                                }
                                            }
                                            case 3 ->{
                                                System.out.println("Enter the product name you want to pay: ");
                                                String name = sc.nextLine();
                                                boolean isPaid = userManage.payment(user, name);
                                                if(isPaid){
                                                  boolean isExchange =  userManage.exchange(user, name);
                                                  if(isExchange){
                                                      System.out.println("Success!");
                                                  }
                                                }
                                            }
                                            case 4 -> {
                                                System.out.println("Enter the amount you want to deposit: ");
                                                double amount = sc.nextDouble();
                                                sc.nextLine();
                                                boolean isDepose = userManage.deposit(user, amount);
                                                if(isDepose){
                                                    System.out.println("Success!");
                                                }
                                            }
                                            case 0 -> cartCheck = 1;
                                        }
                                    }
                                }
                                case 2 -> {
                                    System.out.println("Enter the product name you want to search!");
                                    String name = sc.nextLine();
                                    userManage.searchProduct(user, name);
                                }
                                case 3 -> ProductDisplay.SortTheList();
                                case 4 -> userManage.displayThePaidHistory(user.getAccountName());
                                case 0 -> UserCheck = 1;
                            }
                        }
                    }
                }
                 case 3 -> {
                     System.out.println("Enter your account name: ");
                     String name = sc.nextLine();
                     accountManage.forgetPassword(name);
                 }
                 case 0 -> System.exit(0);
            }
        }
    }
}
