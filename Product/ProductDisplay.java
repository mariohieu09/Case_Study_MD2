package Product;

import Controller.ValidateChoice;
import FileIO.ReadFile;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class ProductDisplay {
    static Scanner sc = new Scanner(System.in);
    static ReadFile rf = new ReadFile();
    static List<Product> productList;
    static File productFile = new File("Product.txt");
    public static void display(){
        productList = rf.readFile(productFile);
        System.out.println("Product list: ");
        for(Product product : productList){
            System.out.println(product);
            System.out.println("Quantity: " + product.getQuantity());
        }
    }
    public static void SortTheList(){
        productList = rf.readFile(productFile);
        String choose;
        do {
            System.out.println("What order you want to sort          1.Sort by name            2.Sort by price          3.Sort by quantity");
            choose = sc.nextLine();
        }while (!(ValidateChoice.Sort_Choice(choose)));
        int choice = (int)choose.charAt(0) - 48;
        if(choice == 1) {
            Collections.sort(productList, Comparator.comparing(Product::getName));
            System.out.println("Sort by name: ");
            for(Product product : productList){
                System.out.println(product);
                System.out.println("Quantity: " + product.getQuantity());
            }
        }else if(choice == 2){
            Collections.sort(productList, Comparator.comparing(Product :: getPrice));
            System.out.println("Sort by price: ");
            for(Product product : productList){
                System.out.println(product);
                System.out.println("Quantity: " + product.getQuantity());
            }
        }else{
            Collections.sort(productList, Comparator.comparing(Product :: getQuantity));
            System.out.println("Sort by quantity: ");
            for(Product product : productList){
                System.out.println(product);
                System.out.println("Quantity: " + product.getQuantity());
            }
        }
    }
}
