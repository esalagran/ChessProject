package Domain;

import java.util.*;

import static Domain.Color.blanc;
import static Domain.Color.negre;

public class Maquina extends Usuari{

    protected int depth;
    private FitxaProblema fitxa_move;
    private ParInt pos_move;
    private Algorisme algorisme;

    public Maquina(){}

    public void setDepth(int d){
        if (depth > 0) depth = d;
    }

    public int getDepth(){
        return depth;
    }

    public Object[] GetMoviment(int d, Color color, Tauler tauler) {
        int puntuacio = algorisme.Minimax(depth, color, tauler);
        fitxa_move = algorisme.getFitxa_move();
        pos_move = algorisme.getPos_move();
        return new Object[]{fitxa_move, pos_move};
    }

}