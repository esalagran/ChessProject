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


public void guardarProblema(String FEN, Dificultat dif, int n, int uId){
try{
    boolean repetit = false;
    for (Problema p: problemes
         ) { if (p.GetFEN().equals(FEN)){
             repetit = true;
    }



    }
if(!repetit){
    BufferedWriter writer = new BufferedWriter(new FileWriter(new File("localData/problemes.txt"),true));
    writer.append('\n');
    writer.append("FEN: " + FEN + " dif: " + dif + " n: " + n + " uid: " + uId);
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

        while (sc.hasNext()){
             String s = sc.next();
             //System.out.println(s);
             if(s.equals("FEN:")){
                 FEN = sc.next() + " " + sc.next() + " " + sc.next()+ " " + sc.next()+" " + sc.next();
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


    public String TaulerToFEN(FitxaProblema[][] tauler ){
        String FEN ="";
        int spaces = 0;
        for(int i = 0; i< 8; i++){
            for (int j = 0; j<8; j++){
                if(tauler[i][j] != null){

                    if(spaces != 0){
                        FEN+=spaces;
                        spaces = 0;
                    }
                    if(tauler[i][j].GetTipus() == TipusPeça.Alfil){
                        if(tauler[i][j].GetColor()){
                            FEN+="b";
                        }
                        else FEN+="B";


                    }
                    if(tauler[i][j].GetTipus() == TipusPeça.Torre){
                        if(tauler[i][j].GetColor()){
                            FEN+="r";
                        }
                        else FEN+="R";

                    }
                    if(tauler[i][j].GetTipus() == TipusPeça.Peo){
                        if(tauler[i][j].GetColor()){
                            FEN+="p";
                        }
                        else FEN+="P";


                    }
                    if(tauler[i][j].GetTipus() == TipusPeça.Dama){
                        if(tauler[i][j].GetColor()){
                            FEN+="q";
                        }
                        else FEN+="Q";


                    }
                    if(tauler[i][j].GetTipus() == TipusPeça.Rei){
                        if(tauler[i][j].GetColor()){
                            FEN+="k";
                        }
                        else FEN+="K";


                    }
                    if(tauler[i][j].GetTipus() == TipusPeça.Cavall){
                        if(tauler[i][j].GetColor()){
                            FEN+="n";
                        }
                        else FEN+="N";


                    }

                }
                else{
                    spaces++;
                }

            }
            if(spaces != 0){
                FEN+=spaces;
                spaces = 0;
            }
            if(i!=7)
                FEN+="/";
        }

        FEN+="w - - 0 1";
        System.out.println(FEN);
        return  FEN;

    }

    }

