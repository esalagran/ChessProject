package Persistence;
import Domain.Dificultat;
import Domain.FitxaProblema;
import Domain.Problema;
import Domain.TipusPeça;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class CtrlPersistence {

    public Domain.Huma[] GetUsuaris(){
        return null;
    }


    public List<Problema> GetProblemes(){
        return  carregarProblemes();
    }
    List<Problema> problemes = new ArrayList<>();


    public void guardarProblema(String FEN, Boolean valid){
    try{
    boolean repetit = false;
    for (Problema p: problemes
         ) {

        if (p.GetFEN().equals(FEN)){
             repetit = true;
             break;
    }

    }
    if(!repetit){
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("localData/problemes.txt"),true));
        writer.append('\n');
        writer.append("FEN: " + FEN + " v: " + valid);
        writer.close();}

    else System.out.println("No s'ha pogut guardar el problema ja que el problema està repetit");

    }catch(Exception e){
        System.out.println( "ERROR: " + e);
        }}


    private List<Problema> carregarProblemes(){
    try{

        File file = new File("localData/problemes.txt");
        Scanner sc = new Scanner(file);
        String FEN = "";
        Domain.Dificultat dif = Dificultat.facil;
        int n = 0, uid = 0;
        int id = 0;
        boolean valid = false;
        boolean tornBlanc = true;
        String torn;

        while (sc.hasNext()){
             String s = sc.next();
             //System.out.println(s);
             if(s.equals("FEN:")){

                 FEN = sc.next();
                 torn = sc.next();

                 if(torn.equals("w"))
                     tornBlanc = true;
                 else tornBlanc = false;

                 FEN +=   " " + torn + " " + sc.next()+ " " + sc.next()+" " + sc.next() + " " + sc.next();

                //System.out.println(FEN);
             }

             /*
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
             */

             if(s.equals("v:")){
                 valid = sc.nextBoolean();
                 problemes.add(new Problema(FEN, valid, tornBlanc));

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

