import java.util.Random;

public class test {

    static class tet{
         String e;
    }
    public static int randomNum() {
        int rd = new Random().nextInt(6);
        return rd + 1;
    }

    public static void main(String[] args) {
        int ranDomNumber = randomNum();
        switch (ranDomNumber) {
            case 6:
            System.out.println("""
                    +----------+
                    |  *    *  |
                    |  *    *  |
                    |  *    *  |
                    |__________|
                    """
            );
                break;
            case 1:
                System.out.println("""
                   +----------+
                   |          |  
                   |    *     |
                   |          |
                   |__________|
                """);
                break;
            case 2:
                System.out.println("""
                       +----------+
                       | *        |
                       |          |
                       |        * |
                       |__________|
                        
                        """);
                break;
            case 3:
                System.out.println("""
                        +----------+
                        | *        |
                        |    *     |
                        |       *  |
                        |__________|
                        """);
                break;
            case 4:
                System.out.println("""
                        +----------+
                        | *      * |
                        |          |
                        | *      * |
                        |__________|
                        """);
                break;
            case 5:
                System.out.println("""
                        +----------+
                        | *      * |
                        |     *    |
                        | *      * |
                        |__________|
                        """);
                break;
        }


    }

}