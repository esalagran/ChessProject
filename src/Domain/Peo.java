package Domain;

public class Peo extends Fitxa {
    private VectMov[] _moviments;
    private int _pes;

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public int GetPes() {
        return _pes;
    }

    public Peo()
    {
        _moviments = new VectMov[2];
        _moviments[0] = new VectMov(0,1,0);
        _moviments[1] = new VectMov(0,0,1);
        _pes = 1;
    }
}
