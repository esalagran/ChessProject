package Drivers;

import Domain.*;

import java.util.Scanner;

public class DriverFitxaProblema{

    static Scanner sc = new Scanner(System.in);
    private static FitxaProblema fp;

    static {
        fp = new FitxaProblema(TipusPeça.Dama, new ParInt(4, 4), Color.blanc);
    }

    static private void init(){

        String s = "---------------------------------------\n"
                + "--------FITXA PROBLEMA DRIVER GUIDE---------\n"
                + "---------------------------------------\n\n";

        System.out.println(s);
        s = "----------CHOOSE YOUR OPTION----------\n"
                + "~~~~~~~   0  -> EXIT                       ~~~~~~~\n"
                + "~~~~~~~   1  -> SET COORDENADES ~~~~~~~\n"
                + "~~~~~~~   2  -> GET COORDENADES    ~~~~~~~\n"
                + "~~~~~~~   3  -> GET TIPUS PEÇA    ~~~~~~~\n"
                + "~~~~~~~   4  -> GET COLOR ~~~~~~~\n";
        System.out.println(s);
    }

    public static void start(int cas) {

        switch (cas) {
            case 0:
                System.out.println("Bye Bye!");
                break;

            case 1: // 2 -> SET COORDENADES
                System.out.println("Input a new position(fil,col)");
                int fil = sc.nextInt();
                int col = sc.nextInt();
                fp.SetCoordenades(new ParInt(fil, col));
                System.out.println("Les coordenades de la peça són: ");
                System.out.println("Fila: " + fp.GetCoordenades().GetFirst());
                System.out.println("Columna: " + fp.GetCoordenades().GetSecond());
                System.out.print("\n");
                break;
            case 2:
                System.out.println("Les coordenades de la peça són:");
                ParInt par = fp.GetCoordenades();
                System.out.println("Fila: " + par.GetFirst());
                System.out.println("Columna: " + par.GetSecond());
                break;
            case 3:
                System.out.println("La peça és del tipus " + fp.getIFitxa().getClass());
                break;
            case 4:
                System.out.println("La fitxa és de color " + fp.GetColor().toString());
                break;
        }
    }
    public static void main(String[] args){
        init();
        int num;
        do{
            System.out.println("Input a new number");
            num = sc.nextInt();
            start(num);
        }while(num != 0);
    }
}
