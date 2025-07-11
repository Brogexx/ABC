package main;

import javax.swing.*;

public class main {
    public static void main (String [] args){

        JFrame window = new JFrame(); //neues GUI-Fenster
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //schließt Fenster bei beednden
        window.setResizable(false); // setzt Fenster auf feste Größe
        window.setTitle("Hunted-The Adventure"); // Titel des Fenster

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null); // zentriert das Fenster
        window.setVisible(true); // Sichtbarkeit


        gamePanel.startGameThread();//startet den Spiel-Loop
        gamePanel.setupGame();//Objekte werden generiert
    }
}
