package Domain;

import javax.swing.plaf.synth.ColorType;
//import java.lang.module.FindException;
import java.util.HashMap;
import java.util.List;

public class CtrlDomain {
    private Problema pObert;
    private Partida partidaEnJoc;
    private Persistence.CtrlPersistence CP = new Persistence.CtrlPersistence();
    private Presentation.CtrlPresentation cPres;

    public void JugarPartidesMaquines(Problema[] probJugats) {
        for (Problema p : probJugats) {
            partidaEnJoc = new Partida(p, Modalitat.MM);
            //Falta obtenir el guanyador i jugar la partida
        }
    }

    public void JugarPartidaHuma(Modalitat mode, Problema p) {
        partidaEnJoc = new Partida(p, mode);
        partidaEnJoc.ComençarPartida();
    }

    public void CreaProblema() {
        pObert = new Problema("");
    }

    public void CarregarProblema(String FEN) {
        if (pObert == null)
            pObert = new Problema(FEN);
        else
            System.out.println("Primer has de tancar el problema");
    }

    public void TancarProblema() {
        if (pObert != null)
            pObert = null;
        else
            System.out.println("No tens cap problema obert");
    }

    public void AfegirFitxa(TipusPeça tp, Color c, ParInt coord) {
        try {
            if (pObert.getTauler().FitxaAt(coord) != null)
                System.out.println("Ja hi ha una peça a la posició especificada");
            else
                pObert.AfegirPeça(tp, c, coord);
        } catch (NullPointerException ex) {

            System.out.println("Has de crear o carregar un problema ");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void MoureFitxa(ParInt ini, ParInt fi) {
        try {
            if (pObert.getTauler().FitxaAt(fi) != null)
                System.out.println("Ja hi ha una peça a la posició final");
            else if (pObert.getTauler().FitxaAt(ini) == null)
                System.out.println("No hi ha cap peça a la posició inicial");
            else
                pObert.MourePeça(ini, fi);
        } catch (NullPointerException ex) {

            System.out.println("Has de crear o carregar un problema ");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void EliminarFitxa(ParInt coord) {
        try {
            if (pObert.getTauler().FitxaAt(coord) == null)
                System.out.println("No hi ha cap peça a la posició especificada");
            else
                pObert.EliminarPeça(coord);
        } catch (NullPointerException ex) {

            System.out.println("Has de crear o carregar un problema ");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ValidarProblema() {

        try {
            pObert.validarProblema();
        } catch (NullPointerException ex) {
            System.out.println("Has de crear o carregar un problema ");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Problema getpObert() {
        return pObert;
    }

    public Partida getPartidaEnJoc() {
        return partidaEnJoc;
    }
}



    /* Funcions que s'implementaran a la següent entrga
    private HashMap<String, Huma> cjtUsuaris;
    private List<Problema> cjtProblemes;
    private Huma userLogged;
    /*public List<Problema> GetProblemes(){
        return cjtProblemes;
    }

    public Tauler CrearPartida(int indexP, Modalitat m){
        Problema p = cjtProblemes.get(indexP);

        partidaEnJoc = new Partida(p, m);
        return p.FENtoTauler();

    }

    public Color GetTornPartidaEnJoc(){
        return partidaEnJoc.GetTorn();
    }


        /*
        FitxaProblema[][] t = cjtProblemes.get(0).FENtoTauler();
        System.out.println("ORIGINAL: " + cjtProblemes.get(0).GetFEN());
        cPer.TaulerToFEN(t);
        cPres.dibuixaTaulell(t);
*/
/*
    /**
     * Constructora amb logIn
     * @param nickName indica l'usuari que vol entrar
     * Pre:
     * Post: S'han carregat tots els usuaris i tots els problemes que hi ha al sistema.
     * Si l'usuari no existeix es llan+a una excepció*/
    /*public CtrlDomain(String nickName, Presentation.CtrlPresentation cPr){
        Initialize();

        cPres = cPr;
        /* HO HE COMENTAT PK PRODUEIX java.lang.NullPointerException
        if (cjtUsuaris.isEmpty()){
            Huma[] users = CP.GetUsuaris();
            for (Huma u: users) {
                cjtUsuaris.put(u.GetNickName(), u);
            }
        }
        userLogged = cjtUsuaris.get(nickName);
        if (userLogged.equals(null))
            throw new FindException("No existeix aquest usuari");
*/
    //}

/*
    private void Initialize(){
        /* NULL POINTER EXCEPTION
        Huma[] users = CP.GetUsuaris();
        for (Huma u: users) {
            cjtUsuaris.put(u.GetNickName(), u);
        }
        */

        //cjtProblemes = CP.GetProblemes();
        /*for (Problema p:problemes) {
            cjtProblemes.put(p.GetId(), p);
        }*/
    //}



    /*public void ModificarPeçaProblema(ParInt ini, ParInt fi){
        pObert.ModificarPeça(ini, fi);
    }*/
//}