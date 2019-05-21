package Domain;

import java.util.ArrayList;
import java.util.List;

public class AlgorismeMinMax extends Algorithm{
    public final int Infinit = 100000;


    //Se li ha d'enviar una copia a l'hora de validar
    public int MinMax(Tauler t, Color jugador, int profunditat, boolean IsChecked){
        int iRetVal;
        int iPts;

        if (profunditat == 0){
            return t.EstimaPuntuacio(jugador);
        }
        else{
            if (t.SuficientPecesPelMate()){
                boolean AnyMove = false;
                ArrayList<Move> moveList = t.GetMoviments(jugador);
                iRetVal  = -Infinit;
                for (Move move: moveList) {
                    t.moureFitxa(move);
                    boolean isAttacked;
                    if (jugador.equals(Color.negre)){
                        isAttacked = t.getBlackKing().isAttacked(t.getTaulell());
                    }
                    else{
                        isAttacked = t.getWhiteKing().isAttacked(t.getTaulell());
                    }
                    if (!isAttacked){
                        AnyMove = true;
                        if (jugador.equals(Color.blanc))
                            iPts = -MinMax(t, Color.negre, profunditat - 1,
                                    t.getBlackKing().isAttacked(t.getTaulell()));
                        else
                            iPts = -MinMax(t, Color.blanc, profunditat - 1,
                                    t.getWhiteKing().isAttacked(t.getTaulell()));
                        if (iPts > iRetVal) {
                            iRetVal = iPts;
                        }
                    }
                    t.desferJugada(move);
                }
                if (!AnyMove) {
                    if (IsChecked) {
                        iRetVal = -Infinit - profunditat;
                    }
                    else
                        iRetVal = 0;
                }
            }
            else {
                iRetVal = 0; //no es pot fer mate amb les peces que hi ha
            }
        }

        return iRetVal;
    }

    @Override
    public Move FindBestMoveConcr(Tauler board, Color ePlayer, int iMaxDepth) {
        Move bRetVal;
        int iDepth;
        bRetVal = FindBestMoveUsingMinMaxAtDepth(board, ePlayer, iMaxDepth);
        return(bRetVal);
    }

    public Move FindBestMoveUsingMinMaxAtDepth(Tauler t, Color jugador, int profunditat) {
        Move bestMove = null;
        int  iPts;
        int  iBestPts = Integer.MIN_VALUE;
        ArrayList<Move> moveList = t.GetMoviments(jugador);
        for (Move move: moveList) {
            t.moureFitxa(move);
            boolean isAttacked;
            if (jugador.equals(Color.negre)) {
                isAttacked = t.getBlackKing().isAttacked(t.getTaulell());
            } else {
                isAttacked = t.getWhiteKing().isAttacked(t.getTaulell());
            }
            if (!isAttacked) {
                if (jugador.equals(Color.blanc))
                    iPts = -MinMax(t, Color.negre, profunditat - 1,
                            t.getBlackKing().isAttacked(t.getTaulell()));
                else
                    iPts = -MinMax(t, Color.blanc, profunditat- 1,
                            t.getWhiteKing().isAttacked(t.getTaulell()));
                if (iPts > iBestPts) {
                    iBestPts = iPts;
                    bestMove = move;
                    if (iBestPts > 10000){
                        t.desferJugada(move);
                        break;
                    }
                }
            }
            t.desferJugada(move);
        }
        return bestMove;
    }

    /*private boolean FindBestMoveUsingMinMaxAtDepth(Tauler board, Color ePlayer, ArrayList<Move> moveList, int[] arrIndex, int iDepth, Move moveBest) {
        boolean bRetVal = false;
        Move move;
        int  iPts;
        int  iWhiteMoveCount;
        int  iBlackMoveCount;
        int  iBestPts = Integer.MIN_VALUE;

        for (int iIndex: arrIndex) {
            move = moveList.get(iIndex);
            board.moureFitxa(move);
            if (ePlayer.equals(Color.blanc))
                iPts = -MinMax(board,Color.negre,iDepth - 1,
                        move.getEndPos() == board.getBlackKing().GetCoordenades());
            else
                iPts = -MinMax(board,Color.blanc,iDepth - 1,
                        move.getEndPos() == board.getWhiteKing().GetCoordenades());
            board.desferJugada(move);
            if (iPts > iBestPts) {
                iBestPts = iPts;
                moveBest = move;
                bRetVal  = true;
            }
        }
        return bRetVal;
    }*/
}
