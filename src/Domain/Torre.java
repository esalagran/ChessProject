package Domain;

public class Torre implements IFitxa {
    private VectMov[] _moviments;
    private TipusPeça _nom;
    private int _pes;

    public TipusPeça GetNom(){
        return _nom;
    }

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public int GetPes() {
        return _pes;
    }

    public Torre()
    {
        _nom = TipusPeça.Torre;
        _moviments = new VectMov[2];
        _moviments[0] = new VectMov(0,7,0);
        _moviments[1] = new VectMov(7,0,0);
        _pes = 5;
    }
}
