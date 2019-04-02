package Presentation;

import Domain.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;


public class CtrlPresentation {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";


    private Domain.CtrlDomain CD = new Domain.CtrlDomain(new Huma(), this);
    private  Scanner scanner = new Scanner(System.in);

    public void dibuixaTaulell(FitxaProblema[][] taulell){

        String formatB =  "| " + ANSI_BLUE + "%c " + ANSI_RESET ;
        String formatW =  "| " + ANSI_RED + "%c " + ANSI_RESET ;
        System.out.println("+---+---+---+---+---+---+---+---+");
        for(int i = 0; i < 8; i++){
            for (int j = 0; j<8; j++){
                if(taulell[i][j] != null){
                    TipusPeça tP = taulell[i][j].GetTipus();
                    boolean negre = taulell[i][j].GetColor();

                    if(tP == TipusPeça.Cavall){

                        if(negre)
                            System.out.printf(formatB,  'C' );
                        else System.out.printf(formatW, 'C');
                    }
                    if(tP == TipusPeça.Peo){
                        if(negre)
                            System.out.printf(formatB, 'P');
                        else System.out.printf(formatW, 'P');
                    }
                    if(tP == TipusPeça.Alfil){
                        if(negre)
                            System.out.printf(formatB, 'A');
                        else System.out.printf(formatW, 'A');

                    }

                    if(tP == TipusPeça.Torre){
                        if(negre)
                            System.out.printf(formatB, 'T');
                        else System.out.printf(formatW,'T');
                    }

                    if(tP == TipusPeça.Rei){

                        if(negre)
                            System.out.printf(formatB, 'R');
                        else System.out.printf(formatW, 'R');

                    }

                    if(tP == TipusPeça.Dama){

                        if(negre)
                            System.out.printf(formatB, 'D');
                        else System.out.printf(formatW, 'D');
                    }

                }
                else {
                    System.out.print("|   ");
                }
            }
            System.out.print('|');
            System.out.println();
            System.out.println("+---+---+---+---+---+---+---+---+");

        }

    }

    public void AskCredentials(){

        String usr;
        String pass;

        System.out.println("Usuari: ");
        usr = scanner.next();
        System.out.println("Contrasenya: ");
        pass = scanner.next();


        //Si usuari existeix i contrasenya correcte
        if(true) {
            System.out.println("CORRECTE");
        }


    }

