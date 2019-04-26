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
    static  Problema pMat =  new Problema("k6R/6R1/8/8/8/8/6K1/8 w ");
    static Partida partida = new Partida(p,Modalitat.HH );
    static Partida partidaMat = new Partida(pMat, Modalitat.HH);



    static void Index(){
        String response = "0";

        while(Integer.parseInt(response) < 1 || Integer.parseInt(response) >11){

            System.out.print(ANSI_PURPLE + "Driver de la classe partida " + '\n' +
                    ANSI_CYAN +  "1. Crear partida" + '\n' +
                    "2. Jugar partida" + '\n' +
                    "3. Moure peça" + '\n' +
                            "4. Torn maquina" + '\n' +
                    "5. Fi Torn" + '\n' +
                    "6. Qui juga amb aquest color?" + '\n' +
                    "7 Ha acabat la partida? " + '\n' +
                    "8. Va ser mat?" + '\n' +
                    "9. Get guanyador" + '\n' +
                    "10. Get tauler" + '\n' +
                    "11. Get torn" + '\n' +
                    "12. Get modalitat" + '\n' +
                    "13. Comprovar mat (amb partida en estat de mat)"+ '\n'

                    + ANSI_RESET);


            partida.ComençarPartida();
            partidaMat.ComençarPartida();
            response = scanner.next();

            switch (response) {
                case "1":
                    CrearPartida();
                    response = "0";
                    break;
                case "2":
                    if (!partida.hasEnded())
                        JugarPartida();
                    else
                        System.out.println("La partida no s'ha creat");
                    response = "0";
                    break;

                case "3":
                    if (!partida.hasEnded()) {
                        System.out.print("Es el torn de les: ");
                        if (partida.GetTorn() == Color.blanc)
                            System.out.println("Blanques (a aquest tauler les vermelles)");
                        else System.out.println(("Negres (a aquest tauler les blaves)"));
                        dibuixaPartida(partida.GetTauler());
                        MourePeça();
                        dibuixaPartida(partida.GetTauler());
                        response = "0";
                    }
                    else {
                        System.out.println("La partida ja ha acabat");
                    }
                    break;

                case "4":
                    if (!partida.hasEnded()){
                        System.out.println("PENSANT MOVIMENT");
                        partida.TornMaquina();
                        dibuixaPartida(partida.GetTauler());
                        response = "0";
                    }
                    else {
                        System.out.println("La partida ja ha acabat");
                    }
                    break;

                case "5":
                    if (!partida.hasEnded()){

                        System.out.print("Ara li tocava a les: " + partida.GetTorn());

                        if(partida.isColorHuman(partida.GetTorn()))
                            System.out.println(" I és un humà");
                        else
                            System.out.println(" I és la màquina");

                        partida.FiTorn();
                        System.out.print("Ara li toca a les: " + partida.GetTorn());

                        if(partida.isColorHuman(partida.GetTorn()))
                            System.out.println(" I és un humà");
                        else
                            System.out.println(" I és la màquina");

                        response = "0";
                    }
                    else {
                        System.out.println("La partida ja ha acabat");
                    }
                    break;

                case "6":

                    System.out.println("Escriu la lletra del color que vols comprovar, \"w\" per blanc i \"b\" per negre");
                    String colStr = scanner.next();
                    Color c;


                    if(colStr.equals("w")){
                        c = Color.blanc;
                    }
                    else if(colStr.equals("b")){
                        c = Color.negre;
                    }
                    else{
                        System.out.println("No has especificat el color correctament");
                        response = "0";
                        break;

                    }

                    System.out.print("El color " + c );
                    if(partida.isColorHuman(c))
                        System.out.println(" és humà");
                    else System.out.println(" es la màquina");
                                response = "0";
                    break;
                case "7":
                    if(partida.hasEnded())
                        System.out.println("La partida ja ha acabat");
                    else System.out.println("La partida encara no ha acabat");
                        response = "0";
                    break;

                case "8":
                    if(!partida.hasEnded()){
                        System.out.println("La partida encara no ha acabat, per tant el valor de wasMat no és valid");
                    } else{
                        if(partida.wasMat())
                            System.out.println("Va acabar per mat");
                        else System.out.println("Va acabar per excés de torns");
                    }
                    response = "0";
                    break;
            case "9":

                if(!partida.hasEnded()){
                    System.out.println("La partida encara no ha acabat, per tant el valor de getGuanyador no és valid");
                }
                else
                    System.out.println("El guanyador va ser: " + partida.getGuanyador());

                response = "0";
                break;

            case "10":
                if (!partida.hasEnded())
                    dibuixaPartida(partida.GetTauler());
                else System.out.println("La partida ja s'ha acabat");
                    response = "0";
                break;

            case "11":
                if (!partida.hasEnded()){
                    System.out.println("És el torn de les: " + partida.GetTorn());
                }
                else System.out.println("La partida ja s'ha acabat");

                response = "0";
                break;

            case "12":
                System.out.print("La modalitat es: ");
                Modalitat m =  partida.GetModalitat();
                if(m == Modalitat.HM)
                    System.out.println("Humà vs Màquina");
                else if(m== Modalitat.HH)
                    System.out.println("Humà vs Humà");
                else if(m==Modalitat.MH)
                    System.out.println("Màquina vs Humà");
                else if(m== Modalitat.MM)
                    System.out.println("Màquina vs Màquina");

                    response = "0";
                    break;

                case "13":
                    dibuixaPartida(partidaMat.GetTauler());
                    if(partidaMat.ComprovarMat()){
                        System.out.println("És mat!");
                    }
                    else  System.out.println("No és mat!");

                    response = "0";
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
        System.out.println(p.GetMovimentsPerGuanyar());
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
        p = new Problema(FEN);
        partida = new Partida(p, mod);
        partida.ComençarPartida();
        Index();
    }



    public static void MourePeça(){
        ParInt origen;
        ParInt desti;

        System.out.println(ANSI_PURPLE + "Especifica la coordenada de la peça que vols moure (sense espais, lletra+num)"+ ANSI_RESET);
        origen = StringToCoordenada(scanner.next());

        System.out.println(ANSI_PURPLE + "Especifica la coordenada destí de la peça (sense espais, lletra+num)"+ ANSI_RESET);
        desti = StringToCoordenada(scanner.next());

        partida.MourePeça(origen,desti);

    }


    public static void JugarPartida(){

        partida.ComençarPartida();
        Color guanyador = Color.negre;
        boolean mat = false;
        while (!partida.hasEnded()){
            Color t = partida.GetTorn();
            System.out.print("Es el torn de les: ");
            if(t == Color.blanc)
                System.out.println("Blanques (a aquest tauler les vermelles)");
            else System.out.println(("Negres (a aquest tauler les blaves)"));

            if(partida.isColorHuman(partida.GetTorn())){
                dibuixaPartida(partida.GetTauler());
                MourePeça();
            }
            else{
                System.out.println("PENSANT MOVIMENT");
                partida.TornMaquina();
            }


        }

        if(partida.wasMat())
            System.out.println("MAT!");
        System.out.println("El guanyador és : " + partida.getGuanyador());




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

     static void dibuixaPartida(Tauler tauler){

        String formatB = ANSI_BLACK +  "| " + ANSI_BLUE + "%c " + ANSI_RESET ;
        String formatW = ANSI_BLACK + "| " + ANSI_RED + "%c " + ANSI_RESET ;
        System.out.println();
        System.out.println(ANSI_BLACK + "  +---+---+---+---+---+---+---+---+" + ANSI_RESET);
        for(int i = 0; i < 8; i++){
            System.out.print(ANSI_BLACK);
            System.out.print(8-i);
            System.out.print(" " + ANSI_RESET);

            for (int j = 0; j<8; j++){
                ParInt coord = new ParInt(i,j);
                if(tauler.FitxaAt(coord) != null){
                    TipusPeça tP = Convert.ClassToTipusPeça(tauler.FitxaAt(coord).getIFitxa().getClass().toString());
                    Color c = tauler.FitxaAt(coord).GetColor();

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

    public static void main(String args[]){
        Index();

    }

}