package Domain;

public class Rei implements IFitxa{
    private VectMov[] _moviments;
    private String _nom;
    private int _pes ;

    public String GetNom(){
        return _nom;
    }

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public int GetPes() {
        return _pes;
    }

    public Rei() {
        _nom = "Rei";
        _moviments = new VectMov[3];
        _moviments[0] = new VectMov(0, 1, 0);
        _moviments[1] = new VectMov(1, 0, 0);
        _moviments[1] = new VectMov(0, 0, 1);
        _pes = 100;
    }
}
