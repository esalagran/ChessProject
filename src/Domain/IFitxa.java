package Domain;

public interface IFitxa {

    public TipusPeça GetNom();

    public VectMov[] GetMoviments();

    public int GetPes();

    //Està per decidir on incloure el nombre màxim de peces que pot tenir un problema ja que sempre són els mateixos
    //Segurament estarà al problema, a la llista de les peces, controlat per una classe externa
}
