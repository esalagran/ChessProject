
package Domain;

import java.io.Console;
import java.util.Vector;
import java.util.logging.ConsoleHandler;

public class Partida{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[37m";
    public static final String ANSI_PURPLE = "\u001B[30m";
    public static final String ANSI_BLACK = "\u001B[30m";


    Usuari atacant, defensor;
    Color torn;
    Modalitat mode;
    boolean isBlackHuman;
    boolean isWhiteHuman;
    String guanyador;
    Problema probl;
    Tauler tauler;
    boolean hasEnded = false;
    int moviments =0;
    Maquina m = new Maquina();


    public Partida(Usuari u1, Usuari u2, Problema p){
        atacant = u1;
        defensor = u2;
        probl = p;
    }

    public Partida(Problema p, Modalitat mod){

        probl = p;
        torn = probl.GetTorn();
        mode = mod;
        tauler = p.getTauler();
    }

    public Color GetTorn(){
        return torn;
    }


    public void ComençarPartida(){

        if(mode==Modalitat.MH){
            if(torn == Color.blanc){
                isWhiteHuman = false;
                isBlackHuman = true;
            } else {
                isWhiteHuman = true;
                isBlackHuman = false;
            }
            TornMaquina();

        }
        if(mode == Modalitat.HM){
            if(torn == Color.blanc){
                isWhiteHuman = true;
                isBlackHuman = false;
            } else {
                isWhiteHuman = false;
                isBlackHuman = true;
            }
        }

        if(mode == Modalitat.MM){
                isWhiteHuman = false;
                isBlackHuman = false;
                TornMaquina();

        }

        if(mode == Modalitat.HH){
            isWhiteHuman = true;
            isBlackHuman = true;

        }

        dibuixaProblema();

    }


    public void MourePeça(ParInt origen, ParInt desti){

        if ( origen.GetFirst() != -1 && origen.GetSecond() != -1) {
            if (tauler.FitxaAt(origen.GetFirst(), origen.GetSecond()) != null ) {
                if(torn == tauler.FitxaAt(origen.GetFirst(), origen.GetSecond()).GetColor()){

                    System.out.println(tauler.FitxaAt(origen.GetFirst(), origen.GetSecond()).GetColor());
                }
                else return;


            } else return;


        } else{
            return;

        }

        if (desti.GetFirst() != -1 && desti.GetSecond() != -1) {
            if (tauler.FitxaAt(desti.GetFirst(), desti.GetSecond()) == null || (tauler.FitxaAt(desti.GetFirst(), desti.GetSecond()) != null && (tauler.FitxaAt(desti.GetFirst(), desti.GetSecond()).GetColor() != torn ) )) {

                boolean possible = false;
                Vector<ParInt> movimentsPossibles = tauler.FitxaAt(origen.GetFirst(),origen.GetSecond()).GetMoviments(tauler);
                for(int i = 0; i < movimentsPossibles.size();i++){
                   ParInt pos = movimentsPossibles.elementAt(i);
                   int posX = pos.GetFirst();
                   int posY = pos.GetSecond();

                   if(posX == desti.GetFirst() && posY == desti.GetSecond()){
                       possible = true;
                       System.out.println(posX + " " + posY);
                   }
                }

                if(possible){

                tauler.AfegirPeçaAt(desti.GetFirst(), desti.GetSecond(),tauler.FitxaAt(origen.GetFirst(),origen.GetSecond()));
                tauler.AfegirPeçaAt(origen.GetFirst(), origen.GetSecond(), null);
                } else{
                    System.out.println("Moviment no possible");
                    return;
                }



            } else{
                return;


            }


        } else{
            return;


        }

        FiTorn();

    }




   public void TornMaquina(){

       System.out.print("Es el torn de les: ");
       if(torn == Color.blanc)
           System.out.println("Blanques");
       else System.out.println(("Negres"));

       Object[] mov =  m.GetMoviment(20, torn, tauler);
       ParInt a = (ParInt) mov[0];
       ParInt b = (ParInt) mov[1];
       System.out.println(a.GetFirst() + " " + a.GetSecond());
       System.out.println(b.GetFirst() + " " + b.GetSecond());

       tauler.moureFitxa(a,b);

       FiTorn();

    }


    public boolean hasEnded(){
            return hasEnded;
    }


    public void FiTorn(){
        moviments++;
        if(moviments >= probl.GetMovimentsPerGuanyar()){
            hasEnded = true;
            return;
        }
        dibuixaProblema();
        //CHECK MATE OR MOVEMENTS
        if(torn == Color.blanc){
            torn = Color.negre;
            if(!isBlackHuman)
                TornMaquina();
        }
        else{
            torn = Color.blanc;
            if(!isWhiteHuman)
                TornMaquina();


    }
}

    public void dibuixaProblema(){

        String formatB = ANSI_BLACK +  "| " + ANSI_BLUE + "%c " + ANSI_RESET ;
        String formatW = ANSI_BLACK + "| " + ANSI_RED + "%c " + ANSI_RESET ;
        System.out.println();
        System.out.println(ANSI_BLACK + "  +---+---+---+---+---+---+---+---+" + ANSI_RESET);
        for(int i = 0; i < 8; i++){
            System.out.print(ANSI_BLACK);
            System.out.print(8-i);
            System.out.print(" " + ANSI_RESET);

            for (int j = 0; j<8; j++){
                if(tauler.FitxaAt(i,j) != null){
                    TipusPeça tP = Convert.ClassToTipusPeça(tauler.FitxaAt(i, j).getIFitxa().getClass().toString());
                    Color c = tauler.FitxaAt(i,j).GetColor();

                    if(tP == TipusPeça.Cavall){

                        if(c == Color.negre)
                            System.out.printf(formatB,  'C' );
                        else System.out.printf(formatW, 'C');
                    }
                    if(tP == TipusPeça.Peo){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'P');
                        else System.out.printf(formatW, 'P');
                    }
                    if(tP == TipusPeça.Alfil){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'A');
                        else System.out.printf(formatW, 'A');

                    }

                    if(tP == TipusPeça.Torre){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'T');
                        else System.out.printf(formatW,'T');
                    }

                    if(tP == TipusPeça.Rei){

                        if(c == Color.negre)
                            System.out.printf(formatB, 'R');
                        else System.out.printf(formatW, 'R');

                    }

                    if(tP == TipusPeça.Dama){

                        if(c == Color.negre)
                            System.out.printf(formatB, 'D');
                        else System.out.printf(formatW, 'D');
                    }

                }
                else {
                    System.out.print("|   ");
                }
            }
            System.out.print(ANSI_BLACK + '|');
            System.out.println();
            System.out.println("  +---+---+---+---+---+---+---+---+" + ANSI_RESET);


        }

        System.out.println(ANSI_BLACK + "    A   B   C   D   E   F   G   H" + ANSI_RESET );

    }


}