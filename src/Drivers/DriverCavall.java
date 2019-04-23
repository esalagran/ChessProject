package Drivers;

import Domain.*;

import java.util.Scanner;
import java.util.Vector;

public class DriverCavall{

    private static Scanner sc = new Scanner(System.in);
    private static Cavall c = new Cavall();

    static private void init(){

        String s = "---------------------------------------\n"
                + "--------HORSE DRIVER GUIDE---------\n"
                + "---------------------------------------\n\n";

        System.out.println(s);
        s = "----------CHOOSE YOUR OPTION----------\n"
                + "~~~~~~~   0  -> EXIT                       ~~~~~~~\n"
                + "~~~~~~~   1  -> GET WEIGHT                 ~~~~~~~\n"
                + "~~~~~~~   2  -> GET VECTOR OF MOVEMENTS    ~~~~~~~\n"
                + "~~~~~~~   3  -> GET POSSIBLE MOVEMENTS SIMPLE   ~~~~~~~\n"
                + "~~~~~~~   4  -> GET POSSIBLE MOVEMENTS COMPLEX   ~~~~~~~\n";
        System.out.println(s);
    }

    private static void TaulerSimple(){
        FitxaProblema fitxa = new FitxaProblema(TipusPeça.Cavall,4,4, Color.blanc);
        FitxaProblema[][] aux =
                {
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,fitxa,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null}
                };
        Tauler t = new Tauler(aux);
        System.out.println("Moviments del cavall a (4,4)");
        Vector<ParInt>movements = fitxa.getIFitxa().GetMoviments(fitxa.GetCoordenades(), t, fitxa.GetColor());
        for (ParInt var : movements){
            System.out.println("("+var.GetFirst()+","+var.GetSecond()+")");
        }
    }

    private static void TaulerComplex(){
        FitxaProblema fitxa = new FitxaProblema(TipusPeça.Cavall,4,4,Color.blanc);
        FitxaProblema fitxa2 = new FitxaProblema(TipusPeça.Cavall,4,7,Color.blanc);

        FitxaProblema rival1 = new FitxaProblema(TipusPeça.Alfil,3,2,Color.negre);
        FitxaProblema rival3 = new FitxaProblema(TipusPeça.Alfil,3,3,Color.negre);
        FitxaProblema rival2 = new FitxaProblema(TipusPeça.Peo,2,5,Color.negre);
        FitxaProblema meva = new FitxaProblema(TipusPeça.Peo,6,3,Color.blanc);
        FitxaProblema meva2 = new FitxaProblema(TipusPeça.Peo,6,6,Color.blanc);

        FitxaProblema[][] aux =
                {
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,rival2,null,null},
                        {null,null,rival1,null,null,rival3,null,null},
                        {null,null,null,null,fitxa,null,null,fitxa2},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,meva,null,null,meva2,null},
                        {null,null,null,null,null,null,null,null}
                };
        Tauler t = new Tauler(aux);

        System.out.println("Moviments del cavall a (4,4)");
        Vector<ParInt>movements = fitxa.getIFitxa().GetMoviments(fitxa.GetCoordenades(), t, fitxa.GetColor());
        for (ParInt var : movements){
            System.out.println("("+var.GetFirst()+","+var.GetSecond()+")");
        }


        //En aquest cas es tenen en compte els límits del tauler
        System.out.println("Moviments del cavall a (4,7)");
        movements = fitxa2.getIFitxa().GetMoviments(fitxa2.GetCoordenades(), t, fitxa2.GetColor());
        for (ParInt var : movements){
            System.out.println("("+var.GetFirst()+","+var.GetSecond()+")");
        }
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
            case 3: //3 -> GET POSSIBLE MOVEMENTS SIMPLE
                TaulerSimple();
                break;
            case 4: //4 -> GET POSSIBLE MOVEMENTS COMPLEX
                TaulerComplex();
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
