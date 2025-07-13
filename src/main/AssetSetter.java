package main;

import object.OBJ_door;
import object.OBJ_gold;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObjects(){

        gp.obj[0] = new OBJ_gold();
        gp.obj[0].worldX = 3 * gp.tileSize;
        gp.obj[0].worldY = 41 * gp.tileSize;

        gp.obj[1] = new OBJ_gold();
        gp.obj[1].worldX = 19 * gp.tileSize;
        gp.obj[1].worldY = 39 * gp.tileSize;

        gp.obj[2] = new OBJ_gold();
        gp.obj[2].worldX = 32 * gp.tileSize;
        gp.obj[2].worldY = 39 * gp.tileSize;

        gp.obj[3] = new OBJ_gold();
        gp.obj[3].worldX = 42 * gp.tileSize;
        gp.obj[3].worldY = 20 * gp.tileSize;

        gp.obj[4] = new OBJ_gold();
        gp.obj[4].worldX = 43 * gp.tileSize;
        gp.obj[4].worldY = 8 * gp.tileSize;

        gp.obj[5] = new OBJ_gold();
        gp.obj[5].worldX = 36 * gp.tileSize;
        gp.obj[5].worldY = 15 * gp.tileSize;

        gp.obj[6] = new OBJ_gold();
        gp.obj[6].worldX = 5 * gp.tileSize;
        gp.obj[6].worldY = 2 * gp.tileSize;

    }

    public void resetObjects() {
        for (int i = 0; i < gp.obj.length; i++) {
            gp.obj[i] = null;
        }
    }
}
