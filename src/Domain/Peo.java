package Domain;

import java.util.Vector;

/**
 * @class Peo
 * \brief Classe que conté la informació de la peça peo
 */

public class Peo extends Fitxa {
    private VectMov[] _moviments;
    private int _pes;

    /**
     * Pre:
     * Post: S'obtenen els moviments mínims i màxims del peó en una posició
     * @return Un set dels moviemnts que pot fer;
     */
    public VectMov[] GetMoviments() {
        return _moviments;
    }

    /**
     * Pre:
     * Post: Getter que retorna el pes heurístic del peó
     * @return el pes heurístic del peó
     * */
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

    /**
     * Pre: coord indica la posició del peó en el Tauler actual,
     * Tauler indica el tauler on s'han de calcular les possibilitats i color
     * indica el color de la peça en el tauler
     * */
    @Override
    public Vector<ParInt> GetMoviments(ParInt coord, Tauler actual, Color color) {

        Vector<ParInt> mov = new Vector<>();
        ParInt move;
        int incr;
        if (color.equals(Color.blanc)) incr = -1;
        else incr = 1;
        move = new ParInt(coord.GetFirst() + incr, coord.GetSecond());
        if (Convert.InTheLimits(move) && actual.FitxaAt(move) == null)
            super.addMove(move, color, actual, mov);

        move = new ParInt(coord.GetFirst() + incr, coord.GetSecond() + 1);
        if (actual.PeçaRival(move, color))super.addMove(move, color, actual, mov);

        move = new ParInt(coord.GetFirst() + incr, coord.GetSecond() - 1);
        if (actual.PeçaRival(move, color))super.addMove(move, color, actual, mov);

        return mov;
    }
}
