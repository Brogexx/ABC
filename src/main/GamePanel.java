package main;

import ai.PathFinder;
import entity.Enemy;
import entity.Player;
import object.SuperObject;
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

    public TileManager tileM = new TileManager(this);
    KeyHandler keyH= new KeyHandler(this);
    Thread gameThread ;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public Player player = new Player(this,keyH);
    public Enemy enemy = new Enemy(this,keyH,player);
    public SuperObject obj[] = new SuperObject[10]; //bis zu 10 Objekte können erstellt werden im Spiel
    public PathFinder pFinder = new PathFinder(this);
    public UI ui = new UI(this);

    //Game State
    public int gameState;
    public final int startState = 1;
    public final int playState = 2;
    public final int endState = 3;


    //Konstruktor
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); // Panelgröße
        this.setBackground(Color.BLACK); //Hintergrundfarbe
        this.setDoubleBuffered(true); //verhindert flackern
        this.addKeyListener(keyH); //Tastatureingabe
        this.setFocusable(true);

    }

    public void setupGame(){

        aSetter.setObjects();
        gameState = startState;
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
                update(); //Spielzustand updaten
                repaint(); // neu zeichnen
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount); // Ausgabe FPS pro Sekunde
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

//Spiellogik aktualisieren
    public void update(){
        if (gameState == startState) {
            aSetter.resetObjects();
            aSetter.setObjects();
            player.setDefaultValues();
            enemy.setDefaultValues();
        }
        if (gameState == playState){
            player.update();
            enemy.update();
        }
        if (gameState == endState){
            //Do Nothing
        }


    }
    //Zeichnen aller Spielfunktionen
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;


        //Tile (Hintergrund)
        tileM.draw(g2);//tileM muss über player stehen aufgrund der Layer

        //Objekt
        for (int i = 0; i < obj.length; i++) {
            if(obj[i] != null){
                obj[i].draw(g2, this);
            }

        }
        //Player
        player.draw(g2);
        enemy.draw(g2);
        ui.draw(g2);


        g2.dispose(); //Freigabe der Ressourcen
    }

}
