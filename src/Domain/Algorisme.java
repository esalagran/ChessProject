package Domain;

import java.util.Vector;

import static Domain.Color.blanc;
import static Domain.Color.negre;

public class Algorisme extends Maquina{

    protected final int INFINIT = 100000;
    private FitxaProblema fitxa_move;
    private ParInt pos_move;
    private int step_counter;
    private int depth;

    /**
     * \pre:
     * \post: Inicialitzacio de les propietats basiques d'algorisme
     */
    public Algorisme (){
        step_counter = 0;
        depth = INFINIT;
    }

    /**
     * \pre: d > 0, tauler representa un problema valid, step_counter = 0
     * \post: Es guarden les propietats de la meva jugador amb profunditat d pel Color color
     *  @return Estimacio de la millor jugada
     */
    public int Minimax (int d, Color color, Tauler tauler, int step_counter) {
        if (step_counter < depth && check(tauler,color)) {
            System.out.println("MATE");
            depth = step_counter;
        }
        if (d == 0 || check(tauler,color)) return estimacio(tauler);
        else {
            int best_move = -INFINIT;
            int val;
            Vector<FitxaProblema> peces = getFitxes(tauler, color);
            FitxaProblema substituida = null;
            for (int i = 0; i < peces.size(); i++){
                FitxaProblema aux = peces.get(i);
                Vector <ParInt> moviments = aux.GetMoviments(tauler);
                for (int j = 0; j < moviments.size(); j++){
                    substituida = tauler.FitxaAt(moviments.get(j));
                    ParInt ini = aux.GetCoordenades();
                    tauler.moureFitxa(ini,moviments.get(j));
                    if (color.equals(blanc)) val = -Minimax (d-1, negre,tauler,step_counter+1);
                    else val = -Minimax (d-1, blanc,tauler,step_counter+1);

                    if (val > best_move){
                        best_move = val;
                        fitxa_move = peces.get(i);
                        pos_move = moviments.get(j);
                    }
                    tauler.desferJugada(moviments.get(j),ini, substituida);
                }
            }
            return best_move;
        }
    }

    /**
     * \pre: S'ha aplicat l'algorisme Minimax
     * \post: Retorna la FitxaProblema de la millor jugada
     * @return Retorna la FitxaProblema de la millor jugada
     */
    public FitxaProblema getFitxa_move() {return fitxa_move;}

    /**
     * \pre: S'ha aplicat l'algorisme Minimax
     * \post: Retorna la coordenada de la millor jugada
     * @return Retorna la coordenada de la millor jugada
     */
    public ParInt getPos_move(){return pos_move;}

    /**
     * \pre: S'ha validat el problema i es valid
     * \post: Retorna el minim nombre de passos per resoldre
     * @return Retorna el minim nombre de passos per resoldre
     */
    public int getDepth() {return depth;}

    /**
     * \pre:
     * \post: L'atribut guanyador conte el guanyador si el problema es valid
     * @return Retorna true si el problema es valid, sino fals
     */
    public boolean validarProblema(Color torn, Tauler tauler){
        int a = Minimax(6,torn,tauler,0);
        System.out.println(a);
        return super.getGuanyador() != null;
    }

    /**
     * \pre: Tauler no buit
     * \post: Retorna un enter que representa la situacio del tauler
     * @return Retorna un enter que representa la situacio del tauler
     */
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

    /**
     * \pre: Tauler no buit
     * \post: Retorna totes les fitxes del tauler del Color color
     * @return Retorna totes les fitxes del tauler del Color color
     */
    public Vector<FitxaProblema> getFitxes (Tauler tauler, Color color){
        FitxaProblema[][] fitxes = tauler.getTaulell();
        Vector <FitxaProblema> sol = new Vector<>();
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (fitxes[i][j] != null)
                    if (color.equals(fitxes[i][j].GetColor())) sol.add(fitxes[i][j]);
        return sol;
    }
}