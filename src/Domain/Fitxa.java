package Domain;

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
}
