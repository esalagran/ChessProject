package Domain;

public class Partida{
    Usuari atacant, defensor;
    Modalitat mode;
    String guanyador;
    Problema probl;

    public Partida(Usuari u1, Usuari u2, Problema p){
        atacant = u1;
        defensor = u2;
        probl = p;
    }
}

