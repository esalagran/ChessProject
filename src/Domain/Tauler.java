package Domain;

public class Tauler {
    private FitxaProblema[][] tauler;

    public FitxaProblema FitxaAt(int i, int j){
        return tauler[i][j];
    }

    public Tauler (FitxaProblema[][] t){
        tauler = t;
    }

    public void AfegirPeçaAt(int i, int j, FitxaProblema f){
        tauler[i][j] = f;
    }

    public boolean PeçaMeva(ParInt x) {
        return false;
    }

    public boolean PeçaRival(ParInt x){
        return false;
    }
}
