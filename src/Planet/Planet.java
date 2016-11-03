package Planet;

import Sprit.Sprit;
import javafx.scene.image.Image;

import static Planet.Util.rotate;

/**
 * Created by zyf on 2016/11/2.
 */
public class Planet extends Sprit {
    public Planet() {
        this.image = new Image(getClass().getResource("images/fj (3).png").toString());
        rotates(180);
    }
    public Planet(double x,double y){
        this();
        this.x = x;
        this.y = y;
    }
    public Planet(String text) {
        this.text = text;
    }
    public void rotates(double angle){
        rotate(angle,image);
    }
}
