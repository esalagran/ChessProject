package Presentation;

import javax.swing.*;

public class MainClass {



    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            () -> {
                CtrlPresentation CP = new CtrlPresentation();
                CP.initializePresentation();
            }
        );
        //CP.PGNtoFEN();
    }




}
