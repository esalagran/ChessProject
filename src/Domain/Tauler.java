package Domain;

import javax.sql.rowset.RowSetWarning;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import static Domain.Convert.*;
import static Domain.Convert.InTheLimits;

public class Tauler {
    private FitxaProblema[][] taulell;
    private FitxaProblema whiteKing;
    private FitxaProblema blackKing;
    private  Convert Convert = new Convert();
    private HashMap<Character, Integer> _pecesMax;
    private HashMap<Character,Integer> _numTipusFitxa;

    /**
     * \pre:
     * \post: Inicialitza un taullel buit
     */
    public Tauler (){
        _pecesMax = NumMaxPeces.getInstance();
        _numTipusFitxa = new HashMap<>();
        FillDictionary();
        taulell = new FitxaProblema[8][8];
    }

    /**
     * \pre:
     * \post: taulell = t, s'inicialitzen les FitxaProblema dels dos reis
     */
    public Tauler (FitxaProblema[][] t){
        _pecesMax = NumMaxPeces.getInstance();
        _numTipusFitxa = new HashMap<>();
        FillDictionary();
        taulell = t;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (t[i][j] != null){
                    FitxaProblema fp = t[i][j];
                    if (fp.getIFitxa() instanceof Rei)
                        if (fp.GetColor().equals(Color.blanc))
                            whiteKing = t[i][j];
                        else
                            blackKing = t[i][j];
                }
            }
        }
    }

    /**
     * \pre:
     * \post: Retorna l'atribut taulell
     * @return Retorna l'atribut taulell
     */
    public FitxaProblema[][] getTaulell() {
        return taulell;
    }

    /**
     * \pre: Tauler no buit
     * \post: Retorna l'atribut que representa el Rei negre
     * @return Retorna blackKing
     */
    public FitxaProblema getBlackKing() { return blackKing;}

    /**
     * \pre: king conte la FitxaProblema del rei negre
     * \post: S'ha modificat l'atribut blackKing
     */
    public void setBlackKing(FitxaProblema king){blackKing = king;}

    /**
     * \pre: Tauler no buit
     * \post: Retorna l'atribut que representa el Rei blanc
     * @return Retorna whiteKing
     */
    public FitxaProblema getWhiteKing() { return whiteKing; }

    /**
     * \pre: king conte la FitxaProblema del rei blanc
     * \post: S'ha modificat l'atribut whiteKing
     */
    public void setWhiteKing(FitxaProblema king){whiteKing = king;}

    /**
     * \pre: Les coordenades (i,j) representen una coordenada valida
     * \post: FitxaProblema es troba a la coordenada(i,j)
     */
    public void AfegirPeçaAt(ParInt coord, FitxaProblema f){
        if(f!= null){
            try {
                char key = ParTipusPeçaBoolToChar(f.getIFitxa(), f.GetColor());

                int value = _numTipusFitxa.get(key);
                if (value < _pecesMax.get(Character.toLowerCase(key))){
                    if (taulell[coord.GetFirst()][coord.GetSecond()] == null) {
                        taulell[coord.GetFirst()][coord.GetSecond()] = f;
                        f.SetCoordenades(coord);
                        _numTipusFitxa.put(key, value + 1);
                        if (f.getIFitxa() instanceof Rei) {
                            if (f.GetColor().equals(Color.blanc)) setWhiteKing(f);
                            else setBlackKing(f);
                        }
                    } else {
                        System.out.println("Ja hi ha una peça a l'origen");
                    }
                }
                else {
                    System.out.println("S'ha superat el nombre màxim de peces");
                }
            }
        catch (Exception ex){
                ex.printStackTrace();
        }
    }else{
            taulell[coord.GetFirst()][coord.GetSecond()] = null;
        }

    }

    public void EliminarPeçaAt(ParInt origin){
        try{
            FitxaProblema fp = taulell[origin.GetFirst()][origin.GetSecond()];
            char key = ParTipusPeçaBoolToChar(fp.getIFitxa(), fp.GetColor());
            _numTipusFitxa.put(key, _numTipusFitxa.get(key) - 1);
            taulell[origin.GetFirst()][origin.GetSecond()] = null;
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * \pre: x representa una coordenada
     * \post: Retorna true si el color de la peça de la coordenada x es igual al Color color
     * @return Retorna true si el color de la peça de la coordenada x es igual al Color color
     */
    public boolean PeçaMeva(ParInt x, Color color) {
        if (InTheLimits(x))
            if (taulell[x.GetFirst()][x.GetSecond()] != null)
                return taulell[x.GetFirst()][x.GetSecond()].GetColor().equals(color);

        return false;
    }

    /**
     * \pre: x representa una coordenada
     * \post: Retorna true si el color de la peça de la coordenada x es diferent al Color color
     * @return Retorna true si el color de la peça de la coordenada x es diferent al Color color
     */
    public boolean PeçaRival(ParInt x, Color color){
        if (InTheLimits(x) && taulell[x.GetFirst()][x.GetSecond()] != null) {
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.negre && color.equals(Color.blanc)) return true;
            return taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.blanc && color.equals(Color.negre);
        }
        return false;
    }

    /**
     * \pre: Coordenada (i,j) es una coordenada valida
     * \post: Retorna la FitxaProblema de la coordenada (i,j)
     * @return Retorna la FitxaProblema de la coordenada (i,j)
     */
    public FitxaProblema FitxaAt(ParInt coord){
        try{
            return taulell[coord.GetFirst()][coord.GetSecond()];
        }
        catch (Exception ex){
            if (ex.getClass().equals(IndexOutOfBoundsException.class))
                System.out.println("Coordenades fora dels límits");
            else
                System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * \pre: Coordenades ini i move representen posicions valides
     * \post: Coordenada move conte la FitxaProblema de la coordenada ini, que ara conte peça
     */
    public void desferJugada(Move move){
        Move aux = new Move(null, move.getEndPos(), move.getStartPos());
        moureFitxa(aux);
        taulell[move.getEndPos().GetFirst()][move.getEndPos().GetSecond()] = move.getOriginalPiece();
    }

    /**
     * \pre: Coordenades ini i move representen posicions valides
     * \post: Coordenada move conte la FitxaProblema de la coordenada ini, ini passa a ser null
     */
    public void moureFitxa(Move move){
        ParInt ini = move.getStartPos();
        ParInt fi = move.getEndPos();
        move.setOriginalPiece(taulell[fi.GetFirst()][fi.GetSecond()]);

        taulell[fi.GetFirst()][fi.GetSecond()] = taulell[ini.GetFirst()][ini.GetSecond()];
        taulell[ini.GetFirst()][ini.GetSecond()] = null;

        FitxaProblema fp = taulell[fi.GetFirst()][fi.GetSecond()];
        fp.SetCoordenades(fi);
        Fitxa f = fp.getIFitxa();
        if (f instanceof Rei && fp.GetColor().equals(Color.blanc))
            whiteKing = fp;
        else if (f instanceof Rei && fp.GetColor().equals(Color.negre))
            blackKing = fp;
    }

    public int GetNumPeçaMax(TipusPeça tipusPeça){
            return _pecesMax.get(tipusPeça);
        }

    public int GetNumPeces(Fitxa fitxa, Color c){
        return _numTipusFitxa.get(ParTipusPeçaBoolToChar(fitxa, c));
    }

    public void SetNumPeces(Fitxa fitxa, Color c,  int value){
        char key = ParTipusPeçaBoolToChar(fitxa, c);
        _numTipusFitxa.put(key, value);
    }

    public int EstimaPuntuacio(Color jugador){
        int puntuacio = 0;
        for (int i = 0; i < 8; ++ i){
            for (int j = 0; j < 8; ++j){
                if (taulell[i][j] != null){
                    if (taulell[i][j].GetColor().equals(jugador)){
                        puntuacio += taulell[i][j].getIFitxa().GetPes();
                    }
                    else{
                        puntuacio -= taulell[i][j].getIFitxa().GetPes();
                    }
                }
            }
        }
        return puntuacio;
    }


    private void FillDictionary(){
        _numTipusFitxa.put('P', 0);
        _numTipusFitxa.put('T', 0);
        _numTipusFitxa.put('C', 0);
        _numTipusFitxa.put('A', 0);
        _numTipusFitxa.put('D', 0);
        _numTipusFitxa.put('R', 0);

        _numTipusFitxa.put('p', 0);
        _numTipusFitxa.put('t', 0);
        _numTipusFitxa.put('c', 0);
        _numTipusFitxa.put('a', 0);
        _numTipusFitxa.put('d', 0);
        _numTipusFitxa.put('r', 0);
    }

    public ArrayList<Move> GetMoviments(Color jugador) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j){
                if (taulell[i][j] != null && taulell[i][j].GetColor().equals(jugador)) {
                    Vector<ParInt> movements = taulell[i][j].GetMoviments(this);
                    ParInt origen = new ParInt(i, j);
                    for (ParInt desti : movements) {
                        Move v = new Move(FitxaAt(desti), origen, desti);
                        moves.add(v);
                    }
                }
            }
        }
        return moves;
    }


    boolean IsChecked(Color jugador){
        if (jugador.equals(Color.negre))
            return getBlackKing().isAttacked(taulell);
        return getWhiteKing().isAttacked(taulell);
    }

    boolean IsMate(Color jugador){
        if (!IsChecked(jugador)) return false;
        return someMove(jugador);
    }

    boolean IsTaules(Color jugador) {
        if (IsChecked(jugador)) return false;
        return someMove(jugador);
    }

    private boolean someMove(Color jugador){
        ArrayList<Move> moveList = GetMoviments(jugador);
        for (Move m : moveList) {
            moureFitxa(m);
            boolean NotMove = IsChecked(jugador);
            desferJugada(m);
            if (!NotMove) return false;
        }
        return true;
    }
}
