package Presentation;
import Domain.*;

import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.plaf.FontUIResource;

public class TaulerGUICrearProblema {



    private JFrame f = new JFrame("Crear problema");
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private Image[][] chessPieceImages = new Image[2][6];
    private JPanel chessBoard;
    private FitxaProblema[][] tauler;
    private JLabel message = new JLabel(
            "Selecciona una casella per començar!");
    private JLabel FEN = new JLabel("FEN: 8/8/8/8/8/8/8/8  ");
    private static final String COLS = "ABCDEFGH";
    public static final int QUEEN = 1, KING = 0,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int[] STARTING_ROW = {
            ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK
    };
    public static final int BLACK = 0, WHITE = 1;

    private boolean clicked = false;
    private ParInt clickCoord;
    private Color clickColor;
    private CtrlPresentation CP;
    private Domain.Color torn;
    private JToolBar tools = new JToolBar();
    private boolean isPieceSelected = false;
    private ParInt pieceCoord;
    private boolean moving = false;
    private boolean imported = false;


    public void visible(boolean vis){
        f.setVisible(vis);
    }
    public void desactivar() {
        f.setEnabled(false);
    }
    public void activar() {
        f.setEnabled(true);
    }



    TaulerGUICrearProblema(CtrlPresentation pr, FitxaProblema[][] t) {
        CP = pr;
        initializeGui();
        tauler = t;
        dibuixarTauler();
        imported = true;
    }

    TaulerGUICrearProblema(CtrlPresentation pr) {
        CP = pr;
        initializeGui();
        tauler = new FitxaProblema[8][8];

    }


    public void close(){
        System.exit(0);
    }
    private void dibuixarTauler( ){
        if(tauler!= null){

            FEN.setText("FEN: " + CP.GetFENProblema().split(" ")[0]);

        for(int i = 0; i< 8; i++){
            for(int j = 0; j< 8; j++){

                if(tauler[i][j] != null){
                    Fitxa f = tauler[i][j].getIFitxa();
                    Domain.Color c = tauler[i][j].GetColor();

                    if(f instanceof Cavall){

                        if(c == Domain.Color.negre)
                            chessBoardSquares[j][i].setIcon(new ImageIcon(
                                    chessPieceImages[BLACK][KNIGHT]));
                        else chessBoardSquares[j][i].setIcon(new ImageIcon(
                                chessPieceImages[WHITE][KNIGHT]));
                    }
                    if(f instanceof Peo){
                        if(c == Domain.Color.negre)
                            chessBoardSquares[j][i].setIcon(new ImageIcon(
                                    chessPieceImages[BLACK][PAWN]));
                        else chessBoardSquares[j][i].setIcon(new ImageIcon(
                                chessPieceImages[WHITE][PAWN]));
                    }
                    if(f instanceof Alfil){
                        if(c == Domain.Color.negre)
                            chessBoardSquares[j][i].setIcon(new ImageIcon(
                                    chessPieceImages[BLACK][BISHOP]));
                        else chessBoardSquares[j][i].setIcon(new ImageIcon(
                                chessPieceImages[WHITE][BISHOP]));

                    }

                    if(f instanceof Torre){
                        if(c == Domain.Color.negre)
                            chessBoardSquares[j][i].setIcon(new ImageIcon(
                                    chessPieceImages[BLACK][ROOK]));
                        else chessBoardSquares[j][i].setIcon(new ImageIcon(
                                chessPieceImages[WHITE][ROOK]));
                    }

                    if(f instanceof Rei){

                        if(c == Domain.Color.negre)
                            chessBoardSquares[j][i].setIcon(new ImageIcon(
                                    chessPieceImages[BLACK][KING]));
                        else chessBoardSquares[j][i].setIcon(new ImageIcon(
                                chessPieceImages[WHITE][KING]));

                    }

                    if(f instanceof Dama){

                        if(c == Domain.Color.negre)
                            chessBoardSquares[j][i].setIcon(new ImageIcon(
                                    chessPieceImages[BLACK][QUEEN]));
                        else chessBoardSquares[j][i].setIcon(new ImageIcon(
                                chessPieceImages[WHITE][QUEEN]));
                    }

                }else{
                    chessBoardSquares[j][i].setIcon(null);
                }

            }
        }

    }
    }

