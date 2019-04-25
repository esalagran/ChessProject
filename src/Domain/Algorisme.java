package Domain;

import java.util.Vector;

import static Domain.Color.blanc;
import static Domain.Color.negre;

public class Algorisme extends Maquina{

    private final int INFINIT = 100000;
    protected final int depthValidar = 6;
    private FitxaProblema fitxa_move;
    private ParInt pos_move;
    private int depth;
    private Color torn;
    private Color guanyador;

    /**
     * \pre:
     * \post: Inicialitzacio de les propietats basiques d'algorisme
     */
    public Algorisme (){
        depth = INFINIT;
    }

    public void setTorn(Color torn) { this.torn = torn;}

    public Color getGuanyador(){ return guanyador;}

    /**
     * \pre: d > 0, tauler representa un problema valid, step_counter = 0, s'ha cridat setTorn
     * \post: Es guarden les propietats de la meva jugador amb profunditat d pel Color color
     *  @return Estimacio de la millor jugada
     */
    public int Minimax (int d, Color color, Tauler tauler, int step_counter) {
        boolean check = check(tauler,color);
        if (step_counter < depth && check) {
            depth = (step_counter + 1)/2;
            guanyador = Convert.InvertColor(color);
        }
        if (d == 0 || check) return estimacio(tauler);
        else {
            int best_move = -INFINIT;
            int val;
            Vector<FitxaProblema> peces = getFitxes(tauler, color);
            FitxaProblema substituida;
            for (FitxaProblema aux : peces) {
                Vector<ParInt> moviments = aux.GetMoviments(tauler);
                for (ParInt moviment : moviments) {
                    substituida = tauler.FitxaAt(moviment);
                    ParInt ini = aux.GetCoordenades();
                    tauler.moureFitxa(ini, moviment);

                if (color.equals(blanc)) val = -Minimax(d - 1, negre, tauler, step_counter + 1);
                    else val = -Minimax(d - 1, blanc, tauler, step_counter + 1);

                    if (val > best_move) {
                        best_move = val;
                        if (torn.equals(color)) {
                            fitxa_move = aux;
                            pos_move = moviment;
                        }
                    }
                    tauler.desferJugada(moviment, ini, substituida);
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
        this.torn = torn;
        if (tauler.getWhiteKing() == null || tauler.getBlackKing() == null){
            System.out.println("Falten reis");
            return false;
        }
        else{
            int a = Minimax(depthValidar,torn,tauler,0);
            return guanyador != null;
        }
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