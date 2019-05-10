package Presentation;

import Domain.Modalitat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

////////////////////////

public class VistaTipusProblema {

    // Controlador de presentacion
    private CtrlPresentation iCtrlPresentacion;

    // Componentes de la interficie grafica
    private JFrame frameVista = new JFrame("Tipus de problema");
    private JPanel panelContenidos = new JPanel();
    private JPanel panelOpciones = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JPanel panelTorns = new JPanel();
    private JPanel panelDificultat = new JPanel();

    private JSpinner tornsPerMat;
    private JComboBox modalitat = new  JComboBox();
    private JButton buttonVolver = new JButton("Volver");
    private JButton buttonJugar = new JButton("Jugar");
    private JTextArea textareaInformacion = new JTextArea("HOLA",5,25);


//////////////////////// Constructor y metodos publicos


    public VistaTipusProblema(CtrlPresentation pCtrlPresentacion) {
        iCtrlPresentacion = pCtrlPresentacion;
        inicializarComponentes();
    }

    public void hacerVisible() {
        frameVista.pack();
        frameVista.setVisible(true);
    }

    public void visible(boolean vis){
        frameVista.setVisible(vis);
    }

    public void desactivar() {
        frameVista.setEnabled(false);
    }



//////////////////////// Metodos de las interfaces Listener


    public void actionPerformed_buttonLlamadaDominio (ActionEvent event) {
        ArrayList<String> resulDominio = iCtrlPresentacion.llamadaDominio2();
        for (int i = 0; i < resulDominio.size(); i++)
            System.out.println("Obtenido de dominio: " + resulDominio.get(i));
        // Informa el contenido de algunos componentes (es un ejemplo)
        for (int i = 0; i < resulDominio.size(); i++)
            textareaInformacion.append("\n" + resulDominio.get(i));

    }
    public void actionPerformed_buttonVolver (ActionEvent event) {
        iCtrlPresentacion.sincronizacionVistaTipus_a_Menu();
    }

    public void actionPerformed_buttonJugar (ActionEvent event) {
       String mod = (String) modalitat.getSelectedItem();
            Modalitat mode = Modalitat.HH;
       if (mod.equals("Humà vs humà"))
           mode = Modalitat.HH;
       else if (mod.equals("Humà vs màquina"))
           mode = Modalitat.HM;
       else if (mod.equals("Màquina vs màquina"))
           mode = Modalitat.MM;
       else if (mod.equals("Màquina vs humà"))
           mode = Modalitat.MH;

        iCtrlPresentacion.sincronizacionVistaTipus_a_Tauler(mode);
    }


//////////////////////// Asignacion de listeners


    private void asignar_listenersComponentes() {

        // Listeners para los botones

        buttonVolver.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickado el boton con texto: " + texto);
                        actionPerformed_buttonVolver(event);
                    }
                });

        buttonJugar.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickado el boton con texto: " + texto);
                        actionPerformed_buttonJugar(event);
                    }
                });
    }


//////////////////////// Resto de metodos privados


    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panelContenidos();
        inicializar_panelModalitat();
        inicializar_panelBotones();
        inicializar_panelTorns();
        inicializar_panelDificultat();
        asignar_listenersComponentes();


    }

    private void inicializar_frameVista() {
        // Tamanyo
        frameVista.setMinimumSize(new Dimension(400,200));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(true);
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
        panelContenidos.add(panelOpciones, BorderLayout.NORTH);
        panelContenidos.add(panelDificultat, BorderLayout.CENTER);
        panelContenidos.add(panelTorns, BorderLayout.SOUTH);
        panelContenidos.add(panelBotones, BorderLayout.AFTER_LAST_LINE);

    }

    private void inicializar_panelModalitat() {
        modalitat.addItem("Humà vs humà");
        modalitat.addItem("Humà vs màquina");
        modalitat.addItem("Màquina vs humà");
        modalitat.addItem("Màquina vs màquina");
        JLabel label = new JLabel("Modalitat");
        panelOpciones.add(label);
        panelOpciones.add(new JScrollPane(modalitat));

    }

    private void inicializar_panelTorns(){

        String[] stringTorns = {"1","2","3","4","5","6","7","8"};
        SpinnerListModel torns = new SpinnerListModel(stringTorns);
        tornsPerMat = new JSpinner(torns);
        JLabel label = new JLabel("Torns per mat");
        panelTorns.add(label);
        panelTorns.add(tornsPerMat);
    }

    private void inicializar_panelDificultat(){

        String[] stringTorns = {"Fàcil", "Mitjà", "Difícil"};
        SpinnerListModel torns = new SpinnerListModel(stringTorns);
        tornsPerMat = new JSpinner(torns);
        JLabel label = new JLabel("Dificultat");
        panelDificultat.add(label);
        panelDificultat.add(tornsPerMat);
    }

    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new FlowLayout());
        // Botones
        panelBotones.add(buttonVolver);
        panelBotones.add(buttonJugar);
    }



}

////////////////////////
