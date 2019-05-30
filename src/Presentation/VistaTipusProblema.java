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
    private JPanel panelColor = new JPanel();
    private JPanel panelAlgorisme1 = new JPanel();
    private JPanel panelAlgorisme2 = new JPanel();
    private JPanel panelNumProblemes = new JPanel();
    private JPanel panelSegonHumà = new JPanel();

    private JCheckBox segonHumaDefensa = new JCheckBox();
    private JTextField segonHumà = new JTextField();
    private JSpinner tornsPerMat;
    private JSpinner profunditatA2;
    private JSpinner profunditatA1;
    private JSpinner numProblemes;
    private JComboBox modalitat = new  JComboBox();
    private JComboBox color = new  JComboBox();
    private JComboBox algorisme = new  JComboBox();
    private JComboBox algorisme2 = new  JComboBox();
    private JComboBox dificultat = new  JComboBox();
    private JButton buttonVolver = new JButton("Volver");
    private JButton buttonJugar = new JButton("Jugar");
    private JButton buttonJugarAleatori = new JButton("Jugar aleatori");


//////////////////////// Constructor y metodos publicos

    public void activar() {
        frameVista.setEnabled(true);
    }

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

    public void actionPerformed_buttonVolver (ActionEvent event) {
        iCtrlPresentacion.sincronizacionVistaTipus_a_Menu();
    }

    public void actionPerformed_buttonJugar (ActionEvent event) {

        String mod = (String) modalitat.getSelectedItem();
        String[] paramPartdia = new String[9];
        paramPartdia[0] = dificultat.getSelectedItem().toString();
        paramPartdia[1] = color.getSelectedItem().toString();
        paramPartdia[2] = tornsPerMat.getValue().toString();
        paramPartdia[3] = modalitat.getSelectedItem().toString();

        if (mod.equals("Humà vs humà")){
            if(segonHumà.getText().equals("")){
                VistaDialogo vistaDialogo = new VistaDialogo();
                String[] strBotones = {"Acceptar"};
                int isel = vistaDialogo.setDialogo("Error", "El nom d'usuari del contrincant no pot estar buit",strBotones,3);
                return;
            }
            paramPartdia[4] = segonHumà.getText();
            if (segonHumaDefensa.isSelected())
                paramPartdia[5] = "true";
            else
                paramPartdia[5] = "false";
        }
        else if (mod.equals("Humà vs màquina") || mod.equals("Màquina vs humà")){
           paramPartdia[4] = algorisme.getSelectedItem().toString();
           paramPartdia[5] = profunditatA1.getValue().toString();
        }
        else{
            paramPartdia[4] = algorisme.getSelectedItem().toString();
            paramPartdia[5] = algorisme2.getSelectedItem().toString();
            paramPartdia[6] = profunditatA1.getValue().toString();
            paramPartdia[7] = profunditatA2.getValue().toString();
            paramPartdia[8] = numProblemes.getValue().toString();
        }

        if(!iCtrlPresentacion.crearPartida(paramPartdia, false, -1)){
            VistaDialogo vistaDialogo = new VistaDialogo();
            String[] strBotones = {"Acceptar"};
            int isel = vistaDialogo.setDialogo("Error", "No s'ha trobat cap problema amb aquests filtres.",strBotones,3);
        }
    }

    public void actionPerformed_buttonJugarAleatori (ActionEvent event) {

        String mod = (String) modalitat.getSelectedItem();
        String[] paramPartdia = new String[9];
        paramPartdia[0] = dificultat.getSelectedItem().toString();
        paramPartdia[1] = color.getSelectedItem().toString();
        paramPartdia[2] = tornsPerMat.getValue().toString();
        paramPartdia[3] = modalitat.getSelectedItem().toString();

        if (mod.equals("Humà vs humà")){
            paramPartdia[4] = segonHumà.getText();
            if(paramPartdia[4].equals("")){
                VistaDialogo vistaDialogo = new VistaDialogo();
                String[] strBotones = {"Acceptar"};
                int isel = vistaDialogo.setDialogo("Error", "El nom d'usuari del contrincant no pot estar vuit",strBotones,3);
                return;
            }   
        
            if (segonHumaDefensa.isSelected())
                paramPartdia[5] = "false";
            else
                paramPartdia[5] = "true";
        }
        else if (mod.equals("Humà vs màquina") || mod.equals("Màquina vs humà")){
            paramPartdia[4] = algorisme.getSelectedItem().toString();
            paramPartdia[5] = profunditatA1.getValue().toString();
        }
        else{
            paramPartdia[4] = algorisme.getSelectedItem().toString();
            paramPartdia[5] = algorisme2.getSelectedItem().toString();
            paramPartdia[6] = profunditatA1.getValue().toString();
            paramPartdia[7] = profunditatA2.getValue().toString();
            paramPartdia[8] = numProblemes.getValue().toString();
        }

        if(!iCtrlPresentacion.crearPartida(paramPartdia, true, -1)){
            VistaDialogo vistaDialogo = new VistaDialogo();
            String[] strBotones = {"Acceptar"};
            int isel = vistaDialogo.setDialogo("Error", "No s'ha trobat cap problema amb aquests filtres.",strBotones,3);
        }
    }


