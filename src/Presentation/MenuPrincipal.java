package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

////////////////////////

public class MenuPrincipal {

    // Controlador de presentacion
    private CtrlPresentation iCtrlPresentacion;

    // Componentes de la interficie grafica
    private JFrame frameVista = new JFrame("Menu " +
            "principal");
    private JPanel panelContenidos = new JPanel();
    private JPanel panelInformacion = new JPanel();
    private JPanel panelInformacionA = new JPanel();
    private JPanel panelInformacion1 = new JPanel();
    private JPanel panelInformacion2 = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JButton buttonJugarPartida= new JButton("Jugar partida");
    private JButton buttonCarregarProblema = new JButton("Carregar problema");
    private JButton buttonCrearProblema = new JButton("Crear problema");
    private JButton buttonVeureRanking = new JButton("Veure ranking");


    // Menus
    private JMenuBar menubarVista = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuitemQuit = new JMenuItem("Quit");

    // Resto de atributos
    private int iPanelActivo = 0;


//////////////////////// Constructor y metodos publicos


    public MenuPrincipal (CtrlPresentation pCtrlPresentacion) {
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


    public void actionPerformed_buttonJugarPartida (ActionEvent event) {
            iCtrlPresentacion.sincronizacionVistaMenu_a_Tipus();

    }

    public void actionPerformed_buttonCarregarProblema (ActionEvent event) {
       iCtrlPresentacion.sincronizacionVistaMenu_a_Carregar();
    }

    public void actionPerformed_buttonCrearProblema (ActionEvent event) {
        iCtrlPresentacion.sincronizacionMenu_a_CrearProblema();

    }

    public void actionPerformed_buttonVeureRanking (ActionEvent event) {

    }


//////////////////////// Asignacion de listeners


    private void asignar_listenersComponentes() {

        // Listeners para los botones

        buttonJugarPartida.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickado el boton con texto: " + texto);
                        actionPerformed_buttonJugarPartida(event);
                    }
                });

        buttonCarregarProblema.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickado el boton con texto: " + texto);
                        actionPerformed_buttonCarregarProblema(event);
                    }
                });

        buttonCrearProblema.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickado el boton con texto: " + texto);
                        actionPerformed_buttonCrearProblema(event);
                    }
                });

        buttonVeureRanking.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickado el boton con texto: " + texto);
                        actionPerformed_buttonVeureRanking(event);
                    }
                });

        // Listeners para las opciones de menu


        menuitemQuit.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JMenuItem) event.getSource()).getText();
                        System.out.println("Has seleccionado el menuitem con texto: " + texto);
                        System.exit(0);
                    }
                });

        // Listeners para el resto de componentes




    }


//////////////////////// Resto de metodos privados


    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_menubarVista();
        inicializar_panelContenidos();
        //inicializar_panelInformacion();
        //inicializar_panelInformacion1();
        inicializar_panelBotones();
        asignar_listenersComponentes();
    }

    private void inicializar_frameVista() {
        // Tamanyo
        frameVista.setMinimumSize(new Dimension(700,400));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(false);
        // Posicion y operaciones por defecto
        frameVista.setLocationRelativeTo(null);
        frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Se agrega panelContenidos al contentPane (el panelContenidos se
        // podria ahorrar y trabajar directamente sobre el contentPane)
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContenidos);
    }


    private void inicializar_menubarVista() {

        menuFile.add(menuitemQuit);

        frameVista.setJMenuBar(menubarVista);
    }

    private void inicializar_panelContenidos() {
        // Layout
        panelContenidos.setLayout(new BorderLayout());
        // Paneles
        panelContenidos.add(panelBotones, BorderLayout.CENTER);

    }

    private void inicializar_panelInformacion() {
        // El panelInformacion es solo un contenedor para panelInformacionA, que
        // contendra panelInformacion1 (inicialmente) o panelInformacion2
        panelInformacionA = panelInformacion1;
        iPanelActivo = 1;
        panelInformacion.add(panelInformacionA);
    }

    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        // Componentes
        buttonJugarPartida.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonCarregarProblema.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonCrearProblema.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonVeureRanking.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        int n  = 40; //SPACING
        panelBotones.add(Box.createVerticalStrut(20));
        panelBotones.add(buttonJugarPartida);
        panelBotones.add(Box.createVerticalStrut(n));
        panelBotones.add(buttonCarregarProblema);
        panelBotones.add(Box.createVerticalStrut(n));
        panelBotones.add(buttonCrearProblema);
        panelBotones.add(Box.createVerticalStrut(n));
        panelBotones.add(buttonVeureRanking);
        // Tooltips
        buttonJugarPartida.setToolTipText("Llama al controlador de dominio con la informacion del ComboBox");
        buttonCarregarProblema.setToolTipText("Abre una nueva ventana sincronizada");
        buttonCrearProblema.setToolTipText("Cambia el panel de informacion");
        buttonVeureRanking.setToolTipText("Abre un Dialogo modal simple");
    }

}

////////////////////////

