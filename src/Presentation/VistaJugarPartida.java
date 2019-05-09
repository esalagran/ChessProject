package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

////////////////////////

public class VistaJugarPartida {

    // Controlador de presentacion
    private CtrlPresentation iCtrlPresentacion;

    // Componentes de la interficie grafica
    private JFrame frameVista = new JFrame("Vista Secundaria");
    private JPanel panelContenidos = new JPanel();
    private JPanel panelInformacion = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JButton buttonLlamadaDominio = new JButton("Llamada Dominio");
    private JButton buttonVolver = new JButton("Volver");
    private JTextArea textareaInformacion = new JTextArea(5,25);


//////////////////////// Constructor y metodos publicos


    public VistaJugarPartida (CtrlPresentation pCtrlPresentacion) {
        iCtrlPresentacion = pCtrlPresentacion;
        inicializarComponentes();
    }

    public void hacerVisible() {
        frameVista.pack();
        frameVista.setVisible(true);
    }

    public void hacerInvisible() {
        frameVista.setVisible(false);
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


//////////////////////// Asignacion de listeners


    private void asignar_listenersComponentes() {

        // Listeners para los botones

        buttonLlamadaDominio.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickado el boton con texto: " + texto);
                        actionPerformed_buttonLlamadaDominio(event);
                    }
                });

        buttonVolver.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickado el boton con texto: " + texto);
                        actionPerformed_buttonVolver(event);
                    }
                });
    }


//////////////////////// Resto de metodos privados


    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_panelContenidos();
        inicializar_panelInformacion();
        inicializar_panelBotones();
        asignar_listenersComponentes();
    }

    private void inicializar_frameVista() {
        // Tamanyo
        frameVista.setMinimumSize(new Dimension(400,200));
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
        panelContenidos.setLayout(new BorderLayout());
        // Paneles
        panelContenidos.add(panelBotones,BorderLayout.NORTH);
        panelContenidos.add(panelInformacion,BorderLayout.CENTER);
    }

    private void inicializar_panelInformacion() {
        textareaInformacion.setText("Text Area Informacion");
        panelInformacion.add(new JScrollPane(textareaInformacion));
    }

    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new FlowLayout());
        // Botones
        panelBotones.add(buttonLlamadaDominio);
        panelBotones.add(buttonVolver);
    }

}

////////////////////////
