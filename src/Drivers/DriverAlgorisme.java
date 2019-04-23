package Drivers;

import Domain.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import static Domain.Dificultat.mitja;

public class DriverAlgorisme {

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

    public void createTauler2(){
            Problema p = new Problema (0000, "rnbqkbnr/pp1ppppp/8/2p5/4P3/5N2/PPPP1PPP/RNBQKB1R b KQkq - 1 2", mitja, null);
            tauler = p.getTauler();
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

    public void tryValidarProblema(){
        a = new Algorisme();
        boolean b = a.validarProblema(Color.blanc,tauler);
        if (b)System.out.println("El problema es valid");
        else System.out.println("El problema no es valid");
    }

    public static void main(String [] args) throws IOException {
        Scanner help = new Scanner(System.in);
        DriverAlgorisme da = new DriverAlgorisme();
        String s = "---------------------------------------\n"
                + "--------ALGORISME DRIVER GUIDE---------\n"
                + "---------------------------------------\n\n";

        System.out.println(s);

        s =     "~~~~~~~   a  -> EASY MODE                       ~~~~~~~\n" +
                "~~~~~~~   b  -> DIFFICULT MODE                  ~~~~~~~\n";
        System.out.println(s);
        char aux  = (char) System.in.read();
        String salto = help.nextLine();
        if (aux == 'a') da.createTauler();
        else da.createTauler2();

        boolean exit = false;

        while (!exit){

                s = "----------CHOOSE YOUR OPTION----------\n"
                        + "~~~~~~~   0  -> EXIT                       ~~~~~~~\n"
                        + "~~~~~~~   1  -> SELECCIONAR PECES BLANQUES ~~~~~~~\n"
                        + "~~~~~~~   2  -> SELECCIONAR PECES NEGRES    ~~~~~~~\n"
                        + "~~~~~~~   3  -> MINIMAX    ~~~~~~~\n"
                        + "~~~~~~~   4  -> VALIDAR PROBLEMA    ~~~~~~~\n";
                System.out.println(s);

            aux = (char) System.in.read();
            salto = help.nextLine();
            switch(aux){
                case '0':
                    exit = true;
                    break;
                case '1':
                    da.getPecesBlanques();
                    break;
                case '2':
                    da.getPecesNegres();
                    break;
                case '3':
                    da.tryMinimax();
                    break;
                case '4':
                    da.tryValidarProblema();
            }
        }
    }
}
