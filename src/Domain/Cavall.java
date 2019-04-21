package Domain;

import java.util.Vector;

public class Cavall extends Fitxa {
    private VectMov[] _moviments;
    public int _pes;

    public VectMov[] GetMoviments() {
        return _moviments;
    }

    public int GetPes() {
        return _pes;
    }

    @Override
    public Vector<ParInt> GetMoviments(ParInt coord, Tauler actual, Color color) {
        Vector<ParInt> mov = new Vector<>();
        ParInt move;

        for (int i = 0; i < _moviments.length; i++) {

            int v = _moviments[i].getV();
            int h = _moviments[i].getH();

            move = new ParInt(coord.GetFirst() + v, coord.GetSecond() + h);
            super.addMove(move, color, actual,mov);
            move = new ParInt(coord.GetFirst() + v, coord.GetSecond() - h);
            super.addMove(move, color, actual,mov);
            move = new ParInt(coord.GetFirst() - v, coord.GetSecond() + h);
            super.addMove(move, color, actual,mov);
            move = new ParInt(coord.GetFirst() - v, coord.GetSecond() - h);
            super.addMove(move, color, actual,mov);
        }

        return mov;
    }

    public Cavall()
    {
        _moviments = new VectMov[2];
        _moviments[0] = new VectMov(2,1, 0);
        _moviments[1] = new VectMov(1,2,0);
        _pes = 6;
    }
}
