package Domain;

public class Partida{

    Usuari atacant, defensor;
    Color torn;
    Modalitat mode;
    String guanyador;
    Problema probl;

    public Partida(Usuari u1, Usuari u2, Problema p){
        atacant = u1;
        defensor = u2;
        probl = p;
    }

    public Partida(Problema p, Modalitat mod,  Color t){
        torn = t;
        probl = p;
        mode = mod;
    }

    public Color GetTorn(){
        return torn;
    }
}

