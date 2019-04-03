package Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Huma extends Usuari{
    private boolean loggedIn;
    private String password;

    private List<Problema> problemesCreats = new ArrayList<>();
    private int[] puntuació;

    public boolean IsLoggedIn(){
        return loggedIn;
    }

    public void LogIn(){
        loggedIn = true;
    }

    public void LogOut(){
        loggedIn = false;
    }

    public void AfegirProblema(Problema p){
        problemesCreats.add(p);
    }




}