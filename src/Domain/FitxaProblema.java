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
        int numCavalls = 0;
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
                     if (tp.equals(TipusPeça.Cavall))
                         ++numCavalls;
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
                    if (tp.equals(TipusPeça.Cavall))
                        ++numCavalls;
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
                    if (tp.equals(TipusPeça.Cavall))
                        ++numCavalls;
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
                    if (tp.equals(TipusPeça.Cavall))
                        ++numCavalls;
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
                    if (tp.equals(TipusPeça.Cavall))
                        ++numCavalls;
                    break;
                }
            }
            ++dj;
        }
        dj = col - 1;
        for (int di = fil - 1; di > 0 && dj > 0; --di){
            if (tauler[di][dj] != null) {
                if (tauler[di][dj].GetColor().equals(c))
                    break;
                else {
                    TipusPeça tp = Convert.ClassToTipusPeça(tauler[di][dj].getIFitxa().getClass().toString());
                    if (tp.equals(TipusPeça.Alfil) || tp.equals(TipusPeça.Dama))
                        return true;
                    if (di == fil - 1 && dj == col - 1 && tp.equals(TipusPeça.Rei))
                        return true;
                    if (tp.equals(TipusPeça.Cavall))
                        ++numCavalls;
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
                    if (tp.equals(TipusPeça.Cavall))
                        ++numCavalls;
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
                    if (tp.equals(TipusPeça.Cavall))
                        ++numCavalls;
                    break;
                }
            }
            ++dj;
        }

        //cavall
        if (numCavalls < 2){
            ParInt[] cavMov = new ParInt[]{new ParInt(1, 2), new ParInt(-1, 2),
                    new ParInt(1, -2), new ParInt(-1, -2), new ParInt(2, 1),
                    new ParInt(2, -1), new ParInt(-2, 1), new ParInt(-2, -1)};
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
        }
        return false;
    }

}