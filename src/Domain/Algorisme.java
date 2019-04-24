package Domain;

import java.util.Vector;

import static Domain.Color.blanc;
import static Domain.Color.negre;

public class Algorisme extends Maquina{

    private FitxaProblema fitxa_move;
    private ParInt pos_move;

    public int Minimax (int d, Color color, Tauler tauler) {
        if (d == 0 || check(tauler,color)) return estimacio(tauler);
        else {
            int best_move = -100000;
            int val;
            Vector<FitxaProblema> peces = getFitxes(tauler, color);
            FitxaProblema substituida = null;
            boolean taken;
            for (int i = 0; i < peces.size(); i++){
                FitxaProblema aux = peces.get(i);
                Vector <ParInt> moviments = aux.GetMoviments(tauler);
                ParInt ini = aux.GetCoordenades();
                for (int j = 0; j < moviments.size(); j++){
                    taken = false;
                    if (tauler.FitxaAt(moviments.get(j).GetFirst(),moviments.get(j).GetSecond()) != null){
                        substituida = tauler.FitxaAt(moviments.get(j).GetFirst(),moviments.get(j).GetSecond());
                        taken =true;
                    }
                    tauler.moureFitxa(ini,moviments.get(j));
                    if (color.equals(blanc))
                        val = -Minimax (d-1, negre,tauler);
                    else
                        val = -Minimax (d-1, blanc,tauler);
                    if (val > best_move){
                        best_move = val;
                        fitxa_move = peces.get(i);
                        pos_move = moviments.get(j);
                    }
                    //Undo
                    if (!taken)tauler.moureFitxa(moviments.get(j), ini);
                    else tauler.desferJugada(moviments.get(j),ini,substituida);
                }
            }
            return best_move;
        }
    }

    public FitxaProblema getFitxa_move() {return fitxa_move;}

    public ParInt getPos_move(){return pos_move;}

    public boolean validarProblema(Color torn, Tauler tauler){
        int a = Minimax(12,torn,tauler);
        return super.getGuanyador() != null;
    }

    private int estimacio (Tauler tauler){
        FitxaProblema[][] actual = tauler.getTaulell();
        int weight = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (actual[i][j] != null) {
                    if (actual[i][j].GetColor() == blanc) weight += actual[i][j].getIFitxa().GetPes();
                    if (actual[i][j].GetColor() == negre) weight -= actual[i][j].getIFitxa().GetPes();
                }
            }
        return weight;

    }

    public Vector<FitxaProblema> getFitxes (Tauler tauler, Color color){
        FitxaProblema[][] fitxes = tauler.getTaulell();
        Vector <FitxaProblema> sol = new Vector<>();
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (fitxes[i][j] != null) {
                    if (color.equals(blanc ) && fitxes[i][j].GetColor().equals(blanc)) sol.add(fitxes[i][j]);
                    if (color.equals(negre) && fitxes[i][j].GetColor().equals(negre)) sol.add(fitxes[i][j]);
                }
            }
        }
        return sol;
    }
}