
package Domain;

import java.util.Vector;

public class Partida{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[37m";
    public static final String ANSI_PURPLE = "\u001B[30m";
    public static final String ANSI_BLACK = "\u001B[30m";


    String atacant, defensor;
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


    public Partida(Problema p, Modalitat mod, String atac, String defensa){

        probl = p;
        torn = probl.GetTorn();
        tornInicial = torn;
        mode = mod;
        tauler = p.getTauler();
        atacant = atac;
        defensor = defensa;
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
     * \pre: mode valid
     * \post: retorna la modalitat de la partida
     * @return
     */
    public Modalitat GetModalitat(){
        return mode;
    }

    /**
     * \pre: posició origen i posició desti valides (entre 0 i 7)
     * \post: mou la peça que hi ha a la posició origen a la posició destí (si el moviment és possible en el context de la partida)
     * @return
     */
    public FitxaProblema[][] MourePeça(ParInt origen, ParInt desti){

        if ( origen.GetFirst() != -1 && origen.GetSecond() != -1) {
            if (tauler.FitxaAt(origen) != null ) {
                if(torn == tauler.FitxaAt(origen).GetColor()){

                }
                else return null;


            } else return null;


        } else{
            return null;

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
                               return null;
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
                               return null;
                           }}

                   }
                }

                if(!possible){

                    System.out.println("Moviment no possible");
                    return null;
                }


            } else{
                return null;


            }


        } else{
            return null;


        }


        FiTorn();
        return tauler.getTaulell();

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




    public boolean ComprovarMat(){

        return (m.check(tauler, Color.blanc) | m.check(tauler,Color.negre ));
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

       Object[] mov =  m.GetMoviment(4, torn, tauler);
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
        mate = ComprovarMat();
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
            //guanyador
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