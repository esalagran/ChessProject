package Domain;

import java.util.Vector;

public abstract class Fitxa {

    /**
     * \pre:
     * \post: nombre de caselles màximes d'una peça en les direccions que es pot moure
     * @return array que conté els moviments màxims que es pot moure una peça en les direccions
     */
    public abstract VectMov[] GetMoviments();

    /**
     * \pre:
     * \post: el pes heurístic d'una peça
     * @return el pes heurístic d'una peça
     */
    public abstract int GetPes();

    public Vector<ParInt>GetMoviments(ParInt coord, Tauler actual, Color color){

        Vector<ParInt> moviments = new Vector<>();
        VectMov[] vm = GetMoviments();
        ParInt move;
        boolean stopd1,stopd2,stopd3,stopd4;

        for (int i = 0; i < vm.length; i++) {
            stopd1 = false;
            stopd2 = false;
            stopd3 = false;
            stopd4 = false;

            for (int j = 0; j < vm[i].getD(); j++) {
                move = new ParInt(coord.GetFirst() + j + 1, coord.GetSecond() + j + 1);
                if (!stopd1) {
                    addMove(move, color, actual, moviments);
                    stopd1 = actual.PeçaMeva(move, color) || actual.PeçaRival(move, color);
                }
                move = new ParInt(coord.GetFirst() - j - 1, coord.GetSecond() + j + 1);
                if (!stopd2) {
                    addMove(move, color, actual, moviments);
                    stopd2 = actual.PeçaMeva(move, color) || actual.PeçaRival(move, color);
                }
                move = new ParInt(coord.GetFirst() + j + 1, coord.GetSecond() - j - 1);
                if (!stopd3) {
                    addMove(move, color, actual, moviments);
                    stopd3 = actual.PeçaMeva(move, color) || actual.PeçaRival(move, color);
                }
                move = new ParInt(coord.GetFirst() - j - 1, coord.GetSecond() - j - 1);
                if (!stopd4) {
                    addMove(move, color, actual, moviments);
                    stopd4 = actual.PeçaMeva(move, color) || actual.PeçaRival(move, color);
                }
            }
            stopd1 = false;
            stopd2 = false;
            for (int j = 0; j < vm[i].getH(); j++) {
                move = new ParInt(coord.GetFirst() + j + 1, coord.GetSecond());
                if (!stopd1) {
                    addMove(move, color, actual, moviments);
                    stopd1 = actual.PeçaMeva(move, color) || actual.PeçaRival(move, color);
                }
                move = new ParInt(coord.GetFirst() - j - 1, coord.GetSecond());
                if (!stopd2) {
                    addMove(move, color, actual, moviments);
                    stopd2 = actual.PeçaMeva(move, color) || actual.PeçaRival(move, color);
                }
            }
            stopd1 = false;
            stopd2 = false;
            for (int j = 0; j < vm[i].getV(); j++) {
                move = new ParInt(coord.GetFirst(), coord.GetSecond() + j + 1);
                if (!stopd1) {
                    addMove(move, color, actual, moviments);
                    stopd1 = actual.PeçaMeva(move, color) || actual.PeçaRival(move, color);
                }
                move = new ParInt(coord.GetFirst(), coord.GetSecond() - j - 1);
                if (!stopd2) {
                    addMove(move, color, actual, moviments);
                    stopd2 = actual.PeçaMeva(move, color) || actual.PeçaRival(move, color);
                }
            }
        }
        return moviments;
    }

    public void addMove (ParInt move,Color white, Tauler actual, Vector<ParInt> moviments){
        if (safe(move) && !actual.PeçaMeva(move,white) ) {
            moviments.add(move);
        }
    }

    private Boolean safe(ParInt x) {
        return (x.GetFirst() >= 0 && x.GetFirst() < 8 && x.GetSecond() >= 0 && x.GetSecond() < 8 );
    }
}
