package Domain;

import java.util.*;

import static Domain.Color.blanc;
import static Domain.Color.negre;

public class Maquina extends Usuari{

    protected int depth;
    private FitxaProblema fitxa_move;
    private ParInt pos_move;
    private Algorisme algorisme = new Algorisme();

    public Maquina(){}

    public void setDepth(int d){
        if (depth > 0) depth = d;
    }

    public int getDepth(){
        return depth;
    }

    public Object[] GetMoviment(int d, Color color, Tauler tauler) {
        int puntuacio = algorisme.Minimax(d, color, tauler);
        fitxa_move = algorisme.getFitxa_move();
        pos_move = algorisme.getPos_move();
        return new Object[]{fitxa_move, pos_move};
    }

    protected boolean check(Tauler tauler, Color color) {
        if (color == negre) return isAttacked(tauler,tauler.getBlackKing()) && !mateEvitable(tauler,negre);
        else return isAttacked(tauler, tauler.getWhiteKing()) && !mateEvitable(tauler,blanc);
    }

    private boolean isAttacked(Tauler tauler, FitxaProblema king) {
        return false;
    }

    private boolean mateEvitable (Tauler tauler, Color color){
        return false;
    }

}