package Domain;

import java.util.Dictionary;
import java.util.Enumeration;

public class NumMaxPeces {
    private static Dictionary<String, Integer> ourInstance = new Dictionary<String, Integer>() {
        @Override
        public int size() {
            return 6;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Enumeration<String> keys() {
            return ourInstance.keys();
        }

        @Override
        public Enumeration<Integer> elements() {
            return ourInstance.elements();
        }

        @Override
        public Integer get(Object key) {
            return ourInstance.get(key);
        }

        @Override
        public Integer put(String key, Integer value) {
            try {
                throw new IllegalAccessException("Diccionari de només lectura");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public Integer remove(Object key) {
            try {
                throw new IllegalAccessException("Diccionari de només lectura");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
        {
            put("Peo", 8);
            put("Cavall", 2);
            put("Alfil", 2);
            put("Torre", 2);
            put("Dama", 1);
            put("Rei", 1);

        }
    };

    public static Dictionary<String, Integer> getInstance() {
        return ourInstance;
    }

    private NumMaxPeces() {

    }
}
