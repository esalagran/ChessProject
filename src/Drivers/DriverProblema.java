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
    static Problema p;



    static void CrearProblema(){
        String response = "0";
        FitxaProblema[][] tauler = new FitxaProblema[8][8];

        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >2){

            System.out.print(ANSI_PURPLE + "Com vols crear el problema: " + '\n' +
                    ANSI_CYAN +  "1. Des de zero" + '\n' +
                    "2. A partir d'un FEN" + '\n' + ANSI_RESET);


            response = scanner.next();

            switch (response) {
                case "1":
                    p = new Problema("8/8/8/8/8/8/8", false, Color.blanc );

                    break;
                case "2":
                    System.out.print(ANSI_PURPLE + "Introdueix un FEN valid: " + ANSI_RESET);
                    String FEN = scanner.next();
                    Problema p = new Problema(0,FEN, Dificultat.facil, new Huma() );
                    p = new Problema(FEN, false, Color.blanc );

                    break;

                case "exit":

                    break;
                default:
                    System.out.println( ANSI_RED +"Siusplau, tria una opció vàlida" + ANSI_RESET);
                    response = "0";

            }




        }

    }

    public static void main(String args[]){
        CrearProblema();

}

}