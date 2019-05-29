
package Domain;

import java.util.Vector;

public class Partida{

    private String atacant, defensor;
    //EstatPartida estatPartida;
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
    int accumTime = 0;
    Algorithm algorismeAlfaBeta = new AlgorismeAlfaBeta(7);
    AlgorismeMinMax algorismeMinMax = new AlgorismeMinMax(5);
    ParInt[] ultimMovimentMaq = new ParInt[]{ new ParInt(-1, -1), new ParInt(-1, -1)};
    ParInt[] ultimMovimentHum = new ParInt[]{ new ParInt(-1, -1), new ParInt(-1, -1)};


    public Partida(Problema p, Modalitat mod, int tornsPelMate){
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
        switch (mode){
            case MH:
                isWhiteHuman = torn != Color.blanc;
                isBlackHuman = !isWhiteHuman;
                //estatPartida = EstatPartida.tornMaquina;
                break;
            case HH:
                isWhiteHuman = isBlackHuman = true;
                //estatPartida = EstatPartida.tornHuma;
                break;
            case HM:
                isWhiteHuman = torn == Color.blanc;
                isBlackHuman = !isWhiteHuman;
                //estatPartida = EstatPartida.tornHuma;
                break;
            case MM:
                isWhiteHuman = isBlackHuman = false;
                //estatPartida = EstatPartida.tornMaquina;
                break;
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


    public ParInt[] getUltimMovimentMaq() {
        return ultimMovimentMaq;

    }

    public ParInt[] getUltimMovimentHum() {
        return ultimMovimentHum;

    }

    public Color GetColorAt(ParInt coord){
        return tauler.FitxaAt(coord).GetColor();
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

        long startTime = System.currentTimeMillis();
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
        long endTime = System.currentTimeMillis();
        accumTime = accumTime + (int) (endTime-startTime)/1000;
        System.out.println("He trigat " + accumTime);
        ultimMovimentHum = new ParInt[]{origen, desti};
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




    /* boolean ComprovarMat(){
        return algorismeAlfaBeta.IsMate( Color.blanc, tauler) || algorismeAlfaBeta.IsMate(Color.blanc, tauler);
    }*/

    /**
     * \pre: la partida a acabat
     * \post: retorna el color del guanyador
     * @return guanyador
     */
    public Color getGuanyador(){
        return guanyador;
    }


    public Color TornsEntreMaquines(){
        while (!hasEnded){
            TornMaquina();
        }
        return guanyador;
    }

    /**
     * \pre: torn i tauler diferent de null
     * \post:  ha mogut la maquina
     * @return
     */
   public FitxaProblema[][] TornMaquina(){
       if (!hasEnded) {
           Move m = algorismeAlfaBeta.FindBestMoveConcr(tauler, torn, probl.GetMovimentsPerGuanyar() - moviments);
           tauler.moureFitxa(m);
           ultimMovimentMaq = new ParInt[]{m.getStartPos(), m.getEndPos()};
       }
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
        torn = Convert.InvertColor(torn);
        mat |= probl.getTauler().IsMate(torn);
        if (mat || moviments++ >= probl.GetMovimentsPerGuanyar()){
            guanyador = mat ? tornInicial : Convert.InvertColor(tornInicial);
            if (mode != Modalitat.MM)
                inscriureRanking();
            hasEnded = true;
        }
    }
    private void inscriureRanking(){
        int puntuacio = probl.calculPuntuacio(moviments,accumTime);
        if (mode == Modalitat.HM ){
            if ( guanyador == tornInicial)
                probl.inscriureRanking(atacant, puntuacio);
            else {
                probl.inscriureRanking("Maquina",puntuacio);
                probl.inscriureRanking(atacant,accumTime/moviments);
            }
        }
        if (mode == Modalitat.MH){
            if (guanyador != tornInicial)
                probl.inscriureRanking(defensor,puntuacio);
            else {
                probl.inscriureRanking("Maquina",puntuacio);
                probl.inscriureRanking(defensor,accumTime/moviments);
            }
        }
        if (mode == Modalitat.HH){
            if (guanyador == tornInicial){
                probl.inscriureRanking(atacant,puntuacio);
                probl.inscriureRanking(defensor,accumTime/moviments);
            }
            else {
                probl.inscriureRanking(atacant,accumTime/moviments);
                probl.inscriureRanking(defensor,puntuacio);
            }
        }

    }

}