package Sprit;

import javafx.scene.image.Image;

/**
 * 精灵类
 * Created by zyf on 2016/10/31.
 */
public class Sprit {
    /**
     * 精灵的坐标x,y
     */
    public double x,y,width,height;
    public Image image;
    public String text;
    /**
     * 精灵向上移动
     */
    public void moveUp(){y--;}
    /**
     * 精灵向下移动
     */
    public void moveDown(){y++;}

    /**
     * 精灵向左移动
     */
    public void moveLeft(){x--;}

    /**
     * 精灵向右移动
     */
    public void moveRight(){x++;}

}
