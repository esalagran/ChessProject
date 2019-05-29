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
    private HashMap<TipusPeça, Integer> _pecesMax;
    private HashMap<Character,Integer> _numTipusPeça;

    /**
     * \pre:
     * \post: Inicialitza un taullel buit
     */
    public Tauler (){
        _pecesMax = NumMaxPeces.getInstance();
        _numTipusPeça = new HashMap<>();
        FillDictionary();
        taulell = new FitxaProblema[8][8];
    }

    /**
     * \pre:
     * \post: taulell = t, s'inicialitzen les FitxaProblema dels dos reis
     */
    public Tauler (FitxaProblema[][] t){
        _pecesMax = NumMaxPeces.getInstance();
        _numTipusPeça = new HashMap<>();
        FillDictionary();
        taulell = t;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (t[i][j] != null){
                    if (ClassToTipusPeça(t[i][j].getIFitxa().getClass().toString())== TipusPeça.Rei && t[i][j].GetColor() == Color.blanc) whiteKing = t[i][j];
                    if (ClassToTipusPeça(t[i][j].getIFitxa().getClass().toString()) == TipusPeça.Rei && t[i][j].GetColor() == Color.negre) blackKing = t[i][j];
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
            TipusPeça tp = ClassToTipusPeça(f.getIFitxa().getClass().toString());
            char key = ParTipusPeçaBoolToChar(tp, f.GetColor());

            int value = _numTipusPeça.get(key);
            if (true){
                if (taulell[coord.GetFirst()][coord.GetSecond()] == null){
                    taulell[coord.GetFirst()][coord.GetSecond()] = f;
                    f.SetCoordenades(coord);
                    _numTipusPeça.put(key, value + 1);
                    if ( tp.equals(TipusPeça.Rei)){
                        if (f.GetColor().equals(Color.blanc)) setWhiteKing(f);
                        else setBlackKing(f);
                    }
                }
                else {
                    System.out.println("Ja hi ha una peça a l'origen");
                }
            }
            else {
                System.out.println("S'ha supertat el nombre màxim de peces aqui");
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
            char key = ParTipusPeçaBoolToChar(
                    ClassToTipusPeça(fp.getIFitxa().getClass().toString()), fp.GetColor());
            _numTipusPeça.put(key, _numTipusPeça.get(key) - 1);
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
        if (ClassToTipusPeça(taulell[fi.GetFirst()][fi.GetSecond()].getIFitxa().getClass().toString()) == TipusPeça.Rei
                && taulell[fi.GetFirst()][fi.GetSecond()].GetColor() == Color.blanc) whiteKing = fp;
        else if (ClassToTipusPeça(taulell[fi.GetFirst()][fi.GetSecond()].getIFitxa().getClass().toString()) == TipusPeça.Rei
                && taulell[fi.GetFirst()][fi.GetSecond()].GetColor() == Color.negre) blackKing = fp;
    }

    public boolean SuficientPecesPelMate(){
        int iBigPieceCount;
        int iWhiteBishop;
        int iBlackBishop;
        int iWhiteKnight;
        int iBlackKnight;
        iBigPieceCount = _numTipusPeça.get(ParTipusPeçaBoolToChar(TipusPeça.Torre, Color.blanc)) +
                _numTipusPeça.get(ParTipusPeçaBoolToChar(TipusPeça.Torre, Color.negre)) +
                _numTipusPeça.get(ParTipusPeçaBoolToChar(TipusPeça.Dama, Color.blanc)) +
                _numTipusPeça.get(ParTipusPeçaBoolToChar(TipusPeça.Torre, Color.negre));
        if (iBigPieceCount != 0)
            return true;
        iWhiteBishop = _numTipusPeça.get(ParTipusPeçaBoolToChar(TipusPeça.Alfil, Color.blanc));
        iBlackBishop = _numTipusPeça.get(ParTipusPeçaBoolToChar(TipusPeça.Alfil, Color.negre));
        iWhiteKnight = _numTipusPeça.get(ParTipusPeçaBoolToChar(TipusPeça.Cavall, Color.blanc));
        iBlackKnight = _numTipusPeça.get(ParTipusPeçaBoolToChar(TipusPeça.Cavall, Color.negre));
        return (iWhiteBishop + iWhiteKnight) >= 2 || (iBlackBishop + iBlackKnight) >= 2;
    }

    public int GetNumPeçaMax(TipusPeça tipusPeça){
            return _pecesMax.get(tipusPeça);
        }

    public int GetNumPeces(TipusPeça tp, Color c){
        return _numTipusPeça.get(ParTipusPeçaBoolToChar(tp, c));
    }

    public void SetNumPeces(TipusPeça tp, Color c,  int value){
        char key = ParTipusPeçaBoolToChar(tp, c);
        _numTipusPeça.put(key, value);
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
