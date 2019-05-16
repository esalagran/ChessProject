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
 * \brief Classe contenidora d'un problema
 */

public class Problema{
    private int _id;
    private String _FEN;
    private Dificultat _dif;
    private Color torn = Color.blanc;
    private boolean _guardat;
    private Tauler tauler;
    private boolean _valid;
    private int movimentsPerGuanyar = 6;
    private int nPeces;
    private SortedMap<String,Integer> ranking;
    private Tema tema;
    private Huma _creador;


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[37m";
    public static final String ANSI_PURPLE = "\u001B[30m";
    public static final String ANSI_BLACK = "\u001B[30m";


    public Problema(String FEN){
        _FEN = FEN;
        if (!_FEN.isEmpty()) {
            if (_FEN.contains(new StringBuilder(1).append('w'))) this.torn = Color.blanc;
            else this.torn = Color.negre;
        }
        tauler = FENtoTauler();
        nPeces = getNPeces().GetFirst()+getNPeces().GetSecond();
        _valid = false;
    }

    public Problema(String FEN, Tema tema,boolean valid){
        _FEN = FEN;
        if (!_FEN.isEmpty()) {
            if (_FEN.contains(new StringBuilder(1).append('w'))) this.torn = Color.blanc;
            else this.torn = Color.negre;
        }
        tauler = FENtoTauler();
        nPeces = getNPeces().GetFirst()+getNPeces().GetSecond();
        _valid = valid;
        this.tema = tema;
    }

    public Tema getTema() {return tema;}

    /**
     * \pre: torn es un color valid
     * \post: retorna el color del jugador atacant
     * @return torn
     */
    public Color GetTorn(){
        return torn;
    }


    /**
     * \pre: tauler es un tauler valid
     * \post: retorna el tauler del problema
     * @return tauler
     */
    public Tauler getTauler() { return tauler; }


    /**
     * \pre: movimentsPerGuanyar diferent de null
     * \post: retorna els moviments necessaris per fer mat
     * @return movimentsPerGuanyar
     */
    public int GetMovimentsPerGuanyar(){
        return movimentsPerGuanyar;
    }



    /**
     * \pre: tp, c i desti son parametres valids
     * \post: afegida peça tipus tp de color c a la posició desti
     * @return
     */
    public FitxaProblema[][] AfegirPeça(TipusPeça tp, Color c, ParInt desti ){
        try {
            tauler.AfegirPeçaAt(desti, new FitxaProblema(tp, desti, c));
            nPeces +=1;
            return tauler.getTaulell();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * \pre: origen valid
     * \post: la peça que hi havia a la posicio origen ha estat eliminada
     * @return
     */
    public FitxaProblema[][] EliminarPeça(ParInt origen){
        try{
            tauler.EliminarPeçaAt(origen);
            nPeces-=1;
            return tauler.getTaulell();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
return null;
    }

    /**
     * \pre: origen i desti son parametres valids
     * \post: la peça que hi havia a origen ara esta a dessti
     * @return
     */
    public FitxaProblema[][] MourePeça(ParInt origen, ParInt desti){

        if ( origen.GetFirst() != -1 && origen.GetSecond() != -1) {
            if (tauler.FitxaAt(origen) == null) {

                System.out.println(ANSI_RED + "La posició d'origen està buida" + ANSI_RESET);

            }


        } else{
            System.out.println(ANSI_RED + "Coordenada no valida" + ANSI_RESET);

        }

        if (desti.GetFirst() != -1 && desti.GetSecond() != -1) {
            if (tauler.FitxaAt(desti) == null) {
                tauler.AfegirPeçaAt(desti,tauler.FitxaAt(origen));
                tauler.AfegirPeçaAt(origen, null);

                return tauler.getTaulell();


            } else{
                System.out.println(ANSI_RED + "La posició destí està ocupada" + ANSI_RESET);

            }


        } else{
            System.out.println(ANSI_RED + "Coordenada no valida" + ANSI_RESET);

        }
        return null;
    }

    /**
     * \pre:
     * \post: retorna si la partida es considerada valida
     * @return _valid
     */
    public boolean GetValid(){
        return _valid;
    }

    /**
     * \pre:
     * \post: si el problema es valid es posa _valid a true i es guarda la profuncitat del problema
     * @return
     */
    public boolean validarProblema (){
        Algorisme aux = new Algorisme();
        _valid = aux.validarProblema(torn,tauler);
        if(_valid){
            movimentsPerGuanyar = aux.getDepth();
            setDificultat();
        }
        return _valid;
    }

    public void setDificultat(){
        ParInt peces = getNPeces(); //First: Blanques Second: Negres
        if (movimentsPerGuanyar < 2) _dif = Dificultat.moltfacil;
        else if (torn == Color.blanc && peces.GetFirst()/2 >= peces.GetSecond()) _dif = Dificultat.moltfacil;
        else if (torn == Color.negre && peces.GetSecond()/2 >= peces.GetFirst()) _dif = Dificultat.moltfacil;

        else if (movimentsPerGuanyar < 3) _dif = Dificultat.facil;
        else if (movimentsPerGuanyar < 3 && torn == Color.blanc && peces.GetFirst() >= 4) _dif = Dificultat.facil;
        else if (movimentsPerGuanyar < 3 && torn == Color.negre && peces.GetSecond() >= 4) _dif = Dificultat.facil;

        else if (movimentsPerGuanyar < 4 && torn == Color.blanc && peces.GetFirst() < 4) _dif = Dificultat.mitja;
        else if (movimentsPerGuanyar < 4 && torn == Color.negre && peces.GetSecond() < 4) _dif = Dificultat.mitja;

        else if (movimentsPerGuanyar < 4 && torn == Color.blanc && peces.GetFirst() < peces.GetSecond()) _dif = Dificultat.dificil;
        else if (movimentsPerGuanyar < 4 && torn == Color.negre && peces.GetFirst() > peces.GetSecond()) _dif = Dificultat.dificil;

        else _dif = Dificultat.moltdificil;
    }


    /**
     * \pre: ch es un char valid
     * \post: retorna un color segons el char es majuscules o minuscules, serveix per traduir FEN
     * @return
     */
    private Color charToColor(char ch){
        if(ch >= 65 && ch<= 90)
            return  Color.blanc;
        else return  Color.negre;
    }


    /**
     * \pre:
     * \post: retorna el FEN actualitzat del problema
     * @return
     */
    public String GetFEN(){
        _FEN = TaulerToFEN();
        return _FEN;
    }

    private ParInt getNPeces(){
        FitxaProblema [][] t = tauler.getTaulell();
        int contB = 0;
        int contN = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (t[i][j] != null && t[i][j].GetColor().equals(Color.blanc)) contB++;
                else if (t[i][j] != null && t[i][j].GetColor().equals(Color.negre)) contN++;

        return new ParInt(contB,contN);
    }

    //RANKING FUNCTIONS

    /**
     *
     * @param nmoviments numero de moviments realitzats a la partida
     * @param guanyador guanyador de la partida
     * @return Puntacio del jugador en el problema segons els parametres passats
     */
    public int calculPuntuacio( int nmoviments, Color guanyador, int accumTime){
        int maxPuntuacio = nPeces * movimentsPerGuanyar * 10;
        int tempsMig = accumTime/nmoviments;
        int boost;
        if (tempsMig > 60) boost = 1;
        else boost = 60 - tempsMig;
        //GUANYA EL QUE COMENÇA AMB MENYS O EL NUMERO QUE TOCA DE JUGADES
        return maxPuntuacio*boost;
    }

    /**
     * Si el jugador ja existeix en el ranking actualiza la seva puntacio,
     * sino rep la informacio necessaria per calcular la puntuacio
     * @param nomJugador Nom del jugador a inscriure al ranking
     * @param puntuacio Puntuacio del jugador
     */
    public void inscriureRanking (String nomJugador, int puntuacio){
        if (ranking.replace(nomJugador,puntuacio) == null) ranking.put(nomJugador,puntuacio);

    }

    public Integer consultarPuntuacioJugador(String nickname) {
        return ranking.get(nickname);
    }



    //FEN-TAULER FUNCTIONS


    /**
     * \pre: problema te un FEN valid
     * \post: retorna un tauler a partir de el FEN del problema
     * @return tauler
     */
    public Tauler FENtoTauler(){
        try {

            int x = 0;
           Tauler tauler = new Tauler();

            for(int i = 0; i < _FEN.length(); i++){
                char ch = _FEN.charAt(i);
                int l = x / 8;
                int k = x - (x / 8) * 8;
                // System.out.println( "Pos: " + x + " X: "+ String.valueOf(k) + " Y: " + String.valueOf(l) + "CHAR: " + ch );

                if(x >= 64)
                    break;

                if(ch == 57){
                    System.out.println("FEN invalid 9 es massa gran" + ch);
                    break;

                }

                if (ch > 48 && ch < 57){
                    x += (ch - 48);
                    if(k +(ch - 48) > 8){
                        System.out.println("FEN invalid, espai massa gran " + k+(ch - 48));
                        break;
                    }
                }
                ParInt coord = new ParInt(x/8, x - (x/8) * 8);
                if(ch == '/' && k!= 0){
                    System.out.println("FEN invalid, / detectat massa d'hora " + k);
                    break;
                }

                else if(ch == 'n' || ch == 'N') {
                    FitxaProblema aux = new FitxaProblema(TipusPeça.Cavall, new ParInt(x / 8, x - (x / 8) * 8), charToColor(ch));
                    tauler.AfegirPeçaAt(coord, aux);
                    x++;
                }
                else if(ch == 'b' || ch == 'B') {
                    FitxaProblema aux = new FitxaProblema(TipusPeça.Alfil, new ParInt(x / 8, x - (x / 8) * 8), charToColor(ch));
                    tauler.AfegirPeçaAt(coord, aux);
                    ++x;
                }
                else if(ch == 'p' || ch == 'P') {
                    FitxaProblema aux = new FitxaProblema(TipusPeça.Peo, new ParInt(x / 8, x - (x / 8) * 8), charToColor(ch));
                    tauler.AfegirPeçaAt(coord, aux);
                    x++;
                }
                else if(ch == 'k' || ch == 'K') {
                    FitxaProblema aux = new FitxaProblema(TipusPeça.Rei, new ParInt(x / 8, x - (x / 8) * 8), charToColor(ch));
                    tauler.AfegirPeçaAt(coord, aux);
                    x++;
                }
                else if(ch == 'r' || ch == 'R') {
                    FitxaProblema aux = new FitxaProblema(TipusPeça.Torre, new ParInt(x / 8, x - (x / 8) * 8), charToColor(ch));
                    tauler.AfegirPeçaAt(coord, aux);
                    x++;
                }
                else if(ch == 'q' || ch == 'Q') {
                    FitxaProblema aux = new FitxaProblema(TipusPeça.Dama, new ParInt(x / 8, x - (x / 8) * 8), charToColor(ch));
                    tauler.AfegirPeçaAt(coord, aux);
                    x++;
                }

            }

            return tauler;

        }catch (Exception e){

            return  null;
        }

    }

    /**
     * \pre: el tauler del problema es valid
     * \post: retorna el FEN correponent al tauler del problema
     * @return
     */
    public String TaulerToFEN( ){

        StringBuilder FEN = new StringBuilder();
        int spaces = 0;
        for(int i = 0; i< 8; i++){
            for (int j = 0; j<8; j++){
                ParInt coord = new ParInt(i, j);
                if(tauler.FitxaAt(coord) != null){

                    if(spaces != 0){
                        FEN.append(spaces);
                        spaces = 0;
                    }
                    if(Convert.ClassToTipusPeça(tauler.FitxaAt(coord).getIFitxa().getClass().toString()) == TipusPeça.Alfil){
                        if(tauler.FitxaAt(coord).GetColor() == Color.negre){
                            FEN.append("b");
                        }
                        else FEN.append("B");


                    }
                    if(Convert.ClassToTipusPeça(tauler.FitxaAt(coord).getIFitxa().getClass().toString()) == TipusPeça.Torre){
                        if(tauler.FitxaAt(coord).GetColor() == Color.negre){
                            FEN.append("r");
                        }
                        else FEN.append("R");

                    }
                    if(Convert.ClassToTipusPeça(tauler.FitxaAt(coord).getIFitxa().getClass().toString()) == TipusPeça.Peo){
                        if(tauler.FitxaAt(coord).GetColor() == Color.negre){
                            FEN.append("p");
                        }
                        else FEN.append("P");


                    }
                    if(Convert.ClassToTipusPeça(tauler.FitxaAt(coord).getIFitxa().getClass().toString()) == TipusPeça.Dama){
                        if(tauler.FitxaAt(coord).GetColor() == Color.negre){
                            FEN.append("q");
                        }
                        else FEN.append("Q");


                    }
                    if(Convert.ClassToTipusPeça(tauler.FitxaAt(coord).getIFitxa().getClass().toString()) == TipusPeça.Rei){
                        if(tauler.FitxaAt(coord).GetColor() == Color.negre){
                            FEN.append("k");
                        }
                        else FEN.append("K");


                    }
                    if(Convert.ClassToTipusPeça(tauler.FitxaAt(coord).getIFitxa().getClass().toString()) == TipusPeça.Cavall){
                        if(tauler.FitxaAt(coord).GetColor() == Color.negre){
                            FEN.append("n");
                        }
                        else FEN.append("N");


                    }

                }
                else{
                    spaces++;
                }

            }
            if(spaces != 0){
                FEN.append(spaces);
                spaces = 0;
            }
            if(i!=7)
                FEN.append("/");
        }

        FEN.append(" w - - 0 1");
        //System.out.println(FEN);
        return FEN.toString();

    }
}