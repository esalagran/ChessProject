package Drivers;

import Domain.*;

import java.io.IOException;
import java.util.*;

public class DriverPossibilitats {
    private Possibilitats a,b,c;
    private FitxaProblema peça;
    private Tauler tauler1,tauler2;

    public void testConstructor1(){
        a = new Possibilitats();
    }

    public void testConstructor2(TipusPeça nom){
        peça = new FitxaProblema(nom,4,4,Color.blanc);
        FitxaProblema[][] aux =
                {
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,peça,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null}
                };
        tauler1 = new Tauler(aux);
        b = new Possibilitats(tauler1);
    }

    public void Constructor3(TipusPeça nom){
        peça = new FitxaProblema(nom,4,4,Color.blanc);

        FitxaProblema rival1 = new FitxaProblema(TipusPeça.Alfil,3,2,Color.negre);
        FitxaProblema rival2 = new FitxaProblema(TipusPeça.Peo,2,5,Color.blanc);
        FitxaProblema meva = new FitxaProblema(TipusPeça.Peo,6,3,Color.blanc);

        FitxaProblema[][] aux =
                {
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,rival2,null,null},
                        {null,null,rival1,null,null,null,null,null},
                        {null,null,null,null,peça,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,meva,null,null,null,null},
                        {null,null,null,null,null,null,null,null}
                };
        tauler2 = new Tauler(aux);
        c = new Possibilitats(tauler2);
    }

    public void getMovimentsReina(){
        testConstructor2(TipusPeça.Dama);
        b.validMoves(peça,tauler1,true);
        Vector<ParInt> moves = b.getMoviments();
        System.out.println("Des de la posició (4,4) podem anar a:");
        for (ParInt var : moves){
            System.out.println("("+var.GetFirst()+","+var.GetSecond()+")");
        }
    }

    public void getMovimentsCavall(){
        testConstructor2(TipusPeça.Cavall);
        b.validMoves(peça,tauler1,true);
        Vector<ParInt> moves = b.getMoviments();
        System.out.println("Des de la posició (4,4) podem anar a:");
        for (ParInt var : moves){
            System.out.println("("+var.GetFirst()+","+var.GetSecond()+")");
        }
    }

    public void getMovimentsCavallComplexe(){
        Constructor3(TipusPeça.Cavall);
        c.validMoves(peça,tauler2,true);
        Vector<ParInt> moves = c.getMoviments();
        System.out.println("Des de la posició (4,4) podem anar a:");
        for (ParInt var : moves){
            System.out.println("("+var.GetFirst()+","+var.GetSecond()+")");
        }
    }

    public static void main(String [] args) throws IOException {
        while (true){
            Scanner help = new Scanner(System.in);
            DriverPossibilitats dp = new DriverPossibilitats();
            System.out.println("Driver per la classe Possibilitats:");
            System.out.println("    Prem 1 per provar la constructora basica");
            System.out.println("    Prem 2 per provar la constructora amb arguments");
            System.out.println("    Prem 3 per generar els moviments de la reina");
            System.out.println("    Prem 4 per generar els moviments del cavall");
            System.out.println("    Prem 5 per generar els moviments del cavall complexe");

            char aux = (char) System.in.read();
            String salto = help.nextLine();
            switch(aux){
                case '1':
                    dp.testConstructor1();
                    System.out.println("Working");
                    break;
                case '2':
                    dp.testConstructor2(TipusPeça.Peo);
                    System.out.println("Working");
                    break;
                case '3':
                    dp.getMovimentsReina();
                    break;
                case '4':
                    dp.getMovimentsCavall();
                    break;
                case '5':
                    dp.getMovimentsCavallComplexe();
                    break;
            }
        }
    }
}
