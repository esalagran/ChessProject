package Drivers;

import Domain.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import static Domain.Dificultat.mitja;

public class DriverMaquina {

    private Maquina m;
    private Tauler tauler;
    private FitxaProblema peça;

    public void createTauler(){
        //REIS
        peça = new FitxaProblema(TipusPeça.Rei,4,4,Color.blanc);
        FitxaProblema peça2 = new FitxaProblema(TipusPeça.Rei,1,2,Color.negre);

        //ALTRES FITXES
        FitxaProblema rival1 = new FitxaProblema(TipusPeça.Alfil,3,2,Color.negre);
        FitxaProblema rival2 = new FitxaProblema(TipusPeça.Peo,2,5,Color.blanc);
        FitxaProblema meva = new FitxaProblema(TipusPeça.Peo,6,3,Color.blanc);
        FitxaProblema cavall = new FitxaProblema(TipusPeça.Cavall,5,6,Color.negre);

        FitxaProblema[][] aux =
                {
                        {null,null,null,null,null,null,null,null},
                        {null,null,peça2,null,null,null,null,null},
                        {null,null,null,null,null,rival2,null,null},
                        {null,null,rival1,null,null,null,null,null},
                        {null,null,null,null,peça,null,null,null},
                        {null,null,null,null,null,null,cavall,null},
                        {null,null,null,meva,null,null,null,null},
                        {null,null,null,null,null,null,null,null}
                };
        tauler = new Tauler(aux);
    }

    public void tryIsAttacked(){
        m = new Maquina();
        boolean b = m.isAttacked(tauler,peça,Color.blanc);
        if (b) System.out.println("Peça amenaçada");
        else System.out.println("Peça no amenaçada");
    }

    public void tryMateEvitable(){
        m = new Maquina();
        boolean b = m.mateEvitable(tauler,Color.blanc);
        if (b) System.out.println("Si");
        else System.out.println("No");
    }

    public static void main(String [] args) throws IOException {
        Scanner help = new Scanner(System.in);
        DriverMaquina dm = new DriverMaquina();
        String s = "---------------------------------------\n"
                + "--------MAQUINA DRIVER GUIDE---------\n"
                + "---------------------------------------\n\n";

        System.out.println(s);

        boolean exit = false;

        dm.createTauler();

        while (!exit){

            s = "----------CHOOSE YOUR OPTION----------\n"
                    + "~~~~~~~   0  -> EXIT                       ~~~~~~~\n"
                    + "~~~~~~~   1  -> MIRA SI UNA POSICIO ES AMENAÇADA ~~~~~~~\n"
                    + "~~~~~~~   2  -> MIRA SI DONAT UN REI AMENAÇAT ES EVITABLE EL MATE  ~~~~~~~\n";
            System.out.println(s);

            char aux = (char) System.in.read();
            String salto = help.nextLine();
            switch(aux){
                case '0':
                    exit = true;
                    break;
                case '1':
                    System.out.println("    Rei a (4,4) amenaçat per cavall");
                    dm.tryIsAttacked();
                    break;
                case '2':
                    System.out.println ("   Rei amenaçat a (4,4), te escapatoria?");
                    dm.tryMateEvitable();
                    break;
            }
        }
    }
}

