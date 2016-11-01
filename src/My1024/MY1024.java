package My1024;/**
 * Created by zyf on 2016/10/31.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static Print.Print.print;
import static Print.Print.println;

public class MY1024 extends Application {
    int[][] point;
    double width=100;
    AudioClip music = new AudioClip(getClass().getResource("7905.mp3").toString());
    AudioClip click = new AudioClip(getClass().getResource("2955.mp3").toString());

    public static void main(String[] args) {
        /*
     int[][] vaxs= new int[][]{
             {0,0,0,0},
             {2,2,8,2},
             {2,0,2,4},
             {0,4,0,4}
     };
     MY1024 ll= new MY1024();
        ll.point = vaxs;
        //向上合并
        println("-----------------------------------");
        ll.merge(ll.point,1,0,3,4,-1,-1);
        //向左合并
        println("-----------------------------------");
        ll.merge(ll.point,0,1,4,3,-1,-1);
        //向右合并
        println("-----------------------------------");
        ll.merge(ll.point,0,-1,4,4,-1,0);
        //向下合并
        println("-----------------------------------");
        ll.merge(ll.point,-1,0,4,4,0,-1);
        println("-----------------------------------");
        ll.random();
*/
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        init();
        Canvas canvas = new Canvas(400,400);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        gc.setFont(Font.font(32));
        Pane root = new Pane(canvas);
        primaryStage.setScene(new Scene(root,400,400));
        primaryStage.show();
        primaryStage.setTitle("2048");
        line(gc,width);
        music.setCycleCount(-1);
        music.play();
        canvas.requestFocus();
        canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (click.isPlaying())
                    click.stop();
                if (event.getCode()==KeyCode.UP){merge(point,1,0,3,4,-1,-1);click.play();random();draw(gc);}
                if (event.getCode()==KeyCode.DOWN){merge(point,-1,0,4,4,0,-1);click.play();random();draw(gc);}
                if (event.getCode()==KeyCode.LEFT){merge(point,0,1,4,3,-1,-1);click.play();random();draw(gc);}
                if (event.getCode()==KeyCode.RIGHT){merge(point,0,-1,4,4,-1,0);click.play();random();draw(gc);}
                /*
                click.play();
                random();
                draw(gc);
                */
                if (!defeat()){
                    drawLose(gc);
                    canvas.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {

                        }
                    });
                    canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            try {
                                stop();
                                start(primaryStage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
    public void draw(GraphicsContext gc){
        gc.clearRect(0,0,gc.getCanvas().getWidth(),gc.getCanvas().getHeight());
        line(gc,width);
        for (int i =0;i<4;i++) {
            for (int j = 0; j < 4; j++)
                if (point[i][j]!=0)
                    gc.fillText(String.valueOf(point[i][j]),(j+1)*width-60-(String.valueOf(point[i][j]).length()-1)*15,i*width+60);
        }

    }
    public void line(GraphicsContext gc,double width){
        Paint color = gc.getStroke();
        gc.setStroke(Color.GRAY);
        for (double i = 0;i<gc.getCanvas().getWidth();i++) {
            gc.strokeLine(i, 0, i, gc.getCanvas().getHeight());
            i+=width;
        }
        for (double i = 0;i<gc.getCanvas().getHeight();i++) {
            gc.strokeLine(0,i, gc.getCanvas().getWidth(),i);
            i+=width;
        }
        gc.setStroke(color);
    }
    public void init(){
        point=new int[4][4];
        for (int i =0;i<4;i++)
            for (int j = 0; j < 4; j++)
                point[i][j]=0;
    }
    public boolean defeat(){
        boolean lose=false;
        for (int i =0;i<4;i++) {
            for (int j = 0; j < 4; j++)
                if (point[i][j]==0){
                    lose=true;
                    break;
                }
        }
        if (lose)
            return lose;
        for (int i =0;i<4;i++) {
            for (int j = 0; j < 4; j++){
                if (j-1>-1 && j-1 <4){
                    if (point[i][j] == point[i][j-1]) {
                        lose = true;
                        break;
                    }
                }
                if (j+1>-1 && j+1 <4){
                    if (point[i][j] == point[i][j+1]) {
                        lose = true;
                        break;
                    }
                }
                if (i+1>-1 && i+1 <4){
                    if (point[i][j] == point[i+1][j]) {
                        lose = true;
                        break;
                    }
                }
                if (i-1>-1 && i-1 <4){
                    if (point[i][j] == point[i-1][j]) {
                        lose = true;
                        break;
                    }
                }
            }
        }
        return lose;
    }
    public void drawLose(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.setFont(Font.font(64));
        gc.fillText("失败",140,200);
    }
    public void moveUP(int vaxs[][],int x,int y,int ix,int jy,int imin,int jmin){
        for (int i =0;i<4;i++){
            for (int j=0;j<4;j++){
                if (i<ix&&j<jy&&i>imin&&j>jmin&&vaxs[i][j]==0){
                    vaxs[i][j]=vaxs[i+x][j+y];
                    vaxs[i+x][j+y]=0;
                }
            }
        }
    }
    public void printf(int vaxs[][]){
        for (int i =0;i<4;i++) {
            for (int j = 0; j < 4; j++)
                print(vaxs[i][j]);
            println();
        }
    }

    /**
     * 合并整合的过程中不断移动
     * @param vaxs
     */
    public void merge(int vaxs[][],int x,int y,int ix,int jy,int imin,int jmin){
        for (int i =0;i<4;i++) {
            for (int j = 0; j < 4; j++){
                if ( i<ix && j<jy&&i>imin&&j>jmin && vaxs[i][j]==vaxs[i+x][j+y]){
                    vaxs[i][j]=vaxs[i][j]+vaxs[i][j];
                    vaxs[i+x][j+y]=0;
                    moveUP(vaxs,x,y,ix,jy,imin,jmin);
                }
            }
        }
//        printf(vaxs);
    }

    /**
     * 随机在空的位置出一个数字2
     */
    public void random(){
        List<String> list = new ArrayList<>();
        for (int i =0;i<4;i++) {
            for (int j = 0; j < 4; j++) {
                if (point[i][j]==0){
                    list.add(String.valueOf(i+""+j));
                }
            }
        }
        Random random = new Random();
        if (list.size()==0)
            return;
        String str = list.get(random.nextInt(list.size()));
        point[Integer.parseInt(str.substring(0,1))][Integer.parseInt(str.substring(1,2))]=2;
        printf(point);
    }
}
