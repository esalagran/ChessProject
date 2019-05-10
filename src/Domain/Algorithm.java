package Domain;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Algorithm {

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

    private void FindBestMove(Tauler board, Color ePlayer, int profunditat) {
        boolean bRetVal = false;
        ArrayList<Move> moveList = new ArrayList<>();
        Move moveExt;
        int[] arrIndex;
        int iSwapIndex;
        int iTmp;
        int iPermCount;
        Move BestMove = null;
        //ChessBoard.PosInfoS posInfo;
        moveList = board.GetMoviments(ePlayer);
        arrIndex = new int[moveList.size()];
        for (int iIndex = 0; iIndex < moveList.size(); iIndex++) {
            arrIndex[iIndex] = iIndex;
        }
        Move move = new Move(null, new ParInt(),new ParInt());

        bRetVal = FindBestMoveConcr(board, ePlayer, moveList, arrIndex, BestMove, profunditat);
    }

    protected abstract boolean FindBestMoveConcr(Tauler chessBoard,Color ePlayer, ArrayList<Move> moveList,
                                            int[] arrIndex, Move moveBest,int iMaxDepth);
}

