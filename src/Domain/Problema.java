/**
 * @file Problema.java
 * @brief
 *
 * @autor Oriol
 */

package Domain;

import javax.net.ssl.KeyManager;
import java.nio.charset.IllegalCharsetNameException;
import java.security.KeyPair;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

/**
 * @class Problema
 * \brief
 */

public class Problema{
    private int _id;
    private String _FEN;
    private Dificultat _dif;
    private boolean _guardat;
    private boolean _valid;
    private Huma _creador;
    private HashMap<String, Integer> _ranking;
    private Vector<FitxaProblema> _fitxesProblema;
    private NumMaxPeces _pecesMax;
    private HashMap<ParTipusPeçaBool,Integer> _numTipusPeça;


    private void FillDictionary(){
        _numTipusPeça.put(new ParTipusPeçaBool(TipusPeça.Peo, true), 0);
        _numTipusPeça.put(new ParTipusPeçaBool(TipusPeça.Cavall, true), 0);
        _numTipusPeça.put(new ParTipusPeçaBool(TipusPeça.Alfil, true), 0);
        _numTipusPeça.put(new ParTipusPeçaBool(TipusPeça.Torre, true), 0);
        _numTipusPeça.put(new ParTipusPeçaBool(TipusPeça.Dama, true), 0);
        _numTipusPeça.put(new ParTipusPeçaBool(TipusPeça.Rei, true), 0);

        _numTipusPeça.put(new ParTipusPeçaBool(TipusPeça.Peo, false), 0);
        _numTipusPeça.put(new ParTipusPeçaBool(TipusPeça.Cavall, false), 0);
        _numTipusPeça.put(new ParTipusPeçaBool(TipusPeça.Alfil, false), 0);
        _numTipusPeça.put(new ParTipusPeçaBool(TipusPeça.Torre, false), 0);
        _numTipusPeça.put(new ParTipusPeçaBool(TipusPeça.Dama, false), 0);
        _numTipusPeça.put(new ParTipusPeçaBool(TipusPeça.Rei, false), 0);

    }
    /**
     * Constructora buida
     */
    public Problema() {
        _pecesMax = new NumMaxPeces();
        _numTipusPeça = new HashMap<ParTipusPeçaBool, Integer>();
        FillDictionary();
    }

    /**
     * Constructora 2
     */
    public Problema (int id, String FEN, Dificultat d, Huma h){
        _id = id;
        _FEN = FEN;
        _dif = d;
        _creador = h;
    }

    public void setDificultat (Dificultat d){
        _dif = d;
    }

    public void validarProblema (){
        Maquina aux = new Maquina();
        _valid = aux.validarProblema(_id);
    }

    public HashMap<String, Integer> getRanking() {
        return _ranking;
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
        if (_ranking.replace (nickname, puntuacio) == null) _ranking.put(nickname,puntuacio);
    }

    public Integer consultarPuntuacioJugador(String nickname) {
        return _ranking.get(nickname);
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

    public void AfegirPeça(FitxaProblema fp){

    }
}


