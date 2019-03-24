package Domain;

import java.util.Vector;

/**
 * Aquesta classe implementa l'algorisme simple(Minimax)
 */

public class Simple  extends  Maquina{

    private boolean blanc;

    public Simple() {
        super();
    }

    public void minimax(boolean blanques, int depth){
        blanc = blanques;
        int heuristica = 0;
        if (blanc) heuristica = atac(depth);
        else heuristica = defensa(depth);
    }

    public int atac(int depth){
        if (depth == 0){} //retorna calcul estimat de la partida ja qe no arriba al final


    }

    public int defensa (int depth){

    }
}
