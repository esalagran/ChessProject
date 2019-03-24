package Domain;

public class Dama implements IFitxa {
    private VectMov[] _moviments;
    private String _nom;

    public String GetNom(){
        return _nom;
    }

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public Dama() {
        _nom = "Dama";
        _moviments = new VectMov[3];
        _moviments[0] = new VectMov(0, 7, 0);
        _moviments[1] = new VectMov(7, 0, 0);
        _moviments[1] = new VectMov(0, 0, 7);
    }
}
