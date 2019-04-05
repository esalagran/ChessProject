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

    public boolean PeçaMeva(ParInt x, boolean white) {
        if(taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.negre && !white) return true;
        if(taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.blanc && white) return true;
        return false;
    }

    public boolean PeçaRival(ParInt x, boolean white){
        if(taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.negre && white) return true;
        if(taulell[x.GetFirst()][x.GetSecond()].GetColor() == Color.blanc && !white) return true;
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
