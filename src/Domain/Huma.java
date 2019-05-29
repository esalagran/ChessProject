package Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Huma extends Usuari{
    private boolean loggedIn;
    private String password;

    private List<Problema> problemesCreats = new ArrayList<>();
    private int[] puntuació;

    public Huma(String nickname) {
        super(nickname);
    }


    public boolean IsLoggedIn(){
        return loggedIn;
    }

    public void LogIn(){
        loggedIn = true;
    }

    public void LogOut(){
        loggedIn = false;
    }

    public void AfegirProblema(Problema p){
        problemesCreats.add(p);
    }


    public Object[] MourePeça(Problema problemaEnJoc, ParInt origen, ParInt desti, Color torn){
        Tauler tauler = problemaEnJoc.getTauler();
        Object[] obj = new Object[2];
        obj[0] = null;
        obj[1] = null;

        if (!Convert.InTheLimits(origen)) return obj;
        if (tauler.FitxaAt(origen) == null) return obj;
        if(!torn.equals(tauler.FitxaAt(origen).GetColor())) return obj;
        if (!Convert.InTheLimits(desti)) return obj;
        if (tauler.FitxaAt(desti) != null && tauler.FitxaAt(desti).GetColor() == torn) return obj;

        //long startTime = System.currentTimeMillis();
        boolean possible = false;
        Vector<ParInt> movimentsPossibles = tauler.FitxaAt(origen).GetMoviments(tauler);
        for (ParInt move : movimentsPossibles) {
            Move m = new Move(tauler.FitxaAt(move), origen, move);
            if (move.GetFirst() == desti.GetFirst() && move.GetSecond() == desti.GetSecond()){
                tauler.moureFitxa(m);
                boolean isAttacked = tauler.IsChecked(torn);
                if (isAttacked){
                    tauler.desferJugada(m);
                    System.out.println("Moviment il·legal");
                    return obj;
                }
                possible = true;
                break;
            }
        }
        if(!possible){
            System.out.println("Moviment no possible");
            return obj;
        }
        long endTime = System.currentTimeMillis();
        //accumTime = accumTime + (int) (endTime-startTime)/1000;
        //System.out.println("He trigat " + accumTime);
        obj[0] = new ParInt[]{origen, desti};
        obj[1] = tauler.getTaulell();
        //ultimMovimentHum = new ParInt[]{origen, desti};
        return obj;
    }






}