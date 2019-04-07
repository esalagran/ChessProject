package Drivers;

import Domain.Cavall;
import Domain.VectMov;

import java.util.Scanner;

public class DriverCavall{

    static Scanner sc = new Scanner(System.in);
    static Cavall c = new Cavall();

    static private void init(){

        String s = "---------------------------------------\n"
                + "--------HORSE DRIVER GUIDE---------\n"
                + "---------------------------------------\n\n";

        System.out.println(s);
        s = "----------CHOOSE YOUR OPTION----------\n"
                + "~~~~~~~   0  -> EXIT                       ~~~~~~~\n"
                + "~~~~~~~   1  -> GET WEIGHT                 ~~~~~~~\n"
                + "~~~~~~~   2  -> GET VECTOR OF MOVEMENTS    ~~~~~~~\n";
        System.out.printf(s);
    }

    public static void start(int sc) {

        switch (sc){
            case 0:
                System.out.println("Bye Bye!");
                break;

            case 1: // 1 -> GET WEIGHT
                System.out.println("El pes del cavall és " + c.GetPes());
                break;

            case 2: // 2 -> GET VECTOR OF MOVEMENTS
                VectMov[] m = c.GetMoviments();
                System.out.println("Moviments del cavall");
                for (VectMov v:m) {
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
