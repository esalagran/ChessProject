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
    private Color torn = Color.blanc;
    private boolean _guardat;
    private Tauler tauler;
    private boolean _valid;
    private int movimentsPerGuanyar = 10;
    private Huma _creador;
    private HashMap<String, Integer> _ranking;
    //private Vector<FitxaProblema> _fitxesProblema;
    private HashMap<TipusPeça, Integer> _pecesMax;
    private HashMap<Character,Integer> _numTipusPeça;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[37m";
    public static final String ANSI_PURPLE = "\u001B[30m";
    public static final String ANSI_BLACK = "\u001B[30m";


    private void FillDictionary(){
        _numTipusPeça.put('P', 0);
        _numTipusPeça.put('T', 0);
        _numTipusPeça.put('C', 0);
        _numTipusPeça.put('A', 0);
        _numTipusPeça.put('D', 0);
        _numTipusPeça.put('R', 0);

        _numTipusPeça.put('p', 0);
        _numTipusPeça.put('t', 0);
        _numTipusPeça.put('c', 0);
        _numTipusPeça.put('a', 0);
        _numTipusPeça.put('d', 0);
        _numTipusPeça.put('r', 0);


    }

    public Problema(String FEN){
        _FEN = FEN;
        _pecesMax = NumMaxPeces.getInstance();
        _numTipusPeça = new HashMap<>();
        FillDictionary();
        if (!_FEN.isEmpty()) {
            if (_FEN.contains(new StringBuilder(1).append('w'))) this.torn = Color.blanc;
            else this.torn = Color.negre;
        }
        tauler = FENtoTauler();
        _valid = false;
    }

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
                ParInt coord = new ParInt(i,j);
                if(tauler.FitxaAt(coord) != null){
                    TipusPeça tP = Convert.ClassToTipusPeça(tauler.FitxaAt(coord).getIFitxa().getClass().toString());
                    Color c = tauler.FitxaAt(coord).GetColor();

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

        char key = ParTipusPeçaBoolToChar(tp, c);
        Integer number = _numTipusPeça.get(key);
        if ( number < _pecesMax.get(tp)) {
            if (desti.GetFirst() != -1 && desti.GetSecond() != -1) {
                if (tauler.FitxaAt(desti) == null) {
                    tauler.AfegirPeçaAt(desti, new FitxaProblema(tp, desti, c));

                } else {
                    System.out.println(ANSI_RED + "La posició destí està ocupada" + ANSI_RESET);

                }
                _numTipusPeça.put(key, number + 1);
            } else {
                System.out.println(ANSI_RED + "Coordenada no valida" + ANSI_RESET);

            }
        }
        else{
            System.out.println(ANSI_RED + "S'ha superat el nombre màxim de la peça " + tp.toString()
                    + " d'aquest color: " + c.toString() + ANSI_RESET);
        }


    }

    public void EliminarPeça(ParInt origen){


        if ( origen.GetFirst() != -1 && origen.GetSecond() != -1) {
            if (tauler.FitxaAt(origen) != null) {
                FitxaProblema aux = tauler.FitxaAt(origen);
                char key = ParTipusPeçaBoolToChar(Convert.ClassToTipusPeça(aux.getIFitxa().getClass().toString()), aux.GetColor());
                tauler.AfegirPeçaAt(origen, null);
                _numTipusPeça.put(key, _numTipusPeça.get(key) - 1);

            } else{
                System.out.println(ANSI_RED + "La posició d'origen està buida" + ANSI_RESET);

            }


        } else{
            System.out.println(ANSI_RED + "Coordenada no valida" + ANSI_RESET);

        }

    }
    public void MourePeça(ParInt origen, ParInt desti){

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
        movimentsPerGuanyar = aux.getDepth();
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
    public Tauler FENtoTauler(){
        try {

            int x = 0;
           Tauler tauler = new  Tauler();

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
                    char key = ParTipusPeçaBoolToChar(Convert.ClassToTipusPeça(aux.getIFitxa().getClass().toString()), aux.GetColor());
                    _numTipusPeça.put(key, _numTipusPeça.get(key) + 1);
                    x++;
                }
                else if(ch == 'b' || ch == 'B') {
                    FitxaProblema aux = new FitxaProblema(TipusPeça.Alfil, new ParInt(x / 8, x - (x / 8) * 8), charToColor(ch));
                    tauler.AfegirPeçaAt(coord, aux);
                    char key = ParTipusPeçaBoolToChar(Convert.ClassToTipusPeça(aux.getIFitxa().getClass().toString()), aux.GetColor());
                    _numTipusPeça.put(key, _numTipusPeça.get(key) + 1);             x++;
                }
                else if(ch == 'p' || ch == 'P') {
                    FitxaProblema aux = new FitxaProblema(TipusPeça.Peo, new ParInt(x / 8, x - (x / 8) * 8), charToColor(ch));
                    tauler.AfegirPeçaAt(coord, aux);;
                    char key = ParTipusPeçaBoolToChar(Convert.ClassToTipusPeça(aux.getIFitxa().getClass().toString()), aux.GetColor());
                    _numTipusPeça.put(key, _numTipusPeça.get(key) + 1);
                    x++;
                }
                else if(ch == 'k' || ch == 'K') {
                    FitxaProblema aux = new FitxaProblema(TipusPeça.Rei, new ParInt(x / 8, x - (x / 8) * 8), charToColor(ch));
                    tauler.AfegirPeçaAt(coord, aux);
                    if (aux.GetColor().equals(Color.blanc)) tauler.setWhiteKing(aux);
                    else tauler.setBlackKing(aux);
                    char key = ParTipusPeçaBoolToChar(Convert.ClassToTipusPeça(aux.getIFitxa().getClass().toString()), aux.GetColor());
                    _numTipusPeça.put(key, _numTipusPeça.get(key) + 1);
                    x++;
                }
                else if(ch == 'r' || ch == 'R') {
                    FitxaProblema aux = new FitxaProblema(TipusPeça.Torre, new ParInt(x / 8, x - (x / 8) * 8), charToColor(ch));
                    tauler.AfegirPeçaAt(coord, aux);
                    char key = ParTipusPeçaBoolToChar(Convert.ClassToTipusPeça(aux.getIFitxa().getClass().toString()), aux.GetColor());
                    _numTipusPeça.put(key, _numTipusPeça.get(key) + 1);
                    x++;
                }
                else if(ch == 'q' || ch == 'Q') {
                    FitxaProblema aux = new FitxaProblema(TipusPeça.Dama, new ParInt(x / 8, x - (x / 8) * 8), charToColor(ch));
                    tauler.AfegirPeçaAt(coord, aux);
                    char key = ParTipusPeçaBoolToChar(Convert.ClassToTipusPeça(aux.getIFitxa().getClass().toString()), aux.GetColor());
                    _numTipusPeça.put(key, _numTipusPeça.get(key) + 1);
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


    /**
     * A partir del tauler genera el codi FEN
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

        if(ch >= 'a' && ch <= 'h') res = ch - 'a';

        else if(ch >= 'A' && ch <= 'H'){
            res = ch - 'A' ;
        }

        else if(ch > '0' && ch<= '8')
            res = 8 - Integer.parseInt(String.valueOf(ch));

        else return -1;

        return  res;
    }

    private char ParTipusPeçaBoolToChar(TipusPeça tipusPeça, Color color){
        char result = '0';
        switch (tipusPeça){
            case Peo:
                if (color.equals(Color.blanc)) result = 'P';
                else result = 'p';
                break;
            case Cavall:
                if (color.equals(Color.blanc)) result =  'C';
                else result =  'c';
                break;
            case Rei:
                if (color.equals(Color.blanc)) result =  'R';
                else result =  'r';
                break;
            case Dama:
                if (color.equals(Color.blanc)) result = 'D';
                else result = 'd';
                break;
            case Alfil:
                if (color.equals(Color.blanc)) result =  'A';
                else result = 'a';
                break;
            case Torre:
                if (color.equals(Color.blanc)) result = 'T';
                else result = 't';
                break;
        }
        return result;
    }



}


