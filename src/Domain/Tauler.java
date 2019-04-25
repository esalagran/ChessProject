package Domain;

import javax.sql.rowset.RowSetWarning;

public class Tauler {
    private FitxaProblema[][] taulell;
    private FitxaProblema whiteKing;
    private FitxaProblema blackKing;

    /**
     * \pre:
     * \post: Inicialitza un taullel buit
     */
    public Tauler (){ taulell = new FitxaProblema[8][8]; }

    /**
     * \pre:
     * \post: taulell = t, s'inicialitzen les FitxaProblema dels dos reis
     */
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

    /**
     * \pre: x representa una coordenada
     * \post: Retorna true si el color de la peça de la coordenada x es igual al Color color
     * @return Retorna true si el color de la peça de la coordenada x es igual al Color color
     */
    public boolean PeçaMeva(ParInt x, Color color) {
        if (Convert.InTheLimits(x) && taulell[x.GetFirst()][x.GetSecond()] != null) {
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.negre && color.equals(Color.negre)) return true;
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.blanc && color.equals(Color.blanc)) return true;
        }
        return false;
    }

    /**
     * \pre: x representa una coordenada
     * \post: Retorna true si el color de la peça de la coordenada x es diferent al Color color
     * @return Retorna true si el color de la peça de la coordenada x es diferent al Color color
     */
    public boolean PeçaRival(ParInt x, Color color){
        if (Convert.InTheLimits(x) && taulell[x.GetFirst()][x.GetSecond()] != null) {
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.negre && color.equals(Color.blanc)) return true;
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.blanc && color.equals(Color.negre)) return true;
        }
        return false;
    }

    /**
     * \pre: Coordenada (i,j) es una coordenada valida
     * \post: Retorna la FitxaProblema de la coordenada (i,j)
     * @return Retorna la FitxaProblema de la coordenada (i,j)
     */
    public FitxaProblema FitxaAt(ParInt coord){
        if (Convert.InTheLimits(coord))
            return taulell[coord.GetFirst()][coord.GetSecond()];
        System.out.println("Coordenades fora dels límits");
        return null;
    }

    /**
     * \pre: Coordenades ini i move representen posicions valides
     * \post: Coordenada move conte la FitxaProblema de la coordenada ini, que ara conte peça
     */
    public void desferJugada(ParInt ini, ParInt move, FitxaProblema peça){
        taulell[move.GetFirst()][move.GetSecond()] = taulell[ini.GetFirst()][ini.GetSecond()];
        taulell[ini.GetFirst()][ini.GetSecond()] = peça;

        FitxaProblema fp = taulell[move.GetFirst()][move.GetSecond()];
        fp.SetCoordenades(move);
        if (Convert.ClassToTipusPeça(taulell[move.GetFirst()][move.GetSecond()].getIFitxa().getClass().toString()) == TipusPeça.Rei && taulell[move.GetFirst()][move.GetSecond()].GetColor() == Color.blanc) whiteKing = fp;
        if (Convert.ClassToTipusPeça(taulell[move.GetFirst()][move.GetSecond()].getIFitxa().getClass().toString()) == TipusPeça.Rei && taulell[move.GetFirst()][move.GetSecond()].GetColor() == Color.negre) blackKing = fp;
    }

    /**
     * \pre: Coordenades ini i move representen posicions valides
     * \post: Coordenada move conte la FitxaProblema de la coordenada ini, ini passa a ser null
     */
    public void moureFitxa(ParInt ini, ParInt move){
            taulell[move.GetFirst()][move.GetSecond()] = taulell[ini.GetFirst()][ini.GetSecond()];
            taulell[ini.GetFirst()][ini.GetSecond()] = null;

            FitxaProblema fp = taulell[move.GetFirst()][move.GetSecond()];
            fp.SetCoordenades(move);
            if (Convert.ClassToTipusPeça(taulell[move.GetFirst()][move.GetSecond()].getIFitxa().getClass().toString()) == TipusPeça.Rei && taulell[move.GetFirst()][move.GetSecond()].GetColor() == Color.blanc) whiteKing = fp;
            if (Convert.ClassToTipusPeça(taulell[move.GetFirst()][move.GetSecond()].getIFitxa().getClass().toString()) == TipusPeça.Rei && taulell[move.GetFirst()][move.GetSecond()].GetColor() == Color.negre) blackKing = fp;
    }
}
