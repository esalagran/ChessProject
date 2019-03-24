package Domain;

public interface IFitxa {

    public String GetNom();

    public VectMov[] GetMoviments();

    //Està per decidir on incloure el nombre màxim de peces que pot tenir un problema ja que sempre són els mateixos
    //Segurament estarà al problema, a la llista de les peces, controlat per una classe externa
}
