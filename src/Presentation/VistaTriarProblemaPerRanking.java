package Presentation;

import Domain.FitxaProblema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

////////////////////////

public class VistaTriarProblemaPerRanking {

    // Controlador de presentacion
    private CtrlPresentation iCtrlPresentacion;

    // Componentes de la interficie grafica
    private JFrame frameVista = new JFrame("Menu " +
            "principal");
    private JPanel panelContenidos = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JPanel panelLlista = new JPanel();
    private JPanel panelUsuario = new JPanel();
    private JPanel panelContraseña = new JPanel();
    private JTextField usuariField = new JTextField(10);
    private JTextField contraseñaField = new JPasswordField(10);
    private JButton buttonJugar= new JButton("Veure ranking");
    private JButton buttonVolver= new JButton("Tornar");
    private JList<String> llista = new JList<String>();


    // Menus
    private JMenuBar menubarVista = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuitemQuit = new JMenuItem("Quit");
    private JMenu menuOpciones = new JMenu("Opciones");

    // Resto de atributos
    private int iPanelActivo = 0;


//////////////////////// Constructor y metodos publicos


    public VistaTriarProblemaPerRanking(CtrlPresentation pCtrlPresentacion) {
        System.out.println
                ("isEventDispatchThread: " + SwingUtilities.isEventDispatchThread());
        iCtrlPresentacion = pCtrlPresentacion;
        inicializarComponentes();
    }

    public void hacerVisible() {
        System.out.println
                ("isEventDispatchThread: " + SwingUtilities.isEventDispatchThread());
        frameVista.pack();
        frameVista.setVisible(true);
    }

    public void activar() {
        frameVista.setEnabled(true);
    }

    public void desactivar() {
        frameVista.setEnabled(false);
    }

    public void visible(boolean vis){
        frameVista.setVisible(vis);
    }


//////////////////////// Metodos de las interfaces Listener




    public void actionPerformed_buttonLogin (ActionEvent event) {

        iCtrlPresentacion.sincronizacionLogin_a_Menu();

    }


//////////////////////// Asignacion de listeners


    private void asignar_listenersComponentes() {

        // Listeners para los botones

        buttonVolver.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        iCtrlPresentacion.sincronizacionVistaTriar_a_Menu();
                    }
                });



        buttonJugar.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        Object[][] data;
                       List<Object[]> ranking = iCtrlPresentacion.getRanking(llista.getSelectedValue());
                       System.out.println(llista.getSelectedValue());
                       if(ranking != null && ranking.size()!= 0) {
                           data = new Object[ranking.size()][2];

                       for(int i = 0; i< ranking.size(); i++ ){
                           data[i] = ranking.get(i);
                       }

                       iCtrlPresentacion.sincronizacionVistaTriar_a_Ranking(data);
                       }

                       else{
                           VistaDialogo vistaDialogo = new VistaDialogo();
                           String[] strBotones = {"Acceptar"};
                           int isel = vistaDialogo.setDialogo("Error", "Aquest problema té el ranking vuit",strBotones,3);
                       }

                    }
                });





        // Listeners para las opciones de menu




        // Listeners para el resto de componentes

      


    }


//////////////////////// Resto de metodos privados



    private void EditarButtonClick(){
        String FEN = llista.getSelectedValue();
        FitxaProblema[][] t = iCtrlPresentacion.ImportarProblema(FEN);
        iCtrlPresentacion.sincronizacionVistaCarregar_a_ProblemaImport(t);
    }


    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_menubarVista();
        inicializar_panelContenidos();
        inicializar_panelLLista();
        inicializar_panelUsuario();
        inicializar_panelBotones();
        asignar_listenersComponentes();
    }
    private void inicializar_panelLLista(){
        //CP.GetProblemes();
        String[] str = iCtrlPresentacion.getProblemes();
        //String[] str = {"primer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer","segon","tercer"};

        llista.setListData(str);
        JScrollPane scrollableList = new JScrollPane(llista);
        panelLlista.add(scrollableList);
    }

    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new FlowLayout());

        // Componentes

        // Buttons
        panelBotones.add(buttonVolver);
        panelBotones.add(buttonJugar);
        // Tooltips
    }


    private void inicializar_frameVista() {
        // Tamanyo
        frameVista.setMinimumSize(new Dimension(700,400));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(false);
        // Posicion y operaciones por defecto
        frameVista.setLocationRelativeTo(null);
        frameVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                iCtrlPresentacion.sincronizacionVistaTriar_a_Menu();
            }
        });

        // Se agrega panelContenidos al contentPane (el panelContenidos se
        // podria ahorrar y trabajar directamente sobre el contentPane)
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContenidos);

    }


    private void inicializar_menubarVista() {

    }

    private void inicializar_panelContenidos() {
        // Layout
        panelContenidos.setLayout(new BoxLayout(panelContenidos, BoxLayout.Y_AXIS));
        // Paneles

        panelContenidos.add(panelLlista);
        panelContenidos.add(panelBotones);
    }

    private void inicializar_panelLista() {
        // Layout
        panelLlista.setLayout(new FlowLayout());

        // Componentes

        // Buttons

        panelLlista.add(llista);
        // Tooltips
    }


    private void inicializar_panelUsuario(){
        JLabel label = new JLabel("Nom d'usuari");
        panelUsuario.add(label);
        panelUsuario.add(usuariField);
    }

}

////////////////////////

