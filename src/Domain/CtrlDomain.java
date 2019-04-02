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

    public List<Problema> GetProblemes(){
        return cjtProblemes;
    }

    public CtrlDomain(Huma u, Presentation.CtrlPresentation cPr){
        cPres = cPr;
        userLogged = u;
        cjtUsuaris = cPer.GetUsuaris();
        cjtProblemes = cPer.GetProblemes();



        /*
        FitxaProblema[][] t = cjtProblemes.get(0).FENtoTauler();
        System.out.println("ORIGINAL: " + cjtProblemes.get(0).GetFEN());
        cPer.TaulerToFEN(t);
        cPres.dibuixaTaulell(t);
*/





    }
}



