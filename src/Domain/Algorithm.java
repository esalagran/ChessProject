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


    //Possible millora
    protected static ArrayList<Move> SortMoveList(ArrayList<Move> moveList, int[] arrPoints) {
        ArrayList<Move> moveListRetVal;
        IndexPoint[] arrIndexPoint;

        moveListRetVal = new ArrayList<>(moveList.size());
        arrIndexPoint  = new IndexPoint[arrPoints.length];
        for (int iIndex = 0; iIndex < arrIndexPoint.length; iIndex++) {
            arrIndexPoint[iIndex] = new IndexPoint(arrPoints[iIndex], iIndex);
        }

        int j = arrIndexPoint.length;
        Arrays.sort(arrIndexPoint);

        for (IndexPoint indexPoint : arrIndexPoint) {
            moveListRetVal.add(moveList.get(indexPoint.getiIndex()));
        }
        return(moveListRetVal);
    }

    public abstract Move FindBestMoveConcr(Tauler tauler,Color jugador, int jugadesRestants);

    public abstract boolean validateProblem(Tauler tauler, Color jugador, int profunditat);

    protected int getDepth() {
        return depth;
    }
}

