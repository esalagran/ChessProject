package Drivers;

import Domain.*;

import java.util.Scanner;

public class DriverProblema {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[37m";
    public static final String ANSI_PURPLE = "\u001B[30m";
    public static final String ANSI_BLACK = "\u001B[30m";

    static Scanner scanner = new Scanner(System.in);
    static Problema p = new Problema("7k/1r4R1/4b2K/7B/8/8/6R1/8 w - - 0 1");



    static void Index(){
        String response = "0";

        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >11){

            System.out.print(ANSI_PURPLE + "Driver de la classe problema " + '\n' +
                    ANSI_CYAN +  "1. Crear problema" + '\n' +
                    "2. Afegir peça" + '\n' +
                    "3. Eliminar peça" + '\n' +
                    "4. Moure peça" + '\n' +
                    "5. Valider problema" + '\n' +
                    "6. FEN to tauler" + '\n' +
                    "7. Tauler to FEN" + '\n' +
                    "8. Get torn inicial" + '\n' +
                    "9. Get tauler" + '\n' +
                    "10. Get validesa" + '\n' +
                    "11. Get FEN" + '\n'

                    + ANSI_RESET);


            response = scanner.next();

            switch (response) {
                case "1":
                   CrearProblema();

                    break;
                case "2":
                    AfegirPeça();

                    break;

                case "3":
                    EliminarPeça();

                    break;

                case "4":
                    MourePeça();

                    break;

                case "5":
                    ValidarProblema();

                    break;

                case "6":
                    FENtoTauler();
                    break;

                case "7":
                    TaulerToFEN();
                    break;

                case "8":
                    GetTornInicial();
                    break;

                case "9":
                    GetTauler();
                    break;

                case "10":
                    GetValidesa();
                    break;

                case "11":
                    GetFEN();
                    break;



                case "exit":

                    break;

                default:
                    System.out.println( ANSI_RED +"Siusplau, tria una opció vàlida" + ANSI_RESET);
                    response = "0";

            }




        }



    }

    static  void GetFEN(){
        System.out.println(p.GetFEN());
    }

    static void GetTauler(){
        dibuixaTauler(p.getTauler());

    }

    static void GetValidesa(){
        p.validarProblema();
        if(p.GetValid())
            System.out.println("El problema introduit es valid");
        else
            System.out.println("El problema introduit es invalid");

    }

    static void GetTornInicial(){
        Color c = p.GetTorn();
        if(c == Color.blanc)
            System.out.println("El primer torn es de les blanques");
        else
            System.out.println("El primer torn es de les negres");



    }

    static void TaulerToFEN(){
       System.out.println(p.TaulerToFEN());

    }
    static void FENtoTauler(){
        dibuixaTauler(p.FENtoTauler());


}

