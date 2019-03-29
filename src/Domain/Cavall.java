package Domain;

public class Cavall extends Fitxa {
    private VectMov[] _moviments;
    public int _pes;

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public int GetPes() {
        return _pes;
    }

    public Cavall()
    {
        _moviments = new VectMov[2];
        _moviments[0] = new VectMov(2,1, 0);
        _moviments[1] = new VectMov(1,2,0);
        _pes = 6;
    }
}
