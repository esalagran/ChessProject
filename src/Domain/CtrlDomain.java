package Domain;

public class CtrlDomain {
    private Problema pObert;
    private Partida partidaEnJoc;
    private Persistence.CtrlPersistence CP;
    private Presentation.CtrlPresentation cPres;

    public void inicializarCtrlDominio() {
        CP = new Persistence.CtrlPersistence();
    }

    /**
     * \pre: probJugats conté els problemes que s'han de jugar entre les màquines
     * \post: S'han jugat tots els problemes de probJugats i es van imprimint
     * els guanyador del problema en cada cas.
     * */
    public void JugarPartidesMaquines(Problema[] probJugats) {
        for (Problema p : probJugats) {
            partidaEnJoc = new Partida(p, Modalitat.MM,null,null);
            //Falta obtenir el guanyador i jugar la partida
        }
    }

    /**
     * \pre: p conté el problema que es jugara i mode la modalitat de la partida.
     * \post: Es juga la partida amb la modalitat mode i
     * s'imprimeix el guanyador quan es finalitzi
     * */
    public Color JugarPartidaHuma(Modalitat mode, Dificultat dif, int torns) {
        Problema p = new Problema("7k/1r4R1/4b2K/7B/8/8/6R1/8 w - - 0 1");
        partidaEnJoc = new Partida(p, mode);
        partidaEnJoc.ComençarPartida();
        return p.GetTorn();
    }

    /**
     * \pre:
     * \post: En cas que no hi hagi cap problema obert, es crea un problema buit,
     * altrament es mostra un missatge*/
    public void CreaProblema() {
        if (pObert == null)
            pObert = new Problema("");
        else
            System.out.println("Primer has de tancar el problema");
    }

    /**
     * \pre: FEN conté el codi FEN del problema que es vol carregar
     * \post: En cas que no hi hagi cap problema obert, es carregar
     * el problema del codi FEN, altrament, es mostra un missatge*/
    public void CarregarProblema(String FEN) {
        if (pObert == null)
            pObert = new Problema(FEN);
        else
            System.out.println("Primer has de tancar el problema");
    }

    /**
     * \pre:
     * \post: En cas que hi hagi un problema obert, es tanca el problema,
     * altrament es mostra un missatge*/
    public void TancarProblema() {
        if (pObert != null)
            pObert = null;
        else
            System.out.println("No tens cap problema obert");
    }

    public boolean isColorHuman(Color color){
       return partidaEnJoc.isColorHuman(color);
    }

    public FitxaProblema[][] TornMaquina(){
        return partidaEnJoc.TornMaquina();
    }

    public boolean hasEnded(){
        return partidaEnJoc.hasEnded();
    }

    public String EndedReason(){
        return partidaEnJoc.EndedReason();
    }
    /**
     * \pre: tp conté el tipus, c el color i coord la posició de la peça que es vol afegir.
     * \post: Si hi ha un problema obert, s'ha afegit una peça en el tauler del problema,
     * altrament es mostra un misstage*/
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

    /**
     * \pre: Hi ha d'haver un problema obert, ini indica la posició inicial de la peça,
     * fi indica la posició a la qual es vol moure
     * \post: Si a la posició ini hi ha una peça i a la fi no n'hi ha cap,
     * es mou la peça de la posició ini a la fi, altrament es mostra un missatge*/
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

    /**
     * \pre: Hi ha d'haver un problema obert, coord indica
     * les coordenades de la peça que es vol esborrar
     * \post: Si a la posició coord hi ha una peça, s'elimina una peça,
     * altrament, es mostra un missatge*/
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

    public FitxaProblema[][] MourePeçaPartida(ParInt first, ParInt second){
       return partidaEnJoc.MourePeça(first, second);
    }

    /**
     * \pre pObert no pot ser null
     * \post S'ha validat el problema i es mostra si és valid o no
     */
    public void ValidarProblema() {

        try {
            pObert.validarProblema();
            if (pObert.GetValid()) System.out.println("El problema és valid");
            else System.out.println("El problema no és valid");
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

    public Tauler getTaulerPartidaEnJouc() {
        return partidaEnJoc.GetTauler();
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