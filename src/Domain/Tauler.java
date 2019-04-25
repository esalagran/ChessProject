package Domain;

import javax.sql.rowset.RowSetWarning;

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
                    if (Convert.ClassToTipusPeça(t[i][j].getIFitxa().getClass().toString())== TipusPeça.Rei && t[i][j].GetColor() == Color.blanc) whiteKing = t[i][j];
                    if (Convert.ClassToTipusPeça(t[i][j].getIFitxa().getClass().toString()) == TipusPeça.Rei && t[i][j].GetColor() == Color.negre) blackKing = t[i][j];
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

    public void AfegirPeçaAt(ParInt coord, FitxaProblema f){
        if (Convert.InTheLimits(coord)) {
            taulell[coord.GetFirst()][coord.GetSecond()] = f;
            if (Convert.ClassToTipusPeça(f.getIFitxa().getClass().toString()) == TipusPeça.Rei){
                if (f.GetColor().equals(Color.blanc)) setWhiteKing(f);
                else setBlackKing(f);
            }
        }
        else{
            System.out.println("Coordenades fora dels límits");
        }
    }

    public boolean PeçaMeva(ParInt x, Color color) {
        if (Convert.InTheLimits(x) && taulell[x.GetFirst()][x.GetSecond()] != null) {
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.negre && color.equals(Color.negre)) return true;
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.blanc && color.equals(Color.blanc)) return true;
        }
        return false;
    }

    public boolean PeçaRival(ParInt x, Color color){
        if (Convert.InTheLimits(x) && taulell[x.GetFirst()][x.GetSecond()] != null) {
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.negre && color.equals(Color.blanc)) return true;
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.blanc && color.equals(Color.negre)) return true;
        }
        return false;
    }

    public FitxaProblema FitxaAt(ParInt coord){
        if (Convert.InTheLimits(coord))
            return taulell[coord.GetFirst()][coord.GetSecond()];
        System.out.println("Coordenades fora dels límits");
        return null;
    }

    public void desferJugada(ParInt ini, ParInt move, FitxaProblema peça){
        if (Convert.InTheLimits(ini) && taulell[ini.GetFirst()][ini.GetSecond()] != null) {
            taulell[move.GetFirst()][move.GetSecond()] = taulell[ini.GetFirst()][ini.GetSecond()];
            taulell[ini.GetFirst()][ini.GetSecond()] = peça;
            FitxaProblema fp = taulell[move.GetFirst()][move.GetSecond()];
            fp.SetCoordenades(move);
            if (Convert.ClassToTipusPeça(taulell[move.GetFirst()][move.GetSecond()].getIFitxa().getClass().toString()) == TipusPeça.Rei && taulell[move.GetFirst()][move.GetSecond()].GetColor() == Color.blanc) whiteKing = fp;
            if (Convert.ClassToTipusPeça(taulell[move.GetFirst()][move.GetSecond()].getIFitxa().getClass().toString()) == TipusPeça.Rei && taulell[move.GetFirst()][move.GetSecond()].GetColor() == Color.negre) blackKing = fp;
        }
    }

    public void moureFitxa(ParInt ini, ParInt move){
        if (Convert.InTheLimits(ini) && taulell[ini.GetFirst()][ini.GetSecond()] != null){
            taulell[move.GetFirst()][move.GetSecond()] = taulell[ini.GetFirst()][ini.GetSecond()];
            taulell[ini.GetFirst()][ini.GetSecond()] = null;
            FitxaProblema fp = taulell[move.GetFirst()][move.GetSecond()];
            fp.SetCoordenades(move);
            if (Convert.ClassToTipusPeça(taulell[move.GetFirst()][move.GetSecond()].getIFitxa().getClass().toString()) == TipusPeça.Rei && taulell[move.GetFirst()][move.GetSecond()].GetColor() == Color.blanc) whiteKing = fp;
            if (Convert.ClassToTipusPeça(taulell[move.GetFirst()][move.GetSecond()].getIFitxa().getClass().toString()) == TipusPeça.Rei && taulell[move.GetFirst()][move.GetSecond()].GetColor() == Color.negre) blackKing = fp;
        }
    }
}
