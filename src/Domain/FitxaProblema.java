package Domain;


/**
 * @class: Fitxa problema
 * Classe que implementa els elements que té un problema
 * @author esalagran
 * */

public class FitxaProblema{

    Fitxa fitxa;
    Color c;
    ParInt coord;
    TipusPeça tP;

    public ParInt GetCoordenades(){
        return coord;
    }

    public int GetCol(){
        return coord.GetFirst();
    }

    public int GetFila(){
        return coord.GetSecond();
    }

    public TipusPeça GetTipus(){
        return tP;
    }

    public Color GetColor(){
        return c;
    }

    public void SetCoordenades(ParInt dest){
        coord = dest;
    }

    public Fitxa getIFitxa () { return fitxa;}


    public FitxaProblema(TipusPeça nom, ParInt posIni, Color color){
        CreaFitxa(nom);
        tP = nom;
        coord = posIni;
        c = color;
    }

    public FitxaProblema(TipusPeça nom, int Col, int Fila, Color color){
        CreaFitxa(nom);
        tP = nom;
        coord = new ParInt(Col, Fila);
        c = color;
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