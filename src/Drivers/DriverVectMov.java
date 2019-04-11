package Drivers;

import Domain.VectMov;
import java.util.Scanner;

public class DriverVectMov{

    static Scanner sc = new Scanner(System.in);
    static VectMov vectMov;

    static private void init(){

        String s = "---------------------------------------\n"
                + "--------MAX NUMBER OF PIECES DRIVER GUIDE---------\n"
                + "---------------------------------------\n\n";

        System.out.println(s);
        s = "----------CHOOSE YOUR OPTION----------\n"
                + "~~~~~~~   0  -> EXIT                       ~~~~~~~\n"
                + "~~~~~~~   1  -> GET HORITZONTAL                 ~~~~~~~\n"
                + "~~~~~~~   2  -> GET DIAGONAL                 ~~~~~~~\n"
                + "~~~~~~~   3  -> GET VERTICAL                 ~~~~~~~\n";
        System.out.printf(s);
    }

    public static void start(int num) {

        switch (num) {
            case 0:
                System.out.println("Bye Bye!");
                break;

            case 1: // 1 -> GET HORITZONTAL
                System.out.println("Horitzontal: " + vectMov.getH());
                break;
            case 2: // 1 -> GET DIAGONAL
                System.out.println("Diagonal: " + vectMov.getD());
                break;
            case 3: // 1 -> GET VERTICAL
                System.out.println("Vertical: " + vectMov.getV());
                break;
        }
    }
    public static void main(String args[]){
        int num;
        System.out.println("Input horitzontal movements");
        int h = sc.nextInt();
        System.out.println("Input vertical movements");
        int v = sc.nextInt();
        System.out.println("Input diagonal movements");
        vectMov = new VectMov(h, v, sc.nextInt());
        System.out.println("Movement vector created");
        init();
        do{
            System.out.println("Input a new number");
            num = sc.nextInt();
            start(num);
        }while(num != 0);
    }
}
