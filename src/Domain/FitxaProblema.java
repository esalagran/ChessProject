package Domain;


import java.util.Vector;

/**
 * Fitxa problema
 * Classe que implementa els elements que té un problema
 * @author esalagran
 * */

public class FitxaProblema{

    private Fitxa fitxa;
    private Color c;
    private ParInt coord;
    private TipusPeça tP;

    public ParInt GetCoordenades(){
        return coord;
    }

    /*public TipusPeça GetTipus(){
        return tP;
    }*/

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

    public FitxaProblema(TipusPeça nom, int Fila, int Col, Color color){
        CreaFitxa(nom);
        tP = nom;
        coord = new ParInt(Col, Fila);
        c = color;
    }

    public Vector<ParInt> GetMoviments(Tauler tauler){
        return fitxa.GetMoviments(coord, tauler, c);
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