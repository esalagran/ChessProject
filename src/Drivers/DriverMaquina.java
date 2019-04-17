package Drivers;

import Domain.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class DriverMaquina {

    private Algorisme a;
    private Tauler tauler;

    public void createTauler(){
        //REIS
        FitxaProblema peça = new FitxaProblema(TipusPeça.Rei,4,4,Color.blanc);
        FitxaProblema peça2 = new FitxaProblema(TipusPeça.Rei,1,2,Color.negre);

        //ALTRES FITXES
        FitxaProblema rival1 = new FitxaProblema(TipusPeça.Alfil,3,2,Color.negre);
        FitxaProblema rival2 = new FitxaProblema(TipusPeça.Peo,2,5,Color.blanc);
        FitxaProblema meva = new FitxaProblema(TipusPeça.Peo,6,3,Color.blanc);
        FitxaProblema cavall = new FitxaProblema(TipusPeça.Cavall,5,6,Color.blanc);

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

    public void tryMinimax(){
        a = new Algorisme();
        int out = a.Minimax(3,Color.blanc,tauler);
        System.out.println(out);

    }

    public void getPecesNegres(){
        a = new Algorisme();
        Vector<FitxaProblema> peces =  a.getFitxes(tauler,Color.negre);
        for (FitxaProblema e : peces)
            if (e != null)
                System.out.println("Nom: " + e.GetTipus() + " a (" + e.GetFila() + "," + e.GetCol() + ")");
    }

    public void getPecesBlanques(){
        a = new Algorisme();
        Vector<FitxaProblema> peces = a.getFitxes(tauler,Color.blanc);
        for (FitxaProblema e : peces)
            if (e != null)
                System.out.println("Nom: " + e.GetTipus() + " a (" + e.GetFila() + "," + e.GetCol() + ")");
    }

    public static void main(String [] args) throws IOException {
        while (true){
            Scanner help = new Scanner(System.in);
            DriverMaquina dm = new DriverMaquina();
            System.out.println("Driver per la classe Maquina:");
            System.out.println("    Prem 1 per seleccionar la peces blanques");
            System.out.println("    Prem 2 per seleccionar les peces negres");
            System.out.println("    Prem 3 per provar el Minimax");

            dm.createTauler();

            char aux = (char) System.in.read();
            String salto = help.nextLine();
            switch(aux){
                case '1':
                    dm.getPecesBlanques();
                    break;
                case '2':
                    dm.getPecesNegres();
                    break;
                case '3':
                    dm.tryMinimax();
            }
        }
    }
}
