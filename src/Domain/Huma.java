package Domain;

import java.util.HashMap;

public class Huma extends Usuari{
    private boolean loggedIn;
    private String password;

    private HashMap<Integer, Problema> problemesCreats;
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
        problemesCreats.put(p.GetId(), p);
    }




}