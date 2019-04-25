package Drivers;

import Domain.*;

import java.util.*;

public class DriverFitxa {

    private FitxaProblema fitxa;
    private Tauler tauler1,tauler2;

    private void ConstructorSimple(TipusPeça nom){
        fitxa = new FitxaProblema(nom,4,4,Color.blanc);
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
        tauler1 = new Tauler(aux);
    }

    private void ConstructorComplex(){
        fitxa = new FitxaProblema(TipusPeça.Dama,4,4,Color.blanc);

        FitxaProblema rival1 = new FitxaProblema(TipusPeça.Alfil,2,2,Color.negre);
        FitxaProblema rival2 = new FitxaProblema(TipusPeça.Peo,2,5,Color.negre);
        FitxaProblema meva = new FitxaProblema(TipusPeça.Peo,5,4,Color.blanc);
        FitxaProblema meva2 = new FitxaProblema(TipusPeça.Peo,4,3,Color.blanc);

        FitxaProblema[][] aux =
                {
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,rival1,null,null,rival2,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,meva2,fitxa,null,null,null},
                        {null,null,null,null,meva,null,null,null},
                        {null,null,null,null,null,null,null,null},
                        {null,null,null,null,null,null,null,null}
                };
        tauler2 = new Tauler(aux);
    }

    private void getMovimentsReina(){
        ConstructorSimple(TipusPeça.Dama);
        var moves = fitxa.GetMoviments(tauler1);
        System.out.println("Des de la posició (4,4) podem anar a:");
        for (ParInt var : moves){
            System.out.println("("+var.GetFirst()+","+var.GetSecond()+")");
        }
    }


    private void getMovimentsReinaComplexe(){
        ConstructorComplex();
        var moves = fitxa.GetMoviments(tauler2);
        System.out.println("Des de la posició (4,4) podem anar a:");
        for (ParInt var : moves){
            System.out.println("("+var.GetFirst()+","+var.GetSecond()+")");
        }
    }

    public static void main(String [] args){
        int num;
        Scanner sc = new Scanner(System.in);
        do{
            DriverFitxa dp = new DriverFitxa();
            System.out.println("Driver per la classe Possibilitats:");
            System.out.println("    Prem 0 per sortir");
            System.out.println("    Prem 1 per provar la constructora amb arguments");
            System.out.println("    Prem 2 per generar els moviments de la reina");
            System.out.println("    Prem 3 per generar els moviments del reina complexe");

            num = sc.nextInt();
            switch(num){
                case 0:
                    System.out.println("Bye bye");
                    break;
                case 1:
                    dp.ConstructorSimple(TipusPeça.Peo);
                    System.out.println("Working");
                    break;
                case 2:
                    dp.getMovimentsReina();
                    break;
                case 3:
                    dp.getMovimentsReinaComplexe();
                    break;
            }
        }while(num != 0);
    }
}
