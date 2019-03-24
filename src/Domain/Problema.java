/**
 * @file Problema.java
 * @brief
 *
 * @autor Oriol
 */

package Domain;

import java.util.HashMap;
import java.util.Vector;

/**
 * @class Problema
 * \brief
 */

public class Problema{
    private int id;
    private String FEN;
    private Dificultat dif;
    private boolean guardat;
    private boolean valid;
    private Huma creador;
    private HashMap<String, Integer> ranking;
    private Vector<FitxaProblema> FitxesProblema;

    /**
     * Constructora buida
     */
    public Problema() {}

    /**
     * Constructora 2
     */
    public Problema (int a, String b, Dificultat c, Huma d){
        id = a;
        FEN = b;
        dif = c;
        creador = d;
    }

    public void setDificultat (Dificultat d){
        dif = d;
    }

    public void validarProblema (){
        Maquina aux = new Maquina();
        valid = aux.validarProblema(id);
    }

    public HashMap<String, Integer> getRanking() {
        return ranking;
    }

    //RANKING FUNCTIONS

    /**
     * Si el jugador ja existeix en el ranking actualiza la seva puntacio,
     * sino crea una nova instancia amb el nickname i la puntuacio
     * @param nickname Nom del jugador a inscriure al ranking
     * @param puntuacio Puntuacio del jugador en el problema
     */
    public void inscriureRanking(String nickname, Integer puntuacio) {
        //Replaces the entry for the specified key only if it is currently mapped to some value, otherwise returns NULL
        if (ranking.replace (nickname, puntuacio) == null) ranking.put(nickname,puntuacio);
    }

    public Integer consultarPuntuacioJugador(String nickname) {
        return ranking.get(nickname);
    }

    //PRIVATES

    /**
     * A partir del codi FEN genera el vector de FitxaProblema
     */
    private void FENtoTauler(){

    }

    /**
     * A partir del vector de FitxaProblema genera el codi FEN
     */
    private void TaulertoFEN(){

    }
}


