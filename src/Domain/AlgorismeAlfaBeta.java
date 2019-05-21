package Domain;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class AlgorismeAlfaBeta extends Algorithm{
    private final int Infinit = 10000;

    private int AlphaBeta(Tauler t, Color jugadorActual, Color teoricGuanyador, int alfa, int beta, int profunditat, boolean checked){
        if (beta != Infinit) return alfa;
        if (profunditat == 0){
            if (IsMate(jugadorActual, t, checked)) return Infinit;
            return t.EstimaPuntuacio(jugadorActual);
        }
        ArrayList<Move> moveList = t.GetMoviments(jugadorActual);
        boolean hasMoved = false;
        if (jugadorActual.equals(teoricGuanyador)){
            int best = -Infinit;
            for (Move m: moveList) {
                t.moureFitxa(m);
                boolean isAttacked = IsChecked(t, jugadorActual);
                if (!isAttacked){
                    hasMoved = true;
                    best = Convert.Max(best, AlphaBeta(t, Convert.InvertColor(jugadorActual), teoricGuanyador, alfa,
                            beta, profunditat -1, IsChecked(t, Convert.InvertColor(jugadorActual))));
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
                if (checked)
                    return -Infinit;
                else
                    return 0;
        }
        else{
            int best = Infinit;
            for (Move m: moveList) {
                t.moureFitxa(m);
                boolean isAttacked = IsChecked(t, jugadorActual);
                /*if (jugadorActual.equals(Color.negre)) {
                    isAttacked = t.getBlackKing().isAttacked(t.getTaulell());
                } else {
                    isAttacked = t.getWhiteKing().isAttacked(t.getTaulell());
                }*/
                if (!isAttacked){
                    hasMoved = true;
                    best = Convert.Min(best, AlphaBeta(t, Convert.InvertColor(jugadorActual), teoricGuanyador, alfa,
                            beta, profunditat -1, IsChecked(t, Convert.InvertColor(jugadorActual))));
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
                if (checked)
                    return Infinit;
                else
                    return 0;
        }

    }

    public Move GetMove(Color guanyador, Color jugador, Tauler tauler, int prof){
        System.out.println(AlphaBeta(tauler, jugador, guanyador, -Infinit, Infinit, prof, IsChecked(tauler, jugador)));
        return null;
    }

    public boolean IsValidMove (Move m, Tauler tauler){
        boolean isAttacked;
        FitxaProblema[][] t = tauler.getTaulell();
        tauler.moureFitxa(m);
        if (t[m.getEndPos().GetFirst()][m.getEndPos().GetSecond()].equals(Color.negre)) {
            isAttacked = tauler.getBlackKing().isAttacked(tauler.getTaulell());
        } else {
            isAttacked = tauler.getWhiteKing().isAttacked(tauler.getTaulell());
        }
        tauler.desferJugada(m);
        return isAttacked;
    }

    private boolean IsChecked(Tauler t, Color jugador){
        if (jugador.equals(Color.negre))
            return t.getBlackKing().isAttacked(t.getTaulell());
        return t.getWhiteKing().isAttacked(t.getTaulell());
    }

    private boolean IsMate(Color jugador, Tauler t, boolean isChecked){
        if (!isChecked) return false;
        ArrayList<Move> moveList = t.GetMoviments(jugador);
        boolean mate = true;
        for (Move m : moveList) {
            t.moureFitxa(m);
            mate = IsChecked(t, jugador);
            t.desferJugada(m);
            if (!mate) return false;
        }
        return true;
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
            if (IsChecked(t, jugadorActual)) return null;
            else return new Move(null, null, new ParInt(-1, -1));
        }
        Move bestMove = null;
        int best = Infinit;
        boolean hasMoved = false;
        ArrayList<Move> moveList = t.GetMoviments(jugadorActual);
        for (Move m: moveList) {
            t.moureFitxa(m);
            boolean isAttacked = IsChecked(t, jugadorActual);
            if (!isAttacked){
                hasMoved = true;
                int alpha =  AlphaBeta(t, Convert.InvertColor(jugadorActual), Convert.InvertColor(jugadorActual), -Infinit,
                        Infinit, profunditat -1, IsChecked(t, Convert.InvertColor(jugadorActual)));

                if (bestMove == null || alpha < best){
                    bestMove = m;
                    best = alpha;
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
                        Infinit, profunditat - 1, IsChecked(t, Convert.InvertColor(jugadorActual)));
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
