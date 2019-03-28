package Domain;

public class Alfil implements IFitxa {
    private VectMov[] _moviments;
    private TipusPeça _nom;
    private int _pes;

    public TipusPeça GetNom(){
        return _nom;
    }

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public int GetPes(){
        return _pes;
    }

    public Alfil()
    {
        _nom = TipusPeça.Alfil;
        _moviments = new VectMov[1];
        _moviments[0] = new VectMov(0,0,7);
        _pes = 4;
    }
}
