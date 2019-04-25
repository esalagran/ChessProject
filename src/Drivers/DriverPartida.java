package Drivers;

import Domain.*;

import java.util.Scanner;

public class DriverPartida {

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
    static Partida partida = new Partida(p,Modalitat.HH );



    static void Index(){
        String response = "0";

        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >11){

            System.out.print(ANSI_PURPLE + "Driver de la classe partida " + '\n' +
                    ANSI_CYAN +  "1. Crear partida" + '\n' +
                    "2. Jugar partida" + '\n' +
                    "2. Moure peça" + '\n' +
                    "3. Torn contrari" + '\n' 
                    + ANSI_RESET);


            response = scanner.next();

            switch (response) {
                case "1":
                    CrearPartida();

                    break;
                case "2":

                    JugarPartida();


                    break;

                case "3":


                    break;

                case "4":

                    break;

                case "5":


                    break;

                case "6":

                    break;

                case "7":

                    break;

                case "8":

                    break;

                case "9":

                    break;

                case "10":

                    break;

                case "11":

                    break;



                case "exit":

                    break;

                default:
                    System.out.println( ANSI_RED +"Siusplau, tria una opció vàlida" + ANSI_RESET);
                    response = "0";

            }




        }



    }


    public static void CrearPartida(){

        System.out.println(ANSI_PURPLE + "Selecciona una modalitat" + '\n' +
                                 ANSI_CYAN +
                                    "1. Humà vs humà" + '\n' +
                                    "2. Humà vs màquina" + '\n'  +
                                    "3. Màquina vs  humà" + '\n'  +
                                    "4. Màquina vs màquina" + '\n'
                                    + ANSI_RESET);

        String response = scanner.next();
        Modalitat mod = Modalitat.HH;

        switch (response){
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
        }

        partida = new Partida(p, mod);
        Index();

    }


    public static void MourePeça(){
        ParInt origen;
        ParInt desti;
        System.out.print("Es el torn de les: ");
        Color t = partida.GetTorn();
        if(t == Color.blanc)
            System.out.println("Blanques");
        else System.out.println(("Negres"));

        System.out.println(ANSI_PURPLE + "Especifica la coordenada de la peça que vols moure (sense espais, lletra+num)"+ ANSI_RESET);
        origen = StringToCoordenada(scanner.next());

        System.out.println(ANSI_PURPLE + "Especifica la coordenada destí de la peça (sense espais, lletra+num)"+ ANSI_RESET);
        desti = StringToCoordenada(scanner.next());

        partida.MourePeça(origen,desti);

    }


    public static void JugarPartida(){
        partida.ComençarPartida();
        while (!partida.hasEnded()){
            MourePeça();
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