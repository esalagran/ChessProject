package Domain;


/**
 * @class Rei
 * \brief Classe que conté la informació de la peça rei
 */
public class Rei extends Fitxa{
    private VectMov[] _moviments;
    private int _pes;

    /**
     * Pre:
     * Post: S'obtenen els moviments mínims i màxims del rei en una posició
     * @return Un set dels moviemnts que pot fer;
     */
    public VectMov[] GetMoviments() {
        return _moviments;
    }

    /**
     * Pre:
     * Post: Getter que retorna el pes heurístic d'una peça
     * @return el pes heurístic d'una peça
     * */
    public int GetPes() {
        return _pes;
    }

    public Rei() {
        _moviments = new VectMov[3];
        _moviments[0] = new VectMov(0, 1, 0);
        _moviments[1] = new VectMov(1, 0, 0);
        _moviments[2] = new VectMov(0, 0, 1);
        _pes = 100;
    }
}
