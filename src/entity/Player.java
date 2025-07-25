package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public final int screenX; //Position des Spielers bleibt gleich verändert sich nicht, nur der Background verändert sich
    public final int screenY;
    public int solidAreaX = 8;
    public int solidAreaY = 16;

    public int score;

    public Player(GamePanel gp, KeyHandler keyH){

        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2); //halbiert den Hintergrund und den Spieler Tile , um die Mitte des Spiels zu haben
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(solidAreaX, solidAreaY, 32, 32); // Area in der der Spieler erkannt wird
        solidAreaDefaultX = solidAreaX;
        solidAreaDefaultY = solidAreaY;

        setDefaultValues();
        getPlayerImage();
    }
    public void setDefaultValues (){

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "";
        score = 0;


    }


    public void getPlayerImage(){

        try{
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Player_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Player_right_2.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }


    public void update(){ //wird 60 mal die Sekunde aufgerufen
        if(keyH.upPressed == true){
            direction = "up";
        } else if(keyH.downPressed == true){
            direction = "down";
        } else if(keyH.leftPressed == true){
            direction = "left";
        } else if(keyH.rightPressed == true){
            direction = "right";
        }

        //Check Tile Collision
        collisionOn = false;
        gp.cChecker.checkTile(this);

        //Check Object Collision
        int objIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objIndex);

        //Check Enemy Collision
        gp.cChecker.checkEnemy(this);

        // If Collision is false, Player can move
        if(collisionOn == false){
            switch (direction){
                case "up": worldY   -= speed;
                break;
                case "down": worldY += speed;
                    break;
                case "left": worldX -= speed;
                    break;
                case "right": worldX += speed;
                    break;
            }
        }

        spriteCounter++; //alle 15 Frames ändert sich der Spieler-Image
        if (spriteCounter > 15){
            if (spriteNum == 1){
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void pickUpObject(int i) {
        if(i != -1) {
            score++;
            gp.obj[i] = null;
        }

        if(score == 7) {
            gp.playSound(3);
            gp.gameState = gp.endState;
        }
    }


    public void draw(Graphics2D g2){

//        g2.setColor(Color.white);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
        BufferedImage image = null;

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
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);


    }

}
