package Drivers;

import Domain.*;

import java.util.Scanner;
import java.util.Vector;

public class DriverPeo{

    static Scanner sc = new Scanner(System.in);
    static Peo peo = new Peo();

    static private void init(){

        String s = "---------------------------------------\n"
                + "--------POWN HORSE DRIVER GUIDE---------\n"
                + "---------------------------------------\n\n";

        System.out.println(s);
        s = "----------CHOOSE YOUR OPTION----------\n"
                + "~~~~~~~   0  -> EXIT                       ~~~~~~~\n"
                + "~~~~~~~   1  -> GET WEIGHT                 ~~~~~~~\n"
                + "~~~~~~~   2  -> GET VECTOR OF MOVEMENTS    ~~~~~~~\n"
                + "~~~~~~~   3  -> GET MOVIMENTS BLANC TOTS    ~~~~~~~\n"
                + "~~~~~~~   4  -> BLANC NO ES POT MOURE    ~~~~~~~\n"
                + "~~~~~~~   5  -> GET MOVIMENTS NEGRE TOTS    ~~~~~~~\n"
                + "~~~~~~~   6  -> NEGRE NO ES POT MOURE    ~~~~~~~\n";
        System.out.printf(s);
    }

    private static void TaulerSimple(Color color){
        Color contrari;
        if (color.equals(Color.blanc)) contrari = Color.negre;
        else contrari = Color.blanc;

        FitxaProblema fitxa = new FitxaProblema(TipusPeça.Peo,4,4, color);
        FitxaProblema rival = new FitxaProblema(TipusPeça.Cavall,3,3, contrari);
        FitxaProblema rival2 = new FitxaProblema(TipusPeça.Cavall,3,5, contrari);
        FitxaProblema rival3 = new FitxaProblema(TipusPeça.Cavall,5,5, contrari);
        FitxaProblema rival4 = new FitxaProblema(TipusPeça.Cavall,5,3, contrari);


        FitxaProblema[][] aux =
                {
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,rival,null,rival2,null,null},
                        {null,null,null,null,fitxa,null,null,null},
                        {null,null,null,rival3,null,rival4,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null}
                };
        Tauler t = new Tauler(aux);
        System.out.println("Moviments del peo a (4,4)");
        Vector<ParInt> movements = fitxa.getIFitxa().GetMoviments(fitxa.GetCoordenades(), t, fitxa.GetColor());
        for (ParInt var : movements){
            System.out.println("("+var.GetFirst()+","+var.GetSecond()+")");
        }
    }

    private static void TaulerComplex(Color color){
        Color contrari;
        if (color.equals(Color.blanc)) contrari = Color.negre;
        else contrari = Color.blanc;
        FitxaProblema fitxa = new FitxaProblema(TipusPeça.Peo,4,4, color);
        FitxaProblema rival = new FitxaProblema(TipusPeça.Cavall,3,4, contrari);
        FitxaProblema rival2 = new FitxaProblema(TipusPeça.Cavall,5,4, contrari);
        FitxaProblema meva = new FitxaProblema(TipusPeça.Cavall,5,3, color);
        FitxaProblema meva2 = new FitxaProblema(TipusPeça.Cavall,3,3, color);

        FitxaProblema[][] aux =
                {
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,rival,meva,null,null},
                        {null,null,null,null,fitxa,null,null,null},
                        {null,null,null,meva2,rival2,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null}
                };
        Tauler t = new Tauler(aux);
        System.out.println("Moviments del peo a (4,4)");
        Vector<ParInt> movements = fitxa.getIFitxa().GetMoviments(fitxa.GetCoordenades(), t, fitxa.GetColor());
        for (ParInt var : movements){
            System.out.println("("+var.GetFirst()+","+var.GetSecond()+")");
        }
        if (movements.isEmpty()) System.out.println("No hi ha moviments possibles");
    }

    public static void start(int sc) {

        switch (sc) {
            case 0:
                System.out.println("Bye Bye!");
                break;

            case 1: // 1 -> GET WEIGHT
                System.out.println("El pes del peó és " + peo.GetPes());
                break;

            case 2: // 2 -> GET VECTOR OF MOVEMENTS
                VectMov[] m = peo.GetMoviments();
                System.out.println("Moviments del peo");
                for (VectMov v : m) {
                    System.out.println("Cas:");
                    System.out.println("Horitzontal: " + v.getH() + "\nVertical: " + v.getV()
                            + "\nDiagonal: " + v.getD());
                }
                break;
            case 3: // 3 -> GET MOVIMENTS BLANC TOTS
                TaulerSimple(Color.blanc);
                break;
            case 4: // 4-> GET MOVIMENTS NO ES POT MOURE BLANC
                TaulerComplex(Color.blanc);
                break;
            case 5: //5 -> GET MOVIMENTS NEGRE TOTS
                TaulerSimple(Color.negre);
                break;
            case 6: //6 -> GET MOVIMENTS NO ES POT MOURE NEGRE
                TaulerComplex(Color.negre);
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
