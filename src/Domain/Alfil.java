package Domain;

public class Alfil implements IFitxa {
    private VectMov[] _moviments;
    private String _nom;

    public String GetNom(){
        return _nom;
    }

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public Alfil()
    {
        _nom = "Alfil";
        _moviments = new VectMov[1];
        _moviments[0] = new VectMov(0,0,7);
    }
}
