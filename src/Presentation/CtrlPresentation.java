package Presentation;

import Domain.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;


public class CtrlPresentation {

    private Domain.CtrlDomain CD = new Domain.CtrlDomain(new Huma(), this);
    private  Scanner scanner = new Scanner(System.in);

    public void dibuixaTaulell(FitxaProblema[][] taulell){
        for(int i = 0; i < 8; i++){
            for (int j = 0; j<8; j++){
                if(taulell[i][j] != null){
                    TipusPeça tP = taulell[i][j].GetTipus();
                    boolean negre = taulell[i][j].GetColor();

                    if(tP == TipusPeça.Cavall){
                        if(negre)
                            System.out.print("c ");
                        else System.out.print("C ");
                    }
                    if(tP == TipusPeça.Peo){
                        if(negre)
                            System.out.print("p ");
                        else System.out.print("P ");
                    }
                    if(tP == TipusPeça.Alfil){
                        if(negre)
                            System.out.print("a ");
                        else System.out.print("A ");
                    }

                    if(tP == TipusPeça.Torre){
                        if(negre)
                            System.out.print("t ");
                        else System.out.print("T ");
                    }

                    if(tP == TipusPeça.Rei){
                        if(negre)
                            System.out.print("r ");
                        else System.out.print("R ");
                    }
                    if(tP == TipusPeça.Dama){
                        if(negre)
                            System.out.print("d ");
                        else System.out.print("D ");
                    }

                }
                else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
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
                    //CARREGAR PROBLEMA
                    break;

                case "3":
                    //CREAR PROBLEMA
                    break;
                case "4":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Siusplau, tria una opció vàlida");
                    response = "0";


            }
        }

    }
    private  void CarregarPartida(){
        String response = "0";
        //
    }

    private void DemanarInfoPartida(){
        String response = "0";
        Modalitat mod = Modalitat.HH;
        Dificultat dif = Dificultat.facil;
        Tema tema;
        Color c = Color.blanc;
        int n = 1;
        boolean isRandom = false;

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

                    if(Math.random()<0.5)
                        c = Color.blanc;
                    else c = Color.negre;

                    double random = Math.random();
                    if(random< 0.33)
                        dif = Dificultat.facil;
                    else if(random < 0.66)
                        dif = Dificultat.mitja;
                    else dif = Dificultat.dificil;

                    random = Math.random();
                    if(random< 0.25)
                        mod = Modalitat.HH;
                    else if(random < 0.5)
                        mod = Modalitat.HM;
                    else if(random < 0.75)
                        mod = Modalitat.MM;
                    else mod = Modalitat.MH;

                    n =  (int)((Math.random()*15) + 1);
                    tema = new Tema(n, c);
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


        System.out.print("L'usuari ha triat una partida amb modalitat: " + mod + '\n' +
                "Ha triat un problema amb la dificultat: " + dif + '\n' + "Amb el color " + c + " atacant i amb mat en " + n + " jugades");





    }


}