package Drivers;
import Domain.*;

import java.util.Scanner;

public class DriverCtrlDomain{

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[37m";
    public static final String ANSI_PURPLE = "\u001B[30m";
    public static final String ANSI_BLACK = "\u001B[30m";
    static Scanner sc = new Scanner(System.in);

    private static CtrlDomain ctrlDomain = new CtrlDomain();
    private static String FEN;


    static  void MourePeça(){
        ParInt origen;
        ParInt desti;

        Convert.DibuixaTauler(ctrlDomain.getpObert().getTauler());
        System.out.println(ANSI_PURPLE + "Especifica la coordenada de la peça que vols moure (sense espais, lletra+num)"+ ANSI_RESET);
        origen = Convert.StringToCoordenada(sc.next());

        System.out.println(ANSI_PURPLE + "Especifica la coordenada destí de la peça (sense espais, lletra+num)"+ ANSI_RESET);
        desti = Convert.StringToCoordenada(sc.next());

        ctrlDomain.MoureFitxa(origen,desti);
        Convert.DibuixaTauler(ctrlDomain.getpObert().getTauler());
    }

    static void EliminarPeça(){
        ParInt origen;
        String response = "0";

        Convert.DibuixaTauler(ctrlDomain.getpObert().getTauler());
        System.out.print(ANSI_PURPLE + "Especifica la posició de la peça que vols esborrar:" + ANSI_RESET);
        response = sc.next();
        origen = Convert.StringToCoordenada(response);
        ctrlDomain.EliminarFitxa(origen);
        Convert.DibuixaTauler(ctrlDomain.getpObert().getTauler());

    }

    static void AfegirPeça(){
        TipusPeça tp = TipusPeça.Peo;
        ParInt desti;
        Color c = Color.blanc;

        boolean correcte = false;

        while(!correcte) {

            System.out.println(ANSI_PURPLE + "Especifica el color de la peça que vols afegir (\"w\" o \"b\")" + ANSI_RESET);
            String color = sc.next();


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
            correcte = true;
            System.out.println(ANSI_PURPLE + "Especifica la lletra de la peça que vols afegir");
            System.out.println(ANSI_CYAN + "Peo: p" + '\n' +
                    "Alfil: a" + '\n' +
                    "Cavall: c" + '\n' +
                    "Torre: t" + '\n' +
                    "Dama: d" + '\n' +
                    "Rei: r" + '\n' + ANSI_RESET);

            String peça = sc.next();

            switch (peça) {
                case "p":
                    tp = TipusPeça.Peo;
                    break;
                case "c":
                    tp = TipusPeça.Cavall;
                    break;
                case "a":
                    tp = TipusPeça.Alfil;
                    break;
                case "d":
                    tp = TipusPeça.Dama;
                    break;
                case "t":
                    tp = TipusPeça.Torre;
                    break;
                case "r":
                    tp = TipusPeça.Rei;
                    break;
                default:
                    System.out.println(ANSI_RED + "Fitxa no valida, escriu la lletra de la peça que vols afegir" + ANSI_RESET);
                    correcte = false;
                    break;
            }

        }
        correcte = false;

        while (!correcte) {
            Convert.DibuixaTauler(ctrlDomain.getpObert().getTauler());
            System.out.println(ANSI_PURPLE + "Especifica la coordenada on vols afegir la peça (sense espais, lletra+num)"+ ANSI_RESET);
            desti = Convert.StringToCoordenada(sc.next());

            ctrlDomain.AfegirFitxa(tp,c,desti);
            Convert.DibuixaTauler(ctrlDomain.getpObert().getTauler());
            correcte = true;
        }
    }

    public static void start(int cas) {
        try {
            switch (cas) {
                case 1:
                    ctrlDomain.JugarPartidesMaquines(null);
                    break;
                case 2:
                    ctrlDomain.JugarPartidaHuma(Modalitat.HH, Dificultat.facil, 10);
                    break;
                case 3:
                    ctrlDomain.CreaProblema();
                    Convert.DibuixaTauler(ctrlDomain.getpObert().getTauler());
                    break;
                case 4:
                    ctrlDomain.CarregarProblema(FEN);
                    Convert.DibuixaTauler(ctrlDomain.getpObert().getTauler());
                    break;
                case 5:
                    ctrlDomain.TancarProblema();
                    break;
                case 6:
                    ctrlDomain.ValidarProblema();
                    break;
                case 7:
                    AfegirPeça();
                    break;
                case 8:
                    EliminarPeça();
                    break;
                case 9:
                    MourePeça();
                    break;
                case 10:
                    System.out.println("Bye Bye");
                    break;
                default:
                    System.out.println("Input a correct option");
            }
        }
        catch (NullPointerException ex){
            System.out.println("S'ha d'inicialitzar el taulell ");
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        int num;
        do{
            System.out.print(ANSI_PURPLE + "Driver de la classe Control del domini " + '\n' +
                    ANSI_CYAN +  "1. Jugar partida entre Maquines" + '\n' +
                    ANSI_CYAN +  "2. Jugar partida involucra huma" + '\n' +
                    "3. Crear Problema" + '\n' +
                    "4. Carregar Problema" + '\n' +
                    "5. Tancar Problema" + '\n' +
                    "6. Validar Problema" + '\n' +
                    "7. Afegir Peça" + '\n' +
                    "8. Eliminar Peça" + '\n' +
                    "9. Moure Peça" + '\n' +
                    "10. Sortir" + '\n' +
                    "Input a new number"
                    + ANSI_RESET);
            num = sc.nextInt();
            start(num);
        }while(num != 0);
    }
}
