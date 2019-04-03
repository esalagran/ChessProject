package Domain;

public class Torre extends Fitxa {
    private VectMov[] _moviments;
    private int _pes;

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public int GetPes() {
        return _pes;
    }

    public Torre()
    {
        _moviments = new VectMov[2];
        _moviments[0] = new VectMov(0,7,0);
        _moviments[1] = new VectMov(7,0,0);
        _pes = 5;
    }

}
