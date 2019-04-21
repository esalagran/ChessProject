package Domain;

import java.util.Vector;

public class Peo extends Fitxa {
    private VectMov[] _moviments;
    private int _pes;

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public int GetPes() {
        return _pes;
    }

    public Peo()
    {
        _moviments = new VectMov[2];
        _moviments[0] = new VectMov(0,1,0);
        _moviments[1] = new VectMov(0,0,1);
        _pes = 1;
    }

    @Override
    public Vector<ParInt> GetMoviments(ParInt coord, Tauler actual, Color color) {

        Vector<ParInt> mov = new Vector<>();
        ParInt move;
        for (int i = 0; i < _moviments.length; i++) {
            move = new ParInt(coord.GetFirst(), coord.GetSecond() + 1);
            super.addMove(move, color, actual, mov);
            move = new ParInt(coord.GetFirst() + 1, coord.GetSecond() + 1);
            if (actual.PeçaRival(move, color)) super.addMove(move, color, actual, mov);
        }
        return mov;
    }
}
