package Presentation;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

////////////////////////

public class VistaPrincipal {

    // Controlador de presentacion
    private CtrlPresentation iCtrlPresentacion;

    // Componentes de la interficie grafica
    private JFrame frameVista = new JFrame("Vista Principal");
    private JPanel panelContenidos = new JPanel();
    private JPanel panelInformacion = new JPanel();
    private JPanel panelInformacionA = new JPanel();
    private JPanel panelInformacion1 = new JPanel();
    private JPanel panelInformacion2 = new JPanel();
    private JPanel panelBotones = new JPanel();
    private JButton buttonLlamadaDominio = new JButton("Llamada Dominio");
    private JButton buttonAbrirJFrame = new JButton("Abrir JFrame");
    private JButton buttonCambiarPanel = new JButton("Cambiar Panel");
    private JButton buttonAbrirDialog = new JButton("Abrir Dialog (modal)");
    private JLabel labelPanelInformacion1 = new JLabel("Panel Informacion 1");
    private JComboBox comboboxInformacion1 = new JComboBox();
    private JTextArea textareaInformacion1 = new JTextArea(15,25);
    private JLabel labelPanelInformacion2 = new JLabel("Panel Informacion 2");
    private JTextField textfieldInformacion2 = new JTextField();
    private JSpinner spinnerInformacion2 = new JSpinner();
    private JSlider sliderInformacion2 = new JSlider();

    // Menus
    private JMenuBar menubarVista = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem menuitemQuit = new JMenuItem("Quit");
    private JMenu menuOpciones = new JMenu("Opciones");
    private JMenuItem menuitemLlamadaDominio = new JMenuItem("Llamada Dominio");
    private JMenuItem menuitemAbrirJFrame = new JMenuItem("Abrir JFrame");
    private JMenuItem menuitemCambiarPanel = new JMenuItem("Cambiar Panel");
    private JMenuItem menuitemAbrirDialog = new JMenuItem("Abrir Dialog");

    // Resto de atributos
    private int iPanelActivo = 0;


//////////////////////// Constructor y metodos publicos


    public VistaPrincipal (CtrlPresentation pCtrlPresentacion) {
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


//////////////////////// Metodos de las interfaces Listener


    public void actionPerformed_buttonLlamadaDominio (ActionEvent event) {
        String comboboxSelectedItem =
                (String) comboboxInformacion1.getModel().getSelectedItem();
        ArrayList<String> resulDominio =
                iCtrlPresentacion.llamadaDominio1(comboboxSelectedItem);
        for (int i = 0; i < resulDominio.size(); i++)
            System.out.println("Obtenido de dominio: " + resulDominio.get(i));
        // Informa el contenido de algunos componentes (es un ejemplo)
        for (int i = 0; i < resulDominio.size(); i++)
            textareaInformacion1.append("\n" + resulDominio.get(i));
        SpinnerListModel spinnerModel =
                (SpinnerListModel) spinnerInformacion2.getModel();
        spinnerModel.setList(resulDominio);
    }

    public void actionPerformed_buttonAbrirJFrame (ActionEvent event) {
        System.out.println("Antes de crear la vista secundaria");
        iCtrlPresentacion.sincronizacionVistaPrincipal_a_Secundaria();
        System.out.println("Despues de crear la vista secundaria");
    }

    public void actionPerformed_buttonCambiarPanel (ActionEvent event) {
        // Cambio de panel
        if (iPanelActivo != 0) {
            iPanelActivo = iPanelActivo%2 + 1;
            System.out.println("Cambiando a panel " + iPanelActivo + "...");
            panelInformacion.remove(panelInformacionA);
            if (iPanelActivo == 1)
                panelInformacionA = panelInformacion1;
            else
                panelInformacionA = panelInformacion2;
            panelInformacion.add(panelInformacionA);
            frameVista.pack();
            frameVista.repaint();
        }
    }

    public void actionPerformed_buttonAbrirDialog (ActionEvent event) {
        // Abre una ventana con un dialogo
        VistaDialogo vistaDialogo = new VistaDialogo();
        String[] strBotones = {"Si","No","Cancel"};
        int isel = vistaDialogo.setDialogo("Titulo del Dialogo","Pregunta del Dialogo",strBotones,3);
        System.out.println("Resultado del dialogo: " + isel + " " + strBotones[isel]);
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

        buttonAbrirJFrame.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickado el boton con texto: " + texto);
                        actionPerformed_buttonAbrirJFrame(event);
                    }
                });

        buttonCambiarPanel.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickado el boton con texto: " + texto);
                        actionPerformed_buttonCambiarPanel(event);
                    }
                });

        buttonAbrirDialog.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JButton) event.getSource()).getText();
                        System.out.println("Has clickado el boton con texto: " + texto);
                        actionPerformed_buttonAbrirDialog(event);
                    }
                });

        // Listeners para las opciones de menu

        menuitemLlamadaDominio.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JMenuItem) event.getSource()).getText();
                        System.out.println("Has seleccionado el menuitem con texto: " + texto);
                        actionPerformed_buttonLlamadaDominio(event);
                    }
                });

        menuitemAbrirJFrame.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JMenuItem) event.getSource()).getText();
                        System.out.println("Has seleccionado el menuitem con texto: " + texto);
                        actionPerformed_buttonAbrirJFrame(event);
                    }
                });

        menuitemCambiarPanel.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JMenuItem) event.getSource()).getText();
                        System.out.println("Has seleccionado el menuitem con texto: " + texto);
                        actionPerformed_buttonCambiarPanel(event);
                    }
                });

        menuitemAbrirDialog.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JMenuItem) event.getSource()).getText();
                        System.out.println("Has seleccionado el menuitem con texto: " + texto);
                        actionPerformed_buttonAbrirDialog(event);
                    }
                });

        menuitemQuit.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        String texto = ((JMenuItem) event.getSource()).getText();
                        System.out.println("Has seleccionado el menuitem con texto: " + texto);
                        System.exit(0);
                    }
                });

        // Listeners para el resto de componentes

        sliderInformacion2.addChangeListener
                (new ChangeListener() {
                    public void stateChanged (ChangeEvent event) {
                        int sliderValue = sliderInformacion2.getValue();
                        System.out.println(sliderValue);
                        textfieldInformacion2.setText(Integer.toString(sliderValue));
                    }
                });


    }


