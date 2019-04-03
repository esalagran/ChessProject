package Domain;

import java.util.*;

public class Possibilitats {
    ParInt[] moviments;

    public Vector<ParInt> validMoves(FitxaProblema x) {
        VectMov[] vm = x.getIFitxa().GetMoviments();
        Vector<ParInt> sol = new Vector<ParInt>();

        ParInt ini = x.GetCoordenades();
        ParInt newPos;
        ParInt move;

        for (int i = 0; i < vm.length; i++){
            for (int j = 0; j < vm[i].getH(); j++){
                newPos = new ParInt(ini.GetFirst()+1,ini.GetSecond());
                if (safe(newPos)) sol.add(newPos);
                newPos = new ParInt(ini.GetFirst()-1,ini.GetSecond());
                if (safe(newPos)) sol.add(newPos);
            }
            for (int j = 0; j < vm[i].getV(); j++) {
                newPos = new ParInt(ini.GetFirst(),ini.GetSecond()+1);
                if (safe(newPos)) sol.add(newPos);
                newPos = new ParInt(ini.GetFirst(),ini.GetSecond()-1);
                if (safe(newPos)) sol.add(newPos);
            }
            for (int j = 0; j < vm[i].getD(); j++){
                newPos = new ParInt(ini.GetFirst()+1,ini.GetSecond()+1);
                if (safe(newPos)) sol.add(newPos);
                newPos = new ParInt(ini.GetFirst()-1,ini.GetSecond()+1);
                if (safe(newPos)) sol.add(newPos);
                newPos = new ParInt(ini.GetFirst()+1,ini.GetSecond()-1);
                if (safe(newPos)) sol.add(newPos);
                newPos = new ParInt(ini.GetFirst()-1,ini.GetSecond()-1);
                if (safe(newPos)) sol.add(newPos);
            }
        }
    }

    public Boolean safe(ParInt x) {
        if (x.GetFirst() < 0 || x.GetFirst() > 7 || x.GetSecond() < 0 || x.GetSecond() > 7  ) return false;
        if (hiHaPeça(x)) return false;
    }
}
