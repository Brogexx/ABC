package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_gold extends SuperObject{
    public OBJ_gold(){

            name = "Gold";
            try{
                image = ImageIO.read(getClass().getResourceAsStream("/objects/gold.png"));


            }catch(IOException e){
                e.printStackTrace();
            }
        }

}
