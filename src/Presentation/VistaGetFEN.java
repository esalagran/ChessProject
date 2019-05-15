package Presentation;

import Domain.FitxaProblema;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

////////////////////////

public class VistaGetFEN {

    // Controlador de presentacion
    private CtrlPresentation iCtrlPresentacion;

    // Componentes de la interficie grafica
    private JFrame frameVista = new JFrame("Menu " +
            "principal");
    private JPanel panelContenidos = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JPanel panelUsuario = new JPanel();
    private JPanel panelContraseña = new JPanel();
    private JTextField FENField = new JTextField(10);
    private JTextField contraseñaField = new JPasswordField(10);
    private JButton buttonAccept= new JButton("Importar");
    private JButton buttonCancel= new JButton("Cancelar");


    // Resto de atributos
    private int iPanelActivo = 0;


//////////////////////// Constructor y metodos publicos


    public VistaGetFEN(CtrlPresentation pCtrlPresentacion) {
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



        buttonAccept.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {

                         FitxaProblema[][] t = iCtrlPresentacion.ImportarProblema(FENField.getText());
                         iCtrlPresentacion.sincronizacionVistaFEN_a_ProblemaImport(t);



                    }
                });

        // Listeners para las opciones de menu




        // Listeners para el resto de componentes




    }


//////////////////////// Resto de metodos privados


    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panelContenidos();
        inicializar_panelBotones();
        inicializar_panelUsuario();

        asignar_listenersComponentes();
    }

    private void inicializar_frameVista() {
        // Tamanyo
        frameVista.setMinimumSize(new Dimension(350,200));
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


    private void inicializar_panelContenidos() {
        // Layout
        panelContenidos.setLayout(new BoxLayout(panelContenidos, BoxLayout.Y_AXIS));
        // Paneles
        panelContenidos.add(Box.createVerticalStrut(15));

        panelContenidos.add(panelUsuario);
        panelContenidos.add(panelBotones);
    }

    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new FlowLayout());

        // Componentes

        // Buttons

        panelBotones.add(buttonAccept);
        panelBotones.add(buttonCancel);
        // Tooltips
    }


    private void inicializar_panelUsuario(){
        JLabel label = new JLabel("FEN");
        panelUsuario.add(label);
        panelUsuario.add(FENField);
    }

}

////////////////////////

