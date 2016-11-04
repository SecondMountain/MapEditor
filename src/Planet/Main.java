package Planet;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyf on 2016/11/2.
 */
public class Main extends Application{
    private Planet planet = new Planet(125,350);
    private GraphicsContext gc;
    private List<Bullet> bullets = new ArrayList<>();
    private int divide;
    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (up)
                planet.moveUp();
            if (down)
                planet.moveDown();
            if (left)
                planet.moveLeft();
            if (right)
                planet.moveRight();
            if (divide>=5) {
                bullets.add(new Bullet(planet.x, planet.y));
                divide=0;
            }
            clearRect(gc);
            draw(gc);
            divide++;
        }
    };
    private AnimationTimer drawBullets = new AnimationTimer() {
        @Override
        public void handle(long now) {
            bullets.stream().forEach(item->{
                item.moveUp();
                gc.drawImage(item.image,item.x,item.y);
                if (item.x<=-30)
                    bullets.remove(item);
            });
        }
    };
    private boolean up,down,left,right;
    private boolean start=false;
    public static void main(String[] args){launch(args);}
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(300,400);
        gc= canvas.getGraphicsContext2D();
        primaryStage.setScene(new Scene(new Group(canvas),300,400));
        primaryStage.setTitle("飞机");
        primaryStage.show();
        timer.start();
        drawBullets.start();
        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.UP)
                    up=true;
                if (event.getCode()==KeyCode.DOWN)
                    down=true;
                if (event.getCode()==KeyCode.LEFT)
                    left=true;
                if (event.getCode()==KeyCode.RIGHT)
                    right=true;
            }
        });
        canvas.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode()== KeyCode.UP)
                    up=false;
                if (event.getCode()==KeyCode.DOWN)
                    down=false;
                if (event.getCode()==KeyCode.LEFT)
                    left=false;
                if (event.getCode()==KeyCode.RIGHT)
                    right=false;
            }
        });
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (start){
                    timer.stop();
                    drawBullets.stop();
                    start=false;
                }
                else {
                    timer.start();
                    drawBullets.start();
                    start=true;
                }
            }
        });
        canvas.requestFocus();//获取焦点之后就可以执行按键事件
    }
    public void draw(GraphicsContext gc){
        gc.drawImage(planet.image,planet.x,planet.y,50,42.5);
    }
    public void clearRect(GraphicsContext gc){
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
    }

}
