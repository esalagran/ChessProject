package Domain;

import java.util.ArrayList;

import static Domain.Convert.InvertColor;

public class AlgorismeMinMax extends Algorithm{

    //Se li ha d'enviar una copia a l'hora de validar
    private int MinMax(Tauler t, Color jugador, int profunditat){
        int best;
        if (profunditat == 0){
            if (IsMate(jugador, t)) return -Infinit;
            return t.EstimaPuntuacio(jugador);
        }
        else{
            boolean AnyMove = false;
            ArrayList<Move> moveList = t.GetMoviments(jugador);
            best  = -Infinit;
            for (Move move: moveList) {
                t.moureFitxa(move);
                if (!IsChecked(t, jugador)){
                    AnyMove = true;
                    int iPts = -MinMax(t, InvertColor(jugador), profunditat - 1);
                    if (iPts > best) {
                        best = iPts;
                    }
                }
                t.desferJugada(move);
                if (best >= Infinit)
                    return best;
            }
            if (!AnyMove) {
                if (!IsChecked(t, jugador)) {
                    return 0;
                }
            }
        }
        return best;
    }

    @Override
    public Move FindBestMoveConcr(Tauler t, Color jugador, int profunditat) {
        if (profunditat == 0){
            if (IsMate(jugador, t)) return null;
            return new Move(null, null, new ParInt(-1, -1));
        }
        boolean hasMoved = false;
        Move bestMove = null;
        int  bestPts = -Infinit;
        ArrayList<Move> moveList = t.GetMoviments(jugador);
        for (Move move: moveList) {
            t.moureFitxa(move);
            if (!IsChecked(t, jugador)) {
                hasMoved = true;
                int Pts = -MinMax(t, InvertColor(jugador), profunditat - 1);
                if (Pts > bestPts) {
                    bestPts = Pts;
                    bestMove = move;
                    if (bestPts >= Infinit){
                        t.desferJugada(move);
                        break;
                    }
                }
            }
            t.desferJugada(move);
        }
        if (!hasMoved) {
            if (!IsChecked(t, jugador))
                return new Move(null, new ParInt(-1, -1), null);
        }
        return bestMove;
    }
}
