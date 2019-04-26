
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
    Color torn, tornInicial;
    Modalitat mode;
    boolean isBlackHuman;
    boolean isWhiteHuman;
    boolean mat;
    Color guanyador;
    Problema probl;
    Tauler tauler;
    boolean hasEnded = false;
    int moviments = 0;
    Maquina m = new Maquina();


    public Partida(Problema p, Modalitat mod){

        probl = p;
        torn = probl.GetTorn();
        tornInicial = torn;
        mode = mod;
        tauler = p.getTauler();
    }

    /**
     * \pre:
     * \post: retporna el torn actual, a quin color li toca tirar
     * @return Retorna torn
     */
    public Color GetTorn(){
        return torn;
    }


    /**
     * \pre: l'atribut mode conté una modalitat vàlida
     * \post: els booleans isWhiteHuman i isBlackHuman s'inincialitzen correctament, en cas que el primer torn li toqui a la maquina, crida la funció TornMaquina
     * @return
     */
    public void ComençarPartida(){

        if(mode==Modalitat.MH){
            if(torn == Color.blanc){
                isWhiteHuman = false;
                isBlackHuman = true;
            } else {
                isWhiteHuman = true;
                isBlackHuman = false;
            }

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

        }

        if(mode == Modalitat.HH){
            isWhiteHuman = true;
            isBlackHuman = true;

        }



    }

    /**
     * \pre: posició origen i posició desti valides (entre 0 i 7)
     * \post: mou la peça que hi ha a la posició origen a la posició destí (si el moviment és possible en el context de la partida)
     * @return
     */
    public void MourePeça(ParInt origen, ParInt desti){

        if ( origen.GetFirst() != -1 && origen.GetSecond() != -1) {
            if (tauler.FitxaAt(origen) != null ) {
                if(torn == tauler.FitxaAt(origen).GetColor()){

                }
                else return;


            } else return;


        } else{
            return;

        }

        if (desti.GetFirst() != -1 && desti.GetSecond() != -1) {
            if (tauler.FitxaAt(desti) == null || (tauler.FitxaAt(desti) != null && (tauler.FitxaAt(desti).GetColor() != torn ) )) {

                boolean possible = false;
                Vector<ParInt> movimentsPossibles = tauler.FitxaAt(origen).GetMoviments(tauler);
                for(int i = 0; i < movimentsPossibles.size();i++){
                   ParInt pos = movimentsPossibles.elementAt(i);
                   int posX = pos.GetFirst();
                   int posY = pos.GetSecond();

                    if(posX == desti.GetFirst() && posY == desti.GetSecond()){
                       if(torn == Color.negre){
                           FitxaProblema f = tauler.FitxaAt(desti);
                           tauler.AfegirPeçaAt(desti,tauler.FitxaAt(origen));
                           tauler.AfegirPeçaAt(origen, null);
                           possible = true;
                           if(m.isAttacked(tauler, tauler.getBlackKing(), torn)){
                               System.out.println("moviment il·legal");
                               tauler.AfegirPeçaAt(origen, tauler.FitxaAt(desti));
                               tauler.AfegirPeçaAt(desti, f);
                               return;
                           }
                       }

                       else{
                           FitxaProblema f = tauler.FitxaAt(desti);
                           tauler.AfegirPeçaAt(desti,tauler.FitxaAt(origen));
                           tauler.AfegirPeçaAt(origen, null);
                           possible = true;


                           if(m.isAttacked(tauler, tauler.getWhiteKing(), torn)){
                               System.out.println("moviment il·legal");
                               tauler.AfegirPeçaAt(origen, tauler.FitxaAt(desti));
                               tauler.AfegirPeçaAt(desti, f);
                               return;
                           }}

                   }
                }

                if(!possible){

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

    /**
     * \pre: Color es un color valid
     * \post: retorna si el color passat com a parametre correspon a un humà o no
     * @return
     */
    public boolean isColorHuman(Color color){
        if(color == Color.blanc)
            return isWhiteHuman;
        else return isBlackHuman;

    }

    /**
     * \pre: la partida ha acabat
     * \post: retorna si la partida va acabar en mat o no
     * @return mat
     */
    public boolean wasMat(){
        return mat;
    }

    /**
     * \pre: la partida a acabat
     * \post: retorna el color del guanyador
     * @return guanyador
     */
    public Color getGuanyador(){
        return guanyador;
    }



    /**
     * \pre: torn i tauler diferent de null
     * \post:  ha mogut la maquina
     * @return
     */
   public void TornMaquina(){

       Object[] mov =  m.GetMoviment(5, torn, tauler);
       ParInt a = (ParInt) mov[0];
       ParInt b = (ParInt) mov[1];

       tauler.moureFitxa(a,b);

       FiTorn();

    }

    /**
     * \pre:
     * \post:  retorna el tauler de la partida
     * @return tauler
     */
    public Tauler GetTauler(){
       return tauler;
    }

    /**
     * \pre:
     * \post: retorna si la partida ha acabat
     * @return hasEnded
     */
    public boolean hasEnded(){
        return hasEnded;
    }


    /**
     * \pre: torn i problema diferent de null
     * \post: acaba la partida si es mat o moviments > moviments per guanyar, si no toca acabar la partida, es canvia el torn
     * @return
     */
    public void FiTorn(){
        moviments++;
        boolean mate;
        mate = (m.check(tauler, Color.blanc) | m.check(tauler,Color.negre ));
        if(moviments >= probl.GetMovimentsPerGuanyar() || mate){
            if(mate){
                guanyador = tornInicial;
                mat = true;
            }
            else{
                if(tornInicial == Color.blanc)
                    guanyador = Color.negre;
                else guanyador = Color.blanc;
            }

            hasEnded = true;
            return;
        }
        if(torn == Color.blanc){
            torn = Color.negre;

        }
        else{
            torn = Color.blanc;


    }
}




}