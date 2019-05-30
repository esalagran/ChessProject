package Presentation;

import Domain.*;


import java.io.*;
import java.util.*;

/**Mètode no rellevant per aquesta entrega*/

public class CtrlPresentation {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[37m";
    public static final String ANSI_PURPLE = "\u001B[30m";
    public static final String ANSI_BLACK = "\u001B[30m";

    private VistaLogin login = null;
    private MenuPrincipal menu = null;
    private VistaTipusProblema vistaTipus = null;
    private VistaCarregarPartida vistaCarregar = null;
    private TaulerGUI taulerPartida = null;
    private TaulerGUICrearProblema taulerProblema = null;
    private VistaGetFEN vistaGetFEN = null;
    private VistaModalitatProblema vistaModalitatProblema = null;

    private Domain.CtrlDomain CD = new Domain.CtrlDomain();
    private  Scanner scanner = new Scanner(System.in);


    public CtrlPresentation() {
        CD = new CtrlDomain();
        CD.CarregarProblemes();
        if (login == null)  // innecesario
            login= new VistaLogin(this);
    }

    public void initializePresentation() {
        login.hacerVisible();
    }




    //////////////////////// Metodos de sincronizacion entre vistas


    public void sincronizacionVistaMenu_a_Tipus() {
        menu.desactivar();
        menu.visible(false);

        // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
        if (vistaTipus == null)
            vistaTipus = new VistaTipusProblema(this);
        vistaTipus.hacerVisible();
        vistaTipus.activar();
    }

    public void sincronizacionVistaLlista_a_Modalitat() {
        vistaCarregar.desactivar();
        vistaCarregar.visible(false);

        // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
        if (vistaModalitatProblema == null)
            vistaModalitatProblema = new VistaModalitatProblema(this);
        vistaModalitatProblema.hacerVisible();
        vistaModalitatProblema.activar();
    }

    public void setIndexoModalitat(int i){
        vistaModalitatProblema.SetIndex(i);
    }

    public void sincronizacionVistaMenu_a_Carregar() {
        menu.desactivar();
        menu.visible(false);

        // Solo se crea una vista secundaria (podria crearse una nueva cada vez)
        if (vistaCarregar == null)
            vistaCarregar = new VistaCarregarPartida(this);
        vistaCarregar.hacerVisible();
        vistaCarregar.activar();
    }


    public String[] getProblemes(){

        return  CD.GetProblemes();
    }


    public void sincronizacionVistaTipus_a_Menu() {
        // Se hace invisible la vista secundaria (podria anularse)
        vistaTipus.desactivar();
        vistaTipus.visible(false);
        menu.visible(true);
        menu.activar();
    }



    public void sincronizacionLogin_a_Menu() {
        // Se hace invisible la vista secundaria (podria anularse)
        login.desactivar();
        login.visible(false);

        if(menu== null)
            menu = new MenuPrincipal(this );
        menu.activar();
        menu.visible(true);
    }

    public void sincronizacionMenu_a_Login() {
        // Se hace invisible la vista secundaria (podria anularse)
        menu.desactivar();
        menu.visible(false);
        login.activar();
    }

    public void sincronizacionMenu_a_CrearProblema(){
        menu.desactivar();
        menu.visible(false);

        if(taulerProblema==null) {
           CD.CreaProblema();
           taulerProblema = new TaulerGUICrearProblema(this);
        }
        taulerProblema.run();
    }

    public void sincronizacionJugarPartida_a_Menu(){
        taulerPartida.visible(false);
        taulerPartida.desactivar();
        menu.activar();
        menu.visible(true);

    }

    public FitxaProblema[][] ImportarProblema(String FEN){
        return CD.ImportarProblema(FEN);
    }


    public void sincronizacionVistaProblema_a_FEN(){
        if(vistaGetFEN == null){
            vistaGetFEN = new VistaGetFEN(this);
            vistaGetFEN.visible(true);
        }

    }


    public void sincronizacionVistaFEN_a_ProblemaImport( FitxaProblema[][] tauler){
        vistaGetFEN.visible(false);
        taulerProblema.visible(false);
        taulerProblema.desactivar();
        taulerProblema = new TaulerGUICrearProblema(this, tauler);
        taulerProblema.run();

    }

    public void sincronizacionVistaCarregar_a_ProblemaImport( FitxaProblema[][] tauler){
        vistaCarregar.visible(false);
        if(taulerProblema != null){
        taulerProblema.visible(false);
        taulerProblema.desactivar();
        }
        taulerProblema = new TaulerGUICrearProblema(this, tauler);
        taulerProblema.run();

    }


    public void sincronizacionVistaTipus_a_Tauler(String[] paramsPartida){
        vistaTipus.desactivar();
        vistaTipus.visible(false);
        Color torn = null;
        switch (paramsPartida[3]){
            case "Màquina vs màquina":
                CD.JugarPartidesMaquines(paramsPartida[0], paramsPartida[1], Integer.parseInt(paramsPartida[2]),
                        paramsPartida[4],paramsPartida[5], Integer.parseInt(paramsPartida[6]),
                        Integer.parseInt(paramsPartida[7]), Integer.parseInt(paramsPartida[8]));
                break;
            case "Humà vs màquina":
                torn = CD.JugarPartidaHM(paramsPartida[0], paramsPartida[1], Integer.parseInt(paramsPartida[2]),
                        paramsPartida[4], paramsPartida[5], Integer.parseInt(paramsPartida[6]), false);
                break;
            case "Màquina vs humà":
                torn = CD.JugarPartidaHM(paramsPartida[0], paramsPartida[1], Integer.parseInt(paramsPartida[2]),
                        paramsPartida[4], paramsPartida[5], Integer.parseInt(paramsPartida[6]), true);
                break;
            case "Humà vs humà":
                torn = CD.JugarPartidaHH(paramsPartida[0], paramsPartida[1], Integer.parseInt(paramsPartida[2]),
                        paramsPartida[4], paramsPartida[5]);


        }

        if (torn != null){
            Tauler t  = CD.getTaulerPartidaEnJoc();
            taulerPartida = new TaulerGUI(t.getTaulell(), torn,  this);
            taulerPartida.run();
            taulerPartida.visible(true);
            taulerPartida.activar();
        }
    }

    public void sincronizacionVistaModalitat_a_Tauler(Modalitat mod, int  i){
        vistaModalitatProblema.desactivar();
        vistaModalitatProblema.visible(false);
        if(taulerPartida==null){
            Color torn = CD.JugarPartidaHuma(mod, i);
            Tauler t  = CD.getTaulerPartidaEnJoc();
            taulerPartida = new TaulerGUI(t.getTaulell(), torn,  this);}
        taulerPartida.run();
        taulerPartida.visible(true);
        taulerPartida.activar();
    }







//////////////////////// Llamadas al controlador de dominio

    public boolean hasEnded(){
        return CD.hasEnded();
    }

    public String EndedReason(){
        return CD.EndedReason();
    }

    public ParInt[] GetLastMoveMaq(){
        return CD.GetLastMoveMaq();
    }
    public ParInt[] GetLastMoveHum(){
        return CD.GetLastMoveHum();
    }

    public Color GetColor(ParInt coord){
        return CD.GetColor(coord);
    }


    public ArrayList<String> llamadaDominio1 (String selectedItem) {
        return null;
    }

    public ArrayList<String> llamadaDominio2() {
        return null;
    }

    public FitxaProblema[][] MourePeçaPartida(ParInt first, ParInt second){
        return CD.MourePeçaPartida(first, second);
    }

    public FitxaProblema[][] MourePeçaProblema(ParInt first, ParInt second){
        return CD.MoureFitxa(first, second);
    }

    public FitxaProblema[][] AfegirPeçaProblema(int peça, int color, ParInt coord){
        return  CD.AfegirFitxa(peça, color ,coord);
    }

    public FitxaProblema[][] EliminarFitxa(ParInt coord){
       return CD.EliminarFitxa(coord);
    }

    public void GuardarProblema(){
        CD.GuardarProblema();
    }

    public boolean ValidarProblema(int profunditat, boolean torn){
        return CD.ValidarProblema(profunditat, torn);
    }

    public String GetFENProblema(){
        return  CD.GetFEN();
    }

    public boolean isColorHuman(Color color) {
        return CD.isColorHuman(color);
    }

    public FitxaProblema[][] TornMaquina(){
        return CD.TornMaquina();
    }


    public void dibuixaTauler(FitxaProblema[][] tauler){

        String formatB = ANSI_BLACK +  "| " + ANSI_BLUE + "%c " + ANSI_RESET ;
        String formatW = ANSI_BLACK + "| " + ANSI_RED + "%c " + ANSI_RESET ;
        System.out.println();
        System.out.println(ANSI_BLACK + "  +---+---+---+---+---+---+---+---+" + ANSI_RESET);
        for(int i = 0; i < 8; i++){
            System.out.print(ANSI_BLACK);
            System.out.print(8-i);
            System.out.print(" " + ANSI_RESET);

            for (int j = 0; j<8; j++){
                if(tauler[i][j] != null){
                    Fitxa f = tauler[i][j].getIFitxa();
                    Color c = tauler[i][j].GetColor();

                    if(f instanceof Cavall){

                        if(c == Color.negre)
                            System.out.printf(formatB,  'C' );
                        else System.out.printf(formatW, 'C');
                    }
                    if(f instanceof Peo){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'P');
                        else System.out.printf(formatW, 'P');
                    }
                    if(f instanceof Alfil){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'A');
                        else System.out.printf(formatW, 'A');

                    }

                    if(f instanceof Torre){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'T');
                        else System.out.printf(formatW,'T');
                    }

                    if(f instanceof Rei){

                        if(c == Color.negre)
                            System.out.printf(formatB, 'R');
                        else System.out.printf(formatW, 'R');

                    }

                    if(f instanceof Dama){

                        if(c == Color.negre)
                            System.out.printf(formatB, 'D');
                        else System.out.printf(formatW, 'D');
                    }

                }
                else {
                    System.out.print("|   ");
                }
            }
            System.out.print(ANSI_BLACK + '|');
            System.out.println();
            System.out.println("  +---+---+---+---+---+---+---+---+" + ANSI_RESET);


        }

        System.out.println(ANSI_BLACK + "    A   B   C   D   E   F   G   H" + ANSI_RESET );

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
            System.out.print(ANSI_PURPLE + "Menú principal" + '\n' + ANSI_CYAN +
                            "1. Jugar partida" + '\n' +
                            "2. Carregar problema"  + '\n' +
                            "3. Crear problema "  + '\n' +
                            "4. Veure ranking "  + '\n' +
                            "5. Sortir "  + '\n' + ANSI_RESET
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
                    CrearProblema();
                    break;
                case "4":
                    //VEURE RANKING
                    break;
                case "5":
                    System.exit(0);

                default:
                    System.out.println(ANSI_RED + "Siusplau, tria una opció vàlida" + ANSI_RESET);
                    response = "0";


            }
        }

    }

    private void CrearProblema(){
        String response = "0";
        FitxaProblema[][] tauler = new FitxaProblema[8][8];

        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >2){

            System.out.print(ANSI_PURPLE + "Com vols crear el problema: " + '\n' +
             ANSI_CYAN +  "1. Des de zero" + '\n' +
                    "2. A partir d'un FEN" + '\n' + ANSI_RESET);


            response = scanner.next();

            switch (response) {
                case "1":
                    ModificaTauler(tauler);

                    break;
                case "2":
                    System.out.print(ANSI_PURPLE + "Introdueix un FEN valid: " + ANSI_RESET);
                    String FEN = scanner.next();
                    Problema p = new Problema(FEN);
                    ModificaTauler(p.FENtoTauler().getTaulell());

                    break;

                case "exit":
                    Start();
                    break;
                default:
                    System.out.println( ANSI_RED +"Siusplau, tria una opció vàlida" + ANSI_RESET);
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

/*
    private void JugarPartida(int indexP, Modalitat m){
        boolean humaTorn = false;
        FitxaProblema[][] tauler =   CD.CrearPartida(indexP, m).getTaulell();
        Color torn =  CD.GetTornPartidaEnJoc();


        if(m == Modalitat.HM){
            humaTorn = true;

            dibuixaTauler(tauler);

            while(true) {                //No mat && jugades < jugadesPerMat

                if(humaTorn){
                    DemanarMoviment(tauler, torn);
                    humaTorn = false;
                    if(torn == Color.blanc)
                        torn = Color.negre;
                    else torn = Color.blanc;


                }

                else{
                    Maquina ma = new Maquina();
                    ma.GetMoviment(10, torn, new Tauler(tauler));
                    humaTorn = true;
                    if(torn == Color.blanc)
                        torn = Color.negre;
                    else torn = Color.blanc;


                }

            }
        }

        else if(m == Modalitat.MH){

            humaTorn = false;
            dibuixaTauler(tauler);

            while (true) {      //No mat && jugades < jugadesPerMat

                if (humaTorn) {
                    DemanarMoviment(tauler, torn);
                    humaTorn = false;
                    if(torn == Color.blanc)
                        torn = Color.negre;
                    else torn = Color.blanc;

                } else {
                    // CD.DemanarTornMaquina(TaulerToFEN(tauler));
                    humaTorn = true;
                    if(torn == Color.blanc)
                        torn = Color.negre;
                    else torn = Color.blanc;

                }

            }
        }

        else if(m == Modalitat.HH){
            dibuixaTauler(tauler);

            humaTorn = true;

            while (true) {      //No mat && jugades < jugadesPerMat

                if (humaTorn) {

                    if(torn == Color.blanc)
                        System.out.println("Torn de les blanques");
                    else
                        System.out.println("Torn de les negres");

                    DemanarMoviment(tauler, torn);


                    if(torn == Color.blanc)
                        torn = Color.negre;
                    else torn = Color.blanc;



                }

            }

        }




    }*/


    private void DemanarMoviment( FitxaProblema[][] tauler, Color torn){

        ParInt origen = new ParInt(-1,-1);
        ParInt desti = new ParInt(-1,-1);
        boolean correcte = false;
        boolean exit = false;


        String response = scanner.next();


                correcte = false;

                while(!correcte) {
                    System.out.print(ANSI_PURPLE + "Especifica la posició de la peça que vols moure:" + ANSI_RESET);
                    response = scanner.next();
                    origen = StringToCoordenada(response);

                    if ( origen.GetFirst() != -1 && origen.GetSecond() != -1) {
                        if (tauler[origen.GetFirst()][origen.GetSecond()] != null)  {
                            if(tauler[origen.GetFirst()][origen.GetSecond()].GetColor() != torn){

                               correcte = true;

                           }
                            else{

                                System.out.println( ANSI_RED + "Aquesta peça no es teva" + ANSI_RESET);
                                ResetInput();

                            }



                        } else{
                            System.out.println( ANSI_RED + "La posició d'origen està buida" + ANSI_RESET);
                            ResetInput();

                        }


                    } else{
                        System.out.println(ANSI_RED + "Coordenada no valida"+ ANSI_RESET);
                        ResetInput();

                    }

                }
                correcte = false;


                while(!correcte) {
                    System.out.print(ANSI_PURPLE + "Especifica la posició destí de la peça:" + ANSI_RESET);
                    response = scanner.next();
                    desti = StringToCoordenada(response);

                    if (desti.GetFirst() != -1 && desti.GetSecond() != -1) {
                        if (tauler[desti.GetFirst()][desti.GetSecond()] == null) {

                            if(true) { //comprovar que el moviment es valid

                                tauler[desti.GetFirst()][desti.GetSecond()] = tauler[origen.GetFirst()][origen.GetSecond()];
                                tauler[origen.GetFirst()][origen.GetSecond()] = null;
                                dibuixaTauler(tauler);
                                correcte = true;
                            }

                        } else{
                            System.out.println(ANSI_RED + "La posició destí està ocupada" + ANSI_RESET);
                            ResetInput();

                        }


                    } else{
                        System.out.println(ANSI_RED + "Coordenada no valida" + ANSI_RESET);
                        ResetInput();

                    }

                }

        }


    private void ModificaTauler(FitxaProblema[][] tauler){

        dibuixaTauler(tauler);
        ParInt origen = new ParInt(-1,-1);
        ParInt desti = new ParInt(-1,-1);
        boolean correcte = false;
        boolean exit = false;
        Color c = Color.blanc;


        System.out.println(ANSI_YELLOW + "Comandes disponibles: \"add\", \"move\", \"delete\", \"save\", \"help\", \"exit\". " + '\n' +
                "Escriu qualsevol comanda per més informació." + '\n'+
                "Si vols tornar a veure les comandes fes servir la comanda \"help\" ." + ANSI_RESET);

        while (!exit){

            String response = scanner.next();

            switch (response){
                case "move":
                    correcte = false;

                    while(!correcte) {
                        System.out.print(ANSI_PURPLE + "Especifica la posició de la peça que vols moure:" + ANSI_RESET);
                        response = scanner.next();
                        origen = StringToCoordenada(response);

                        if ( origen.GetFirst() != -1 && origen.GetSecond() != -1) {
                            if (tauler[origen.GetFirst()][origen.GetSecond()] != null) {

                                correcte = true;

                            } else{
                                System.out.println( ANSI_RED + "La posició d'origen està buida" + ANSI_RESET);
                                ResetInput();

                            }


                        } else{
                            System.out.println(ANSI_RED + "Coordenada no valida"+ ANSI_RESET);
                            ResetInput();

                        }

                    }
                    correcte = false;


                    while(!correcte) {
                        System.out.print(ANSI_PURPLE + "Especifica la posició destí de la peça:" + ANSI_RESET);
                        response = scanner.next();
                        desti = StringToCoordenada(response);

                        if (desti.GetFirst() != -1 && desti.GetSecond() != -1) {
                            if (tauler[desti.GetFirst()][desti.GetSecond()] == null) {
                                tauler[desti.GetFirst()][desti.GetSecond()] = tauler[origen.GetFirst()][origen.GetSecond()];
                                tauler[origen.GetFirst()][origen.GetSecond()] = null;
                                dibuixaTauler(tauler);
                                correcte = true;

                            } else{
                                System.out.println(ANSI_RED + "La posició destí està ocupada" + ANSI_RESET);
                                ResetInput();

                            }


                        } else{
                            System.out.println(ANSI_RED + "Coordenada no valida" + ANSI_RESET);
                            ResetInput();

                        }

                    }
                    break;

                    case "add":

                        correcte = false;
                        boolean negre = false;

                        while(!correcte) {

                            System.out.println(ANSI_PURPLE + "Especifica el color de la peça que vols afegir (\"w\" o \"b\")" + ANSI_RESET);
                            String color = scanner.next();


                            if (color.equals("w") || color.equals("W")){
                                negre = false;
                                correcte = true;
                                c = Color.blanc;
                            }
                            else if (color.equals("b") || color.equals("B")){
                                negre = true;
                                correcte = true;
                                c = Color.blanc;

                            }
                            else {
                                System.out.println(ANSI_RED + "Color no valid, escriu \"w\" (blanc) o \"b\" (negre)" + ANSI_RESET);
                                ResetInput();


                            }
                        }

                        correcte = false;

                        TipusPeça tp = TipusPeça.Peo;


                        while (!correcte) {

                            System.out.println(ANSI_PURPLE + "Especifica la lletra de la peça que vols afegir");
                            System.out.println(ANSI_CYAN + "Peo: p" + '\n' +
                                    "Alfil: a" + '\n' +
                                    "Cavall: c" + '\n' +
                                    "Torre: t" + '\n' +
                                    "Dama: d" + '\n' +
                                    "Rei: r" + '\n'  + ANSI_RESET);

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
                                System.out.println(ANSI_RED + "Fitxa no valida, escriu la lletra de la peça que vols afegir" + ANSI_RESET);
                                ResetInput();

                            }

                        }



                        correcte = false;

                        while (!correcte) {

                            dibuixaTauler(tauler);
                            System.out.println(ANSI_PURPLE + "Especifica la coordenada on vols afegir la peça"+ ANSI_RESET);

                            desti = StringToCoordenada(scanner.next());

                            if (desti.GetFirst() != -1 && desti.GetSecond() != -1) {
                                if (tauler[desti.GetFirst()][desti.GetSecond()] == null) {
                                    tauler[desti.GetFirst()][desti.GetSecond()] = new FitxaProblema(tp, desti.GetSecond(), desti.GetFirst(), c);
                                    dibuixaTauler(tauler);
                                    correcte =true;

                                } else {
                                    System.out.println(ANSI_RED + "La posició destí està ocupada" + ANSI_RESET);
                                    ResetInput();

                                }
                            } else{
                                System.out.println(ANSI_RED + "Coordenada no valida" + ANSI_RESET);
                                ResetInput();

                            }

                        }

                        break;




                case "delete":

                    correcte = false;

                    while(!correcte) {
                        System.out.print(ANSI_PURPLE + "Especifica la posició de la peça que vols esborrar:" + ANSI_RESET);
                        response = scanner.next();
                        origen = StringToCoordenada(response);

                        if ( origen.GetFirst() != -1 && origen.GetSecond() != -1) {
                            if (tauler[origen.GetFirst()][origen.GetSecond()] != null) {

                                tauler[origen.GetFirst()][origen.GetSecond()] = null;
                                dibuixaTauler(tauler);
                                correcte = true;

                            } else{
                                System.out.println(ANSI_RED + "La posició d'origen està buida" + ANSI_RESET);
                                ResetInput();

                            }


                        } else{
                            System.out.println(ANSI_RED + "Coordenada no valida" + ANSI_RESET);
                            ResetInput();

                        }

                    }
                break;

                case "exit":
                    Start();
                    exit = true;
                    break;

                case "save":

                    System.out.print(ANSI_PURPLE + "Vols validar el problema abans de guardar-lo?" + '\n' +
                            ANSI_CYAN +  "1.Si" + '\n' +
                            "2.No"+ '\n' + ANSI_RESET);
                    response = scanner.next();

                    switch (response){
                        case "1":
                            System.out.println(ANSI_RED + "La funcionalitat de validar el problema encara no està implementada, per tant el problema " +
                                    "es guardarà sense validar." + ANSI_RESET);
                            CD.CarregarProblema(TaulerToFEN(tauler));
                            Start();
                            break;
                        case "2":
                            CD.CreaProblema();
                            Start();
                            break;
                        default:

                    }

                    break;

                case "play":

                    System.out.println(ANSI_RED + "La funcionalitat jugar encara no està implementada." + ANSI_RESET);
                    break;

                case "help":
                    System.out.println(ANSI_YELLOW + "Comandes disponibles: \"add\", \"move\", \"delete\", \"save\", \"help\", \"exit\". " + '\n' +
                            "Escriu qualsevol comanda per més informació." + ANSI_RESET);
                    break;

                default:

                    System.out.println(ANSI_RED +"La comanda \"" + response +  "\" no existeix, escriu \"help\" per veure les comandes disponibles." + ANSI_RESET);
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
        StringBuilder FEN = new StringBuilder();
        int spaces = 0;
        for(int i = 0; i< 8; i++){
            for (int j = 0; j<8; j++){
                if(tauler[i][j] != null){

                    if(spaces != 0){
                        FEN.append(spaces);
                        spaces = 0;
                    }
                    Fitxa f = tauler[i][j].getIFitxa();
                    if(f instanceof Alfil){
                        if(tauler[i][j].GetColor() == Color.negre){
                            FEN.append("b");
                        }
                        else FEN.append("B");


                    }
                    if(f instanceof Torre){
                        if(tauler[i][j].GetColor() == Color.negre){
                            FEN.append("r");
                        }
                        else FEN.append("R");

                    }
                    if(f instanceof Peo){
                        if(tauler[i][j].GetColor() == Color.negre){
                            FEN.append("p");
                        }
                        else FEN.append("P");


                    }
                    if(f instanceof Dama){
                        if(tauler[i][j].GetColor() == Color.negre){
                            FEN.append("q");
                        }
                        else FEN.append("Q");


                    }
                    if(f instanceof Rei){
                        if(tauler[i][j].GetColor() == Color.negre){
                            FEN.append("k");
                        }
                        else FEN.append("K");


                    }
                    if(f instanceof Cavall){
                        if(tauler[i][j].GetColor() == Color.negre){
                            FEN.append("n");
                        }
                        else FEN.append("N");


                    }

                }
                else{
                    spaces++;
                }

            }
            if(spaces != 0){
                FEN.append(spaces);
                spaces = 0;
            }
            if(i!=7)
                FEN.append("/");
        }

        FEN.append(" w - - 0 1");
        //System.out.println(FEN);
        return FEN.toString();

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
        List<Problema> problemes =  null;
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

                    try {
                        if (Integer.parseInt(response) >= 0 && Integer.parseInt(response) < 10) {
                            if (pagina < 0) {
                                aux = problemes.size() / 10 - pagina - 1;
                                ModificaTauler(problemes.get(Integer.parseInt(response) + aux * 10).FENtoTauler().getTaulell());
                            } else
                                ModificaTauler(problemes.get(Integer.parseInt(response) + pagina * 10).FENtoTauler().getTaulell());
                            done = true;
                        } else {
                            System.out.println(ANSI_RED + "Siusplau, tria una opció vàlida" + ANSI_RESET);
                            break;
                        }
                    }catch (Exception e){
                        System.out.println(ANSI_RED + "Comanda no valida" + ANSI_RESET);
                        break;

                    }
            }
        }





        //
    }

    private void PrintProblemes(int pagina, List<Problema> problemes){
        int count = 0;
        int aux;

        if(pagina<0){
            aux = (int)Math.ceil(problemes.size()/10) + pagina + 1;

        System.out.println( ANSI_BLUE + "PAGINA " + (aux+1) + ANSI_RESET);

        if(problemes.size() == 0)
            System.out.println("No hi ha problemes a mostrar");

        for (int i = 0 + aux*10 ; i < 10+aux*10 && i < problemes.size() && i>=0 ; i++) {

            System.out.println(ANSI_RED + count  + ANSI_RESET +" FEN: " + ANSI_BLACK + problemes.get(i).GetFEN() + ANSI_RESET);
            count ++;

        }
        }

        else{
            System.out.println(ANSI_BLUE + "PAGINA " + (pagina+1) + ANSI_RESET);

            if(problemes.size() == 0)
                System.out.println(ANSI_RED + "No hi ha problemes a mostrar" + ANSI_RESET);

            for (int i = 0 + pagina*10  ; i < 10+pagina*10 && i < problemes.size() && i>=0 ; i++) {

                System.out.println(ANSI_RED + count + ANSI_RESET + " FEN: " + ANSI_BLACK + problemes.get(i).GetFEN() + ANSI_RESET);
                count ++;

            }
        }


        System.out.println(ANSI_YELLOW + "Fes servir les tecles \"A\" i \"D\" per passar pagina");
        System.out.println("Escriu el numero del problema que vulguis carregar" + ANSI_RESET);

    }

    private void DemanarInfoPartida(){

        String response = "0";
        Modalitat mod = Modalitat.HH;
        Dificultat dif = Dificultat.facil;
        Tema tema;
        Color c = Color.blanc;
        int n = 1;
        boolean isRandom = false;
        List<Problema> problemes =  null;

        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >4 ) {
            System.out.print(ANSI_PURPLE + "Tria la modalitat de la partida: " + '\n' +
       ANSI_CYAN +  "1. Humà vs Humà" + '\n' +
                    "2. Humà vs Màquina" + '\n' +
                    "3. Màquina vs Humà" + '\n' +
                    "4. Màquina vs Màquina" + '\n' + ANSI_RESET);


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
                    System.out.println(ANSI_RED + "Siusplau, tria una opció vàlida" + ANSI_RESET);
                    response = "0";

            }

        }
        response = "0";

        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >2 ) {
            System.out.print(ANSI_PURPLE + "Vols un problema aleatori? " + '\n' +
                    ANSI_CYAN +   "1. Si" + '\n' +
                    "2. No" + '\n' + ANSI_RESET);



            response = scanner.next();


            switch (response) {
                case "exit":
                    Start();
                    break;
                case "1":
                    isRandom = true;
                    int randIndex = (int)(Math.random() * problemes.size());
                    //JugarPartida(randIndex, mod);

                    break;
                case "2":

                    break;

                default:
                    System.out.println(ANSI_RED + "Siusplau, tria una opció vàlida" + ANSI_RESET);
                    response = "0";

            }



        }
        response = "0";

        while(!isRandom && Integer.parseInt(response) < 1 || Integer.parseInt(response) >3 ) {
            System.out.print(ANSI_PURPLE + "Tria la dificultat del problema: " + '\n' +
       ANSI_CYAN +  "1. Fàcil" + '\n' +
                    "2. Mitjà" + '\n' +
                    "3. Difícil" + '\n' + ANSI_RESET);

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
                    System.out.println(ANSI_RED + "Siusplau, tria una opció vàlida" + ANSI_RESET);
                    response = "0";

            }
        }
        response = "0";


        while(!isRandom && Integer.parseInt(response) < 1 || Integer.parseInt(response) >2 ) {
            System.out.print(ANSI_PURPLE +"Quin color vols que ataqui? " + '\n' +
        ANSI_CYAN + "1. Blanc" + '\n' +
                    "2. Negre" + '\n' + ANSI_RESET);

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
                    System.out.println(ANSI_RED + "Siusplau, tria una opció vàlida" + ANSI_RESET);
                    response = "0";
            }
        }
        response = "0";

        while(!isRandom && Integer.parseInt(response) < 1 ) {
            System.out.print(ANSI_PURPLE + "Mat en quants moviments " + '\n' + ANSI_RESET);
            response = scanner.next();
            try{

                if( Integer.parseInt(response) >= 1)
                    n = Integer.parseInt(response);
                tema = new Tema(n, c);

            } catch (Exception e) {
                System.out.println(ANSI_RED + "Siusplau, tria una opció vàlida" + ANSI_RESET);
                response ="0";
            }
        }
        System.out.println(ANSI_RED + "Els filtres encara no estan implementats" + ANSI_RESET);
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