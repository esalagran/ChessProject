package Domain;

import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CtrlDomain {
    private Problema pObert;
    private Partida partidaEnJoc;
    private List<Problema> problemes  = new ArrayList<Problema>();

    private Persistence.CtrlPersistence CP;
    private Presentation.CtrlPresentation cPres;
    private TipusPeça[] peçesEscacs = {TipusPeça.Rei, TipusPeça.Dama, TipusPeça.Torre, TipusPeça.Cavall, TipusPeça.Alfil, TipusPeça.Peo};
    private Color[] colorPeça = {Color.negre, Color.blanc};

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
            partidaEnJoc = new Partida(p, Modalitat.MM,p.GetMovimentsPerGuanyar());
            //Falta obtenir el guanyador i jugar la partida
        }
    }

    public void GuardarProblema(){
        if(problemes.contains(pObert))
            problemes.remove(pObert);
        problemes.add(pObert);
    }

    /**
     * \pre: p conté el problema que es jugara i mode la modalitat de la partida.
     * \post: Es juga la partida amb la modalitat mode i
     * s'imprimeix el guanyador quan es finalitzi
     * */
    public Color JugarPartidaHuma(Modalitat mode, Dificultat dif, int torns) {
        // en realitat es busca un problema aletatori de la base de dades amb la dificultat
        Problema p = new Problema("7k/1r4R1/4b2K/7B/8/8/6R1/8 w - - 0 1");
        partidaEnJoc = new Partida(p, mode, torns);
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

    public void CreaProblema(String FEN) {
        if (pObert == null)
            pObert = new Problema(FEN);
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

    public FitxaProblema[][] ImportarProblema(String FEN){
        GuardarProblema();
        TancarProblema();
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
       return partidaEnJoc.MourePeça(first, second);
    }

    /**
     * \pre pObert no pot ser null
     * \post S'hda validat el problema i es mostra si és valid o no
     */
    public boolean ValidarProblema() {
        boolean valid = false;
        try {
            valid = pObert.validarProblema();
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

    public Partida getPartidaEnJoc() {
        return partidaEnJoc;
    }

    public Tauler getTaulerPartidaEnJouc() {
        return partidaEnJoc.GetTauler();
    }

    public Map<String,Integer> getRanking(Problema p){
        return p.getRanking();
    }

    public Dificultat getDificultat(Problema p){
        Dificultat d = p.getDificultat();
        if (d == null){
            System.out.println("El problema s'ha de validar primer.");
            return null;
        }
        else return d;
    }
}