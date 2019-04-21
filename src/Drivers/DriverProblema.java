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
    static Problema p = new Problema("7k/1r4R1/4b2K/7B/8/8/6R1/8 w - - 0 1", false);



    static void Index(){
        String response = "0";

        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >2){

            System.out.print(ANSI_PURPLE + "Driver de la classe problema " + '\n' +
                    ANSI_CYAN +  "1. Crear problema" + '\n' +
                    "2. Afegir peça" + '\n' +
                    "3. Eliminar peça" + '\n' +
                    "4. Moure peça" + '\n'
                    + ANSI_RESET);


            response = scanner.next();

            switch (response) {
                case "1":
                   CrearProblema();

                    break;
                case "2":
                    AfegirPeça();

                    break;

                case "exit":

                    break;

                default:
                    System.out.println( ANSI_RED +"Siusplau, tria una opció vàlida" + ANSI_RESET);
                    response = "0";

            }




        }



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
        System.out.println(ANSI_PURPLE + "Especifica la coordenada on vols afegir la peça sense espais (lletra num)"+ ANSI_RESET);
        desti = StringToCoordenada(scanner.next());


        p.AfegirPeça(tp,c,desti);
        p.dibuixaProblema();
        correcte = true;


        }

}

    static void CrearProblema(){
        String response = "0";
        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >2){

            System.out.print(ANSI_PURPLE + "Com vols crear el problema: " + '\n' +
                    ANSI_CYAN +  "1. Des de zero" + '\n' +
                    "2. A partir d'un FEN" + '\n' + ANSI_RESET);


            response = scanner.next();

            switch (response) {
                case "1":
                    p = new Problema("8/8/8/8/8/8/8", false);
                    p.dibuixaProblema();
                    Index();

                    break;
                case "2":
                    System.out.print(ANSI_PURPLE + "Introdueix un FEN valid: " + ANSI_RESET);
                    String FEN = scanner.next();
                    p = new Problema(0,FEN, Dificultat.facil, new Huma() );
                    p = new Problema(FEN, false);
                    p.dibuixaProblema();
                    Index();

                    break;

                case "exit":

                    break;
                default:
                    System.out.println( ANSI_RED +"Siusplau, tria una opció vàlida" + ANSI_RESET);
                    response = "0";

            }




        }

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