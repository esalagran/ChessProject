package Domain;

import static Domain.Color.blanc;
import static Domain.Color.negre;

/**
 * @Classe: Calaix de sastre de les conversions utilitzades
 */

public class Convert {
    /**
     * \pre:
     * \post: Es retorna true si coord està en els límits del tauler, false altrament
     * @return true si coord està als límits, false altrament
     */
    public static boolean InTheLimits(ParInt coord){
        return (coord.GetFirst() >= 0 && coord.GetFirst() < 8 && coord.GetSecond() >= 0 && coord.GetSecond() < 8 );
    }

    /**
     * \pre:
     * \post: Es retorna  blanc si color es negre i negre si color es blanc
     * @return blanc si color es negre i negre si color es blanc
     */

    public static Color InvertColor (Color color){
        if (color == negre) return blanc;
        return negre;
    }


    /**
     * \pre: tipusPeça i color valids
     * \post: retorna el char equivalent a la peça en codi FEN
     * @return el char equivalent a la peça en codi FEN
     */
    public static char ParTipusPeçaBoolToChar(Fitxa f, Color color){
        char result = '0';
            if (f instanceof Peo)
                if (color.equals(Color.blanc)) result = 'P';
                else result = 'p';
            else if (f instanceof Cavall)
                if (color.equals(Color.blanc)) result =  'C';
                else result =  'c';
            else if (f instanceof Rei)
                if (color.equals(Color.blanc)) result =  'R';
                else result =  'r';
            else if (f instanceof Dama)
                if (color.equals(Color.blanc)) result = 'D';
                else result = 'd';
            else if (f instanceof Alfil)
                if (color.equals(Color.blanc)) result =  'A';
                else result = 'a';
            else if (f instanceof Torre)
                if (color.equals(Color.blanc)) result = 'T';
                else result = 't';

        return result;
    }

    public static Color StringToColor(String torns) {
        if (torns == null) return null;
        torns = torns.toLowerCase();
        if (torns.charAt(0) == 'b') return blanc;
        return negre;
    }

    public static Dificultat StringToDificultat(String dif){
        if (dif == null) return null;
        dif = dif.toLowerCase();
        char d = dif.charAt(0);
        if (d == 'f') return Dificultat.facil;
        if (d == 'm') {
            if (dif.contains("f")) return Dificultat.moltfacil;
            if (dif.contains("d")) return Dificultat.moltdificil;
            return Dificultat.mitja;
        }
        return Dificultat.dificil;
    }
}
