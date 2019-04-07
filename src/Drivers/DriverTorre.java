package Drivers;

import Domain.Torre;
import Domain.VectMov;

import java.util.Scanner;

public class DriverTorre{

    static Scanner sc = new Scanner(System.in);
    static Torre torre = new Torre();

    static private void init(){

        String s = "---------------------------------------\n"
                + "--------ROCK DRIVER GUIDE---------\n"
                + "---------------------------------------\n\n";

        System.out.println(s);
        s = "----------CHOOSE YOUR OPTION----------\n"
                + "~~~~~~~   0  -> EXIT                       ~~~~~~~\n"
                + "~~~~~~~   1  -> GET WEIGHT                 ~~~~~~~\n"
                + "~~~~~~~   2  -> GET VECTOR OF MOVEMENTS    ~~~~~~~\n";
        System.out.printf(s);
    }

    public static void start(int sc) {

        switch (sc) {
            case 0:
                System.out.println("Bye Bye!");
                break;

            case 1: // 1 -> GET WEIGHT
                System.out.println("El pes de la torre és " + torre.GetPes());
                break;

            case 2: // 2 -> GET VECTOR OF MOVEMENTS
                VectMov[] m = torre.GetMoviments();
                System.out.println("Moviments de la torre");
                for (VectMov v : m) {
                    System.out.println("Cas:");
                    System.out.println("Horitzontal: " + v.getH() + "\nVertical: " + v.getV()
                            + "\nDiagonal: " + v.getD());
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
