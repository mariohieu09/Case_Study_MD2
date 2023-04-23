package Account;

import FileIO.ReadFile;
import FileIO.WriteFile;
import Validate.Validate;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class AccountManage {
    Scanner sc = new Scanner(System.in);
    List<Account> accounts;
    ReadFile rf = new ReadFile();
    WriteFile wf = new WriteFile();
    File DataBase = new File("DataBase.txt");

    public boolean SignUp(){
        boolean isSignUp = false;
        accounts = rf.readFile(DataBase);
        System.out.println("Who do you want to sign up: \n1.Sign up as User  \n2.Sign up as Seller");
        int choice = sc.nextInt();
        sc.nextLine();
        String accountName;
        String password = null;
        String inAppName = null;
        do {
            System.out.println("Enter new account name(6 Character long): ");
            accountName = sc.nextLine();
        }while (!(Validate.Signup_validate(accountName)));
        boolean isExist = checkTheAccountInDataBase(accountName);
        if (!isExist) {
            System.out.println("Enter new password: ");
            password = sc.nextLine();
            System.out.println("Enter new in app name: ");
            inAppName = sc.nextLine();
                if (choice == 1) {
                    Account user = new User(inAppName, accountName, password);
                    accounts.add(user);
                    isSignUp = true;
                } else {
                    Account Seller = new Seller(inAppName, accountName, password);
                    accounts.add(Seller);
                    isSignUp = true;
                }
            }else{
                isSignUp = false;
            }
        wf.writeFile(DataBase, accounts);
        return isSignUp;
    }
    private boolean checkTheAccountInDataBase(String name){
        boolean isExist = false;
        accounts = rf.readFile(DataBase);
        for(Account account : accounts){
            if(account.getAccountName().equals(name)){
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    private boolean checkIfTheAccountExist(String name, String password){
        boolean isExist = false;
        accounts = rf.readFile(DataBase);
        for(Account account : accounts){
            if(account.getAccountName().equals(name) && account.getPassword().equals(password)){
                isExist = true;
                break;
            }
        }
        return isExist;
    }
    public int SignIn(String accountName, String password){
        int Role = -1;
        accounts = rf.readFile(DataBase);
        boolean isExist = checkIfTheAccountExist(accountName, password);
            if(isExist){
              String lilRole = checkRole(accountName);
              if(lilRole.equals("Seller")){
                  Role = 1;
              }else{
                  Role = 0;
              }
            }
            return Role;
        }
    public String checkRole(String accountName){
        accounts = rf.readFile(DataBase);
        String role = "None";
        Account account = accounts.stream()
                .filter(x -> x.getAccountName().equals(accountName))
                .findAny().get();
        role = account.getRole();
        return  role;
    }
    public void forgetPassword(String name){
        accounts = rf.readFile(DataBase);
        boolean exist = checkTheAccountInDataBase(name);
        if(exist){
            System.out.println("Enter new password: ");
            String password = sc.nextLine();
            for(Account account : accounts){
                if(account.getAccountName().equals(name)){
                    account.setPassword(password);
                    System.out.println("Success!");
                    break;
                }
            }
        }else{
            System.out.println("Can't find the account!");
        }
        wf.writeFile(DataBase, accounts);
    }
}
