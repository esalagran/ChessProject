package Persistence;
import Domain.Dificultat;
import Domain.Problema;

import java.io.File;
import java.util.*;

public class CtrlPersistence {

    public Domain.Huma[] GetUsuaris(){
        return null;
    }


    public List<Problema> GetProblemes(){
        return  carregarProblemes();
    }
    List<Problema> problemes = new ArrayList<>();





    private List<Problema> carregarProblemes(){
try{

        File file = new File("localData/problemes.txt");
        Scanner sc = new Scanner(file);
        String FEN = "";
        Domain.Dificultat dif = Dificultat.facil;
        int n = 0, uid = 0;
        int id = 0;

        while (sc.hasNext()){
             String s = sc.next();
             //System.out.println(s);
             if(s.equals("FEN:")){
                 FEN = sc.next() + sc.next() + sc.next() + sc.next() + sc.next() + sc.next();
                //System.out.println(FEN);
             }

             if(s.equals("dif:")){
                 String difS = sc.next();
                 if(difS.equals("facil"))
                     dif = Dificultat.facil;
                 else if (difS.equals("mitja"))
                    dif = Dificultat.mitja;
                 else if (difS.equals("dificil"))
                     dif = Dificultat.dificil;
             }

             if( s.equals("n:")) {
                 n = Integer.valueOf(sc.next());

             }

             if(s.equals("uid:")){
                 uid = sc.nextInt();
                 problemes.add(new Problema(id, FEN, dif, new Domain.Huma()));
                // System.out.println("El FEN es: " + FEN + ", la dificultat es: " + dif.toString() + " el mat es en " + n + " jugades i el creador te l'id: " + uid );
                 id++;

             }

        }
        return problemes;
    }
    catch(Exception e) {
        System.out.println("ERROOOOR");
        return null;

    }
}

    }