    static void dibuixaTauler(Tauler tauler){

        String formatB = ANSI_BLACK +  "| " + ANSI_BLUE + "%c " + ANSI_RESET ;
        String formatW = ANSI_BLACK + "| " + ANSI_RED + "%c " + ANSI_RESET ;
        System.out.println();
        System.out.println(ANSI_BLACK + "  +---+---+---+---+---+---+---+---+" + ANSI_RESET);
        for(int i = 0; i < 8; i++){
            System.out.print(ANSI_BLACK);
            System.out.print(8-i);
            System.out.print(" " + ANSI_RESET);

            for (int j = 0; j<8; j++){
                if(tauler.FitxaAt(i,j) != null){
                    TipusPeça tP = tauler.FitxaAt(i,j).GetTipus();
                    Color c = tauler.FitxaAt(i,j).GetColor();

                    if(tP == TipusPeça.Cavall){

                        if(c == Color.negre)
                            System.out.printf(formatB,  'C' );
                        else System.out.printf(formatW, 'C');
                    }
                    if(tP == TipusPeça.Peo){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'P');
                        else System.out.printf(formatW, 'P');
                    }
                    if(tP == TipusPeça.Alfil){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'A');
                        else System.out.printf(formatW, 'A');

                    }

                    if(tP == TipusPeça.Torre){
                        if(c == Color.negre)
                            System.out.printf(formatB, 'T');
                        else System.out.printf(formatW,'T');
                    }

                    if(tP == TipusPeça.Rei){

                        if(c == Color.negre)
                            System.out.printf(formatB, 'R');
                        else System.out.printf(formatW, 'R');

                    }

                    if(tP == TipusPeça.Dama){

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
    static  void MourePeça(){
        ParInt origen;
        ParInt desti;

        p.dibuixaProblema();
        System.out.println(ANSI_PURPLE + "Especifica la coordenada de la peça que vols moure (sense espais, lletra+num)"+ ANSI_RESET);
        origen = StringToCoordenada(scanner.next());

        System.out.println(ANSI_PURPLE + "Especifica la coordenada destí de la peça (sense espais, lletra+num)"+ ANSI_RESET);
        desti = StringToCoordenada(scanner.next());

        p.MourePeça(origen,desti);
        p.dibuixaProblema();


    }

    static  void ValidarProblema(){

        p.validarProblema();
        if(p.GetValid())
            System.out.println("El problema introduit es valid");
        else
        System.out.println("El problema introduit es invalid");


    }


    static void EliminarPeça(){
        ParInt origen;
        String response = "0";

        p.dibuixaProblema();
        System.out.print(ANSI_PURPLE + "Especifica la posició de la peça que vols esborrar:" + ANSI_RESET);
        response = scanner.next();
        origen = StringToCoordenada(response);
        p.EliminarPeça(origen);
        p.dibuixaProblema();

    }

    static void AfegirPeça(){
        TipusPeça tp = TipusPeça.Peo;
        ParInt desti;
        Color c = Color.blanc;

        boolean correcte = false;

        while(!correcte) {

            System.out.println(ANSI_PURPLE + "Especifica el color de la peça que vols afegir (\"w\" o \"b\")" + ANSI_RESET);
            String color = scanner.next();


            if (color.equals("w") || color.equals("W")){
                correcte = true;
                c = Color.blanc;
            }
            else if (color.equals("b") || color.equals("B")){

                correcte = true;
                c = Color.blanc;

            }
            else {
                System.out.println(ANSI_RED + "Color no valid, escriu \"w\" (blanc) o \"b\" (negre)" + ANSI_RESET);



            }
        }

        correcte = false;


        while(!correcte) {
             correcte = false;


            System.out.println(ANSI_PURPLE + "Especifica la lletra de la peça que vols afegir");
            System.out.println(ANSI_CYAN + "Peo: p" + '\n' +
                    "Alfil: a" + '\n' +
                    "Cavall: c" + '\n' +
                    "Torre: t" + '\n' +
                    "Dama: d" + '\n' +
                    "Rei: r" + '\n' + ANSI_RESET);

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

            }


        }
        correcte = false;

        while (!correcte) {
            p.dibuixaProblema();
        System.out.println(ANSI_PURPLE + "Especifica la coordenada on vols afegir la peça (sense espais, lletra+num)"+ ANSI_RESET);
        desti = StringToCoordenada(scanner.next());


        p.AfegirPeça(tp,c,desti);
        p.dibuixaProblema();
        correcte = true;


        }

}

    static void CrearProblema(){

        System.out.print(ANSI_PURPLE + "Introdueix un FEN valid (escriu \";\" un cop l'hagis escrit). " + ANSI_RESET);

        String FEN = "";
        Boolean limit = false;

        while (!limit){

            String next =  scanner.next();
            for(int i = 0; i< next.length(); i++){
                if(next.charAt(i) == ';')
                    limit = true;
            }

            if(!limit){
            FEN += next;
            }
        }
        p = new Problema(0,FEN, Dificultat.facil, new Huma() );
        p = new Problema(FEN);
        p.dibuixaProblema();
        Index();





        }


    static ParInt StringToCoordenada(String str){
        char primer = str.charAt(0);
        char segon = str.charAt(1);

        if((primer >= 'a' && primer <= 'h' ) || primer>='A' && primer<= 'H'){
            char aux = primer;
            primer = segon;
            segon = aux;

        }

        return new ParInt(CharToCoordenadaInt(primer), CharToCoordenadaInt(segon));


    }

    static int CharToCoordenadaInt( char ch ){

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

    public static void main(String args[]){
       Index();

}

}