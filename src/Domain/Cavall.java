package Domain;

import java.util.Vector;
/**
 * @class Cavall
 * \brief Classe que conté la informació de la peça cavall
 */
public class Cavall extends Fitxa {
    private VectMov[] _moviments;
    private int _pes;

    /**
     * Pre:
     * Post: S'obtenen els moviments mínims i màxims del cavall en una posició
     * @return Un set dels moviemnts que pot fer;
     */
    public VectMov[] GetMoviments() {
        return _moviments;
    }

    /**
     * Pre:
     * Post: Getter que retorna el pes heurístic d'una peça
     * @return el pes heurístic d'una peça
     * */
    public int GetPes() {
        return _pes;
    }

    /**
     * Pre: coord indica la posició del cavall en el Tauler actual,
     * Tauler indica el tauler on s'han de calcular les possibilitats i color
     * indica el color de la peça en el tauler
     * */
    @Override
    public Vector<ParInt> GetMoviments(ParInt coord, Tauler actual, Color color) {
        Vector<ParInt> mov = new Vector<>();
        ParInt move;

        for (VectMov moviment : _moviments) {

            int v = moviment.getV();
            int h = moviment.getH();

            move = new ParInt(coord.GetFirst() + v, coord.GetSecond() + h);
            super.addMove(move, color, actual, mov);
            move = new ParInt(coord.GetFirst() + v, coord.GetSecond() - h);
            super.addMove(move, color, actual, mov);
            move = new ParInt(coord.GetFirst() - v, coord.GetSecond() + h);
            super.addMove(move, color, actual, mov);
            move = new ParInt(coord.GetFirst() - v, coord.GetSecond() - h);
            super.addMove(move, color, actual, mov);
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
