package Presentation;

import Domain.*;
import org.junit.platform.commons.function.Try;

import java.util.*;

/**Mètode no rellevant per aquesta entrega*/

public class CtrlPresentation {

    private VistaLogin login = null;
    private MenuPrincipal menu = null;
    private VistaTipusProblema vistaTipus = null;
    private VistaCarregarPartida vistaCarregar = null;
    private TaulerGUI taulerPartida = null;
    private TaulerGUICrearProblema taulerProblema = null;
    private VistaGetFEN vistaGetFEN = null;
    private VistaModalitatProblema vistaModalitatProblema = null;
    private VistaMaquinaVSmaquina vistaMM = null;
    private VistaGetProf prof = null;

    private Domain.CtrlDomain CD;


    public CtrlPresentation() {
        CD = new CtrlDomain();
        CD.CarregarProblemes();
        login= new VistaLogin(this);
    }

    public void initializePresentation() {
        login.hacerVisible();
    }




    //////////////////////// Metodos de sincronizacion entre vistas


    public void sincronizacionVistaMenu_a_Tipus() {
        menu.desactivar();
        menu.visible(false);
        vistaTipus = new VistaTipusProblema(this);
        vistaTipus.hacerVisible();
        vistaTipus.activar();
    }

    public void sincronizacionVistaLlista_a_Modalitat() {
        vistaCarregar.desactivar();
        vistaCarregar.visible(false);

        if (vistaModalitatProblema == null)
            vistaModalitatProblema = new VistaModalitatProblema(this);
        vistaModalitatProblema.hacerVisible();
        vistaModalitatProblema.activar();
    }

    public void setIndexoModalitat(int i){
        vistaModalitatProblema.SetIndex(i);
    }

    public void sincronizacionVistaMenu_a_Carregar() {
        menu.desactivar();
        menu.visible(false);
        if (vistaCarregar == null)
            vistaCarregar = new VistaCarregarPartida(this);
        vistaCarregar.hacerVisible();
        vistaCarregar.activar();
    }

    public void sincronizacionVistaCarregar_a_Menu(){
        vistaCarregar.desactivar();
        vistaCarregar.visible(false);
        menu = new MenuPrincipal(this);
        menu.visible(true);
        menu.activar();
    }


    public String[] getProblemes(){

        return  CD.GetProblemes();
    }


    public void sincronizacionVistaTipus_a_Menu() {
        // Se hace invisible la vista secundaria (podria anularse)
        vistaTipus.desactivar();
        vistaTipus.visible(false);
        menu = new MenuPrincipal(this);
        menu.visible(true);
        menu.activar();
    }



    public void sincronizacionLogin_a_Menu() {
        // Se hace invisible la vista secundaria (podria anularse)
        login.desactivar();
        login.visible(false);

        if(menu== null)
            menu = new MenuPrincipal(this );
        menu.activar();
        menu.visible(true);
    }

    public void sincronizacionMenu_a_Login() {
        // Se hace invisible la vista secundaria (podria anularse)
        menu.desactivar();
        menu.visible(false);
        login.activar();
    }

    public void sincronizacionMenu_a_CrearProblema(){
        menu.desactivar();
        menu.visible(false);

 //       if(taulerProblema==null) {
           CD.CreaProblema();
           taulerProblema = new TaulerGUICrearProblema(this);
   //     }
        taulerProblema.run();
    }

    public void sincronizacionJugarPartida_a_Menu(){
        taulerPartida.visible(false);
        taulerPartida.desactivar();
        menu = new MenuPrincipal(this);
        menu.activar();
        menu.visible(true);

    }

    public void sincronizacionEditarProblema_a_Menu(){

            taulerProblema.visible(false);
            taulerProblema.desactivar();
            CD.GuardarProblema();
            CD.TancarProblema();
            menu = new MenuPrincipal(this);
            menu.activar();
            menu.visible(true);


    }

    public FitxaProblema[][] ImportarProblema(String FEN){
        return CD.ImportarProblema(FEN);
    }


    public void sincronizacionVistaProblema_a_FEN(){
        if(vistaGetFEN == null){
            vistaGetFEN = new VistaGetFEN(this);
            vistaGetFEN.visible(true);
        }
        else vistaGetFEN.visible(true);


    }


    public void sincronizacionVistaFEN_a_ProblemaImport( FitxaProblema[][] tauler){
        vistaGetFEN.visible(false);
        taulerProblema.visible(false);
        taulerProblema.desactivar();
        taulerProblema = new TaulerGUICrearProblema(this, tauler);
        taulerProblema.run();

    }

    public void sincronizacionVistaCarregar_a_ProblemaImport( FitxaProblema[][] tauler){
        vistaCarregar.visible(false);
        if(taulerProblema != null){
        taulerProblema.visible(false);
        taulerProblema.desactivar();
        }
        taulerProblema = new TaulerGUICrearProblema(this, tauler);
        taulerProblema.run();

    }

    public void sincronizacionVistaTipusAmaquinaVSmaquina(Object[][] res){

        vistaMM = new VistaMaquinaVSmaquina(this, res);
        vistaMM.activar();
        vistaMM.visible(true);

    }


