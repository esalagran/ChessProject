package Domain;

public class Convert {

    public static TipusPeça ClassToTipusPeça(String classe){
        TipusPeça tipusPeça = null;
        switch (classe){
            case "Domain.Cavall":
                tipusPeça = TipusPeça.Cavall;
                break;
            case "Domain.Alfil":
                tipusPeça = TipusPeça.Alfil;
                break;
            case "Domain.Torre":
                tipusPeça = TipusPeça.Torre;
                break;
            case "Domain.Dama":
                tipusPeça = TipusPeça.Dama;
                break;
            case "Domain.Peo":
                tipusPeça = TipusPeça.Peo;
                break;
            case "Domain.Rei":
                tipusPeça = TipusPeça.Rei;
                break;
        }
        return tipusPeça;
    }
}
