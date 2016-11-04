package Planet;/**
 * Created by zyf on 2016/11/3.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;

import static IO.NewIo.CopyFile.copy;

/**
 * drop file from computer to scene when dropOver it will become green and dropped it will change to white color
 */
public class DropFile extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group(),300,400);
        stage.setScene(scene);
        stage.show();
        /**
         * 当拖动的文件经过的时候
         */
        scene.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                scene.setFill(Color.GREEN);
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                    db.setDragView(new Image(getClass().getResource("images/fj (4).png").toString()),event.getX(),event.getY());
                    db.setDragViewOffsetX(event.getX());
                } else {
                    event.consume();
                }
            }
        });
        //当拖动的文件放下之后
        scene.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                db.setDragView(new Image(getClass().getResource("images/fj (4).png").toString()),event.getX(),event.getY());
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    scene.setFill(Color.WHITE);
                    String filePath = null;
                    for (File file:db.getFiles()) {
                        filePath = file.getAbsolutePath();
                        try {
                            copy(file.getAbsolutePath(),"src/files/"+file.getName());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        println(file.renameTo(new File("src/files/"+file.getName())));
                        System.out.println(file.getName());
                        System.out.println(filePath);
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });
    }
}
