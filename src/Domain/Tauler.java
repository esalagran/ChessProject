package Domain;

public class Tauler {
    private PeçaProblem[][] taulell;

    public Tauler (){}

    public PeçaProblem getInfo(ParInt x) {
        return taulell[x.GetFirst()][x.GetSecond()];
    }
}
