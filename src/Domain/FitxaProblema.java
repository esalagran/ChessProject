package Domain;


import java.util.Vector;

/**
 * Fitxa problema
 * Classe que implementa els elements que té un problema
 * */

public class FitxaProblema{

    private Fitxa fitxa;
    private Color c;
    private ParInt coord;

    public ParInt GetCoordenades(){
        return coord;
    }

    public Color GetColor(){
        return c;
    }

    public void SetCoordenades(ParInt dest){
        coord = dest;
    }

    public Fitxa getIFitxa () { return fitxa;}


    public FitxaProblema(TipusPeça nom, ParInt posIni, Color color){
        CreaFitxa(nom);
        coord = posIni;
        c = color;
    }

    public FitxaProblema(TipusPeça nom, int Fila, int Col, Color color){
        CreaFitxa(nom);
        coord = new ParInt(Fila, Col);
        c = color;
    }

    /**
     * \pre: tauler conté la informació de la peça en la situació
     * \post: tots els possibles moviments de la peça en el tauler*/
    public Vector<ParInt> GetMoviments(Tauler tauler){
        return fitxa.GetMoviments(coord, tauler, c);
    }

    private void CreaFitxa(TipusPeça nom){
        switch (nom){
            case Peo:
                fitxa = new Peo();
                break;
            case Cavall:
                fitxa = new Cavall();
                break;
            case Alfil:
                fitxa = new Alfil();
                break;
            case Torre:
                fitxa = new Torre();
                break;
            case Dama:
                fitxa =  new Dama();
                break;
            case Rei:
                fitxa = new Rei();
                break;
            default:
                throw new IllegalArgumentException("El nom de la peça no és correcte");
        }
    }

