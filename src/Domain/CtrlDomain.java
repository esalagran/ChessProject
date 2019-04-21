package Domain;

import javax.swing.plaf.synth.ColorType;
//import java.lang.module.FindException;
import java.util.HashMap;
import java.util.List;

public class CtrlDomain {

    private HashMap<String, Huma> cjtUsuaris;
    private List<Problema> cjtProblemes;
    private Huma userLogged;
    private Problema pObert;
    private Partida partidaEnJoc;
    private Persistence.CtrlPersistence CP = new Persistence.CtrlPersistence();
    private Presentation.CtrlPresentation cPres;

    public void CreaProblema(){
        Problema p = new Problema();
        userLogged.AfegirProblema(p);
    }

    public void AfegirProblema(String FEN, boolean valid){
//        userLogged.AfegirProblema(p);
        Problema p = new Problema(FEN);
        CP.guardarProblema(FEN,valid);
        cjtProblemes = CP.GetProblemes();
    }

    public void CarregarProblema(String FEN, boolean valid, Color torn){
        Problema p = new Problema(FEN);
        cjtProblemes.add(p);

    }

    public List<Problema> GetProblemes(){
        return cjtProblemes;
    }

    public Tauler CrearPartida(int indexP, Modalitat m){
        Problema p = cjtProblemes.get(indexP);

        partidaEnJoc = new Partida(p, m, p.GetTorn());
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

    /**
     * Constructora amb logIn
     * @param nickName indica l'usuari que vol entrar
     * Pre:
     * Post: S'han carregat tots els usuaris i tots els problemes que hi ha al sistema.
     * Si l'usuari no existeix es llan+a una excepció*/
    public CtrlDomain(String nickName, Presentation.CtrlPresentation cPr){
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
    }


    private void Initialize(){
        /* NULL POINTER EXCEPTION
        Huma[] users = CP.GetUsuaris();
        for (Huma u: users) {
            cjtUsuaris.put(u.GetNickName(), u);
        }
        */

        cjtProblemes = CP.GetProblemes();
        /*for (Problema p:problemes) {
            cjtProblemes.put(p.GetId(), p);
        }*/
    }

    public void CreaPartida(int u1, int u2, int p){

        partidaEnJoc = new Partida(cjtUsuaris.get(u1), cjtUsuaris.get(u2), cjtProblemes.get(p));
    }

    /*public void ModificarPeçaProblema(ParInt ini, ParInt fi){
        pObert.ModificarPeça(ini, fi);
    }*/
}