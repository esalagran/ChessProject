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
    public static TipusPeça ClassToTipusPeça(String classe){
        if (classe.contains("Rei")) return TipusPeça.Rei;
        if (classe.contains("Dama")) return TipusPeça.Dama;
        if (classe.contains("Torre")) return TipusPeça.Torre;
        if (classe.contains("Alfil")) return TipusPeça.Alfil;
        if (classe.contains("Cavall")) return TipusPeça.Cavall;
        if (classe.contains("Peo")) return TipusPeça.Peo;
        System.out.println("Conversio peça erronia");
        return null;
    }

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
     * \pre: t conté el tauler que es vol dibuixar i no és null
     * \post: es dibuixa per consola el tauler t
     */
    public static void DibuixaTauler(Tauler t){
        String formatB = ANSI_BLACK +  "| " + ANSI_BLUE + "%c " + ANSI_RESET ;
        String formatW = ANSI_BLACK + "| " + ANSI_RED + "%c " + ANSI_RESET ;
        System.out.println();
        System.out.println(ANSI_BLACK + "  +---+---+---+---+---+---+---+---+" + ANSI_RESET);
        for(int i = 0; i < 8; i++){
            System.out.print(ANSI_BLACK);
            System.out.print(8-i);
            System.out.print(" " + ANSI_RESET);

            for (int j = 0; j<8; j++){
                ParInt coord = new ParInt(i, j);
                if(t.FitxaAt(coord) != null){
                    TipusPeça tP = Convert.ClassToTipusPeça(t.FitxaAt(coord).getIFitxa().getClass().toString());
                    Color c = t.FitxaAt(coord).GetColor();

                    if(tP == TipusPeça.Cavall){

                        if(c == Color.negre)
                            System.out.printf(formatB,  'C' );
                        else System.out.printf(formatW, 'C');
                    }
                    if(tP == TipusPeça.Peo){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'P');
                        else System.out.printf(formatW, 'P');
                    }
                    if(tP == TipusPeça.Alfil){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'A');
                        else System.out.printf(formatW, 'A');

                    }

                    if(tP == TipusPeça.Torre){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'T');
                        else System.out.printf(formatW,'T');
                    }

                    if(tP == TipusPeça.Rei){

                        if(c == Color.negre)
                            System.out.printf(formatB, 'R');
                        else System.out.printf(formatW, 'R');

                    }

                    if(tP == TipusPeça.Dama){

                        if(c == Color.negre)
                            System.out.printf(formatB, 'D');
                        else System.out.printf(formatW, 'D');
                    }

                }
                else {
                    System.out.print("|   ");
                }
            }
            System.out.print(ANSI_BLACK + '|');
            System.out.println();
            System.out.println("  +---+---+---+---+---+---+---+---+" + ANSI_RESET);
        }
        System.out.println(ANSI_BLACK + "    A   B   C   D   E   F   G   H" + ANSI_RESET );
    }

}
