package Domain;

public class Dama extends Fitxa {
    private VectMov[] _moviments;
    private int _pes;

    /**
     * \pre:
     * \post: retorna els moviments màxims que pot fer una dama en una direcció concreta
     * @return els moviments màxims que pot fer una dama en una direcció concreta
     */
    public VectMov[] GetMoviments() {
        return _moviments;
    }

    /**
     * \pre:
     * \post: el pes heurísitc de la dama
     * @return el pes heurísitc de la dama
     */
    public int GetPes(){
        return _pes;
    }

    /**
     * \pre
     * \post: Inicialització de les propietats bàsiques
     */
    public Dama() {
        _moviments = new VectMov[3];
        _moviments[0] = new VectMov(0, 7, 0);
        _moviments[1] = new VectMov(7, 0, 0);
        _moviments[2] = new VectMov(0, 0, 7);
        _pes = 8;
    }
}
