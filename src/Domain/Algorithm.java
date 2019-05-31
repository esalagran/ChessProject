package Domain;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Algorithm extends Usuari {

    final int Infinit = 10000;
    private int depth;

    public Algorithm (int profunditat, String type){
        super(type);
        depth = profunditat;
    }


    public abstract Move FindBestMoveConcr(Tauler tauler,Color jugador, int jugadesRestants);

    public abstract boolean validateProblem(Tauler tauler, Color jugador, int profunditat);

    protected int getDepth() {
        return depth;
    }
}

