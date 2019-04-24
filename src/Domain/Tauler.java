package Domain;

public class Tauler {
    private FitxaProblema[][] taulell;
    private FitxaProblema whiteKing;
    private FitxaProblema blackKing;

    public Tauler (){ taulell = new FitxaProblema[8][8]; }

    public Tauler (FitxaProblema[][] t){
        taulell = t;
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (t[i][j] != null){
                    if (t[i][j].tP == TipusPeça.Rei && t[i][j].GetColor() == Color.blanc) whiteKing = t[i][j];
                    if (t[i][j].tP == TipusPeça.Rei && t[i][j].GetColor() == Color.negre) blackKing = t[i][j];
                }
            }
        }
    }

    public FitxaProblema[][] getTaulell() {
        return taulell;
    }

    public FitxaProblema getBlackKing() {
        return blackKing;
    }

    public void setBlackKing(FitxaProblema king){blackKing = king;}

    public FitxaProblema getWhiteKing() {
        return whiteKing;
    }

    public void setWhiteKing(FitxaProblema king){whiteKing = king;}

    public void AfegirPeçaAt(int i, int j, FitxaProblema f){ taulell[i][j] = f; }

    public boolean PeçaMeva(ParInt x, Color color) {
        if (x.GetFirst() < 8 && x.GetFirst() >= 0 && x.GetSecond() < 8 && x.GetSecond() >=0 && taulell[x.GetFirst()][x.GetSecond()] != null) {
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.negre && color.equals(Color.negre)) return true;
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.blanc && color.equals(Color.blanc)) return true;
        }
        return false;
    }

    public boolean PeçaRival(ParInt x, Color color){
        if (x.GetFirst() < 8 && x.GetFirst() >= 0 && x.GetSecond() < 8 && x.GetSecond() >=0 && taulell[x.GetFirst()][x.GetSecond()] != null) {
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.negre && color.equals(Color.blanc)) return true;
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.blanc && color.equals(Color.negre)) return true;
        }
        return false;
    }

    public FitxaProblema FitxaAt(int i, int j){
        return taulell[i][j];
    }

    public void desferJugada(ParInt ini, ParInt move, FitxaProblema peça){
        taulell[move.GetFirst()][move.GetSecond()] = taulell[ini.GetFirst()][ini.GetSecond()];
        taulell[ini.GetFirst()][ini.GetSecond()] = peça;

        FitxaProblema fp = taulell[move.GetFirst()][move.GetSecond()];
        fp.SetCoordenades(move);
        if (taulell[move.GetFirst()][move.GetSecond()].GetTipus() == TipusPeça.Rei && taulell[move.GetFirst()][move.GetSecond()].GetColor() == Color.blanc) whiteKing = fp;
        if (taulell[move.GetFirst()][move.GetSecond()].GetTipus() == TipusPeça.Rei && taulell[move.GetFirst()][move.GetSecond()].GetColor() == Color.negre) blackKing = fp;
    }

    public void moureFitxa(ParInt ini, ParInt move){
            taulell[move.GetFirst()][move.GetSecond()] = taulell[ini.GetFirst()][ini.GetSecond()];
            taulell[ini.GetFirst()][ini.GetSecond()] = null;

            FitxaProblema fp = taulell[move.GetFirst()][move.GetSecond()];
            fp.SetCoordenades(move);
            if (taulell[move.GetFirst()][move.GetSecond()].GetTipus() == TipusPeça.Rei && taulell[move.GetFirst()][move.GetSecond()].GetColor() == Color.blanc) whiteKing = fp;
            if (taulell[move.GetFirst()][move.GetSecond()].GetTipus() == TipusPeça.Rei && taulell[move.GetFirst()][move.GetSecond()].GetColor() == Color.negre) blackKing = fp;
    }
}
