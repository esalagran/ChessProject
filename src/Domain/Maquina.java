package Domain;

import java.util.*;

import static Domain.Color.blanc;
import static Domain.Color.negre;

public class Maquina extends Usuari{

    protected int depth;
    private FitxaProblema fitxa_move;
    private ParInt pos_move;
    private Algorisme algorisme;
    private Color guanyador;

    public Maquina(){}

    public void setDepth(int d){
        if (depth > 0) depth = d;
    }

    public int getDepth(){
        return depth;
    }

    protected Color getGuanyador() { return guanyador; }

    public Object[] GetMoviment(int d, Color color, Tauler tauler) {
        int puntuacio = algorisme.Minimax(depth, color, tauler);
        fitxa_move = algorisme.getFitxa_move();
        pos_move = algorisme.getPos_move();
        return new Object[]{fitxa_move, pos_move};
    }

    protected boolean check(Tauler tauler, Color color) {
        if (color == negre) return isAttacked(tauler,tauler.getBlackKing(), negre) && !mateEvitable(tauler,negre);
        else return isAttacked(tauler, tauler.getWhiteKing(),blanc) && !mateEvitable(tauler,blanc);
    }

    private boolean isAttacked(Tauler tauler, FitxaProblema king, Color color) {
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                FitxaProblema aux = tauler.FitxaAt(i,j);
                if (aux != null &&  aux.GetColor() != color){
                    Vector<ParInt> moves = aux.getIFitxa().GetMoviments(new ParInt(i,j),tauler, aux.GetColor());
                    for (int k = 0; k < moves.size(); k++){
                        if (moves.get(k).GetFirst() == king.GetFila() &&
                                moves.get(k).GetSecond() == king.GetCol()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean mateEvitable (Tauler tauler, Color color){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++){
                FitxaProblema aux = tauler.FitxaAt(i,j);
                if (aux != null && aux.GetColor() == color){
                    Vector<ParInt> moves = aux.getIFitxa().GetMoviments(new ParInt(i,j),tauler, aux.GetColor());
                    for (int k = 0; k < moves.size(); k++) {
                        tauler.moureFitxa(new ParInt(i, j), moves.get(k));
                        if (color == blanc) if (!isAttacked(tauler, tauler.getWhiteKing(), negre)) {
                            //Sino es atacat esfaig moviment i retorno evitable
                            tauler.moureFitxa(moves.get(k), new ParInt(i, j));
                            return true;
                        }
                        if (color == negre) if (!isAttacked(tauler, tauler.getBlackKing(), blanc)){
                            //Sino es atacat esfaig moviment i retorno evitable
                            tauler.moureFitxa(moves.get(k), new ParInt(i, j));
                            return true;
                        }
                        tauler.moureFitxa(moves.get(k),new ParInt(i,j));
                    }
                }
            }
        }
        if (color == negre) guanyador = blanc;
        else guanyador = negre;
        return false;
    }

}