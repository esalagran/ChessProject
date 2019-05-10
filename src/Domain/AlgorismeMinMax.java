package Domain;

import java.util.ArrayList;
import java.util.List;

public class AlgorismeMinMax extends Algorithm{

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
                iRetVal  = Integer.MIN_VALUE;
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
                        iRetVal = Integer.MIN_VALUE + profunditat;
                    }
                }
            }
            else {
                iRetVal = Integer.MIN_VALUE; //no es pot fer mate amb les peces que hi ha
            }
        }

        return iRetVal;
    }

    @Override
    protected boolean FindBestMoveConcr(Tauler board, Color ePlayer, ArrayList<Move> moveList, int[] arrIndex, Move moveBest, int iMaxDepth) {
        boolean bRetVal = false;
        int iDepth;
        bRetVal   = FindBestMoveUsingMinMaxAtDepth(board, ePlayer, moveList, arrIndex, iMaxDepth, moveBest);
        return(bRetVal);
    }

    private boolean FindBestMoveUsingMinMaxAtDepth(Tauler board, Color ePlayer, ArrayList<Move> moveList, int[] arrIndex, int iDepth, Move moveBest) {
        boolean bRetVal = false;
        Move move;
        int  iPts;
        int  iWhiteMoveCount;
        int  iBlackMoveCount;
        int  iBestPts;

        iBestPts = Integer.MIN_VALUE;
        if (ePlayer.equals(Color.blanc)) {
            iWhiteMoveCount = moveList.size();
            iBlackMoveCount = 0;
        } else {
            iWhiteMoveCount = 0;
            iBlackMoveCount = moveList.size();
        }
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
    }
}
