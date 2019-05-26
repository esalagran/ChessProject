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
    private JPanel panelTorns = new JPanel();
    private JPanel panelDificultat = new JPanel();
    private JPanel panelColor = new JPanel();
    private JPanel panelAlgorisme1 = new JPanel();
    private JPanel panelAlgorisme2 = new JPanel();

    private JSpinner tornsPerMat;
    private JComboBox modalitat = new  JComboBox();
    private JComboBox color = new  JComboBox();
    private JComboBox algorisme = new  JComboBox();
    private JComboBox algorisme2 = new  JComboBox();
    private JButton buttonVolver = new JButton("Volver");
    private JButton buttonJugar = new JButton("Jugar");
    private JTextArea textareaInformacion = new JTextArea("HOLA",5,25);

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

        iCtrlPresentacion.sincronizacionVistaModalitat_a_Tauler(mode, index);
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
                }

                else if (mod.equals("Màquina vs humà")){
                    panelContenidos.add(panelAlgorisme1);
                    panelContenidos.remove(panelAlgorisme2);
                }

                    else{
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
        inicializar_panelColor();
        inicializar_panelBotones();
        inicializar_panelTorns();
        inicializar_panelDificultat();
        inicialitzar_panelAlgorisme1();
        inicialitzar_panelAlgorisme2();
        asignar_listenersComponentes();


    }

    private void inicializar_frameVista() {
        // Tamanyo
        frameVista.setMinimumSize(new Dimension(600,400));
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
        panelContenidos.add(panelOpciones,BorderLayout.CENTER);
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
