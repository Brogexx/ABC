package main;

import entity.Enemy;
import entity.Player;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.*;


public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 Pixel
    final int scale = 3; // 16 * 3 = 48 Pixel

    public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxScreenCol = 16; //Zeilenlänge
    public final int maxScreenRow = 16;// Spaltenlänge
    public final int screenWidth = tileSize * maxScreenCol; // Spielfeldbreite: 48 * 16 = 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // Spielfeldhöhe: 48 * 16 = 768 pixels

    // World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;


    //FPS(wie oft pro Sekunde das Spiel aktualisiert und neu gezeichnet wird)
    int FPS = 60;

    TileManager tileM = new TileManager(this);
    KeyHandler keyH= new KeyHandler();
    Thread gameThread ;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public Player player = new Player(this,keyH);
    public Enemy enemy = new Enemy(this,keyH,player);


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);//verhindert flackern
        this.addKeyListener(keyH);//Tastatureingabe
        this.setFocusable(true);

    }

public void startGameThread(){//damit das Spiel neben dem Hauptprogramm läuft und das Programm nicht einfriertSo (Update + Rendern)
        gameThread = new Thread(this);
        gameThread.start();
}

    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;


        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
            //         Was passiert hier?
//                    drawInterval ist die Zeit, die für ein Frame bei deiner Ziel-FPS vergehen soll (in Nanosekunden).
//            Die Variable delta summiert, wie viele Frames seit dem letzten Update "angekommen" sind.
//            while (gameThread != null) ist die Spielschleife, die immer weiterläuft, solange der Thread lebt.
//                    Innerhalb der Schleife:
//            Berechne, wie viel Zeit seit dem letzten Loop vergangen ist (currentTime - lastTime).
//                    Addiere das Verhältnis zur erwarteten Framezeit (drawInterval) auf delta.
//            Wenn delta >= 1, ist genug Zeit vergangen, um ein Frame zu zeichnen.
//            Dann wird:
//            update() aufgerufen (Spielzustand aktualisieren, z.B. Positionen, Logik)
//            repaint() aufgerufen (zeichnet das Spiel neu)
//            delta um 1 verringert (ein Frame wurde bearbeitet)
//            drawCount erhöht (Zähler für Frames in dieser Sekunde)
//            Alle 1 Sekunde (timer >= 1.000.000.000 ns) wird die aktuelle FPS ausgegeben (drawCount), und die Zähler werden zurückgesetzt.
//


        }
    }


    public void update(){

        player.update();
        enemy.update();

    }
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;


        tileM.draw(g2);//tileM muss über player stehen aufgrund der Layer

        player.draw(g2);
        enemy.draw(g2);

        g2.dispose();
    }

}
