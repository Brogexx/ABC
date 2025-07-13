package main;

import org.w3c.dom.css.Rect;

import java.awt.*;

public class UI {

    GamePanel gp;
    Graphics2D g2;

    Font font;
    Font fontBold;

    public UI(GamePanel gp) {
        this.gp = gp;

        font = new Font("Arial", Font.PLAIN, 40);
        fontBold = new Font("Arial", Font.BOLD, 60);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(font);
        g2.setColor(Color.WHITE);

        if(gp.gameState == gp.startState) {
            g2.drawString("Score: " + gp.player.score, 25, 50);
            g2.drawString("Press Space", gp.screenWidth/2-100, gp.screenHeight/2-80);
            g2.drawString("to start the Game", gp.screenWidth/2-140, gp.screenHeight/2-40);
        }

        if(gp.gameState == gp.playState) {
            g2.drawString("Score: " + gp.player.score, 25, 50);
        }

        if(gp.gameState == gp.endState) {
            Rectangle endScreen = new Rectangle(gp.screenWidth/2-250, gp.screenHeight/2-250, 500, 500);
            g2.setColor(Color.DARK_GRAY);
            g2.fill(endScreen);
            if(gp.player.score == 7) {
                g2.setColor(Color.ORANGE);
                g2.setFont(fontBold);
                g2.drawString("Victory !", gp.screenWidth/2-110, gp.screenHeight/2-180);

                g2.setFont(font);
                g2.setColor(Color.WHITE);
                g2.drawString("Score: " + gp.player.score, gp.screenWidth/2-70, gp.screenHeight/2-100);
                g2.drawString("Press Space to play again", gp.screenWidth/2-230, gp.screenHeight/2+100);
            }
            else {
                g2.setColor(Color.RED);
                g2.setFont(fontBold);
                g2.drawString("Defeat !", gp.screenWidth/2-100, gp.screenHeight/2-180);

                g2.setFont(font);
                g2.setColor(Color.WHITE);
                g2.drawString("Score: " + gp.player.score, gp.screenWidth/2-70, gp.screenHeight/2-100);
                g2.drawString("Press Space to try again", gp.screenWidth/2-210, gp.screenHeight/2+100);
            }
        }
    }
}
