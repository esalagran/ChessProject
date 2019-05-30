/**
 * @file Problema.java
 * @brief
 *
 * @autor Oriol
 */

package Domain;

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
    private List<Object[]> ranking;
    private Tema tema;
    private String _creador;


    public Problema(String FEN, String creador){
        _FEN = FEN;
        if (!_FEN.isEmpty()) {
            if (_FEN.contains(new StringBuilder(1).append('w'))) this.torn = Color.blanc;
            else this.torn = Color.negre;
        }
        tauler = FENtoTauler();
        nPeces = getNPeces().GetFirst()+getNPeces().GetSecond();
        _valid = false;
        _creador = creador;
        ranking =  new ArrayList<>();
        _dif = Dificultat.mitja;
    }

    public Problema(String FEN, Tema tema, boolean valid, String creador, Dificultat dificultat, List<Object[]> ranking){
        _FEN = FEN;
        if (!_FEN.isEmpty()) {
            if (_FEN.contains(new StringBuilder(1).append('w'))) this.torn = Color.blanc;
            else this.torn = Color.negre;
        }
        tauler = FENtoTauler();
        nPeces = getNPeces().GetFirst()+getNPeces().GetSecond();
        _guardat = true;
        _valid = valid;
        this.tema = tema;
        this.ranking = ranking;
        _creador = creador;
        _dif = dificultat;
    }

    public String GetCreador(){return _creador;}

    public void SetCreador(String creador) {_creador = creador;}

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

    public Dificultat getDificultat(){return _dif;}

    /**
     * \pre: tp, c i desti son parametres valids
     * \post: afegida peça tipus tp de color c a la posició desti
     * @return
     */
    public FitxaProblema[][] AfegirPeça(TipusPeça tp, Color c, ParInt desti ){
        try {
            tauler.AfegirPeçaAt(desti, new FitxaProblema(tp, desti, c));
            nPeces +=1;
            _valid = false;
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
            _valid = false;
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


            }


        } else{

        }
        if (desti.GetFirst() != -1 && desti.GetSecond() != -1) {
            if (tauler.FitxaAt(desti) == null) {
                FitxaProblema fp = tauler.FitxaAt(origen);
                tauler.EliminarPeçaAt(origen);
                tauler.AfegirPeçaAt(desti,fp);
                _valid = false;
                return tauler.getTaulell();


            } else{

            }
        } else{

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
    public boolean validarProblema (int numJugades, Color jugador){
        int profunditat = numJugades * 2 - 1;
        Algorithm aux = new AlgorismeAlfaBeta(profunditat);
        _valid = aux.validateProblem(tauler, jugador, profunditat);
        if(_valid){
            movimentsPerGuanyar = aux.getDepth();
            setDificultat(profunditat);
            torn = jugador;
        }
        return _valid;
    }

    public void setDificultat(int moviments){
        int puntuacioTauler = tauler.EstimaPuntuacio(torn);

        switch (moviments){
            case 1:
                if (puntuacioTauler < -4) _dif = Dificultat.facil;
                else _dif = Dificultat.moltfacil;
                break;
            case 3:
                if (puntuacioTauler > 4) _dif = Dificultat.moltfacil;
                else if (puntuacioTauler < -4) _dif = Dificultat.mitja;
                else _dif = Dificultat.facil;
                break;
            case 5:
                if (puntuacioTauler > 4) _dif = Dificultat.facil;
                else if (puntuacioTauler < -4) _dif = Dificultat.dificil;
                else _dif = Dificultat.mitja;
                break;
            case 7:
                if (puntuacioTauler > 4) _dif = Dificultat.mitja;
                else if (puntuacioTauler < -4) _dif = Dificultat.moltdificil;
                else _dif = Dificultat.dificil;
                break;
            case 9:
                if (puntuacioTauler < -4) _dif = Dificultat.dificil;
                else _dif = Dificultat.moltdificil;
                break;
        }
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
     * @return Puntacio del jugador en el problema segons els parametres passats
     */
    public int calculPuntuacio( int nmoviments, int accumTime){
        int maxPuntuacio = nPeces * movimentsPerGuanyar * 10;
        int tempsMig = accumTime/nmoviments;
        int boost;
        if (tempsMig > 60) boost = 1;
        else boost = 60 - tempsMig;
        return maxPuntuacio*boost;
    }

    /**
     * Si el jugador ja existeix en el ranking actualiza la seva puntacio,
     * sino rep la informacio necessaria per calcular la puntuacio
     * @param nomJugador Nom del jugador a inscriure al ranking
     * @param puntuacio Puntuacio del jugador
     */
    public void inscriureRanking (String nomJugador, int puntuacio){
        Object[] newPlayer = new Object[] {
            nomJugador,
            puntuacio
        };
        if (ranking == null){
            ranking = new ArrayList<>();
            ranking.add(newPlayer);
        }
        else {
            boolean insert = false;
            for (int i = 0; i < ranking.size(); i++) {
                Object[] player = ranking.get(i);
                if ((int) player[1] < puntuacio && !insert){
                    ranking.add(i, newPlayer);
                    insert  = true;
                }
            }
            if (!insert) ranking.add(newPlayer);
        }
    }

    public List<Object[]> getRanking(){return ranking;}

    public void setRanking(List<Object[]> r){ranking = r;}

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
     * @return el FEN corresponent al tauler del problema
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
                    Fitxa f = tauler.FitxaAt(coord).getIFitxa();
                    if(f instanceof Alfil){
                        if(tauler.FitxaAt(coord).GetColor() == Color.negre){
                            FEN.append("b");
                        }
                        else FEN.append("B");


                    }
                    else if(f instanceof Torre){
                        if(tauler.FitxaAt(coord).GetColor() == Color.negre){
                            FEN.append("r");
                        }
                        else FEN.append("R");

                    }
                    else if(f instanceof Peo){
                        if(tauler.FitxaAt(coord).GetColor() == Color.negre){
                            FEN.append("p");
                        }
                        else FEN.append("P");


                    }
                    else if(f instanceof Dama){
                        if(tauler.FitxaAt(coord).GetColor() == Color.negre){
                            FEN.append("q");
                        }
                        else FEN.append("Q");


                    }
                    else if(f instanceof Rei){
                        if(tauler.FitxaAt(coord).GetColor() == Color.negre){
                            FEN.append("k");
                        }
                        else FEN.append("K");


                    }
                    else if(f instanceof Cavall){
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