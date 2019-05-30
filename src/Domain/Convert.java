package Domain;

import static Domain.Color.blanc;
import static Domain.Color.negre;

/**
 * @Classe: Calaix de sastre de les conversions utilitzades
 */

public class Convert {

    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_BLACK = "\u001B[30m";


    /**
     * \pre: classe conté la classe que es vol convertir a TipusPeça
     * \post: Es retorna quin el TipusPeça de la classe enviada
     * @return Tipus de peça de la classe enviada
     */
    /*public static TipusPeça ClassToTipusPeça(String classe){
        if (classe.contains("Rei")) return TipusPeça.Rei;
        if (classe.contains("Dama")) return TipusPeça.Dama;
        if (classe.contains("Torre")) return TipusPeça.Torre;
        if (classe.contains("Alfil")) return TipusPeça.Alfil;
        if (classe.contains("Cavall")) return TipusPeça.Cavall;
        if (classe.contains("Peo")) return TipusPeça.Peo;
        System.out.println("Conversio peça erronia");
        return null;
    }*/

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
     * \pre: str conté dos characters: el primer indica la columna del tauler(lletra)
     * i el segon la fila del tauler(numero)
     * \post: Es converteix les coordenades de tauler a coordenades de matriu
     * @return les coordenades de tauler derivades de str
     */
    public static ParInt StringToCoordenada(String str){
        char primer = str.charAt(0);
        char segon = str.charAt(1);

        if((primer >= 'a' && primer <= 'h' ) || primer>='A' && primer<= 'H'){
            char aux = primer;
            primer = segon;
            segon = aux;

        }
        return new ParInt(CharToCoordenadaInt(primer), CharToCoordenadaInt(segon));
    }

    /**
     * \pre: ch conté la columna del tauler en coordenades de tauler
     * \post: converteix la variabale ch en un número en coordenades de tauler
     * @return el nombre en coordenades de tauler de la variable ch
     * */
    public static int CharToCoordenadaInt( char ch ){

        int res = 0;

        if(ch >= 'a' && ch <= 'h'){
            res = ch - 'a' ;
        }

        else if(ch >= 'A' && ch <= 'H'){
            res = ch - 'A' ;
        }

        else if(ch > '0' && ch<= '8')
            res = 8 - Integer.parseInt(String.valueOf(ch));

        else return -1;

        return  res;
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


    static int RUN = 32;

    // this function sorts array from left index to
    // to right index which is of size atmost RUN
    public static void insertionSort(int[] arr, int left, int right){
        for (int i = left + 1; i <= right; i++){
            int temp = arr[i];
            int j = i - 1;
            while (arr[j] > temp && j >= left){
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = temp;
        }
    }

    // merge function merges the sorted runs
    public static void merge(int[] arr, int l,int m, int r){
        // original array is broken in two parts
        // left and right array
        int len1 = m - l + 1, len2 = r - m;
        int[] left = new int[len1];
        int[] right = new int[len2];
        for (int x = 0; x < len1; x++){
            left[x] = arr[l + x];
        }
        for (int x = 0; x < len2; x++){
            right[x] = arr[m + 1 + x];
        }

        int i = 0;
        int j = 0;
        int k = l;

        // after comparing, we merge those two array
        // in larger sub array
        while (i < len1 && j < len2){
            if (left[i] <= right[j]){
                arr[k] = left[i];
                i++;
            }
            else{
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        // copy remaining elements of left, if any
        while (i < len1){
            arr[k] = left[i];
            k++;
            i++;
        }

        // copy remaining element of right, if any
        while (j < len2){
            arr[k] = right[j];
            k++;
            j++;
        }
    }

    // iterative Timsort function to sort the
    // array[0...n-1] (similar to merge sort)
    public static void timSort(int[] arr, int n){

        // Sort individual subarrays of size RUN
        for (int i = 0; i < n; i += RUN){
            insertionSort(arr, i, Math.min((i + 31), (n - 1)));
        }

        // start merging from size RUN (or 32). It will merge
        // to form size 64, then 128, 256 and so on ....
        for (int size = RUN; size < n; size = 2 * size){

            // pick starting point of left sub array. We
            // are going to merge arr[left..left+size-1]
            // and arr[left+size, left+2*size-1]
            // After every merge, we increase left by 2*size
            for (int left = 0; left < n; left += 2 * size){
                // find ending point of left sub array
                // mid+1 is starting point of right sub array
                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (n - 1));

                // merge sub array arr[left.....mid] &
                // arr[mid+1....right]
                merge(arr, left, mid, right);
            }
        }
    }

    public static Color StringToColor(String torns) {
        torns = torns.toLowerCase();
        if (torns.charAt(0) == 'b') return blanc;
        return negre;
    }

    public static Dificultat StringToDificultat(String dif){
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
