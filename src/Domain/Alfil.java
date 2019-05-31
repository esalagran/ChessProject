package Domain;

/**
 * @Class: classe on hi ha la informació referent a la peça àlfil
 * */
public class Alfil extends Fitxa {
    private VectMov[] _moviments;
    private int _pes;

    /**
     * \pre:
     * \post: retorna els moviments màxims que pot fer un àlfil en una direcció concreta
     */
    public VectMov[] GetMoviments() {
        return _moviments;
    }

    /**
     * \pre:
     * \post: el pes heurísitc de l'àlfil
     * @return el pes heurísitc de l'alifil
     */
    public int GetPes(){
        return _pes;
    }

    /**
     * \pre
     * \post: Inicialització de les propietats bàsiques
     */
    public Alfil()
    {
        _moviments = new VectMov[1];
        _moviments[0] = new VectMov(0,0,7);
        _pes = 4;
    }
}