    public final void initializeGui() {
        // create the images for the chess pieces
        createImages();
        message.setFont(new Font(message.getFont().getName(), Font.BOLD, 10));
        FEN.setFont(new Font(message.getFont().getName(), Font.BOLD, 10));

        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        JButton moveButton = new JButton("Moure");
        tools.add(moveButton);
        moveButton.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        if(clicked && chessBoardSquares[clickCoord.GetFirst()][clickCoord.GetSecond()].getIcon() != null ) {
                            Write("Clicka a la posició destí de la peça");
                            moving = true;
                        }
                        else Write("No hi ha cap peça seleccionada");
                    }
                });
        JButton deleteButton = new JButton("Eliminar");

        tools.add(deleteButton);
        deleteButton.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        if(clicked && chessBoardSquares[clickCoord.GetFirst()][clickCoord.GetSecond()].getIcon() != null ){
                            delete();
                            Write("Peça eliminada");
                        }
                        else Write("No hi ha cap peça seleccionada");

                    }
                });
        tools.addSeparator();

        Action importarFEN = new AbstractAction("Importar") {

            @Override
            public void actionPerformed(ActionEvent e) {

                CP.sincronizacionVistaProblema_a_FEN();
            }
        };
        tools.add(importarFEN);

        Action tryProblemaAction = new AbstractAction("Provar problema") {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("No implementat encara");
            }
        };
        tools.add(tryProblemaAction);
        JButton validarButton = new JButton("Validar");
        tools.add(validarButton);

        validarButton.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        /*
                        if(CP.ValidarProblema(4, true)){
                            VistaDialogo vistaDialogo = new VistaDialogo();
                            String[] strBotones = {"Acceptar", "Tornar"};
                            int isel = vistaDialogo.setDialogo("Validar problema", "El problema és vàlid",strBotones,3);
                        }

                        else{
                            VistaDialogo vistaDialogo = new VistaDialogo();
                            String[] strBotones = {"Acceptar", "Tornar"};
                            int isel = vistaDialogo.setDialogo("Validar problema", "El problema no és vàlid",strBotones,3);
                        }
*/
                        CP.sincronitzacioProblemaAprofunditat();
                    }
                });

        JButton saveButton = new JButton("Guardar");

        saveButton.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        CP.GuardarProblema();
                        Write("S'ha guardat el problema");

                    }
                });
        tools.add(saveButton);


        JButton sortirButton = new JButton("Sortir");
        sortirButton.addActionListener
                (new ActionListener() {
                    public void actionPerformed (ActionEvent event) {
                        CP.sincronizacionEditarProblema_a_Menu();

                    }
                });

        tools.add(sortirButton);
        tools.addSeparator();
        tools.add(message);

        //gui.add(new JLabel("?"), BorderLayout.LINE_START);

        JPanel lateralButtons = new JPanel(new GridLayout(6, 2));
        //lateralButtons.add(new JLabel("FEN: "));
        //lateralButtons.add(FEN);
        gui.add(FEN, BorderLayout.SOUTH);

        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 6 ; j++){
                JButton button = new JButton();
                button.setIcon(new ImageIcon(chessPieceImages[i][j]));
                button.setBackground(Color.WHITE);
                button.setSize(20,20);
                lateralButtons.add(button);

                button.addActionListener
                        (new ActionListener() {
                            public void actionPerformed (ActionEvent event) {
                                String texto = ((JButton) event.getSource()).getText();

                                int i = (button.getY()/96);
                                int j = (button.getX()/98);
                                int a = i/3;
                                int b = (j + i*2)%6;
                                /*
                                int i = (button.getY());
                                int j = (button.getX());

                                System.out.println(i);
                                System.out.println(j);
                                */

                                selectPiece(new ParInt(a,b));


                                //clickOnCoord(new ParInt(i,j));


                            }
                        });

            }}

        gui.add(lateralButtons,  BorderLayout.LINE_START);


        chessBoard = new JPanel(new GridLayout(0, 8)) {

            /**
             * Override the preferred size to return the largest it can, in
             * a square shape.  Must (must, must) be added to a GridBagLayout
             * as the only component (it uses the parent as a guide to size)
             * with no GridBagConstaint (so it is centered).
             */
            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int)d.getWidth(),(int)d.getHeight());
                } else if (c!=null &&
                        c.getWidth()>d.getWidth() &&
                        c.getHeight()>d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (w>h ? h : w);
                return new Dimension(s,s);
            }
        };
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8,8,8,8),
                new LineBorder(Color.BLACK)
        ));
        // Set the BG to be ochre
        Color ochre = Color.gray;
        chessBoard.setBackground(ochre);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
        boardConstrain.setBackground(ochre);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);

        // create the chess board squares
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);

                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..


                b.addActionListener
                        (new ActionListener() {
                            public void actionPerformed (ActionEvent event) {
                                String texto = ((JButton) event.getSource()).getText();
                                int i = (b.getY()/70);
                                int j = (b.getX()/70);
                                System.out.println(i);
                                System.out.println(j);
                                clickOnCoord(new ParInt(j,i));


                            }
                        });
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                chessBoardSquares[jj][ii] = b;
            }
        }

        /*
         * fill the chess board
         */

        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
          /*  chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                            SwingConstants.CENTER));*/
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    //case 0:
                       //chessBoard.add(new JLabel("" + (9-(ii + 1)),
                         //       SwingConstants.CENTER));
                   //     break;
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
    }

    private void delete(){

        ParInt inv1 = new ParInt(clickCoord.GetSecond(), clickCoord.GetFirst());
        tauler =  CP.EliminarFitxa(inv1);
        dibuixarTauler();
    }

    public final JComponent getGui() {
        return gui;
    }

    private final void createImages() {
        try {
            URL url = new URL("http://i.stack.imgur.com/memI0.png");
            BufferedImage bi = ImageIO.read(url);
            for (int ii = 0; ii < 2; ii++) {
                for (int jj = 0; jj < 6; jj++) {
                    chessPieceImages[ii][jj] = bi.getSubimage(
                            jj * 64, ii * 64, 64, 64);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void Write(String str){
        message.setText(str);
    }

    /**
     * Initializes the icons of the initial chess board piece places
     */
    private final void setupNewGame() {
        message.setText("Make your move!");
        // set up the black pieces
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][0].setIcon(new ImageIcon(
                    chessPieceImages[BLACK][STARTING_ROW[ii]]));
        }
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][1].setIcon(new ImageIcon(
                    chessPieceImages[BLACK][PAWN]));
        }
        // set up the white pieces
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][6].setIcon(new ImageIcon(
                    chessPieceImages[WHITE][PAWN]));
        }
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][7].setIcon(new ImageIcon(
                    chessPieceImages[WHITE][STARTING_ROW[ii]]));
        }
    }

    private void clickOnCoord(ParInt coord){
        dibuixarTauler();
        if(moving){
            ParInt inv1 = new ParInt(clickCoord.GetSecond(), clickCoord.GetFirst());
            ParInt inv2 = new ParInt(coord.GetSecond(), coord.GetFirst());
            tauler = CP.MourePeçaProblema(inv1, inv2);
            moving = false;
            Write("Pots moure o eliminar la peça amb els botons de la part superior");

        }

        if(!clicked){

            clickCoord = coord;
            clickColor = chessBoardSquares[clickCoord.GetFirst()][clickCoord.GetSecond()].getBackground();
            chessBoardSquares[clickCoord.GetFirst()][clickCoord.GetSecond()].setBackground(Color.orange);
            if (chessBoardSquares[clickCoord.GetFirst()][clickCoord.GetSecond()].getIcon() == null)
                Write("Prem sobre una peça de la barra lateral per afegir-la al tauler");
            else{
                Write("Pots moure o eliminar la peça amb els botons de la part superior");
            }
            clicked = true;
        }
        else if(clickCoord.GetFirst() == coord.GetFirst() && clickCoord.GetSecond() == coord.GetSecond()){
            chessBoardSquares[clickCoord.GetFirst()][clickCoord.GetSecond()].setBackground(clickColor);
            clicked = false;

            Write("Seleciona una casella");

        }


        else {
            chessBoardSquares[clickCoord.GetFirst()][clickCoord.GetSecond()].setBackground(clickColor);
            clickCoord = coord;
            clickColor = chessBoardSquares[clickCoord.GetFirst()][clickCoord.GetSecond()].getBackground();
            chessBoardSquares[clickCoord.GetFirst()][clickCoord.GetSecond()].setBackground(Color.orange);
            if (chessBoardSquares[clickCoord.GetFirst()][clickCoord.GetSecond()].getIcon() == null)
                Write("Prem sobre una peça de la barra lateral per afegir-la al tauler");
            else{
                Write("Pots moure o eliminar la peça amb els botons de la part superior");
            }
        }

        dibuixarTauler();

        }



    private void selectPiece(ParInt p){
        isPieceSelected = true;
        pieceCoord = p;
        if(clicked){
            afegeixPeça();
            Write("Pots moure o eliminar la peça amb els botons de la part superior");
        }
    }


    private void afegeixPeça(){
       // chessBoardSquares[clickCoord.GetFirst()][clickCoord.GetSecond()].setIcon(new ImageIcon(chessPieceImages[pieceCoord.GetFirst()][pieceCoord.GetSecond()]));
        ParInt inv = new ParInt(clickCoord.GetSecond(), clickCoord.GetFirst());
        tauler = CP.AfegirPeçaProblema(pieceCoord.GetSecond(), pieceCoord.GetFirst(), inv );
        dibuixarTauler();


    }

    private void tornContrari(){
        if(torn == Domain.Color.blanc)
            torn = Domain.Color.negre;
        else torn = Domain.Color.blanc;
    }


    public void run() {

        TaulerGUICrearProblema cg = new TaulerGUICrearProblema(CP);


        if(imported)
            cg = new TaulerGUICrearProblema(CP, tauler);



        f.add(cg.getGui());
        // Ensures JVM closes after frame(s) closed and
        // all non-daemon threads are finished
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        f.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                CP.sincronizacionEditarProblema_a_Menu();
            }
        });

        // See https://stackoverflow.com/a/7143398/418556 for demo.
        f.setLocationByPlatform(true);
        f.setResizable(false);
        // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        f.pack();
        // ensures the minimum size is enforced.
        f.setMinimumSize(f.getSize());
        f.setVisible(true);
    }

    // Swing GUIs should be created and updated on the EDT
    // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
}