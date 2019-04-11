package Drivers;

import Domain.ParInt;


import java.util.Scanner;

public class DriverParInt{

    static Scanner sc = new Scanner(System.in);
    static ParInt parInt = new ParInt();

    static private void init(){

        String s = "---------------------------------------\n"
                + "--------PARINT DRIVER GUIDE---------\n"
                + "---------------------------------------\n\n";

        System.out.println(s);
        s = "----------CHOOSE YOUR OPTION----------\n"
                + "~~~~~~~   0  -> EXIT                       ~~~~~~~\n"
                + "~~~~~~~   1  -> SET FIRST                 ~~~~~~~\n"
                + "~~~~~~~   2  -> GET FIRST    ~~~~~~~\n"
                + "~~~~~~~   3  -> SET SECOND                 ~~~~~~~\n"
                + "~~~~~~~   4  -> GET SECOND    ~~~~~~~\n"
                + "~~~~~~~   5  -> CONSTRUCTOR EMPTY                 ~~~~~~~\n"
                + "~~~~~~~   6  -> CONSTRUCTOR NOT EMPTY    ~~~~~~~\n";
        System.out.printf(s);
    }

    public static void start(int num) {

        switch (num) {
            case 0:
                System.out.println("Bye Bye!");
                break;

            case 1: // 1 -> SET FIRST
                System.out.println("Input a number");
                parInt.SetSecond(sc.nextInt());

            case 2: // 2 -> GET SECOND
                System.out.println("El segon element es" + parInt.GetSecond());
                break;
            case 3: // 3 -> SET FIRST
                System.out.println("Input a number");
                parInt.SetFirst(sc.nextInt());

            case 4: // 4 -> GET FIRST
                System.out.println("El primer element es " + parInt.GetFirst());
                break;
            case 5: // 5 -> EMPTY BUILDER
                parInt = new ParInt();
                System.out.println("El primer element es " + parInt.GetFirst());
                System.out.println("El segon element es " + parInt.GetSecond());
                break;
            case 6: // 6 -> BUILDER
                System.out.println("Input the first number");
                int x = sc.nextInt();
                System.out.println("Input the second number");
                parInt = new ParInt(x, sc.nextInt());
                System.out.println("El primer element es " + parInt.GetFirst());
                System.out.println("El segon element es " + parInt.GetSecond());
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