    public void Start(){
        String response = "0";

        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >5 ) {
            System.out.print(
                            "1. Jugar partida" + '\n' +
                            "2. Carregar problema"  + '\n' +
                            "3. Crear problema "  + '\n' +
                            "4. Veure ranking "  + '\n' +
                            "5. Sortir "  + '\n'
            );


            response  = scanner.next();

            switch(response){
                case "1":
                    DemanarInfoPartida();
                    break;
                case "2":
                    CarregarProblema();
                    break;

                case "3":
                    //CREAR PROBLEMA
                    break;
                case "4":
                    //VEURE PROBLEMA
                    break;
                case "5":
                    System.exit(0);

                default:
                    System.out.println("Siusplau, tria una opció vàlida");
                    response = "0";


            }
        }

    }
    private  void CarregarProblema(){

        boolean done = false;
        String response = "0";
        List<Problema> problemes =  CD.GetProblemes();
        int pagina = 0;

        while(!done) {

            printProblems(pagina, problemes);

            response  = scanner.next();

            switch(response){

                case "a":
                    pagina--;
                    break;

                case "d":
                    pagina++;
                    break;




                default:
                    if(Integer.parseInt(response) >= 0 && Integer.parseInt(response) <10 ){
                        dibuixaTaulell(problemes.get(Integer.parseInt(response)+ pagina*9).FENtoTauler());
                        done = true;
                    }
                    else {
                        System.out.println("Siusplau, tria una opció vàlida");
                    }

            }
        }





        //
    }
    private void printProblems(int pagina, List<Problema> problemes){
        int count = 0;

        System.out.println("PAGINA " + (pagina+1));
        for (int i = 0 + pagina*9 ; i < 10+pagina*9 && i < problemes.size() && i>=0 ; i++) {

            System.out.println(count +" FEN: " + problemes.get(i).GetFEN());
            count ++;

        }

        System.out.println("Fes servir les tecles \"A\" i \"D\" per passar pagina");
        System.out.println("Escriu el numero del problema que vulguis carregar");

    }

    private void DemanarInfoPartida(){
        String response = "0";
        Modalitat mod = Modalitat.HH;
        Dificultat dif = Dificultat.facil;
        Tema tema;
        Color c = Color.blanc;
        int n = 1;
        boolean isRandom = false;
        List<Problema> problemes =  CD.GetProblemes();

        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >4 ) {
            System.out.print("Tria la modalitat de la partida: " + '\n' +
                    "1. Humà vs Humà" + '\n' +
                    "2. Humà vs Màquina" + '\n' +
                    "3. Màquina vs Humà" + '\n' +
                    "4. Màquina vs Màquina" + '\n');


            response = scanner.next();


            switch (response) {
                case "1":
                    mod = Modalitat.HH;
                    break;
                case "2":
                    mod = Modalitat.HM;
                    break;
                case "3":
                    mod = Modalitat.MH;
                    break;
                case "4":
                    mod = Modalitat.MM;
                    break;

                default:
                    System.out.println("Siusplau, tria una opció vàlida");
                    response = "0";

            }

        }
        response = "0";

        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >2 ) {
            System.out.print("Vols un problema aleatori? " + '\n' +
                    "1. Si" + '\n' +
                    "2. No" + '\n');



            response = scanner.next();


            switch (response) {
                case "1":
                    isRandom = true;
                    int randIndex = (int)(Math.random() * problemes.size());
                    dibuixaTaulell(problemes.get(randIndex).FENtoTauler());


                    break;
                case "2":

                    break;

                default:
                    System.out.println("Siusplau, tria una opció vàlida");
                    response = "0";

            }



        }
        response = "0";

        while(!isRandom && Integer.parseInt(response) < 1 || Integer.parseInt(response) >3 ) {
            System.out.print("Tria la dificultat del problema: " + '\n' +
                    "1. Fàcil" + '\n' +
                    "2. Mitjà" + '\n' +
                    "3. Difícil" + '\n');

            response = scanner.next();

            switch (response) {
                case "1":
                    dif = Dificultat.facil;
                    break;
                case "2":
                    dif = Dificultat.mitja;
                    break;
                case "3":
                    dif = Dificultat.dificil;
                    break;

                default:
                    System.out.println("Siusplau, tria una opció vàlida");
                    response = "0";

            }
        }
        response = "0";


        while(!isRandom && Integer.parseInt(response) < 1 || Integer.parseInt(response) >2 ) {
            System.out.print("Quin color vols que ataqui? " + '\n' +
                    "1. Blanc" + '\n' +
                    "2. Negre" + '\n');

            response = scanner.next();

            switch (response) {
                case "1":
                    c = Color.blanc;
                    break;
                case "2":
                    c = Color.negre;
                    break;
                default:
                    System.out.println("Siusplau, tria una opció vàlida");
                    response = "0";
            }
        }
        response = "0";

        while(!isRandom && Integer.parseInt(response) < 1 ) {
            System.out.print("Mat en quants moviments " + '\n');
            response = scanner.next();
            try{

                if( Integer.parseInt(response) >= 1)
                    n = Integer.parseInt(response);
                tema = new Tema(n, c);

            } catch (Exception e) {
                System.out.println("Siusplau, tria una opció vàlida");
                response ="0";
            }
        }

/*
        System.out.print("L'usuari ha triat una partida amb modalitat: " + mod + '\n' +
                "Ha triat un problema amb la dificultat: " + dif + '\n' + "Amb el color " + c + " atacant i amb mat en " + n + " jugades");
*/




    }

    public void PGNtoFEN(){
        try {
            File file = new File("localData/PGN.txt");
            Scanner sc = new Scanner(file);
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("localData/FENfromPGN.txt"), true));
            int c = 0;

            while (sc.hasNext()){
                c++;
                String word = sc.next();
                //System.out.println(word);

                if(word.equals("[FEN")) {
                    String FEN = sc.next();
                    FEN = FEN.replace("\"", "");
                    FEN = FEN + " w - - 0 1";

                    try {

                        writer.append('\n');
                        writer.append("FEN: " + FEN + " dif: " + "facil" + " n: " + 2 + " uid: " + 0);


                    }catch (Exception e){
                        System.out.println("ERRPR "  + e);
                    }
                }
                if(!sc.hasNext())
                    System.out.println("ENDED : " + word);
            }

            writer.close();

        }catch (Exception e){
            System.out.println("ERRPR "  + e);
        }
    }


}