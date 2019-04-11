package Domain;

import java.util.*;

public class Possibilitats {
    private Vector <ParInt> moviments = new Vector<>();
    private Tauler a;

    public Possibilitats (){}

    public Possibilitats (Tauler tauler){
        a = tauler;
    }

    public void validMoves(FitxaProblema x, Tauler actual, Color white) {
        a = actual;
        VectMov[] vm = x.getIFitxa().GetMoviments();

        ParInt ini = x.GetCoordenades();
        ParInt move;
        boolean stopd1,stopd2,stopd3,stopd4;

        for (int i = 0; i < vm.length; i++){
            stopd1 = false;
            stopd2 = false;
            stopd3 = false;
            stopd4 = false;

            if (x.getIFitxa().getClass().getName() == "Domain.Torre" || x.getIFitxa().getClass().getName() == "Domain.Dama" ||
                    x.getIFitxa().getClass().getName() == "Domain.Rei" || x.getIFitxa().getClass().getName() == "Domain.Alfil") {
                for (int j = 0; j < vm[i].getD(); j++){
                    move = new ParInt(ini.GetFirst()+j+1,ini.GetSecond()+j+1);
                    if (!stopd1){
                        addMove(move,white);
                        stopd1 = a.PeçaMeva(move,white) || a.PeçaRival(move,white);
                    }
                    move = new ParInt(ini.GetFirst()-j-1,ini.GetSecond()+j+1);
                    if (!stopd2){
                        addMove(move,white);
                        stopd2 = a.PeçaMeva(move,white) || a.PeçaRival(move,white);
                    }
                    move = new ParInt(ini.GetFirst()+j+1,ini.GetSecond()-j-1);
                    if (!stopd3){
                        addMove(move,white);
                        stopd3 = a.PeçaMeva(move,white) || a.PeçaRival(move,white);
                    }
                    move = new ParInt(ini.GetFirst()-j-1,ini.GetSecond()-j-1);
                    if (!stopd4){
                        addMove(move,white);
                        stopd4 = a.PeçaMeva(move,white) || a.PeçaRival(move,white);
                    }
                }
                stopd1 = false;
                stopd2 = false;
                for (int j = 0; j < vm[i].getH(); j++){
                    move = new ParInt(ini.GetFirst()+j+1,ini.GetSecond());
                    if (!stopd1){
                        addMove(move,white);
                        stopd1 = a.PeçaMeva(move,white) || a.PeçaRival(move,white);
                    }
                    move = new ParInt(ini.GetFirst()-j-1,ini.GetSecond());
                    if (!stopd2){
                        addMove(move,white);
                        stopd2 = a.PeçaMeva(move,white) || a.PeçaRival(move,white);
                    }
                }
                stopd1 = false;
                stopd2 = false;
                for (int j = 0; j < vm[i].getV(); j++){
                    move = new ParInt(ini.GetFirst(),ini.GetSecond()+j+1);
                    if (!stopd1){
                        addMove(move,white);
                        stopd1 = a.PeçaMeva(move,white) || a.PeçaRival(move,white);
                    }
                    move = new ParInt(ini.GetFirst(),ini.GetSecond()-j-1);
                    if (!stopd2){
                        addMove(move,white);
                        stopd2 = a.PeçaMeva(move,white) || a.PeçaRival(move,white);
                    }
                }

            }
            if (x.getIFitxa().getClass().getName() == "Domain.Peo"){
                move = new ParInt(ini.GetFirst(),ini.GetSecond()+1);
                addMove(move,white);
                move = new ParInt(ini.GetFirst()+1,ini.GetSecond()+1);
                if (a.PeçaRival(move,white)) addMove(move,white);

            }
            if (x.getIFitxa().getClass().getName() == "Domain.Cavall"){
                int v = vm[i].getV();
                int h = vm[i].getH();

                move = new ParInt(ini.GetFirst()+v, ini.GetSecond()+h);
                addMove(move,white);
                move = new ParInt(ini.GetFirst()+v, ini.GetSecond()-h);
                addMove(move,white);
                move = new ParInt(ini.GetFirst()-v, ini.GetSecond()+h);
                addMove(move,white);
                move = new ParInt(ini.GetFirst()-v, ini.GetSecond()-h);
                addMove(move,white);

            }
        }

    }

    public void addMove (ParInt move,Color white){
        if (safe(move) && !a.PeçaMeva(move,white) ) {
            moviments.add(move);
        }
    }

    public Boolean safe(ParInt x) {
        return (x.GetFirst() >= 0 && x.GetFirst() < 8 && x.GetSecond() >= 0 && x.GetSecond() < 8 );
    }

    public Vector<ParInt> getMoviments () {return moviments;}
}
