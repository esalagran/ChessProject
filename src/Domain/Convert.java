package Domain;

import static Domain.Color.blanc;
import static Domain.Color.negre;

public class Convert {

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

    public static boolean InTheLimits(ParInt coord){
        return (coord.GetFirst() >= 0 && coord.GetFirst() < 8 && coord.GetSecond() >= 0 && coord.GetSecond() < 8 );
    }

    public static Color InvertColor (Color color){
        if (color == negre) return blanc;
        return negre;
    }
}
