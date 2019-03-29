package Domain;

public class Alfil extends Fitxa {
    private VectMov[] _moviments;
    private int _pes;

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public int GetPes(){
        return _pes;
    }

    public Alfil()
    {
        _moviments = new VectMov[1];
        _moviments[0] = new VectMov(0,0,7);
        _pes = 4;
    }
}