    public boolean crearPartida(String[] paramsPartida, boolean rand, int index){
        Color torn;
        switch (paramsPartida[3]){
            case "Màquina vs màquina":
                        Object[][] resultat = CD.JugarPartidesMaquines(paramsPartida[0], paramsPartida[1], TryParseInt(paramsPartida[2]),
                        paramsPartida[4],paramsPartida[5], TryParseInt(paramsPartida[6]),
                        TryParseInt(paramsPartida[7]), TryParseInt(paramsPartida[8]),rand, index);
                        sincronizacionVistaTipusAmaquinaVSmaquina(resultat);
                return true;


            case "Humà vs màquina":

                torn = CD.JugarPartidaHM(paramsPartida[0], paramsPartida[1], TryParseInt(paramsPartida[2]),
                        paramsPartida[4], paramsPartida[5], TryParseInt(paramsPartida[6]), false, rand, index);
                if(torn!=null){
                    if (index == -1)
                        sincronizacionVistaTipus_a_Tauler(torn);
                    else
                        sincronizacionVistaModalitat_a_Tauler(torn);
                    return true;
                }

                break;
            case "Màquina vs humà":
                torn = CD.JugarPartidaHM(paramsPartida[0], paramsPartida[1], TryParseInt(paramsPartida[2]),
                        paramsPartida[4], paramsPartida[5], TryParseInt(paramsPartida[6]), true, rand, index);
                if(torn!=null){
                    if (index == -1)
                        sincronizacionVistaTipus_a_Tauler(torn);
                    else
                        sincronizacionVistaModalitat_a_Tauler(torn);
                    return true;
                }

                break;
            case "Humà vs humà":
                boolean isAttackingH2 = paramsPartida[5].contains("blanc");
                torn = CD.JugarPartidaHH(paramsPartida[0], paramsPartida[1], TryParseInt(paramsPartida[2]),
                        paramsPartida[4], isAttackingH2, rand, index);
                if(torn!=null){
                    if (index == -1)
                        sincronizacionVistaTipus_a_Tauler(torn);
                    else
                        sincronizacionVistaModalitat_a_Tauler(torn);
                    return true;
                }

        }
        return false;
    }


    public void jugarPartidaAleatoria(){

        //CD.

    }

    public void sincronizacionVistaTipus_a_Tauler(Color torn){
        vistaTipus.desactivar();
        vistaTipus.visible(false);
        Tauler t  = CD.getTaulerPartidaEnJoc();
        taulerPartida = new TaulerGUI(t.getTaulell(), torn,  this);
        taulerPartida.run();
        taulerPartida.visible(true);
        taulerPartida.activar();
        }




    public void sincronizacionVistaModalitat_a_Tauler(Color torn){
        vistaModalitatProblema.desactivar();
        vistaModalitatProblema.visible(false);
        Tauler t  = CD.getTaulerPartidaEnJoc();
        taulerPartida = new TaulerGUI(t.getTaulell(), torn,  this);
        taulerPartida.run();
        taulerPartida.visible(true);
        taulerPartida.activar();
    }







//////////////////////// Llamadas al controlador de dominio

    public boolean hasEnded(){
        return CD.hasEnded();
    }

    public String EndedReason(){
        return CD.EndedReason();
    }

    public ParInt[] GetLastMoveMaq(){
        return CD.GetLastMoveMaq();
    }
    public ParInt[] GetLastMoveHum(){
        return CD.GetLastMoveHum();
    }

    public Color GetColor(ParInt coord){
        return CD.GetColor(coord);
    }


    public ArrayList<String> llamadaDominio1 (String selectedItem) {
        return null;
    }

    public ArrayList<String> llamadaDominio2() {
        return null;
    }

    public FitxaProblema[][] MourePeçaPartida(ParInt first, ParInt second){
        return CD.MourePeçaPartida(first, second);
    }

    public void sincronitzacioProblemaAprofunditat(){

        prof = new VistaGetProf(this);
        prof.visible(true);


    }

    public FitxaProblema[][] MourePeçaProblema(ParInt first, ParInt second){
        return CD.MoureFitxa(first, second);
    }

    public FitxaProblema[][] AfegirPeçaProblema(int peça, int color, ParInt coord){
        return  CD.AfegirFitxa(peça, color ,coord);
    }

    public FitxaProblema[][] EliminarFitxa(ParInt coord){
       return CD.EliminarFitxa(coord);
    }

    public void GuardarProblema(){
        CD.GuardarProblema();
    }

    public boolean ValidarProblema(int profunditat, boolean torn){
        return CD.ValidarProblema(profunditat, torn);
    }

    public String GetFENProblema(){
        return  CD.GetFEN();
    }

    public boolean isColorHuman(Color color) {
        return CD.isColorHuman(color);
    }

    public FitxaProblema[][] TornMaquina(){
        return CD.TornMaquina();
    }

    public void Login(String str){
        CD.Login(str);
    }

    private int TryParseInt(String n){
        try{
            return Integer.parseInt(n);
        }
        catch (Exception ex){
            return -1;
        }
    }




}