package Domain;

import java.util.HashMap;

/**
 * Singleton que serveix per controla el nombre màxim de peces en un problema
 */

public class NumMaxPeces {

    private static HashMap<Character, Integer> ourInstance = new HashMap<Character, Integer>() {
        {
            put('p', 8);
            put('c', 2);
            put('a', 2);
            put('t', 2);
            put('d', 1);
            put('r', 1);
        }
    };

    public static HashMap<Character, Integer> getInstance() {
        return ourInstance;
    }

    public static Integer getNumMaxPeces(char c){ return ourInstance.get(c);}
}
