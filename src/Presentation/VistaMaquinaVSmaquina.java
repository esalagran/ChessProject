package Presentation;

import Domain.FitxaProblema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

////////////////////////

public class VistaMaquinaVSmaquina {

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
    private JPanel panelTaule = new JPanel();
    private JTextField usuariField = new JTextField(10);
    private JTextField contraseñaField = new JPasswordField(10);
    private JButton buttonJugar= new JButton("Jugar");
    private JButton buttonEditar= new JButton("Editar");
    private JButton buttonVolver= new JButton("Volver");
    private String[] columnNames = {"Problema",
            "Algorisme1",
            "Temps1",
            "Algorsime2",
            "Temps2",
            "Guanyador",
            "Motiu"

    };
    private JTable taula;

    // Menus
    private JMenuBar menubarVista = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuitemQuit = new JMenuItem("Quit");
    private JMenu menuOpciones = new JMenu("Opciones");

    // Resto de atributos
    private int iPanelActivo = 0;


//////////////////////// Constructor y metodos publicos


    public VistaMaquinaVSmaquina(CtrlPresentation pCtrlPresentacion, Object[][] data) {

        iCtrlPresentacion = pCtrlPresentacion;
        taula = new JTable(data, columnNames);
        panelTaule.add(taula);
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
                        iCtrlPresentacion.sincronizacionVistaCarregar_a_Menu();
                    }
                });







        // Listeners para las opciones de menu




        // Listeners para el resto de componentes

      


    }


//////////////////////// Resto de metodos privados




    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_menubarVista();
        inicializar_panelContenidos();
        inicializar_panelUsuario();
        inicializar_panelBotones();
        asignar_listenersComponentes();
    }

    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new FlowLayout());

        // Componentes

        // Buttons
        panelBotones.add(buttonVolver);
        panelBotones.add(buttonEditar);
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
                iCtrlPresentacion.sincronizacionVistaCarregar_a_Menu();
            }
        });

        // Se agrega panelContenidos al contentPane (el panelContenidos se
        // podria ahorrar y trabajar directamente sobre el contentPane)
        JPanel contentPane = (JPanel) frameVista.getContentPane();
        contentPane.add(panelContenidos);

    }


    private void inicializar_menubarVista() {

        menuFile.add(menuitemQuit);
        menubarVista.add(menuFile);
        menubarVista.add(menuOpciones);
        frameVista.setJMenuBar(menubarVista);
    }

    private void inicializar_panelContenidos() {
        // Layout
        panelContenidos.setLayout(new BoxLayout(panelContenidos, BoxLayout.Y_AXIS));
        // Paneles

        panelContenidos.add(panelTaule);
        panelContenidos.add(panelBotones);
    }



    private void inicializar_panelUsuario(){
        JLabel label = new JLabel("Nom d'usuari");
        panelUsuario.add(label);
        panelUsuario.add(usuariField);
    }

}

////////////////////////

