package Presentation;

import Domain.Dificultat;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.*;


public class CtrlPresentation {

    private Domain.CtrlDomain CD = new Domain.CtrlDomain();
    private  Scanner scanner = new Scanner(System.in);



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
        String response;
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
                //JUGAR PARTIDA
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


        }

    }

    private void DemanarInfoPartida(){


    }


}