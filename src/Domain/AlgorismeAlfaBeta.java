package Domain;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class AlgorismeAlfaBeta{
    private final int Infinit = 10000;
    private Move BestMove;
    //private int prof = 3;
    private int estimacio = 0;
    private Random rnd = new Random();

    /*private int alfaBeta(Tauler tauler, Color jugador, int alpha, int beta, int profunditat) {
        if(profunditat ==0)
            return tauler.EstimaPuntuacio(jugador);
        int best = -Infinit;
        ArrayList<Move> MoveList = tauler.GetMoviments(jugador);
        for (Move m:MoveList) {
            if (!IsValidMove(m, tauler)){
                MoveList.remove(m);
            }
        }
        int size = MoveList.size();
            //MoveList = randomize(tauler.getTaulell(), MoveList);
            while(MoveList.size()>0 && best<beta) {
                Move m = (Move)MoveList.remove(0);
                m.perform(board);
                if(best>alpha)
                    alpha = best;
                int est = -alfaBeta(!white, -beta, -alpha, depth-1);
                if(est>best) {
                    best = est;
                    if(profunditat==prof) {
                        BestMove = m; // Current choice of move
                        val = estimate();
                    }
                }
                m.undo(board);
                if(depth == dd) {
                    super.board.think.setText((mm==null?"":mm+" - ")+v.toString());
                    super.board.progress.setValue(siz-v.size());
                    System.out.println(super.board.think.getText());
                }
            }
        }
        return best;
    }*/


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
        return BestMove;
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


    protected ArrayList<Move>randomize(FitxaProblema[][] taulell, ArrayList<Move> v) {
        int s=v.size();
        int b=0;
        for(int i=0;i<s;i++) {
            Move m = (Move)v.get(i);
            if(m.getOriginalPiece() !=null) {
                v.remove(i);
                v.add(0, m);
                b++;
            }
        }
        for(int i=b;i<s-1;i++) {
            Move o = v.get(i);
            int j = rnd.nextInt(s-i-1)+i+1;
            v.set(i, v.get(j));
            v.set(j, o);
        }
        return v;
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



}
