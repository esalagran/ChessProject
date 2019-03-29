package Domain;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class CtrlDomain {

    private Huma[] cjtUsuaris;
    private Problema[] cjtProblemes;
    private Huma userLogged;
    private Persistence.CtrlPersistence CP = new Persistence.CtrlPersistence();

    public void CreaProblema(){
        Problema p = new Problema();
        userLogged.AfegirProblema(p);
    }

    public CtrlDomain(Huma u){
        userLogged = u;
        cjtUsuaris = CP.GetUsuaris();
        cjtProblemes = CP.GetProblemes();
    }
}



