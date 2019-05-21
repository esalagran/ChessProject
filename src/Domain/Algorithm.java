package Domain;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Algorithm {

    final int Infinit = 10000;

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

    public abstract Move FindBestMoveConcr(Tauler chessBoard,Color ePlayer,int iMaxDepth);

    boolean IsChecked(Tauler t, Color jugador){
        if (jugador.equals(Color.negre))
            return t.getBlackKing().isAttacked(t.getTaulell());
        return t.getWhiteKing().isAttacked(t.getTaulell());
    }

    boolean IsMate(Color jugador, Tauler t){
        if (!IsChecked(t, jugador)) return false;
        ArrayList<Move> moveList = t.GetMoviments(jugador);
        boolean mate = true;
        for (Move m : moveList) {
            t.moureFitxa(m);
            mate = IsChecked(t, jugador);
            t.desferJugada(m);
            if (!mate) return false;
        }
        return mate;
    }
}

