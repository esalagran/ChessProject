package Domain;


/**
 * @class: Fitxa problema
 * Classe que implementa els elements que té un problema
 * @author esalagran
 * */

public class FitxaProblema{

    Fitxa fitxa;
    boolean negre;
    ParInt coord;
    TipusPeça tP;

    public int GetPes(){
        return fitxa.GetPes();
    }

    public ParInt GetCoordenades(){
        return coord;
    }

    public int GetCol(){
        return coord.GetSecond();
    }

    public int GetFila(){
        return coord.GetFirst();
    }

    public TipusPeça GetTipus(){
        return tP;
    }

    public boolean GetColor(){
        return negre;
    }

    void SetCoordenades(ParInt dest){
        coord = dest;
    }

    public Fitxa getIFitxa () { return fitxa;}


    public FitxaProblema(TipusPeça nom, ParInt posIni, boolean color){
        CreaFitxa(nom);
        tP = nom;
        coord = posIni;
        negre = color;
    }

    public FitxaProblema(TipusPeça nom, int Col, int Fila, boolean color){
        CreaFitxa(nom);
        tP = nom;
        coord = new ParInt(Col, Fila);
        negre = color;
    }

    private void CreaFitxa(TipusPeça nom){
        switch (nom){
            case Peo:
                fitxa = new Peo();
                break;
            case Cavall:
                fitxa = new Cavall();
                break;
            case Alfil:
                fitxa = new Alfil();
                break;
            case Torre:
                fitxa = new Torre();
                break;
            case Dama:
                fitxa =  new Dama();
                break;
            case Rei:
                fitxa = new Rei();
                break;
            default:
                throw new IllegalArgumentException("El nom de la peça no és correcte");
        }
    }

}
