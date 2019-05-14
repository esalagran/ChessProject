package Presentation;

import javax.swing.*;

////////////////////////

public class VistaDialogoConInput {

    public VistaDialogoConInput() {
    }

    public int setDialogo
            (String strTitulo, String strTexto, String[] strBotones, int iTipo) {

        int oTipo = 1;
        switch (iTipo) {
            case 0: oTipo = JOptionPane.ERROR_MESSAGE; break;
            case 1: oTipo = JOptionPane.INFORMATION_MESSAGE; break;
            case 2: oTipo = JOptionPane.WARNING_MESSAGE; break;
            case 3: oTipo = JOptionPane.QUESTION_MESSAGE; break;
            case 4: oTipo = JOptionPane.PLAIN_MESSAGE; break;
        }

        // Crea y viisualiza el dialogo
        JOptionPane optionPane = new JOptionPane(strTexto);
        JPanel inputPanel = new JPanel();
        JLabel FENLabel = new JLabel("FEN: ");
        JTextField FEN = new JTextField(10);
        inputPanel.add(FENLabel);
        inputPanel.add(FEN);
        optionPane.setOptions(strBotones);

        JDialog dialogOptionPane = optionPane.createDialog(new JFrame().add(inputPanel),strTitulo);
        //dialogOptionPane.add(inputPanel);

        dialogOptionPane.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialogOptionPane.pack();
        dialogOptionPane.setVisible(true);

        // Captura la opcion elegida
        String vsel = (String) optionPane.getValue();
        int isel;
        for (isel = 0;
             isel < strBotones.length && !strBotones[isel].equals(vsel); isel++);
        return isel;
    }

}

////////////////////////
