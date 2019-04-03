package Domain;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class CtrlDomain {

    private HashMap<String, Huma> cjtUsuaris;
    private HashMap<Integer, Problema> cjtProblemes;
    private Huma userLogged;
    private Problema pObert;
    private Partida partidaEnJoc;
    private Persistence.CtrlPersistence CP = new Persistence.CtrlPersistence();
    private Presentation.CtrlPresentation cPres;

    public void CreaProblema(){
        Problema p = new Problema();
        userLogged.AfegirProblema(p);
    }

    public void CarregaProblema(int idP){
        pObert = cjtProblemes.get(idP);
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

    public CtrlDomain(){
        Initialize();
    }

    /**
     * Constructora amb logIn
     * @param nickName indica l'usuari que vol entrar
     * Pre:
     * Post: S'han carregat tots els usuaris i tots els problemes que hi ha al sistema.
     * Si l'usuari no existeix es llan+a una excepció*/
    public CtrlDomain(String nickName){

        if (cjtUsuaris.isEmpty())
            Initialize();

        if (!cjtUsuaris.containsKey(nickName)) throw new NoSuchFieldException("No existeix aquest usuari")
        userLogged = cjtUsuaris.get(nickName);
    }


    private void Initialize(){
        Usuari[] users = CP.GetUsuaris();
        for (Usuari u: users) {
            cjtUsuaris.put(u.GetNickName(), u);
        }

        Problema[] problemes = CP.GetProblemes();
        for (Problema p:problemes) {
            cjtProblemes.put(p.GetId(), p);
        }
    }

    public void CreaPartida(int u1, int u2, int p){

        partidaEnJoc = new Partida(cjtUsuaris.get(u1), cjtUsuaris.get(u2), cjtProblemes.get(p));
    }

    /*public void ModificarPeçaProblema(ParInt ini, ParInt fi){
        pObert.ModificarPeça(ini, fi);
    }*/
}



