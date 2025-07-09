package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum [] [];

    public TileManager(GamePanel gp){

        this.gp = gp;

        tile = new Tile[10];//Größe
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];


        getTileImage();
        loadMap();

    }

    public void getTileImage(){
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));// PNG wird in image übertragen welches als Buffered Image in der CLass Tile definiert wurde

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(){

        try {
            InputStream is = getClass().getResourceAsStream("/maps/map01.txt.rtf"); //Textfile wird importiert und wird durch den BufferedReader ausgelesen
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){

                String line = br.readLine(); //List die Text Quelle aus

                while(col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");//Spaltet die Linien und gibt die tile Nummer one by one aus
                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row ++;

                }
            }
            br.close();

        }catch(Exception e){}


    }

    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize; // 0 * 48 = 0, 1 * 48 = 48 ...
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX; // Wird benötigt um zu wissen, wo die Pixels gezeichnet werden sollen, da der Spieler immer mittig ist wird er benötigt in dem Code
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
            worldCol++;


            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;

            }
        }

    }


}
