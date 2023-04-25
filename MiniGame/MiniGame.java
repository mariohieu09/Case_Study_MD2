package MiniGame;

import Account.AccountManage;

import java.util.Scanner;
import Account.*;

public class MiniGame {
    Scanner sc = new Scanner(System.in);
    public void start(Account acc){
        Game game = new Game();
        Xucxac xx = new Xucxac();
        int check = 0;
        UserManage am = new UserManage();
        while(check != 1) {
            int currentRoll = am.displayRoll(acc);
            System.out.println("""
                    **********************************************************|              
                    1.Bet                  2.Deposit                  3.Back  |
                                                                              |
                    **********************************************************|                
                    """);
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                if (currentRoll > 0) {
                    game.run();
                    int choose = sc.nextInt();
                    sc.nextLine();
                    double bet = am.bet(acc);
                    int Tong = xx.run1();
                    if (choose == 1) {
                        if (Tong > 10) {
                            am.win(acc, bet);
                            System.out.println("Win");
                        } else {
                            System.out.println("Lose");
                        }
                    } else if (choose == 2) {
                        if (Tong <= 10) {
                            am.win(acc, bet);
                            System.out.println("Win");
                        } else {
                            System.out.println("Lose");
                        }
                    }
                }else{
                    System.out.println("You don't have any roll. Please buy some product!");
                }
            }else if (choice == 2) {
                    System.out.println("Enter the amount: ");
                    double amount = sc.nextDouble();
                    am.deposit(acc, amount);
                } else {
                    check = 1;
                }
            }

    }
}
