package Drivers;

import Domain.NumMaxPeces;
import Domain.TipusPeça;
import Domain.VectMov;

import java.util.HashMap;
import java.util.Scanner;

public class DriverNumMaxPeces{

    static Scanner sc = new Scanner(System.in);

    static private void init(){

        String s = "---------------------------------------\n"
                + "--------MAX NUMBER OF PIECES DRIVER GUIDE---------\n"
                + "---------------------------------------\n\n";

        System.out.println(s);
        s = "----------CHOOSE YOUR OPTION----------\n"
                + "~~~~~~~   0  -> EXIT                       ~~~~~~~\n"
                + "~~~~~~~   1  -> GET INSTANCE                 ~~~~~~~\n";
        System.out.printf(s);
    }

    public static void start(int sc) {

        switch (sc) {
            case 0:
                System.out.println("Bye Bye!");
                break;

            case 1: // 1 -> GET INSTANCE
                HashMap<TipusPeça, Integer> peces = NumMaxPeces.getInstance();
                for (TipusPeça tp:peces.keySet()) {
                    System.out.println(tp.toString() + ": " + peces.get(tp));
                }
                break;
        }
    }
    public static void main(String args[]){
        init();
        int num;
        do{
            System.out.println("Input a new number");
            num = sc.nextInt();
            start(num);
        }while(num != 0);
    }
}
