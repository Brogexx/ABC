package main;

import object.OBJ_door;
import object.OBJ_gold;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        gp.obj[0] = new OBJ_gold();
        gp.obj[0].worldX = 37 * gp.tileSize;
        gp.obj[0].worldY = 16 * gp.tileSize;

        gp.obj[1] = new OBJ_gold();
        gp.obj[1].worldX = 6 * gp.tileSize;
        gp.obj[1].worldY = 3 * gp.tileSize;

    }
}