//////////////////////// Asignacion de listeners


    private void asignar_listenersComponentes() {

        // Listeners para los botones

        buttonVolver.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {

                        actionPerformed_buttonVolver(event);
                    }
                });

        buttonJugarAleatori.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        actionPerformed_buttonJugarAleatori(event);

                    }
                });

        buttonJugar.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {

                        actionPerformed_buttonJugar(event);
                    }
                });

        modalitat.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {

                String mod = (String) modalitat.getSelectedItem();
                panelContenidos.removeAll();
                panelContenidos.add(panelOpciones);

                if (mod.equals("Humà vs màquina") || mod.equals("Màquina vs humà")){
                    panelContenidos.add(panelAlgorisme1);
                    panelContenidos.remove(panelAlgorisme2);
                    panelContenidos.remove(panelNumProblemes);
                }

                else if (mod.equals("Màquina vs màquina")){
                    panelContenidos.add(panelAlgorisme1);
                    panelContenidos.add(panelAlgorisme2);
                    panelContenidos.add(panelNumProblemes);
                }
                else if(mod.equals("Humà vs humà")){
                    panelContenidos.add(panelSegonHumà);
                }

                else{
                    panelContenidos.remove(panelAlgorisme1);
                    panelContenidos.remove(panelAlgorisme2);
                    panelContenidos.remove(panelNumProblemes);
                }

                panelContenidos.add(panelDificultat, BorderLayout.CENTER);
                panelContenidos.add(panelTorns, BorderLayout.SOUTH);
                panelContenidos.add(panelColor);
                panelContenidos.add(panelBotones, BorderLayout.AFTER_LAST_LINE);
                frameVista.pack();
                frameVista.repaint();

            }
        });
    }


//////////////////////// Resto de metodos privados


    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panelContenidos();
        inicializar_panelModalitat();
        inicializar_panelColor();
        inicializar_panelBotones();
        inicializar_panelTorns();
        inicializar_panelDificultat();
        inicialitzar_panelAlgorisme1();
        inicialitzar_panelAlgorisme2();
        inicializar_panelProfunditatA1();
        inicializar_panelProfunditatA2();
        inicializar_panelNumeroProblemes();
        asignar_listenersComponentes();
    }

    private void inicializar_frameVista() {
        // Tamanyo
        frameVista.setMinimumSize(new Dimension(600,400));
        frameVista.setPreferredSize(frameVista.getMinimumSize());
        frameVista.setResizable(true);
        // Posicion y operaciones por defecto
        frameVista.setLocationRelativeTo(null);
        frameVista.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                iCtrlPresentacion.sincronizacionVistaTipus_a_Menu();
            }
        });
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
        panelContenidos.add(panelSegonHumà);
        panelContenidos.add(panelDificultat, BorderLayout.CENTER);
        panelContenidos.add(panelTorns, BorderLayout.SOUTH);
        panelContenidos.add(panelColor);
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


    private void inicializar_panelColor() {
        color.addItem("Blanc");
        color.addItem("Negre");
        JLabel label = new JLabel("Color");
        panelColor.add(label);
        panelColor.add(new JScrollPane(color));

    }

    private void inicialitzar_panelAlgorisme1() {
        algorisme.addItem("MinMax");
        algorisme.addItem("AlphaBeta");
        JLabel label = new JLabel("Màquina 1");
        panelAlgorisme1.add(label);
        panelAlgorisme1.add(new JScrollPane(algorisme));

    }

    private void inicialitzar_panelAlgorisme2() {
        algorisme2.addItem("MinMax");
        algorisme2.addItem("AlphaBeta");
        JLabel label = new JLabel("Màquina 2");
        panelAlgorisme2.add(label);
        panelAlgorisme2.add(new JScrollPane(algorisme2));
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
        dificultat.addItem("Molt fàcil");
        dificultat.addItem("Fàcil");
        dificultat.addItem("Mitjà");
        dificultat.addItem("Difícil");
        dificultat.addItem("Molt Difícil");
        JLabel label = new JLabel("Dificultat");
        panelDificultat.add(label);
        panelDificultat.add(dificultat);
    }

    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new FlowLayout());
        // Botones
        panelBotones.add(buttonVolver);
        panelBotones.add(buttonJugar);
        panelBotones.add(buttonJugarAleatori);
    }

    private void inicializar_panelProfunditatA1(){
        String[] stringTorns = {"1","2","3","4","5","6","7"};
        SpinnerListModel torns = new SpinnerListModel(stringTorns);
        profunditatA1 = new JSpinner(torns);
        JLabel label = new JLabel("Profunditat");
        panelAlgorisme1.add(label);
        panelAlgorisme1.add(profunditatA1);
    }

    private void inicializar_panelProfunditatA2(){
        String[] stringTorns = {"1","2","3","4","5","6","7"};
        SpinnerListModel torns = new SpinnerListModel(stringTorns);
        profunditatA2 = new JSpinner(torns);
        JLabel label = new JLabel("Profunditat");
        panelAlgorisme2.add(label);
        panelAlgorisme2.add(profunditatA2);
    }

    private void inicializar_panelNumeroProblemes(){
        String[] stringTorns = {"1","2","3","4","5","6","7","8", "9", "10"};
        SpinnerListModel torns = new SpinnerListModel(stringTorns);
        numProblemes = new JSpinner(torns);
        JLabel label = new JLabel("Problemes a jugar");
        panelNumProblemes.add(label);
        panelNumProblemes.add(numProblemes);
        panelSegonHumà.add(new JLabel("Nom del segon humà"));
        segonHumà.setColumns(10);
        panelSegonHumà.add(segonHumà);
        panelSegonHumà.add(segonHumaDefensa);
        panelSegonHumà.add(new JLabel("Defensor"));
    }




}

////////////////////////