//////////////////////// Resto de metodos privados


    private void inicializarComponentes() {
        inicializar_frameVista();
        inicializar_menubarVista();
        inicializar_panelContenidos();
        inicializar_panelInformacion();
        inicializar_panelInformacion1();
        inicializar_panelInformacion2();
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
        menuOpciones.add(menuitemLlamadaDominio);
        menuOpciones.add(menuitemAbrirJFrame);
        menuOpciones.add(menuitemCambiarPanel);
        menuOpciones.add(menuitemAbrirDialog);
        menubarVista.add(menuFile);
        menubarVista.add(menuOpciones);
        frameVista.setJMenuBar(menubarVista);
    }

    private void inicializar_panelContenidos() {
        // Layout
        panelContenidos.setLayout(new BorderLayout());
        // Paneles
        panelContenidos.add(panelBotones,BorderLayout.NORTH);
        panelContenidos.add(panelInformacion,BorderLayout.CENTER);
    }

    private void inicializar_panelInformacion() {
        // El panelInformacion es solo un contenedor para panelInformacionA, que
        // contendra panelInformacion1 (inicialmente) o panelInformacion2
        panelInformacionA = panelInformacion1;
        iPanelActivo = 1;
        panelInformacion.add(panelInformacionA);
    }

    private void inicializar_panelInformacion1() {
        // Layout
        panelInformacion1.setLayout(new BorderLayout());
        // Componentes
        panelInformacion1.add(labelPanelInformacion1,BorderLayout.NORTH);
        comboboxInformacion1.addItem("combobox opcion1");
        comboboxInformacion1.addItem("combobox opcion2");
        comboboxInformacion1.addItem("combobox opcion3");
        panelInformacion1.add(comboboxInformacion1,BorderLayout.EAST);
        textareaInformacion1.setText("Text Area Informacion1");
        panelInformacion1.add(new JScrollPane(textareaInformacion1),BorderLayout.SOUTH);
    }

    private void inicializar_panelInformacion2() {
        // Layout
        panelInformacion2.setLayout(new BorderLayout());
        // Componentes
        panelInformacion2.add(labelPanelInformacion2,BorderLayout.NORTH);
        textfieldInformacion2.setText("30");
        textfieldInformacion2.setPreferredSize(new Dimension(40,40));
        panelInformacion2.add(textfieldInformacion2,BorderLayout.WEST);
        String[] spinnerStrings = {"spinner string1","spinner string2"};
        SpinnerListModel spinnerStringsModel = new SpinnerListModel(spinnerStrings);
        spinnerInformacion2.setModel(spinnerStringsModel);
        spinnerInformacion2.setPreferredSize(new Dimension(500,20));
        panelInformacion2.add(spinnerInformacion2,BorderLayout.SOUTH);
        sliderInformacion2.setMinimum(0);
        sliderInformacion2.setMaximum(50);
        sliderInformacion2.setValue(30);
        sliderInformacion2.setMajorTickSpacing(10);
        sliderInformacion2.setOrientation(JSlider.HORIZONTAL);
        sliderInformacion2.setPaintLabels(true);
        sliderInformacion2.setPaintTicks(false);
        sliderInformacion2.setPreferredSize(new Dimension(100,50));
        panelInformacion2.add(sliderInformacion2,BorderLayout.CENTER);
    }

    private void inicializar_panelBotones() {
        // Layout
        panelBotones.setLayout(new FlowLayout());
        // Componentes
        panelBotones.add(buttonLlamadaDominio);
        panelBotones.add(buttonAbrirJFrame);
        panelBotones.add(buttonCambiarPanel);
        panelBotones.add(buttonAbrirDialog);
        // Tooltips
        buttonLlamadaDominio.setToolTipText("Llama al controlador de dominio con la informacion del ComboBox");
        buttonAbrirJFrame.setToolTipText("Abre una nueva ventana sincronizada");
        buttonCambiarPanel.setToolTipText("Cambia el panel de informacion");
        buttonAbrirDialog.setToolTipText("Abre un Dialogo modal simple");
    }

}

////////////////////////

