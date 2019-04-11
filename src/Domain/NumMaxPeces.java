package Domain;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;


//Singleton que serveix per controla el nombre m&agrave;xim de peces en un problema
public class NumMaxPeces {
    private static HashMap<TipusPeça, Integer> ourInstance = new HashMap<TipusPeça, Integer>() {
        {
            put(TipusPeça.Peo, 8);
            put(TipusPeça.Cavall, 2);
            put(TipusPeça.Alfil, 2);
            put(TipusPeça.Torre, 2);
            put(TipusPeça.Dama, 1);
            put(TipusPeça.Rei, 1);
        }
    };

    public static HashMap<TipusPeça, Integer> getInstance() {
        return ourInstance;
    }
}
