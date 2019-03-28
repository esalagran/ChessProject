package Domain;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;


//Singleton que serveix per controla el nombre m&agrave;xim de peces en un problema
public class NumMaxPeces {
    private static HashMap<String, Integer> ourInstance = new HashMap<String, Integer>() {
        {
            put("Peo", 8);
            put("Cavall", 2);
            put("Alfil", 2);
            put("Torre", 2);
            put("Dama", 1);
            put("Rei", 1);
        }
    };

    public static HashMap<String, Integer> getInstance() {
        return ourInstance;
    }
}
