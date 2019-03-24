package Domain;

public interface IFitxa {
    
    public VectMov GetNumMax();

    public String GetNom();
    public void SetNom(String Nom); //En aquest cas el nom indica el tipus

    //Està per decidir on incloure el nombre màxim de peces que pot tenir un problema ja que sempre són els mateixos
    //Segurament estarà al problema, a la llista de les peces, controlat per una classe externa
}
