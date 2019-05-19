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

public class TaulerGUI {


    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private Image[][] chessPieceImages = new Image[2][6];
    private JPanel chessBoard;
    private FitxaProblema[][] tauler;
    private JLabel message = new JLabel(
            "Chess Champ is ready to play!");
    private static final String COLS = "ABCDEFGH";
    public static final int QUEEN = 1, KING = 0,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int[] STARTING_ROW = {
            ROOK, KNIGHT, BISHOP, KING, QUEEN, BISHOP, KNIGHT, ROOK
    };
    public static final int BLACK = 0, WHITE = 1;

    private boolean firstClick = true;
    private ParInt firstCoord;
    private ParInt secondCoord;
    private Color firstColor;
    private CtrlPresentation CP;
    private Domain.Color torn;
    private JToolBar tools = new JToolBar();
    private boolean hasEnded;


    TaulerGUI( FitxaProblema[][] t, Domain.Color pt,  CtrlPresentation pr) {
        CP = pr;
        tauler = t;
        torn = pt;
        message = new JLabel(pt.toString());
        initializeGui();
        if(!CP.isColorHuman(torn)){
         tauler =  CP.TornMaquina();
         tornContrari();
        }
        dibuixarTauler(tauler);
    }

    private void dibuixarTauler(FitxaProblema[][] t ){

        for(int i = 0; i< 8; i++){
            for(int j = 0; j< 8; j++){

                if(t[i][j] != null){
                TipusPeça tP = Convert.ClassToTipusPeça(t[i][j].getIFitxa().getClass().toString());
                Domain.Color c = t[i][j].GetColor();

                if(tP == TipusPeça.Cavall){

                    if(c == Domain.Color.negre)
                        chessBoardSquares[j][i].setIcon(new ImageIcon(
                                chessPieceImages[BLACK][KNIGHT]));
                    else chessBoardSquares[j][i].setIcon(new ImageIcon(
                            chessPieceImages[WHITE][KNIGHT]));
                }
                if(tP == TipusPeça.Peo){
                    if(c == Domain.Color.negre)
                        chessBoardSquares[j][i].setIcon(new ImageIcon(
                                chessPieceImages[BLACK][PAWN]));
                    else chessBoardSquares[j][i].setIcon(new ImageIcon(
                            chessPieceImages[WHITE][PAWN]));
                }
                if(tP == TipusPeça.Alfil){
                    if(c == Domain.Color.negre)
                        chessBoardSquares[j][i].setIcon(new ImageIcon(
                                chessPieceImages[BLACK][BISHOP]));
                    else chessBoardSquares[j][i].setIcon(new ImageIcon(
                            chessPieceImages[WHITE][BISHOP]));

                }

                if(tP == TipusPeça.Torre){
                    if(c == Domain.Color.negre)
                        chessBoardSquares[j][i].setIcon(new ImageIcon(
                                chessPieceImages[BLACK][ROOK]));
                    else chessBoardSquares[j][i].setIcon(new ImageIcon(
                            chessPieceImages[WHITE][ROOK]));
                }

                if(tP == TipusPeça.Rei){

                    if(c == Domain.Color.negre)
                        chessBoardSquares[j][i].setIcon(new ImageIcon(
                                chessPieceImages[BLACK][KING]));
                    else chessBoardSquares[j][i].setIcon(new ImageIcon(
                            chessPieceImages[WHITE][KING]));

                }

                if(tP == TipusPeça.Dama){

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

    public final void initializeGui() {
        // create the images for the chess pieces
        createImages();

        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        Action newGameAction = new AbstractAction("New") {

            @Override
            public void actionPerformed(ActionEvent e) {
                setupNewGame();
            }
        };
        tools.add(newGameAction);
        tools.add(new JButton("Save")); // TODO - add functionality!
        tools.add(new JButton("Restore")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(new JButton("Resign")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(message);

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
        Color ochre = Color.white;
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
                                clickOnCoord(new ParInt(i,j));


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
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
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

        System.out.println(coord.GetFirst() + " " + coord.GetSecond());

        if(firstClick){
            firstCoord = coord;
            firstClick = false;
            firstColor = chessBoardSquares[firstCoord.GetSecond()][firstCoord.GetFirst()].getBackground();
            chessBoardSquares[firstCoord.GetSecond()][firstCoord.GetFirst()].setBackground(Color.orange);
        }
        else if(firstCoord.GetFirst() == coord.GetFirst() && firstCoord.GetSecond() == coord.GetSecond()){
            firstClick = true;
            chessBoardSquares[firstCoord.GetSecond()][firstCoord.GetFirst()].setBackground(firstColor);
            firstColor = null;



        }

        else{
            secondCoord = coord;
            System.out.println("MOVE FROM " + firstCoord.GetFirst() +"," + firstCoord.GetSecond()  + " TO " + secondCoord.GetFirst() +"," + secondCoord.GetSecond() );
            chessBoardSquares[firstCoord.GetSecond()][firstCoord.GetFirst()].setBackground(firstColor);
            firstColor = null;
            FitxaProblema[][] t = CP.MourePeçaPartida(firstCoord, secondCoord);
            if(t!= null){
                dibuixarTauler(t);
                tornContrari();
                tools.remove(message);
                message = new JLabel(torn.toString());
                tools.add(message);

            }
            firstCoord = null;
            firstClick = true;

            if (!CP.isColorHuman(torn)) {
               t = CP.TornMaquina();
               dibuixarTauler(t);
                tornContrari();



            tools.remove(message);
            message = new JLabel(torn.toString());
            tools.add(message);
            }

            hasEnded = CP.hasEnded();

            if(hasEnded){
                VistaDialogo vistaDialogo = new VistaDialogo();
                String[] strBotones = {"Rematch", "Sortir"};
                int isel = vistaDialogo.setDialogo("Fi de la partida", CP.EndedReason(),strBotones,3);
                System.out.println("Resultado del dialogo: " + isel + " " + strBotones[isel]);
            }


        }
    }

    private void tornContrari(){
        if(torn == Domain.Color.blanc)
            torn = Domain.Color.negre;
        else torn = Domain.Color.blanc;
    }

            public void run() {
                TaulerGUI cg = new TaulerGUI(tauler, torn, CP);

                JFrame f = new JFrame("ChessChamp");
                f.add(cg.getGui());
                // Ensures JVM closes after frame(s) closed and
                // all non-daemon threads are finished
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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