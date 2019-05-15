
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


    private String atacant, defensor;
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
    AlgorismeMinMax alg = new AlgorismeMinMax();


    public Partida(Problema p, Modalitat mod, int torns){

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

        if (!Convert.InTheLimits(origen)) return null;
        if (tauler.FitxaAt(origen) == null) return null;
        if(!torn.equals(tauler.FitxaAt(origen).GetColor())) return null;
        if (!Convert.InTheLimits(desti)) return null;
        if (tauler.FitxaAt(desti) != null && tauler.FitxaAt(desti).GetColor() == torn) return null;

        boolean possible = false;
        Vector<ParInt> movimentsPossibles = tauler.FitxaAt(origen).GetMoviments(tauler);
        for (ParInt move : movimentsPossibles) {
            Move m = new Move(tauler.FitxaAt(move), origen, move);
            if (move.GetFirst() == desti.GetFirst() && move.GetSecond() == desti.GetSecond()){
                tauler.moureFitxa(m);
                boolean isAttacked;
                if (torn.equals(Color.blanc)){
                    isAttacked = tauler.getWhiteKing().isAttacked(tauler.getTaulell());
                }
                else{
                    isAttacked = tauler.getBlackKing().isAttacked(tauler.getTaulell());
                }
                if (isAttacked){
                    tauler.desferJugada(m);
                    System.out.println("Moviment il·legal");
                    return null;
                }
                possible = true;
                break;
            }
        }
        if(!possible){
            System.out.println("Moviment no possible");
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
   public FitxaProblema[][] TornMaquina(){

       /*Object[] mov =  m.GetMoviment(4, torn, tauler);
       ParInt a = (ParInt) mov[0];
       ParInt b = (ParInt) mov[1];*/
       Move m = alg.FindBestMoveUsingMinMaxAtDepth(tauler, torn, 6);
       tauler.moureFitxa(m);

       FiTorn();
       return tauler.getTaulell();

    }

    public String EndedReason(){

       if(mat)
           return "Guanyen les " + guanyador.toString() + " per mat";
       else  return "Guanyen les " + guanyador.toString() + " per sobreviure " + probl.GetMovimentsPerGuanyar() + " torns";

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
            inscriureRanking();
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
    private void inscriureRanking(){
        int puntuacio = probl.calculPuntuacio(moviments,guanyador);
        if (mode == Modalitat.HM)probl.inscriureRanking(atacant,puntuacio);
        if (mode == Modalitat.MH)probl.inscriureRanking(defensor,puntuacio);
    }

}