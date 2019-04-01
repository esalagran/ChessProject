package Domain;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class CtrlDomain {

    private Huma[] cjtUsuaris;
    private List<Problema> cjtProblemes;
    private Huma userLogged;
    private Persistence.CtrlPersistence cPer = new Persistence.CtrlPersistence();
    private Presentation.CtrlPresentation cPres;

    public void CreaProblema(){
        Problema p = new Problema();
        userLogged.AfegirProblema(p);
    }

    public CtrlDomain(Huma u, Presentation.CtrlPresentation cPr){
        cPres = cPr;
        userLogged = u;
        cjtUsuaris = cPer.GetUsuaris();
        cjtProblemes = cPer.GetProblemes();


        /*
        FitxaProblema[][] t = cjtProblemes.get(0).FENtoTauler();
        cPres.dibuixaTaulell(t);
        */



    }
}



