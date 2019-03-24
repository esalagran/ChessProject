package Domain;

public class FitxaProblema{

    IFitxa fitxa;
    boolean negre;
    ParInt coord;

    public String GetNom(){
        return fitxa.GetNom();
    };



    void MourePeça(int a, boolean b, ParInt desti){

    }

    FitxaProblema(String nom, ParInt posIni, boolean color){
        CreaFitxa(nom);
        coord = posIni;
        negre = color;
    }

    private void CreaFitxa(String nom){
        switch (nom){
            case "Peo":
                fitxa = new Peo();
                break;
            case "Cavall":
                fitxa = new Cavall();
                break;
            case "Alfil":
                fitxa = new Alfil();
                break;
            case "Torre":
                fitxa = new Torre();
                break;
            case "Dama":
                fitxa =  new Dama();
                break;
            case "Rei":
                fitxa = new Rei();
                break;
            default:
                throw new IllegalArgumentException("El nom de la peça no és correcte");
        }
    }

}
