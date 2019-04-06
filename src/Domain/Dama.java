package Domain;

public class Dama extends Fitxa {
    private VectMov[] _moviments;
    private int _pes;

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public int GetPes(){
        return _pes;
    }

    public Dama() {
        _moviments = new VectMov[3];
        _moviments[0] = new VectMov(0, 7, 0);
        _moviments[1] = new VectMov(7, 0, 0);
        _moviments[2] = new VectMov(0, 0, 7);
        _pes = 8;
    }
}
