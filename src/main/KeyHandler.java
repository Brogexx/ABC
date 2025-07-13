package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    //Zur Verfolgung der gedrückten Tasten
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) { // Wird bei Eingabe eines "Zeichens" aufgerufen (z.B. Buchstabe, Zahl)

    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W){
            upPressed = true;

        }
        if(code == KeyEvent.VK_S){
            downPressed = true;

        }
        if(code == KeyEvent.VK_A){
            leftPressed = true;

        }
        if(code == KeyEvent.VK_D){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_SPACE){
            if(gp.gameState == gp.startState) {
                gp.gameState = gp.playState;
            }

            if(gp.gameState == gp.endState) {
                gp.gameState = gp.startState;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();


        if(code == KeyEvent.VK_W){
            upPressed = false;

        }
        if(code == KeyEvent.VK_S){
            downPressed = false;

        }
        if(code == KeyEvent.VK_A){
            leftPressed = false;

        }
        if(code == KeyEvent.VK_D){
            rightPressed = false;

        }


    }
}
