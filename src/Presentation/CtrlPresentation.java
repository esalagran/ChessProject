package Presentation;

import Domain.*;


import java.io.*;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;


public class CtrlPresentation {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";


    private Domain.CtrlDomain CD = new Domain.CtrlDomain("", this);
    private  Scanner scanner = new Scanner(System.in);

    public void dibuixaTauler(FitxaProblema[][] tauler){

        String formatB =  "| " + ANSI_BLUE + "%c " + ANSI_RESET ;
        String formatW =  "| " + ANSI_RED + "%c " + ANSI_RESET ;
        System.out.println("  +---+---+---+---+---+---+---+---+");
        for(int i = 0; i < 8; i++){
            System.out.print(8-i);
            System.out.print(" ");

            for (int j = 0; j<8; j++){
                if(tauler[i][j] != null){
                    TipusPeça tP = tauler[i][j].GetTipus();
                    boolean negre = tauler[i][j].GetColor();

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
            System.out.println("  +---+---+---+---+---+---+---+---+");


        }

        System.out.println("    A   B   C   D   E   F   G   H" );

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
                    JugarPartida();
                    break;
                case "2":
                    CarregarProblema();
                    break;

                case "3":
                    CrearProblema();
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

    private void CrearProblema(){
        String response = "0";
        FitxaProblema[][] tauler = new FitxaProblema[8][8];

        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >2){

            System.out.print("Com vols crear el problema: " + '\n' +
                    "1. Des de zero" + '\n' +
                    "2. A partir d'un FEN" + '\n');


            response = scanner.next();

            switch (response) {
                case "1":
                    ModificaTauler(tauler);

                    break;
                case "2":
                    System.out.print("Introdueix un FEN valid: ");
                    String FEN = scanner.next();
                    Problema p = new Problema(0,FEN, Dificultat.facil, new  Huma() );
                    ModificaTauler(p.FENtoTauler());

                    break;

                case "exit":
                    Start();
                    break;
                default:
                    System.out.println("Siusplau, tria una opció vàlida");
                    response = "0";

            }




        }

    }

    private void ResetInput(){
        try {


        //System.in.readAllBytes();
        }catch (Exception e){
            System.out.println(e);
        }

    }

    private void ModificaTauler(FitxaProblema[][] tauler){

        dibuixaTauler(tauler);
        ParInt origen = new ParInt(-1,-1);
        ParInt desti = new ParInt(-1,-1);
        boolean correcte = false;
        boolean exit = false;


        System.out.println("Comandes disponibles: \"add\", \"move\", \"delete\", \"save\", \"help\", \"exit\". " + '\n' +
                "Escriu qualsevol comanda per més informació." + '\n'+
                "Si vols tornar a veure les comandes fes servir la comanda \"help\" .");

        while (!exit){

            String response = scanner.next();

            switch (response){
                case "move":
                    correcte = false;

                    while(!correcte) {
                        System.out.print("Especifica la posició de la peça que vols moure:");
                        response = scanner.next();
                        origen = StringToCoordenada(response);

                        if ( origen.GetFirst() != -1 && origen.GetSecond() != -1) {
                            if (tauler[origen.GetFirst()][origen.GetSecond()] != null) {

                                correcte = true;

                            } else{
                                System.out.println("La posició d'origen està buida");
                                ResetInput();

                            }


                        } else{
                            System.out.println("Coordenada no valida");
                            ResetInput();

                        }

                    }
                    correcte = false;


                    while(!correcte) {
                        System.out.print("Especifica la posició destí de la peça:");
                        response = scanner.next();
                        desti = StringToCoordenada(response);

                        if (desti.GetFirst() != -1 && desti.GetSecond() != -1) {
                            if (tauler[desti.GetFirst()][desti.GetSecond()] == null) {
                                tauler[desti.GetFirst()][desti.GetSecond()] = tauler[origen.GetFirst()][origen.GetSecond()];
                                tauler[origen.GetFirst()][origen.GetSecond()] = null;
                                dibuixaTauler(tauler);
                                correcte = true;

                            } else{
                                System.out.println("La posició destí està ocupada");
                                ResetInput();

                            }


                        } else{
                            System.out.println("Coordenada no valida");
                            ResetInput();

                        }

                    }
                    break;

                    case "add":

                        correcte = false;
                        boolean negre = false;

                        while(!correcte) {

                            System.out.println("Especifica el color de la peça que vols afegir (\"w\" o \"b\")");
                            String color = scanner.next();


                            if (color.equals("w") || color.equals("W")){
                                negre = false;
                                correcte = true;
                            }
                            else if (color.equals("b") || color.equals("B")){
                                negre = true;
                                correcte = true;
                            }
                            else {
                                System.out.println("Color no valid, escriu \"w\" (blanc) o \"b\" (negre)");
                                ResetInput();


                            }
                        }

                        correcte = false;

                        TipusPeça tp = TipusPeça.Peo;


                        while (!correcte) {

                            System.out.println("Especifica la lletra de la peça que vols afegir");
                            System.out.println("Peo: p" + '\n' +
                                    "Alfil: a" + '\n' +
                                    "Cavall: c" + '\n' +
                                    "Torre: t" + '\n' +
                                    "Dama: d" + '\n' +
                                    "Rei: r" + '\n' );

                            String peça = scanner.next();

                            if (peça.equals("p")) {
                                tp = TipusPeça.Peo;
                                correcte = true;

                            } else if (peça.equals("c")) {
                                tp = TipusPeça.Cavall;
                                correcte = true;
                            } else if (peça.equals("a")) {
                                tp = TipusPeça.Alfil;
                                correcte = true;

                            } else if (peça.equals("d")) {
                                tp = TipusPeça.Dama;
                                correcte = true;

                            } else if (peça.equals("t")) {
                                tp = TipusPeça.Torre;
                                correcte = true;

                            } else if (peça.equals("r")) {
                                tp = TipusPeça.Rei;
                                correcte = true;

                            } else {
                                System.out.println("Peça no valida, escriu la lletra de la peça que vols afegir");
                                ResetInput();

                            }

                        }



                        correcte = false;

                        while (!correcte) {

                            dibuixaTauler(tauler);
                            System.out.println("Especifica la coordenada on vols afegir la peça");

                            desti = StringToCoordenada(scanner.next());

                            if (desti.GetFirst() != -1 && desti.GetSecond() != -1) {
                                if (tauler[desti.GetFirst()][desti.GetSecond()] == null) {
                                    tauler[desti.GetFirst()][desti.GetSecond()] = new FitxaProblema(tp, desti.GetSecond(), desti.GetFirst(), negre);
                                    dibuixaTauler(tauler);
                                    correcte =true;

                                } else {
                                    System.out.println("La posició destí està ocupada");
                                    ResetInput();

                                }
                            } else{
                                System.out.println("Coordenada no valida");
                                ResetInput();

                            }

                        }

                        break;




                case "delete":

                    correcte = false;

                    while(!correcte) {
                        System.out.print("Especifica la posició de la peça que vols esborrar:");
                        response = scanner.next();
                        origen = StringToCoordenada(response);

                        if ( origen.GetFirst() != -1 && origen.GetSecond() != -1) {
                            if (tauler[origen.GetFirst()][origen.GetSecond()] != null) {

                                tauler[origen.GetFirst()][origen.GetSecond()] = null;
                                dibuixaTauler(tauler);
                                correcte = true;

                            } else{
                                System.out.println("La posició d'origen està buida");
                                ResetInput();

                            }


                        } else{
                            System.out.println("Coordenada no valida");
                            ResetInput();

                        }

                    }
                break;

                case "exit":
                    Start();
                    exit = true;
                    break;

                case "save":

                    System.out.print("Vols validar el problema abans de guardar-lo?" + '\n' +
                            "1.Si" + '\n' +
                            "2.No"+ '\n');
                    response = scanner.next();

                    switch (response){
                        case "1":
                            System.out.println("La funcionalitat de validar el problema encara no està implementada, per tant el problema " +
                                    "es guardarà sense validar.");
                            CD.AfegirProblema(new Problema(TaulerToFEN(tauler), false));
                            Start();
                            break;
                        case "2":
                            CD.AfegirProblema(new Problema(TaulerToFEN(tauler), false));
                            Start();
                            break;
                        default:

                    }

                    break;

                case "play":

                    System.out.println("La funcionalitat jugar encara no està implementada.");
                    break;

                case "help":
                    System.out.println("Comandes disponibles: \"add\", \"move\", \"delete\", \"save\", \"help\", \"exit\". " + '\n' +
                            "Escriu qualsevol comanda per més informació.");

                default:

                    System.out.println("La comanda \"" + response +  "\" no existeix, escriu \"help\" per veure les comandes disponibles.");
                    ResetInput();
                    break;



            }

        }

    }

    private ParInt StringToCoordenada(String str){
        char primer = str.charAt(0);
        char segon = str.charAt(1);

        if((primer >= 'a' && primer <= 'h' ) || primer>='A' && primer<= 'H'){
            char aux = primer;
            primer = segon;
            segon = aux;

        }

        return new ParInt(CharToCoordenadaInt(primer), CharToCoordenadaInt(segon));


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

        FEN+=" w - - 0 1";
        //System.out.println(FEN);
        return  FEN;

    }

    private int CharToCoordenadaInt( char ch ){

        int res = 0;

        if(ch >= 'a' && ch <= 'h'){
            res = ch - 'a' ;
        }

        else if(ch >= 'A' && ch <= 'H'){
            res = ch - 'A' ;
        }

        else if(ch > '0' && ch<= '8')
            res = 8 - Integer.parseInt(String.valueOf(ch));

        else return -1;

        return  res;
    }

    private  void CarregarProblema(){

        boolean done = false;
        String response = "0";
        List<Problema> problemes =  CD.GetProblemes();
        int pagina = 0;
        int aux;

        while(!done) {

            PrintProblemes(pagina, problemes);

            response  = scanner.next();

            switch(response){

                case "exit":
                    Start();
                    break;

                case "a":
                    pagina--;
                    break;

                case "d":
                    pagina++;
                    break;




                default:
                    if(Integer.parseInt(response) >= 0 && Integer.parseInt(response) <10 ){
                        if(pagina<0){
                            aux = problemes.size()/10 - pagina;
                            ModificaTauler(problemes.get(Integer.parseInt(response)+ aux*9).FENtoTauler());
                        }
                        else
                            ModificaTauler(problemes.get(Integer.parseInt(response)+ pagina*9).FENtoTauler());
                        done = true;
                    }
                    else {
                        System.out.println("Siusplau, tria una opció vàlida");
                    }

            }
        }





        //
    }

    private void PrintProblemes(int pagina, List<Problema> problemes){
        int count = 0;

        if(pagina<0)
            pagina = problemes.size()/10 - pagina;

        System.out.println("PAGINA " + (pagina+1));

        if(problemes.size() == 0)
            System.out.println("No hi ha problemes a mostrar");

        for (int i = 0 + pagina*9 ; i < 10+pagina*9 && i < problemes.size() && i>=0 ; i++) {

            System.out.println(count +" FEN: " + problemes.get(i).GetFEN());
            count ++;

        }

        System.out.println("Fes servir les tecles \"A\" i \"D\" per passar pagina");
        System.out.println("Escriu el numero del problema que vulguis carregar");

    }

    private void JugarPartida(){
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
                case "exit":
                    Start();
                    break;
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
                case "exit":
                    Start();
                    break;
                case "1":
                    isRandom = true;
                    int randIndex = (int)(Math.random() * problemes.size());
                    dibuixaTauler(problemes.get(randIndex).FENtoTauler());
                    System.out.println("La funcionalitat jugar encara no està implementada, per ara et dibuixo el taulell.");
                    Start();


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
                case "exit":
                    Start();
                    break;
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
                case "exit":
                    Start();
                    break;
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
        System.out.println("Els filtres encara no estan implementats");
        Start();

/*
        System.out.print("L'usuari ha triat una partida amb modalitat: " + mod + '\n' +
                "Ha triat un problema amb la dificultat: " + dif + '\n' + "Amb el color " + c + " atacant i amb mat en " + n + " jugades");
*/





    }

    public void PGNtoFEN(){
        try {
            File file = new File("localData/PGN.txt");
            //Scanner sc = new Scanner(file);
            BufferedReader sc = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("localData/FENfromPGN.txt"), true));


            String line;
            while ((line = sc.readLine()) != null){

                //System.out.println(word);
                if(line.contains("[FEN")) {
                    String[] splits = line.split("\"");
                    String FEN = splits[1];

                    try {

                        writer.append('\n');
                        //writer.append("FEN: " + FEN + " dif: " + "facil" + " n: " + 2 + " uid: " + 0);
                        writer.append("FEN: " + FEN + " v: " + true);


                    }catch (Exception e){
                        System.out.println("ERRPR "  + e);
                    }
                }
            }

            writer.close();

        }catch (Exception e){
            System.out.println("ERRPR "  + e);
        }
    }


}