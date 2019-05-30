package Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CtrlDomain {
    private Problema pObert;
    private PartidaRefactor partidaEnJoc;
    private List<Problema> problemes;
    private List<Huma> usuaris;
    private Huma usuariLoggedIn;


    private Persistence.CtrlPersistence CP;
    private TipusPeça[] peçesEscacs = {TipusPeça.Rei, TipusPeça.Dama, TipusPeça.Torre, TipusPeça.Cavall, TipusPeça.Alfil, TipusPeça.Peo};
    private Color[] colorPeça = {Color.negre, Color.blanc};

    public CtrlDomain() {
        CP = new Persistence.CtrlPersistence();
        CarregarProblemes();
    }

    /**
     * \pre: probJugats conté els problemes que s'han de jugar entre les màquines
     * \post: S'han jugat tots els problemes de probJugats i es van imprimint
     * els guanyador del problema en cada cas.
     * */
    public Object[][] JugarPartidesMaquines(String dif, String color, int jugadesPelMate, String t1, String t2, int p1, int p2, int numProblemes) {
        List<Problema> candidates = getCandidates(Convert.StringToDificultat(dif), Convert.StringToColor(color), jugadesPelMate);
        if (candidates.isEmpty()) return null;
        Algorithm a1 = getTipusAlgorithm(t1, p1);
        Algorithm a2 = getTipusAlgorithm(t2, p2);
        Color guanyador;
        Random rnd = new Random();

        //PartidaRefactor pr;
        Object[][] result = new Object[numProblemes][4];
        for (int i = 0; i < numProblemes; ++i) {
            Problema p = candidates.get(rnd.nextInt(candidates.size()));
            if (p != null) {
                PartidaRefactor pr;
                int isA1 = rnd.nextInt(1);
                if (isA1 == 0) {
                    pr = new PartidaMM(p, a1, a2);
                } else {
                    pr = new PartidaMM(p, a2, a1);
                }

                //obj[0] indica el gunayador, obj[1] temps/moviments de a1, obj[2] = 0bj[1], pero de a2
                Object[] obj = pr.MovimentMaquina();
                result[i][0] = p.GetFEN();
                result[i][1] = "a1";
                result[i][2] = obj[1];
                result[i][3] = "a2";
                result[i][4] = obj[2];

                result[i][1] = pr.getEstatPartida();
                result[i][2] = obj[1];
                result[i][3] = obj[2];

                System.out.println("Problema: " + p.GetFEN());
                if (isA1 == 0 && obj[0].equals(p.GetTorn()) || (isA1 != 0 && !obj[0].toString().equals(color)))
                    result[i][5] = "a1";
                else
                    result[i][5] = "a2";
                result[i][6] = pr.getEstatPartida().toString();
            }
        }
        return result;

    }

    public ParInt[] GetLastMoveMaq(){
        return partidaEnJoc.GetLastMove();
    }

    public ParInt[] GetLastMoveHum(){
        return partidaEnJoc.GetLastMove();
    }


    public Color GetColor(ParInt coord){
        return partidaEnJoc.GetColorAt(coord);
    }

    public void GuardarProblema(){
        String color;
        if (pObert.GetTorn().equals(Color.blanc)) color = "blanc";
        else color = "negre";
        String dif = pObert.getDificultat() == null ? "" : pObert.getDificultat().toString();
        CP.guardarProblema(pObert.GetFEN(),pObert.GetValid(),pObert.GetMovimentsPerGuanyar(),color,pObert.GetCreador(),
                dif,usuariLoggedIn.GetNickName());
        CarregarProblemes();
    }

    public void CarregarProblemes(){
        List<Object[]> p = CP.GetProblemes();
        problemes = new ArrayList<>();
        for (Object[] info : p){
            Color color;
            if (((String) info[3]).contains("blanc")) color = Color.blanc;
            else color = Color.negre;

            //mira si hi ha un usuari amb nickname info[5], si no hi és significa que el problema existia per defecte
            String nickName = "";
            if (CP.hihaUsuari(info[5].toString()))
                nickName = info[5].toString();

            Problema aux = new Problema((String) info[0], new Tema((int) info[2], color), (boolean) info[1], nickName,
                    Convert.StringToDificultat(info[6].toString()), (List<Object[]>) info[4]);
            problemes.add(aux);
        }
    }

    public String[] GetProblemes(){
        String[] FENS = new String[problemes.size()];
        for(int i = 0; i< problemes.size(); i++){
            FENS[i] = problemes.get(i).GetFEN();
        }
        return FENS;
    }


    public void afegirRanking(String FEN, String nickname, int puntucacio)
    {
        CP.afegirJugadorProblema(FEN, nickname, puntucacio);
    }

    public List<Object[]> getRanking(String FEN){
        for(Problema p : problemes){
            if (p.GetFEN().contains(FEN) || FEN.contains(p.GetFEN())){
                return p.getRanking();
            }
        }
        return null;
    }

    public void Login (String str){
        CP.InsertaUsuari(str);
        usuariLoggedIn = new Huma(str);

    }

    /**
     * \pre: p conté el problema que es jugara i mode la modalitat de la partida.
     * \post: Es juga la partida amb la modalitat mode i
     * s'imprimeix el guanyador quan es finalitzi
     * */
    public Color JugarPartidaHH(String dif, String torns, int jugadesPelMate, String h1, String h2) {
        Problema p = TriaProblema(Convert.StringToDificultat(dif), Convert.StringToColor(torns), jugadesPelMate);
        if (p == null) return null;
        partidaEnJoc = new PartidaHH(p, new Huma(h1), new Huma(h2));
        return p.GetTorn();
    }

    public Color JugarPartidaHM(String dif, String torns, int jugadesPelMate, String nickname,
                                String algName, int profunditat, boolean isMachine1){
        Problema p = TriaProblema(Convert.StringToDificultat(dif), Convert.StringToColor(torns), jugadesPelMate);
        if (p == null) return null;
        if (isMachine1){
            //new Huma s'ha de canviar per una cerca a la base de dades d'usuari. En aquest cas hi ha de ser sempre
            //ja que és l'usuari que s'ha registrat
            partidaEnJoc = new PartidaHM(p, getTipusAlgorithm(algName, profunditat), new Huma(nickname));
        }
        else{
            partidaEnJoc = new PartidaHM(p, new Huma(nickname), getTipusAlgorithm(algName, profunditat));
        }
        return p.GetTorn();
    }

    public Color JugarPartidaHuma(Modalitat mode, int index) {
        // en realitat es busca un problema aletatori de la base de dades amb la dificultat
        Random rand = new Random();
        Problema p = problemes.get(index);
        partidaEnJoc = CreaPartida(mode, p, "root", "complex", 7, 7);
        //partidaEnJoc = new Partida(p, mode, 10);
        //partidaEnJoc.ComençarPartida();
        return p.GetTorn();
    }

    /**
     * \pre:
     * \post: En cas que no hi hagi cap problema obert, es crea un problema buit,
     * altrament es mostra un missatge*/
    public void CreaProblema() {
        if (pObert == null)
            pObert = new Problema("", usuariLoggedIn.GetNickName());
        else
            System.out.println("Primer has de tancar el problema");
    }

    public void CreaProblema(String FEN) {
        if (pObert == null)
            pObert = new Problema(FEN, usuariLoggedIn.GetNickName());
        else
            System.out.println("Primer has de tancar el problema");
    }

    /**
     * \pre: FEN conté el codi FEN del problema que es vol carregar
     * \post: En cas que no hi hagi cap problema obert, es carregar
     * el problema del codi FEN, altrament, es mostra un missatge
     * @return Retorna cert si carrega exitosament, retorna fals altrament
     */
    public boolean CarregarProblema(String FEN) {
        if (pObert == null) {
            Object[] info = CP.carregaProblema(FEN);
            //info conte: FEN, validesa, jugades pel mat, color que realitza el mat, ranking
            if (info != null) {
                Color color;
                if (((String) info[3]).contains("blanc")) color = Color.blanc;
                else color = Color.negre;
                String nickName = "";
                if (!CP.hihaUsuari(info[5].toString()))
                    nickName = info[5].toString();
                pObert = new Problema((String) info[0], new Tema((int) info[2], color), (boolean) info[1], nickName,
                        Convert.StringToDificultat(info[6].toString()), (List<Object[]>) info[4]);
                return true;
            }
            else return false;
        }
        else
            return false;
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
       if (partidaEnJoc instanceof PartidaMM)
           return false;
       if (partidaEnJoc instanceof PartidaHH)
           return true;
       PartidaHM p = (PartidaHM)partidaEnJoc;
       if (p.isWinnerHuman())
           return partidaEnJoc.getProblemaEnJoc().GetTorn().equals(color);
       return !partidaEnJoc.getProblemaEnJoc().GetTorn().equals(color);

    }

    public FitxaProblema[][] TornMaquina(){
        return (FitxaProblema[][]) partidaEnJoc.MovimentMaquina();
    }

    public boolean hasEnded(){
        return partidaEnJoc.hasEnded();
    }

    public String EndedReason(){
        /*Color guanyador = partidaEnJoc.getGuanyador();
        if (guanyador != null && )
        return partidaEnJoc.EndedReason();*/
        if (partidaEnJoc.getGuanyador() != null){
            String resposta = "Ha guanyat el " + partidaEnJoc.getGuanyador() + " per ";
            if (partidaEnJoc.getEstatPartida().equals(EstatPartida.jugadesSuperades))
                resposta += "haver sobreviscut a " +  (partidaEnJoc.getProblemaEnJoc().GetMovimentsPerGuanyar() - 1)
                        + " moviemnts\n";
            else
                resposta += partidaEnJoc.getEstatPartida().toString() + "\n";
            return resposta;
        }
        else
            return "La partida encara està en joc\n";
    }

    public FitxaProblema[][] ImportarProblema(String FEN){
        if (pObert != null){
            GuardarProblema();
            TancarProblema();
        }
        CreaProblema(FEN);
        FitxaProblema[ ][ ] t = pObert.getTauler().getTaulell();
        for (int i = 0; i< t.length; i++){
            System.out.println(t[i].getClass().getName());
        }
        return pObert.getTauler().getTaulell();
    }

    public String GetFEN(){
        return pObert.GetFEN();
    }
    /**
     * \pre: tp conté el tipus, c el color i coord la posició de la peça que es vol afegir.
     * \post: Si hi ha un problema obert, s'ha afegit una peça en el tauler del problema,
     * altrament es mostra un misstage*/
    public FitxaProblema[][] AfegirFitxa(int peça, int color, ParInt coord) {
        try {
            if (pObert.getTauler().FitxaAt(coord) != null)
                System.out.println("Ja hi ha una peça a la posició especificada");
            else
               return pObert.AfegirPeça(peçesEscacs[peça], colorPeça[color], coord);

        } catch (NullPointerException ex) {

            System.out.println("Has de crear o carregar un problema ");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * \pre: Hi ha d'haver un problema obert, ini indica la posició inicial de la peça,
     * fi indica la posició a la qual es vol moure
     * \post: Si a la posició ini hi ha una peça i a la fi no n'hi ha cap,
     * es mou la peça de la posició ini a la fi, altrament es mostra un missatge*/
    public FitxaProblema[][] MoureFitxa(ParInt ini, ParInt fi) {
        try {
            if (pObert.getTauler().FitxaAt(fi) != null)
                System.out.println("Ja hi ha una peça a la posició final");
            else if (pObert.getTauler().FitxaAt(ini) == null){
                System.out.println("No hi ha cap peça a la posició inicial");

            }
            else{
                return pObert.MourePeça(ini, fi);
            }
        } catch (NullPointerException ex) {

            System.out.println("Has de crear o carregar un problema ");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    /**
     * \pre: Hi ha d'haver un problema obert, coord indica
     * les coordenades de la peça que es vol esborrar
     * \post: Si a la posició coord hi ha una peça, s'elimina una peça,
     * altrament, es mostra un missatge*/
    public FitxaProblema[][] EliminarFitxa(ParInt coord) {
        try {
            if (pObert.getTauler().FitxaAt(coord) == null)
                System.out.println("No hi ha cap peça a la posició especificada");
            else
                return pObert.EliminarPeça(coord);
        } catch (NullPointerException ex) {
            System.out.println("Has de crear o carregar un problema ");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public FitxaProblema[][] MourePeçaPartida(ParInt first, ParInt second){
       return partidaEnJoc.MovimentHuma(first, second);
    }

    /**
     * \pre pObert no pot ser null
     * \post S'hda validat el problema i es mostra si és valid o no
     */
    public boolean ValidarProblema(int profunditat, boolean torn) {
        boolean valid = false;
        Color jugador = (torn)? Color.blanc : Color.negre;
        try {
            valid = pObert.validarProblema(profunditat, jugador);
            if (pObert.GetValid()) System.out.println("El problema és valid");
            else System.out.println("El problema no és valid");
        } catch (NullPointerException ex) {
            System.out.println("Has de crear o carregar un problema ");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return valid;
    }

    public Problema getpObert() {
        return pObert;
    }

    public PartidaRefactor getPartidaEnJoc() {
        return partidaEnJoc;
    }

    public Tauler getTaulerPartidaEnJoc() {
        return partidaEnJoc.getProblemaEnJoc().getTauler();
    }

    public Dificultat getDificultat(Problema p){
        Dificultat d = p.getDificultat();
        if (d == null){
            System.out.println("El problema s'ha de validar primer.");
            return null;
        }
        else return d;
    }

    private PartidaRefactor CreaPartida (Modalitat m, Problema p, String nickname, String nickname2, int p1, int p2){
        switch (m){
            case MM:
                return new PartidaMM(p, getTipusAlgorithm(nickname, p1), getTipusAlgorithm(nickname2, p2));
            case HH:
                return new PartidaHH(p, new Huma(nickname), new Huma(nickname2));
            case HM:
                return  new PartidaHM(p, new Huma(nickname), getTipusAlgorithm(nickname2, p2));
            case MH:
                return new PartidaHM(p, getTipusAlgorithm(nickname, p1), new Huma(nickname2));
        }
        return null;
    }

    private Algorithm getTipusAlgorithm(String tipus, int profunditat){
        Algorithm a;
        if (tipus.equals("AlphaBeta"))
            a = new AlgorismeAlfaBeta(profunditat);
        else
            a = new AlgorismeMinMax(profunditat);

        return a;
    }

    private Problema TriaProblema(Dificultat dif, Color torn, int jugadesPelMate){
        Random rand = new Random();
        int jugades = jugadesPelMate * 2 - 1;
        List<Problema> candidates = getCandidates(dif, torn, jugades);
        if (candidates.isEmpty()) return null;
        return candidates.get(rand.nextInt(candidates.size()));
    }

    private List<Problema> getCandidates(Dificultat dif, Color torn, int jugadesPelMate){
        List<Problema> candidates = new ArrayList<>();
        for (Problema p : problemes) {
            if (p.getDificultat().equals(dif) && p.GetTorn().equals(torn) &&
                    p.GetMovimentsPerGuanyar() == jugadesPelMate && p.GetValid())
                candidates.add(p);
        }
        return candidates;
    }
}