    /**
     * Consulta si es dona una situacio d'atac en contra d'una FitxaProblema
     * @param tauler Tauler amb una situacio determinada
     * @return Retorna true si la fitxa es amenaçada per una fitxa rival
     */
    public boolean isAttacked(FitxaProblema[][] tauler) {
        int fil = coord.GetFirst();
        int col = coord.GetSecond();
        //Fila
        for (int i = fil - 1; i >= 0; i--){
            if (tauler[i][col] != null){
                if (tauler[i][col].GetColor().equals(c))
                    break;
                else{
                     TipusPeça tp = Convert.ClassToTipusPeça(tauler[i][col].getIFitxa().getClass().toString());
                     if (tp.equals(TipusPeça.Torre) || tp.equals(TipusPeça.Dama))
                         return true;
                     if (i == fil - 1 && tp.equals(TipusPeça.Rei))
                         return true;
                     break;
                }
            }
        }

        for (int i = fil + 1; i < 8; ++i){
            if (tauler[i][col] != null){
                if (tauler[i][col].GetColor().equals(c))
                    break;
                else{
                    TipusPeça tp = Convert.ClassToTipusPeça(tauler[i][col].getIFitxa().getClass().toString());
                    if (tp.equals(TipusPeça.Torre) || tp.equals(TipusPeça.Dama))
                        return true;
                    if (i == fil + 1 && tp.equals(TipusPeça.Rei))
                        return true;
                    break;
                }
            }
        }

        //Columna
        for (int j = col - 1; j >=0; j--){
            if (tauler[fil][j] != null) {
                if (tauler[fil][j].GetColor().equals(c))
                    break;
                else {
                    TipusPeça tp = Convert.ClassToTipusPeça(tauler[fil][j].getIFitxa().getClass().toString());
                    if (tp.equals(TipusPeça.Torre) || tp.equals(TipusPeça.Dama))
                        return true;
                    if (j == col - 1 && tp.equals(TipusPeça.Rei))
                        return true;
                    break;
                }
            }
        }
        for (int j = col + 1; j < 8; j++){
            if (tauler[fil][j] != null) {
                if (tauler[fil][j].GetColor().equals(c))
                    break;
                else {
                    TipusPeça tp = Convert.ClassToTipusPeça(tauler[fil][j].getIFitxa().getClass().toString());
                    if (tp.equals(TipusPeça.Torre) || tp.equals(TipusPeça.Dama))
                        return true;
                    if (j == col + 1 && tp.equals(TipusPeça.Rei))
                        return true;
                    break;
                }
            }
        }

        //diagonal /
        int dj = col + 1;
        for (int di = fil + 1; di < 8 && dj < 8; ++di){
            if (tauler[di][dj] != null) {
                if (tauler[di][dj].GetColor().equals(c))
                    break;
                else {
                    TipusPeça tp = Convert.ClassToTipusPeça(tauler[di][dj].getIFitxa().getClass().toString());
                    if (tp.equals(TipusPeça.Alfil) || tp.equals(TipusPeça.Dama))
                        return true;
                    if (di == fil + 1 && dj == col + 1 && tp.equals(TipusPeça.Rei))
                        return true;
                    break;
                }
            }
            ++dj;
        }
        dj = col - 1;
        for (int di = fil - 1; di >= 0 && dj >= 0; --di){
            if (tauler[di][dj] != null) {
                if (tauler[di][dj].GetColor().equals(c))
                    break;
                else {
                    TipusPeça tp = Convert.ClassToTipusPeça(tauler[di][dj].getIFitxa().getClass().toString());
                    if (tp.equals(TipusPeça.Alfil) || tp.equals(TipusPeça.Dama))
                        return true;
                    if (di == fil - 1 && dj == col - 1 && tp.equals(TipusPeça.Rei))
                        return true;
                    break;
                }
            }
            --dj;
        }

        // diagonal \
        dj = col - 1 ;
        for (int di = fil + 1; di < 8 && dj >= 0; ++di){
            if (tauler[di][dj] != null) {
                if (tauler[di][dj].GetColor().equals(c))
                    break;
                else {
                    TipusPeça tp = Convert.ClassToTipusPeça(tauler[di][dj].getIFitxa().getClass().toString());
                    if (tp.equals(TipusPeça.Alfil) || tp.equals(TipusPeça.Dama))
                        return true;
                    if (di == fil + 1 && dj == col - 1 && tp.equals(TipusPeça.Rei))
                        return true;
                    break;
                }
            }
            --dj;
        }
        dj = col + 1;
        for (int di = fil - 1; di >= 0 && dj < 8; --di ){
            if (tauler[di][dj] != null) {
                if (tauler[di][dj].GetColor().equals(c))
                    break;
                else {
                    TipusPeça tp = Convert.ClassToTipusPeça(tauler[di][dj].getIFitxa().getClass().toString());
                    if (tp.equals(TipusPeça.Alfil) || tp.equals(TipusPeça.Dama))
                        return true;
                    if (di == fil - 1 && dj == col + 1 && tp.equals(TipusPeça.Rei))
                        return true;
                    break;
                }
            }
            ++dj;
        }

        //cavall
        ParInt[] cavMov = new ParInt[]{new ParInt( fil + 1, col + 2), new ParInt(fil -1, col + 2),
                new ParInt(fil + 1, col - 2), new ParInt(fil - 1, col - 2), new ParInt(fil + 2, col + 1),
                new ParInt(fil + 2, col - 1), new ParInt(fil - 2, col + 1), new ParInt(fil - 2, col - 1)};
        for (ParInt mov: cavMov) {
            if (Convert.InTheLimits(mov)) {
                if (tauler[mov.GetFirst()][mov.GetSecond()] != null) {
                    if (tauler[mov.GetFirst()][mov.GetSecond()].GetColor().equals(Convert.InvertColor(c)) &&
                            Convert.ClassToTipusPeça(tauler[mov.GetFirst()][mov.GetSecond()].
                                    getIFitxa().getClass().toString()).equals(TipusPeça.Cavall))
                        return true;
                }
            }

        }

        //peo
        if (c.equals(Color.blanc)){
            int row = fil - 1;
            int co = col + 1;
            if (Convert.InTheLimits(new ParInt(row, co)) && tauler[row][co] != null && tauler[row][co].GetColor().equals(Color.negre) &&
                    Convert.ClassToTipusPeça(tauler[row][co].getIFitxa().getClass().toString()).equals(TipusPeça.Peo))
                return true;
            co = col - 1;
            if (Convert.InTheLimits(new ParInt(row, co)) && tauler[row][co] != null && tauler[row][co].GetColor().equals(Color.negre) &&
                    Convert.ClassToTipusPeça(tauler[row][co].getIFitxa().getClass().toString()).equals(TipusPeça.Peo))
                return true;
        }
        else{
            int row = fil + 1;
            int co = col + 1;
            if (Convert.InTheLimits(new ParInt(row, co)) && tauler[row][co] != null && tauler[row][co].GetColor().equals(Color.blanc) &&
                    Convert.ClassToTipusPeça(tauler[row][co].getIFitxa().getClass().toString()).equals(TipusPeça.Peo))
                return true;
            co = col - 1;
            if (Convert.InTheLimits(new ParInt(row, co)) && tauler[row][co] != null && tauler[row][co].GetColor().equals(Color.blanc) &&
                    Convert.ClassToTipusPeça(tauler[row][co].getIFitxa().getClass().toString()).equals(TipusPeça.Peo))
                return true;
        }

        return false;
    }

}