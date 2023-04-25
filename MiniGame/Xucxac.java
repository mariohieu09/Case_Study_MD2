package MiniGame;
import java.util.Random;
import java.util.Scanner;

public class Xucxac implements Runnable{
    public int rd1;
    int rd2;
    int rd3;

    public static int randomNum() {
        int rd = new Random().nextInt(6);
        return rd + 1;
    }

    @Override
    public void run() {
    }

    public static String print(int ranDomNumber){
        String xucXac = "";
        switch (ranDomNumber) {
            case 6:
                xucXac = ("""     
                    +----------+       
                    |  *    *  |      
                    |  *    *  |      
                    |  *    *  |      
                    |__________|      
                    """
                );
                break;
            case 1:
                xucXac = ("""     
                +----------+     
                |          |     
                |    *     |     
                |          |     
                |__________|     
                """);
                break;
            case 2:
                xucXac =("""     
                       +----------+       
                       | *        |       
                       |          |       
                       |        * |       
                       |__________|       
                        
                        """);
                break;
            case 3:
                xucXac =("""     
                        +----------+       
                        | *        |       
                        |    *     |       
                        |       *  |       
                        |__________|       
                        """);
                break;
            case 4:
                xucXac =("""     
                        +----------+      
                        | *      * |      
                        |          |      
                        | *      * |      
                        |__________|      
                        """);
                break;
            case 5:
                xucXac =("""     
                        +----------+      
                        | *      * |      
                        |     *    |      
                        | *      * |
                        |__________|
                        """);
                break;
        }
        return xucXac;
    }

    public int run1() {
        int Tong = Tong1();
        System.out.println(print(rd1));
        System.out.println(print(rd2));
        System.out.println(print(rd3));
        if(Tong > 10){
            System.out.println("Tai "  + Tong);
        }else{
            System.out.println("Xiu "  + Tong);
        }

        return Tong;
    }
    public int Tong1(){
        rd1 = randomNum();
        rd2 = randomNum();
        rd3 = randomNum();
        int Tong = rd1 + rd2 + rd3;
        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).run();
        return Tong;
    }
}

