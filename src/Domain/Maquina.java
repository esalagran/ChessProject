package Domain;

import java.util.*;

import static Domain.Color.blanc;
import static Domain.Color.negre;

public class Maquina extends Usuari{

    protected int depth;
    protected int heuristica;      //Estimacio
    private FitxaProblema fitxa_move;
    private ParInt pos_move;

    public Maquina(){}

    public void setDepth(int d){
        if (depth > 0) depth = d;
    }

    public int getDepth(){
        return depth;
    }

    public int Minimax (int d, boolean blanc, Tauler tauler) {
        if (d == 0 || check()) return estimacio(tauler);
        else {
            int best_move = 0;
            Vector <FitxaProblema> peces = getFitxes(tauler, blanc);
            for (int i = 0; i < peces.size(); i++){
                FitxaProblema aux = peces.get(i);
                Possibilitats p = new Possibilitats();
                p.validMoves(aux, tauler,blanc);
                Vector <ParInt> moviments = p.getMoviments();
                for (int j = 0; j < moviments.size(); j++){
                    ParInt ini = aux.GetCoordenades();
                    tauler.moureFitxa(ini,moviments.get(j));
                    ParInt pos = new ParInt(ini.GetFirst()+moviments.get(j).GetFirst(), ini.GetSecond()+moviments.get(j).GetSecond());
                    int val = -Minimax (d-1,!blanc,tauler);
                    if (val > best_move){
                        best_move = val;
                        fitxa_move = peces.get(i);
                        pos_move = pos;
                    }
                    ParInt undo = new ParInt(-moviments.get(j).GetFirst(), -moviments.get(j).GetSecond());
                    tauler.moureFitxa(pos, undo);
                }
            }
            return best_move;
        }
    }

    private boolean check() { return true;}

    public boolean validarProblema(int idP){
        return false;
    }

    private int estimacio (Tauler tauler){
        FitxaProblema[][] actual = tauler.getTaulell();
        int weight = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (actual[i][j] != null) weight += actual[i][j].GetPes();

        return weight;

    }

    private Vector<FitxaProblema> getFitxes (Tauler tauler, boolean white){
        FitxaProblema[][] fitxes = tauler.getTaulell();
        Vector <FitxaProblema> sol = new Vector<>();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (white && fitxes[i][j].GetColor() == blanc) sol.add(fitxes[i][j]);
                if (!white && fitxes[i][j].GetColor() == negre) sol.add(fitxes[i][j]);
            }
        }
        return sol;

    }

}