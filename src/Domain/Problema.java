/**
 * @file Problema.java
 * @brief
 *
 * @autor Oriol
 */

package Domain;

import javax.net.ssl.KeyManager;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.IllegalCharsetNameException;
import java.security.KeyPair;
import java.util.*;

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

    private boolean _isMayus(char ch){
        if(ch >= 65 && ch<= 90)
            return  true;
        else return  false;
    }



    public HashMap<String, Integer> getRanking() {
        return _ranking;
    }

    public int GetId(){ return _id;}
    public String GetFEN(){ return _FEN;}

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
    public FitxaProblema[][] FENtoTauler(){
        try {

            int x = 0;
            FitxaProblema[][] tauler = new FitxaProblema[8][8];


            for(int i = 0; i < _FEN.length(); i++){
                char ch = _FEN.charAt(i);
                int l = x / 8;
                int k = x - (x / 8) * 8;
               // System.out.println( "Pos: " + x + " X: "+ String.valueOf(k) + " Y: " + String.valueOf(l) + "CHAR: " + ch );

                if(x >= 64)
                    break;

                if(ch == 57){
                    System.out.println("FEN invalidm 9 es massa gran" + ch);
                    break;

                }

                if (ch > 48 && ch < 57){
                    x += (ch - 48);
                    if(k +(ch - 48) > 8){
                        System.out.println("FEN invalid, espai massa gran " + k+(ch - 48));
                        break;
                    }
                }

                if(ch == '/' && k!= 0){
                    System.out.println("FEN invalid, / detectat massa d'hora " + k);
                    break;
                }

                else if(ch == 'n' || ch == 'N') {
                    tauler[x / 8][x -(x / 8) * 8] = new FitxaProblema(TipusPeça.Cavall, new ParInt(x % 8, x - (x % 8) * 8), !_isMayus(ch));
                    x++;
                }
                else if(ch == 'b' || ch == 'B') {
                    tauler[x / 8][x - (x / 8) * 8] = new FitxaProblema(TipusPeça.Alfil, new ParInt(x % 8, x - (x % 8) * 8), !_isMayus(ch));
                    x++;
                }
                else if(ch == 'p' || ch == 'P') {
                    tauler[x / 8][x - (x / 8) * 8] = new FitxaProblema(TipusPeça.Peo, new ParInt(x % 8, x - (x % 8) * 8), !_isMayus(ch));
                    x++;
                }
                else if(ch == 'k' || ch == 'K') {
                    tauler[x / 8][x - (x / 8) * 8] = new FitxaProblema(TipusPeça.Rei, new ParInt(x % 8, x - (x % 8) * 8), !_isMayus(ch));
                    x++;
                }
                else if(ch == 'r' || ch == 'R') {

                    tauler[x / 8][x - (x / 8) * 8] = new FitxaProblema(TipusPeça.Torre, new ParInt(x % 8, x - (x % 8) * 8), !_isMayus(ch));
                    x++;
                }
                else if(ch == 'q' || ch == 'Q') {
                    tauler[x / 8][x - (x / 8) * 8] = new FitxaProblema(TipusPeça.Dama, new ParInt(x % 8, x - (x % 8) * 8), !_isMayus(ch));
                    x++;
                }


            }

            return tauler;

        }catch (Exception e){

            System.out.print("ERROOOOOR: " + e);
            return  null;
        }

    }


    /**
     * A partir del vector de FitxaProblema genera el codi FEN
     */


    public void AfegirPeça(FitxaProblema fp){

    }
}


