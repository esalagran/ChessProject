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
        FitxaProblema peça = new FitxaProblema(TipusPeça.Rei,new ParInt(4,4),Color.blanc);
        FitxaProblema peça2 = new FitxaProblema(TipusPeça.Rei,1,2,Color.negre);

        //ALTRES FITXES
        FitxaProblema rival1 = new FitxaProblema(TipusPeça.Alfil,3,2,Color.negre);
        FitxaProblema rival2 = new FitxaProblema(TipusPeça.Peo,2,5,Color.blanc);
        FitxaProblema meva = new FitxaProblema(TipusPeça.Peo,6,3,Color.blanc);
        FitxaProblema cavall = new FitxaProblema(TipusPeça.Cavall,5,6,Color.blanc);

        FitxaProblema[][] aux = new FitxaProblema[8][8];
        aux [4][4] = peça; aux[2][1] = peça2;
        aux [2][3] = rival1; aux[5][2] = rival2;
        aux [3][6] = meva; aux[6][5] = cavall;

        tauler = new Tauler(aux);
    }

    public void createTauler2(){
            Problema p = new Problema ( "7k/1r4R1/4b2K/7B/8/8/6R1/8 w - - 0 1");
            //Problema p = new Problema ( "3K4/8/8/p2k4/pp1B4/N5N1/P2Q4/8 w - - 0 0");
            tauler = p.getTauler();
    }

    public void tryMinimax(){
        a = new Algorisme();
        a.setTorn(Color.blanc);
        int out = a.Minimax(4,Color.blanc,tauler,0);
        System.out.println(out);
        FitxaProblema sol = a.getFitxa_move();
        ParInt coord = a.getPos_move();
        System.out.println(Convert.ClassToTipusPeça(sol.getIFitxa().getClass().toString()) + " " + sol.GetColor() + " a " + coord.GetFirst() + "," + coord.GetSecond());
    }

    public void getPecesNegres(){
        a = new Algorisme();
        Vector<FitxaProblema> peces =  a.getFitxes(tauler,Color.negre);
        for (FitxaProblema e : peces)
            if (e != null)
                System.out.println("Nom: " + Convert.ClassToTipusPeça(e.getIFitxa().getClass().toString())+ " a (" + e.GetCoordenades().GetFirst() + "," + e.GetCoordenades().GetSecond() + ")");
    }

    public void getPecesBlanques(){
        a = new Algorisme();
        Vector<FitxaProblema> peces = a.getFitxes(tauler,Color.blanc);
        for (FitxaProblema e : peces)
            if (e != null)
                System.out.println("Nom: " + Convert.ClassToTipusPeça(e.getIFitxa().getClass().toString())+ " a (" + e.GetCoordenades().GetFirst() + "," + e.GetCoordenades().GetSecond() + ")");
    }

    public void tryValidarProblema(){
        a = new Algorisme();
        boolean b = a.validarProblema(Color.negre,tauler);
        System.out.println(a.getGuanyador());
        if (b) System.out.println("El problema es valid en " + a.getDepth() + " passos");
        else System.out.println("El problema no es valid en 6 jugades");
    }

    public void MinImax(){
        String FEN1 = "6k1/4Rppp/8/8/8/3K4/8/8 w - - 1 0";
        String FEN2 = "4kb1r/p2n1ppp/4q3/4p1B1/4P3/1Q6/PPP2PPP/2KR4 w k - 1 0";
        String FEN3 = "7k/1r4R1/4b2K/7B/8/8/6R1/8 w - - 0 1";
        String FEN4 = "2B5/7p/5K2/7k/7p/8/6P1/5R2 w - - 0 1 v: true";
        Problema p = new Problema(FEN4);
        AlgorismeMinMax alg = new AlgorismeMinMax();
        Color torn = Color.negre;
        for (int i = 6; i> 0; --i){
            torn = Convert.InvertColor(torn);
            Move m = alg.FindBestMoveUsingMinMaxAtDepth(p.getTauler(), torn, i);
            if (m == null){
                Convert.DibuixaTauler(p.getTauler());
                System.out.println("Ha guanyat el jugador " + Convert.InvertColor(torn));
                break;
            }
            else {
                Convert.DibuixaTauler(p.getTauler());
                p.getTauler().moureFitxa(m);
                System.out.println("S'ha mogut de (" + m.getStartPos().GetFirst() + "," +
                        m.getStartPos().GetSecond() + ") a (" + m.getEndPos().GetFirst() + ","+
                        m.getEndPos().GetSecond() + ")");
            }
        }
        //System.out.println(alg.MinMax(p.getTauler(), Color.blanc, 6, false));

    }

    public void JugarPartidaFake(){
        String FEN3 = "7k/1r4R1/4b2K/7B/8/8/6R1/8 w - - 0 1";
        Problema p = new Problema(FEN3);
        Tauler t = p.getTauler();
        AlgorismeMinMax amm = new AlgorismeMinMax();
        Move[] conjMov = new Move[5];
        for (int i = 6; i > 0; --i){

        }
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
                case '5':
                    da.MinImax();
                    break;
            }
        }
    }
}
