package entity;

// Importiert Klassen für grafische Darstellung
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {

    // Position der Entität in der Weltkarte
    public int worldX, worldY;
    // Bewegungsgeschwindigkeit der Entität
    public int speed;

    // Animationen: Zwei Bilder pro Richtung für laufende Bewegung
    public BufferedImage up1, up2 , down1, down2, left1, left2, right1, right2;
    // Aktuelle Bewegungsrichtung (z.b. "up", "down")
    public String direction;

    // Zähler und Zustand für die Animation
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // Rechteck zur Kollisionserkennung (Hitbox)
    public Rectangle solidArea;
    public boolean collisionOn = false;

}



