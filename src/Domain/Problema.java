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
    private Color torn;
    private boolean _guardat;
    private Tauler tauler;
    private boolean _valid;
    private Huma _creador;
    private HashMap<String, Integer> _ranking;
    private Vector<FitxaProblema> _fitxesProblema;
    private NumMaxPeces _pecesMax;
    private HashMap<ParTipusPeçaBool,Integer> _numTipusPeça;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[37m";
    public static final String ANSI_PURPLE = "\u001B[30m";
    public static final String ANSI_BLACK = "\u001B[30m";


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
        tauler =  new Tauler(FENtoTauler());
        _dif = d;
        _creador = h;
    }

    public Problema(String FEN, boolean valid, Color torn){
        _FEN = FEN;
        tauler = new Tauler(FENtoTauler());
        _valid = valid;
    }

    public Color GetTorn(){
        return torn;
    }

    public void dibuixaProblema(){

        String formatB = ANSI_BLACK +  "| " + ANSI_BLUE + "%c " + ANSI_RESET ;
        String formatW = ANSI_BLACK + "| " + ANSI_RED + "%c " + ANSI_RESET ;
        System.out.println();
        System.out.println(ANSI_BLACK + "  +---+---+---+---+---+---+---+---+" + ANSI_RESET);
        for(int i = 0; i < 8; i++){
            System.out.print(ANSI_BLACK);
            System.out.print(8-i);
            System.out.print(" " + ANSI_RESET);

            for (int j = 0; j<8; j++){
                if(tauler.FitxaAt(i,j) != null){
                    TipusPeça tP = tauler.FitxaAt(i,j).GetTipus();
                    Color c = tauler.FitxaAt(i,j).GetColor();

                    if(tP == TipusPeça.Cavall){

                        if(c == Color.negre)
                            System.out.printf(formatB,  'C' );
                        else System.out.printf(formatW, 'C');
                    }
                    if(tP == TipusPeça.Peo){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'P');
                        else System.out.printf(formatW, 'P');
                    }
                    if(tP == TipusPeça.Alfil){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'A');
                        else System.out.printf(formatW, 'A');

                    }

                    if(tP == TipusPeça.Torre){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'T');
                        else System.out.printf(formatW,'T');
                    }

                    if(tP == TipusPeça.Rei){

                        if(c == Color.negre)
                            System.out.printf(formatB, 'R');
                        else System.out.printf(formatW, 'R');

                    }

                    if(tP == TipusPeça.Dama){

                        if(c == Color.negre)
                            System.out.printf(formatB, 'D');
                        else System.out.printf(formatW, 'D');
                    }

                }
                else {
                    System.out.print("|   ");
                }
            }
            System.out.print(ANSI_BLACK + '|');
            System.out.println();
            System.out.println("  +---+---+---+---+---+---+---+---+" + ANSI_RESET);


        }

        System.out.println(ANSI_BLACK + "    A   B   C   D   E   F   G   H" + ANSI_RESET );

    }


    public void AfegirPeça(TipusPeça tp, Color c, ParInt desti ){



        if (desti.GetFirst() != -1 && desti.GetSecond() != -1) {
            if (tauler.FitxaAt(desti.GetFirst(),desti.GetSecond()) == null) {
                tauler.AfegirPeçaAt(desti.GetFirst(),desti.GetSecond(), new FitxaProblema(tp, desti.GetSecond(), desti.GetFirst(), c));

            } else {
                System.out.println(ANSI_RED + "La posició destí està ocupada" + ANSI_RESET);

            }
        } else{
            System.out.println(ANSI_RED + "Coordenada no valida" + ANSI_RESET);

        }


    }

    public void EliminarPeça(ParInt origen){


        if ( origen.GetFirst() != -1 && origen.GetSecond() != -1) {
            if (tauler.FitxaAt(origen.GetFirst(), origen.GetSecond()) != null) {

                tauler.AfegirPeçaAt(origen.GetFirst(), origen.GetSecond(), null);


            } else{
                System.out.println(ANSI_RED + "La posició d'origen està buida" + ANSI_RESET);

            }


        } else{
            System.out.println(ANSI_RED + "Coordenada no valida" + ANSI_RESET);

        }

    }
    public void MourePeça(ParInt origen, ParInt desti){

        if ( origen.GetFirst() != -1 && origen.GetSecond() != -1) {
            if (tauler.FitxaAt(origen.GetFirst(), origen.GetSecond()) != null) {

                tauler.AfegirPeçaAt(origen.GetFirst(), origen.GetSecond(), null);


            } else{
                System.out.println(ANSI_RED + "La posició d'origen està buida" + ANSI_RESET);

            }


        } else{
            System.out.println(ANSI_RED + "Coordenada no valida" + ANSI_RESET);

        }

        if (desti.GetFirst() != -1 && desti.GetSecond() != -1) {
            if (tauler.FitxaAt(desti.GetFirst(), desti.GetSecond()) == null) {
                tauler.AfegirPeçaAt(desti.GetFirst(), desti.GetSecond(),tauler.FitxaAt(origen.GetFirst(),origen.GetSecond()));
                tauler.AfegirPeçaAt(origen.GetFirst(), origen.GetSecond(), null);


            } else{
                System.out.println(ANSI_RED + "La posició destí està ocupada" + ANSI_RESET);

            }


        } else{
            System.out.println(ANSI_RED + "Coordenada no valida" + ANSI_RESET);

        }

    }




    public void setDificultat (Dificultat d){
        _dif = d;
    }

    public boolean GetValid(){
        return _valid;
    }


    public void validarProblema (){
        Algorisme aux = new Algorisme();
        _valid = aux.validarProblema(torn,tauler);
    }

    private Color charToColor(char ch){
        if(ch >= 65 && ch<= 90)
            return  Color.blanc;
        else return  Color.negre;
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
                    tauler[x / 8][x -(x / 8) * 8] = new FitxaProblema(TipusPeça.Cavall, new ParInt(x % 8, x - (x % 8) * 8), charToColor(ch));
                    x++;
                }
                else if(ch == 'b' || ch == 'B') {
                    tauler[x / 8][x - (x / 8) * 8] = new FitxaProblema(TipusPeça.Alfil, new ParInt(x % 8, x - (x % 8) * 8), charToColor(ch));
                    x++;
                }
                else if(ch == 'p' || ch == 'P') {
                    tauler[x / 8][x - (x / 8) * 8] = new FitxaProblema(TipusPeça.Peo, new ParInt(x % 8, x - (x % 8) * 8), charToColor(ch));
                    x++;
                }
                else if(ch == 'k' || ch == 'K') {
                    tauler[x / 8][x - (x / 8) * 8] = new FitxaProblema(TipusPeça.Rei, new ParInt(x % 8, x - (x % 8) * 8), charToColor(ch));
                    x++;
                }
                else if(ch == 'r' || ch == 'R') {

                    tauler[x / 8][x - (x / 8) * 8] = new FitxaProblema(TipusPeça.Torre, new ParInt(x % 8, x - (x % 8) * 8), charToColor(ch));
                    x++;
                }
                else if(ch == 'q' || ch == 'Q') {
                    tauler[x / 8][x - (x / 8) * 8] = new FitxaProblema(TipusPeça.Dama, new ParInt(x % 8, x - (x % 8) * 8), charToColor(ch));
                    x++;
                }


            }

            return tauler;

        }catch (Exception e){

            System.out.print("ERROOOOOR: " + e);
            return  null;
        }

    }

    public String TaulerToFEN( ){

        String FEN ="";
        int spaces = 0;
        for(int i = 0; i< 8; i++){
            for (int j = 0; j<8; j++){
                if(tauler.FitxaAt(i, j) != null){

                    if(spaces != 0){
                        FEN+=spaces;
                        spaces = 0;
                    }
                    if(tauler.FitxaAt(i, j).GetTipus() == TipusPeça.Alfil){
                        if(tauler.FitxaAt(i, j).GetColor() == Color.negre){
                            FEN+="b";
                        }
                        else FEN+="B";


                    }
                    if(tauler.FitxaAt(i, j).GetTipus() == TipusPeça.Torre){
                        if(tauler.FitxaAt(i, j).GetColor() == Color.negre){
                            FEN+="r";
                        }
                        else FEN+="R";

                    }
                    if(tauler.FitxaAt(i, j).GetTipus() == TipusPeça.Peo){
                        if(tauler.FitxaAt(i, j).GetColor() == Color.negre){
                            FEN+="p";
                        }
                        else FEN+="P";


                    }
                    if(tauler.FitxaAt(i, j).GetTipus() == TipusPeça.Dama){
                        if(tauler.FitxaAt(i, j).GetColor() == Color.negre){
                            FEN+="q";
                        }
                        else FEN+="Q";


                    }
                    if(tauler.FitxaAt(i, j).GetTipus() == TipusPeça.Rei){
                        if(tauler.FitxaAt(i, j).GetColor() == Color.negre){
                            FEN+="k";
                        }
                        else FEN+="K";


                    }
                    if(tauler.FitxaAt(i, j).GetTipus() == TipusPeça.Cavall){
                        if(tauler.FitxaAt(i, j).GetColor() == Color.negre){
                            FEN+="n";
                        }
                        else FEN+="N";


                    }

                }
                else{
                    spaces++;
                }

            }
            if(spaces != 0){
                FEN+=spaces;
                spaces = 0;
            }
            if(i!=7)
                FEN+="/";
        }

        FEN+=" w - - 0 1";
        //System.out.println(FEN);
        return  FEN;

    }



    /**
     * A partir del vector de FitxaProblema genera el codi FEN
     */


    private ParInt StringToCoordenada(String str){
        char primer = str.charAt(0);
        char segon = str.charAt(1);

        if((primer >= 'a' && primer <= 'h' ) || primer>='A' && primer<= 'H'){
            char aux = primer;
            primer = segon;
            segon = aux;

        }

        return new ParInt(CharToCoordenadaInt(primer), CharToCoordenadaInt(segon));


    }
    private int CharToCoordenadaInt( char ch ){

        int res = 0;

        if(ch >= 'a' && ch <= 'h'){
            res = ch - 'a' ;
        }

        else if(ch >= 'A' && ch <= 'H'){
            res = ch - 'A' ;
        }

        else if(ch > '0' && ch<= '8')
            res = 8 - Integer.parseInt(String.valueOf(ch));

        else return -1;

        return  res;
    }



}


