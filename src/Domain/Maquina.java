package Domain;

import java.util.*;

import static Domain.Color.blanc;
import static Domain.Color.negre;

/**
 * @class Maquina
 * \brief Superclasse de l'algorisme de cerca heurísitca
 */

public class Maquina extends Usuari{

    protected int depth;
    private FitxaProblema fitxa_move;
    private ParInt pos_move;
    private Algorisme algorisme;
    private Color guanyador;

    /**
     * Creadora buida
     */
    public Maquina(){
        guanyador = null;
    }

    /**
     * Setter de la profunditat de cerca
     * @param d enter amb la profunditat desitjada
     */
    public void setDepth(int d){
        if (depth > 0) depth = d;
    }

    /**
     * Consulta de la profunditat de cerca
     * @return enter amb la profunditat de cerca
     */
    public int getDepth(){
        return depth;
    }

    /**
     * Consulta el color guanyador després d'aplicar un algorisme de cerca
     * @return Color guanyador
     */
    protected Color getGuanyador() { return guanyador; }

    /**
     * Genera un moviment optim pel jugador Maquina
     * @param d profunditat de cerca
     * @param color torn del jugador en la partida actual
     * @param tauler situacio del tauler en la partida actual
     * @return Objecte amb la fitxa a moure i la posicio
     */
    public Object[] GetMoviment(int d, Color color, Tauler tauler) {
        algorisme = new Algorisme();
        int puntuacio = algorisme.Minimax(d, color, tauler,0);
        fitxa_move = algorisme.getFitxa_move();
        pos_move = algorisme.getPos_move();
        return new Object[]{fitxa_move.GetCoordenades(), pos_move};
    }

    /**
     * Consulta si es dona una situacio d'escac i mat contra el jugador de Color color
     * @param tauler Tauler amb una situacio determinada
     * @param color Color del jugador el que volem consultar l'escac i mat
     * @return Retorna cert si el jugador de Color color es troba en escac i mat
     */
    protected boolean check(Tauler tauler, Color color) {
        if (color == negre) return isAttacked(tauler,tauler.getBlackKing(), negre) && !mateEvitable(tauler,negre);
        else return isAttacked(tauler, tauler.getWhiteKing(),blanc) && !mateEvitable(tauler,blanc);
    }

    /**
     * Consulta si es dona una situacio d'atac en contra d'una FitxaProblema
     * @param tauler Tauler amb una situacio determinada
     * @param fitxa FitxaProblema la qual volem saber si es amenaçada
     * @param color Color del jugador rival, per tant el color de la fitxa != color
     * @return Retorna true si la fitxa es amenaçada per una fitxa rival
     */
    public boolean isAttacked(Tauler tauler, FitxaProblema fitxa, Color color) {
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                FitxaProblema aux = tauler.FitxaAt(new ParInt(i, j));
                if (aux != null &&  aux.GetColor() != color){
                    Vector<ParInt> moves = aux.getIFitxa().GetMoviments(new ParInt(i,j),tauler, aux.GetColor());
                    for (ParInt move : moves) {
                        if (move.GetFirst() == fitxa.GetCoordenades().GetFirst() &&
                                move.GetSecond() == fitxa.GetCoordenades().GetSecond()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Consulta si donada una situacio de mate es evitable
     * @param tauler Tauler amb una situacio determinada
     * @param color color del jugador que es troba en mat
     * @return Retorna true si el mat es evitable i per tant no hi ha escac i mat
     */
    public boolean mateEvitable (Tauler tauler, Color color){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++){
                FitxaProblema aux = tauler.FitxaAt(new ParInt(i, j));
                if (aux != null && aux.GetColor() == color){
                    Vector<ParInt> moves = aux.getIFitxa().GetMoviments(new ParInt(i,j),tauler, aux.GetColor());
                    for (int k = 0; k < moves.size(); k++) {
                        FitxaProblema substituida = tauler.FitxaAt(moves.get(k));
                        tauler.moureFitxa(new ParInt(i, j), moves.get(k));
                        if (color == blanc) if (!isAttacked(tauler, tauler.getWhiteKing(), negre)) {
                            //Sino es atacat esfaig moviment i retorno evitable
                            tauler.desferJugada(moves.get(k), new ParInt(i, j),substituida);
                            return true;
                        }
                        if (color == negre) if (!isAttacked(tauler, tauler.getBlackKing(), blanc)){
                            //Sino es atacat esfaig moviment i retorno evitable
                            tauler.desferJugada(moves.get(k), new ParInt(i, j),substituida);
                            return true;
                        }
                        tauler.desferJugada(moves.get(k),new ParInt(i,j),substituida);
                    }
                }
            }
        }
        if (color == negre) guanyador = blanc;
        else guanyador = negre;
        return false;
    }
}
