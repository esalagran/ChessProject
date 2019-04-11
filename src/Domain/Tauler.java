package Domain;

public class Tauler {
    private FitxaProblema[][] taulell;

    public Tauler (){}

    public Tauler (FitxaProblema[][] t){
        taulell = t;
    }

    public FitxaProblema[][] getTaulell() {
        return taulell;
    }

    public void AfegirPeçaAt(int i, int j, FitxaProblema f){
        taulell[i][j] = f;
    }

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
            if (taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.blanc && !color.equals(Color.negre)) return true;
        }
        return false;
    }

    public FitxaProblema FitxaAt(int i, int j){
        return taulell[i][j];
    }

    public void moureFitxa(ParInt ini, ParInt move){
        FitxaProblema fp = taulell[ini.GetFirst()][ini.GetSecond()];
        taulell[ini.GetFirst()][ini.GetSecond()] = null;
        taulell[ini.GetFirst()+ move.GetFirst()][ini.GetSecond()+move.GetSecond()] = fp;
    }
}
