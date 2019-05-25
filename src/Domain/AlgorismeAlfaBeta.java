package Domain;

import java.util.ArrayList;

public class AlgorismeAlfaBeta extends Algorithm{

    private int AlphaBeta(Tauler t, Color jugadorActual, Color teoricGuanyador, int alfa, int beta, int profunditat){
        if (profunditat == 0){
            if (IsMate(jugadorActual, t)) return Infinit;
            return t.EstimaPuntuacio(jugadorActual);
        }
        ArrayList<Move> moveList = t.GetMoviments(jugadorActual);
        boolean hasMoved = false;
        if (jugadorActual.equals(teoricGuanyador)){
            int best = -Infinit;
            for (Move m: moveList) {
                t.moureFitxa(m);
                if (!IsChecked(t, jugadorActual)){
                    hasMoved = true;
                    best = Convert.Max(best, AlphaBeta(t, Convert.InvertColor(jugadorActual), teoricGuanyador, alfa,
                            beta, profunditat -1));
                    alfa = Convert.Max(best, alfa);
                    if (beta <= alfa){
                        t.desferJugada(m);
                        break;
                    }
                }
                t.desferJugada(m);
            }
            if (hasMoved)
                return best;
            else
                if (IsChecked(t, jugadorActual))
                    return -Infinit;
                else
                    return 0;
        }
        else{
            int best = Infinit;
            for (Move m: moveList) {
                t.moureFitxa(m);
                if (!IsChecked(t, jugadorActual)){
                    hasMoved = true;
                    best = Convert.Min(best, AlphaBeta(t, Convert.InvertColor(jugadorActual), teoricGuanyador, alfa,
                            beta, profunditat -1));
                    beta = Convert.Min(best, beta);
                    if (beta <= alfa){
                        t.desferJugada(m);
                        break;
                    }
                }
                t.desferJugada(m);
            }
            if (hasMoved)
                return best;
            else
                if (IsChecked(t, jugadorActual))
                    return Infinit;
                else
                    return 0;
        }

    }

    @Override
    public Move FindBestMoveConcr(Tauler chessBoard, Color ePlayer, int iMaxDepth) {
        if (iMaxDepth %2 == 0)
            return FindBestMoveAtDepthAlfa(chessBoard, ePlayer, iMaxDepth);
        return FindBestMoveAtDepthBeta(chessBoard, ePlayer, iMaxDepth);
    }

    /**
    * @return el millor moviment possible si es pot moure, Altrament,
     * move.getEndPos() = (-1,-1) si és profunditat 0 i no es pot fer mate, null si és mat i
     * move.getStartPos() = (-1,-1) si està ofegat
    * */
    private Move FindBestMoveAtDepthAlfa(Tauler t, Color jugadorActual, int profunditat){
        if (profunditat == 0){
            if (IsMate(jugadorActual, t)) return null;
            else return new Move(null, null, new ParInt(-1, -1));
        }
        Move bestMove = null;
        int beta = Infinit;
        int alpha = -Infinit;
        int best = Infinit;
        boolean hasMoved = false;
        ArrayList<Move> moveList = t.GetMoviments(jugadorActual);
        for (Move m: moveList) {
            t.moureFitxa(m);
            if (!IsChecked(t, jugadorActual)){
                if (!hasMoved){
                    hasMoved = true;
                    bestMove = m;
                }
                best = Convert.Min(best, AlphaBeta(t, Convert.InvertColor(jugadorActual), Convert.InvertColor(jugadorActual), alpha,
                        beta, profunditat -1));
                
                if (bestMove == null || best < beta){
                    bestMove = m;
                    beta = best;
                }
                if (beta <= alpha){
                    t.desferJugada(m);
                    break;
                }
            }
            t.desferJugada(m);
        }
        if (hasMoved)
            return bestMove;
        else
        if (IsChecked(t, jugadorActual))
            return null;
        else
            return new Move(null, new ParInt(-1, -1), null);
    }

    /*
    * En cas que no es pugui moure hi ha dues opcions: si el rei està amenaçat, és mat i es retorna null,
    * si no ho està, bestMove.GetFirst = (-1,-1)
    * */
    private Move FindBestMoveAtDepthBeta(Tauler t, Color jugadorActual, int profunditat){
        ArrayList<Move> moveList = t.GetMoviments(jugadorActual);
        Move bestMove = null;
        boolean hasMoved = false;
        int best = -Infinit;
        for (Move m: moveList) {
            t.moureFitxa(m);
            boolean isAttacked = IsChecked(t, jugadorActual);
            if (!isAttacked){
                hasMoved = true;
                int auxBest = AlphaBeta(t, Convert.InvertColor(jugadorActual), jugadorActual, -Infinit,
                        Infinit, profunditat - 1);
                //best = Convert.Max(best, auxBest);
                if (bestMove == null || auxBest > best){
                    bestMove = m;
                    best = auxBest;
                }
                if (Infinit <= best){
                    t.desferJugada(m);
                    break;
                }
            }
            t.desferJugada(m);
        }
        if (hasMoved)
            return bestMove;
        else
            if (IsChecked(t, jugadorActual))
                return  null;
            else
                return new Move(null, new ParInt(-1, -1), null);
    }



}
