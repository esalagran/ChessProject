package Domain;

import java.util.*;

public class Possibilitats {
    private Vector <ParInt> moviments = new Vector<>();
    private Tauler a;

    public void validMoves(FitxaProblema x) {
        VectMov[] vm = x.getIFitxa().GetMoviments();

        ParInt ini = x.GetCoordenades();
        ParInt move;
        boolean stopd1,stopd2,stopd3,stopd4;
        boolean outLimits = false;
        for (int i = 0; i < vm.length; i++){
            stopd1 = false;
            stopd2 = false;
            stopd3 = false;
            stopd4 = false;
            if (x.getIFitxa().getClass().getName() == "Torre" || x.getIFitxa().getClass().getName() == "Dama" ||
                    x.getIFitxa().getClass().getName() == "Rei" || x.getIFitxa().getClass().getName() == "Alfil") {
                for (int j = 0; j < vm[i].getD(); j++){
                    move = new ParInt(ini.GetFirst()+j+1,ini.GetSecond()+j+1);
                    if (!stopd1){
                        addMove(move);
                        stopd1 = a.PeçaMeva(move) || a.PeçaRival(move);
                    }
                    move = new ParInt(ini.GetFirst()-j-1,ini.GetSecond()+j+1);
                    if (!stopd2){
                        addMove(move);
                        stopd2 = a.PeçaMeva(move) || a.PeçaRival(move);
                    }
                    move = new ParInt(ini.GetFirst()+j+1,ini.GetSecond()-j-1);
                    if (!stopd3){
                        addMove(move);
                        stopd3 = a.PeçaMeva(move) || a.PeçaRival(move);
                    }
                    move = new ParInt(ini.GetFirst()-j-1,ini.GetSecond()-j-1);
                    if (!stopd4){
                        addMove(move);
                        stopd4 = a.PeçaMeva(move) || a.PeçaRival(move);
                    }
                }
                stopd1 = false;
                stopd2 = false;
                for (int j = 0; j < vm[i].getH(); j++){
                    move = new ParInt(ini.GetFirst()+j+1,ini.GetSecond());
                    if (!stopd1){
                        addMove(move);
                        stopd1 = a.PeçaMeva(move) || a.PeçaRival(move);
                    }
                    move = new ParInt(ini.GetFirst()-j-1,ini.GetSecond());
                    if (!stopd2){
                        addMove(move);
                        stopd2 = a.PeçaMeva(move) || a.PeçaRival(move);
                    }
                }
                stopd1 = false;
                stopd2 = false;
                for (int j = 0; j < vm[i].getV(); j++){
                    move = new ParInt(ini.GetFirst(),ini.GetSecond()+j+1);
                    if (!stopd1){
                        addMove(move);
                        stopd1 = a.PeçaMeva(move) || a.PeçaRival(move);
                    }
                    move = new ParInt(ini.GetFirst(),ini.GetSecond()-j-1);
                    if (!stopd2){
                        addMove(move);
                        stopd2 = a.PeçaMeva(move) || a.PeçaRival(move);
                    }
                }

            }
            if (x.getIFitxa().getClass().getName() == "Peo"){
                move = new ParInt(ini.GetFirst(),ini.GetSecond()+1);
                addMove(move);
                move = new ParInt(ini.GetFirst()+1,ini.GetSecond()+1);
                if (a.PeçaRival(move)) addMove(move);

            }
            if (x.getIFitxa().getClass().getName() == "Cavall"){
                int v = vm[i].getV();
                int h = vm[i].getH();

                move = new ParInt(ini.GetFirst()+v, ini.GetSecond()+h);
                addMove(move);
                move = new ParInt(ini.GetFirst()+v, ini.GetSecond()-h);
                addMove(move);
                move = new ParInt(ini.GetFirst()-v, ini.GetSecond()+h);
                addMove(move);
                move = new ParInt(ini.GetFirst()-v, ini.GetSecond()-h);
                addMove(move);

            }
        }

    }

    public void addMove (ParInt move){
        if (safe(move) && !a.PeçaMeva(move)) {
            moviments.add(move);
        }
    }

    public Boolean safe(ParInt x) {
        return (x.GetFirst() >= 0 && x.GetFirst() < 8 && x.GetSecond() >= 0 && x.GetSecond() < 8 );
    }
}