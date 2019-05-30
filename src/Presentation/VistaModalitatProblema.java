package Presentation;

import Domain.Modalitat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

////////////////////////

public class VistaModalitatProblema {

    // Controlador de presentacion
    private CtrlPresentation iCtrlPresentacion;

    // Componentes de la interficie grafica
    private JFrame frameVista = new JFrame("Modalitat del problema");
    private JPanel panelContenidos = new JPanel();
    private JPanel panelOpciones = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JPanel panelSegonHuma = new JPanel();
    private JPanel panelAlgorisme1 = new JPanel();
    private JPanel panelAlgorisme2 = new JPanel();
    private JPanel panelModalitat = new JPanel();

    private JCheckBox segonHumaDefensa = new JCheckBox();
    private JSpinner profunditatA1;
    private JTextField segonHuma = new JTextField();
    private JSpinner profunditatA2;
    private JComboBox algorisme = new  JComboBox();
    private JComboBox modalitat = new  JComboBox();
    private JComboBox algorisme2 = new  JComboBox();
    private JButton buttonVolver = new JButton("Tornar");
    private JButton buttonJugar = new JButton("Jugar");

    private int index;

//////////////////////// Constructor y metodos publicos

    public void SetIndex(int i){
        this.index = i;
    }

    public void activar() {
        frameVista.setEnabled(true);
    }

    public VistaModalitatProblema(CtrlPresentation pCtrlPresentacion) {
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
        iCtrlPresentacion.sincronizacionVistaMod_a_Llista();
    }

    public void actionPerformed_buttonJugar (ActionEvent event) {

        String mod = (String) modalitat.getSelectedItem();
        String[] paramPartdia = new String[9];
        paramPartdia[0] = null;
        paramPartdia[1] = null;
        paramPartdia[2] = null;
        paramPartdia[3] = modalitat.getSelectedItem().toString();

        if (mod.equals("Humà vs humà")){
            if(segonHuma.getText().equals("")){
                VistaDialogo vistaDialogo = new VistaDialogo();
                String[] strBotones = {"Acceptar"};
                int isel = vistaDialogo.setDialogo("Error", "El nom d'usuari del contrincant no pot estar buit",strBotones,3);
                return;
            }
            paramPartdia[4] = segonHuma.getText();
            if (segonHumaDefensa.isSelected())
                paramPartdia[5] = "true";
            else
                paramPartdia[5] = "false";
        }
        else if (mod.equals("Humà vs màquina") || mod.equals("Màquina vs humà")){
            paramPartdia[4] = "root";
            paramPartdia[5] = algorisme.getSelectedItem().toString();
            paramPartdia[6] = profunditatA1.getValue().toString();
        }
        else{
            paramPartdia[4] = algorisme.getSelectedItem().toString();
            paramPartdia[5] = algorisme2.getSelectedItem().toString();
            paramPartdia[6] = profunditatA1.getValue().toString();
            paramPartdia[7] = profunditatA2.getValue().toString();
            paramPartdia[8] = null;
        }

        if(!iCtrlPresentacion.crearPartida(paramPartdia, false, index)){
            VistaDialogo vistaDialogo = new VistaDialogo();
            String[] strBotones = {"Acceptar"};
            int isel = vistaDialogo.setDialogo("Error", "El problema seleccionat no és vàlid",strBotones,3);
        }

        //iCtrlPresentacion.sincronizacionVistaModalitat_a_Tauler(paramPartdia, index);
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

        modalitat.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {

                String mod = (String) modalitat.getSelectedItem();
                panelContenidos.removeAll();
                panelContenidos.add(panelOpciones);

                if (mod.equals("Humà vs màquina")){

                    panelContenidos.add(panelAlgorisme1);
                    panelContenidos.remove(panelAlgorisme2);
                }

                else if (mod.equals("Màquina vs màquina")){
                    panelContenidos.add(panelAlgorisme1);
                    panelContenidos.add(panelAlgorisme2);
                    panelContenidos.remove(panelSegonHuma);
                }

                else if (mod.equals("Màquina vs humà")){
                    panelContenidos.add(panelAlgorisme1);
                    panelContenidos.remove(panelAlgorisme2);
                    panelContenidos.remove(panelSegonHuma);
                }

                else{
                    panelContenidos.add(panelSegonHuma);
                    panelContenidos.remove(panelAlgorisme1);
                    panelContenidos.remove(panelAlgorisme2);

                }
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
        inicializar_panelBotones();
        inicializar_panelSegonHuma();
        inicialitzar_panelAlgorisme1();
        inicialitzar_panelAlgorisme2();
        inicializar_panelProfunditatA1();
        inicializar_panelProfunditatA2();
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
                iCtrlPresentacion.sincronizacionVistaMod_a_Llista();
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
        panelContenidos.add(panelOpciones,BorderLayout.CENTER);
        panelContenidos.add(panelSegonHuma);
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

    private void inicializar_panelSegonHuma(){
        String[] stringTorns = {"1","2","3","4","5","6","7","8", "9", "10"};
        SpinnerListModel torns = new SpinnerListModel(stringTorns);
        panelSegonHuma.add(new JLabel("Nom del segon humà"));
        segonHuma.setColumns(10);
        panelSegonHuma.add(segonHuma);
        panelSegonHuma.add(segonHumaDefensa);
        panelSegonHuma.add(new JLabel("Defensor"));
    }

    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new FlowLayout());
        // Botones
        panelBotones.add(buttonVolver);
        panelBotones.add(buttonJugar);
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



}

////////////////////////
