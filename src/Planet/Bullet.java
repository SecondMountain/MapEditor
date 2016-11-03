package Planet;

import javafx.scene.image.Image;

/**
 * Created by zyf on 2016/11/2.
 */
public class Bullet extends Planet{
    public Bullet() {
        this.image = new Image(getClass().getResource("images/fj (4).png").toString());
        rotates(180);
    }
    public Bullet(double x,double y){
        this();
        this.x=x+15;
        this.y=y-30;
    }

    @Override
    public void moveUp() {
        y-=5;
    }
}
