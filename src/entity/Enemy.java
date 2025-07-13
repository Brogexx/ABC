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
    public int solidAreaX = 8;
    public int solidAreaY = 16;
    private Player player;


    public Enemy(GamePanel gp, KeyHandler keyH, Player player){

        this.gp = gp;
        this.keyH = keyH;
        this.player = player;

    // Kollisionserkennungsbereich definieren (Hitbox)
        solidArea = new Rectangle(solidAreaX, solidAreaY, 32, 32); // Area in der der Spieler erkannt wird
        solidAreaDefaultX = solidAreaX;
        solidAreaDefaultY = solidAreaY;

        setDefaultValues();
        getMonsterImage();
    }
    public void setDefaultValues (){
    // Setzt die Anfangsposition in Weltkoordinaten
        worldX = 23 * gp.tileSize;
        worldY = 14 * gp.tileSize;
        speed = 2;
        direction = "";
        screenX = 0;
        screenY = 0;
        worldX2 = 23 * gp.tileSize - gp.player.worldX + gp.player.screenX + screenX;
        worldY2 = 14 * gp.tileSize - gp.player.worldY + gp.player.screenY + screenY;


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
        int goalCol = (player.worldX+player.solidArea.x)/ gp.tileSize;
        int goalRow = (player.worldY+player.solidArea.y)/ gp.tileSize;

        searchPath(goalCol,goalRow);


    }

    public void searchPath(int goalCol, int goalRow){

        int startCol = (worldX + solidArea.x)/gp.tileSize;
        int startRow = (worldY+ solidArea.y)/gp.tileSize;

        gp.pFinder.setNodes(startCol,startRow, goalCol, goalRow);
        if(gp.pFinder.search() == true) {

            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int enLeftX = worldX + solidArea.x;
            int enRightX = worldX + solidArea.x + solidArea.width;
            int enTopY = worldY + solidArea.y;
            int enBottomY = worldY + solidArea.y + solidArea.height;

            if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "up";
            }
            else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize){
                direction = "down";
            }
            else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize){

                if(enLeftX > nextX){
                    direction = "left";
                }
                if(enLeftX < nextX){
                    direction = "right";
                }
            }
            else if (enTopY > nextY && enLeftX > nextX) {

                direction = "up";
                checkCollision();
                if(collisionOn) {
                    direction = "left";
                }
            } else if (enTopY > nextY && enLeftX < nextX) {

                direction = "up";
                checkCollision();
                if(collisionOn) {
                    direction = "right";
                }

            }
            else if (enTopY < nextY && enLeftX > nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn) {
                    direction = "left";
                }
            }

            else if (enTopY < nextY && enLeftX < nextX) {
                direction = "down";
                checkCollision();
                if(collisionOn) {
                    direction = "right";
                }
            }
        }
    }


    public void checkCollision() {
        collisionOn = false;
        worldX = 23 * gp.tileSize + screenX;
        worldY = 14 * gp.tileSize + screenY;
        gp.cChecker.checkTile(this);
    }


    public void update(){ //wird 60 mal die Sekunde aufgerufen

        move();
        //Check Tile Collision
        checkCollision();
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
        worldX2 = 23 * gp.tileSize - gp.player.worldX + gp.player.screenX + screenX;
        worldY2 = 14 * gp.tileSize - gp.player.worldY + gp.player.screenY + screenY;

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
