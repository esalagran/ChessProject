package Domain;

public class Torre implements IFitxa {
    private VectMov[] _moviments;
    private String _nom;

    public String GetNom(){
        return _nom;
    }

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public Torre()
    {
        _nom = "Torre";
        _moviments = new VectMov[2];
        _moviments[0] = new VectMov(0,7,0);
        _moviments[1] = new VectMov(7,0,0);
    }
}
