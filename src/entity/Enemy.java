package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Enemy extends Entity {
    GamePanel gp; // Zugriff auf Spielfeld-Informationen
    KeyHandler keyH;

    public int screenX = 0;
    public int screenY = 0;
    public int worldX2 = 0;
    public int worldY2 = 0;
    private Player player;

    int actionLockCounter = 119; // Zähler, um die Bewegungsrichtung regelmäßig zu ändern

    public Enemy(GamePanel gp, KeyHandler keyH, Player player){

        this.gp = gp;
        this.keyH = keyH;
        this.player = player;

    // Kollisionserkennungsbereich definieren (Hitbox)
        solidArea = new Rectangle(8, 16, 32, 32); // Area in der der Spieler erkannt wird


        setDefaultValues();
        getMonsterImage();
    }
    public void setDefaultValues (){
    // Setzt die Anfangsposition in Weltkoordinaten
        worldX = 20 * gp.tileSize;
        worldY = 21 * gp.tileSize;
        speed = 4;
        direction = "";


    }


    public void getMonsterImage(){

        // Lädt die Bilder für jede Bewegungsrichtung (2 Frames pro Richtung)
        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/enemy/orc_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/enemy/orc_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/enemy/orc_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/enemy/orc_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/enemy/orc_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/enemy/orc_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/enemy/orc_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/enemy/orc_right_2.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void move(){
        actionLockCounter++;
        // Alle 120 Frames (ca. 2 Sekunden bei 60 FPS) neue Richtung wählen
        if(actionLockCounter == 120) {

            Random random = new Random();
            int i = random.nextInt(100) + 1; // pick up a number from 1 to 100

            if(i <= 25) {
                direction = "up";
            }
            if(i > 25 && i <= 50) {
                direction = "down";
            }
            if(i > 50 && i <= 75) {
                direction = "left";
            }
            if(i > 75 && i <= 100) {
                direction = "right";
            }

            actionLockCounter = 0;
        }

    }


    public void update(){ //wird 60 mal die Sekunde aufgerufen

        move();
        //Check Tile Collision
        collisionOn = false;
        worldX = 20 * gp.tileSize + screenX; // Weltposition des Gegners neu berechnen
        worldY = 21 * gp.tileSize + screenY;
        gp.cChecker.checkTile(this); // Prüfen, ob Bewegung zu Kollision führt
        System.out.println(collisionOn);
        // If Collision is false, Player can move
        if(collisionOn == false){
            switch (direction){
                case "up": screenY   -= speed;
                    break;
                case "down": screenY += speed;
                    break;
                case "left": screenX -= speed;
                    break;
                case "right": screenX += speed;
                    break;
            }
        }
        // Gegnerposition im Verhältnis zur Spielerposition
        worldX2 = 20 * gp.tileSize - gp.player.worldX + gp.player.screenX + screenX;
        worldY2 = 21 * gp.tileSize - gp.player.worldY + gp.player.screenY + screenY;

        // Animation: Wechsel zwischen Sprite 1 und 2 alle 15 Frames
        spriteCounter++;
        if (spriteCounter > 15){
            if (spriteNum == 1){
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }


    public void draw(Graphics2D g2){

//       g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;

        // Wählt basierend auf Richtung und Frame das passende Bild aus
        switch(direction){
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                break;
            case "":
                image = down1;
        }

        g2.drawImage(image, worldX2, worldY2, gp.tileSize, gp.tileSize, null);


    }


}
