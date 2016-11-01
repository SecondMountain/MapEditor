package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller {
    @FXML protected Button openFile;
    @FXML protected TextField filePath;
    @FXML protected TextField imageH;
    @FXML protected TextField imageW;
    @FXML protected TextField imageS;
    @FXML protected TextField pyx;
    @FXML protected TextField pyy;
    @FXML protected Button xinjian;
    @FXML protected Button shanchu;
    @FXML protected Button moveUp;
    @FXML protected Button moveDown;
    @FXML protected Slider opacity;
    @FXML protected CheckBox visible;
    @FXML protected CheckBox insect;
    @FXML protected Label mapSize;
//    @FXML protected ListView<String> layers;
    @FXML protected ListView<Label> layers;
    @FXML protected ListView<ImageView> imagelist;
    @FXML protected ListView<ImageView> images;
    @FXML protected ScrollPane Ccanvas;
    @FXML protected ScrollPane element;
    @FXML protected ScrollPane elements;
    private Group group = new Group();
    private Canvas layer;
    private Label labels;
    private int index=0;
    /*
    {
        layers.setEditable(true);
        layers.setCellFactory((ListView<Label> l)->new ListCell<Label>(){
            @Override
            protected void updateItem(Label item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(item);
            }
        });
    }
    */
    @FXML
    protected void setopenFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择图片");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"));
        File list =fileChooser.showOpenDialog(new Stage());
        if (list.exists()) {
            filePath.appendText(list.getName());
            Image image = new Image(list.toURI().toString());
            imageH.setText(String.valueOf(image.getHeight()));
            imageW.setText(String.valueOf(image.getWidth()));
            imageS.setText(String.valueOf(image.getHeight())+" X "+String.valueOf(image.getWidth()));
            ObservableList<ImageView> data=imagelist.getItems();
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(40);
            imageView.setFitWidth(30);
            data.add(imageView);
//            data.add(new ImageView(image));

//            element.getChildrenUnmodifiable().add(new ImageView(image));
        }
    }
    @FXML
    protected void setxinjian(){

        Canvas canvas = new Canvas(Ccanvas.getWidth(),Ccanvas.getHeight());
        canvas.widthProperty().bind(Ccanvas.widthProperty());
        canvas.heightProperty().bind(Ccanvas.heightProperty());
        group.getChildren().add(canvas);
        canvas.getGraphicsContext2D().setFill(Color.WHITESMOKE);
        Ccanvas.setContent(group);
        Label label = new Label("图层"+String.valueOf(index++));
        label.minWidthProperty().bind(layers.widthProperty());
        /*
        layers.setEditable(true);
        layers.setCellFactory((ListView<Label> l)->new ListCell<Label>(){
            @Override
            protected void updateItem(Label item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(item);
            }
        });
        */
        labels=label;
        labels.requestFocus();
        label.setOnMouseReleased((event)->{
                layer = canvas;
                labels = label;
        });
        ObservableList<Label> data=layers.getItems();
        data.add(label);
    }
    @FXML
    protected void setshanchu(){
        if (layer==null)
            return;
        ObservableList<Label> data=layers.getItems();
        data.remove(labels);
        group.getChildren().remove(layer);
        layer=null;
    }
    @FXML
    protected void setmoveUp(){
        if (layer==null)
            return;
        layer.toFront();
    }
    @FXML
    protected void setmoveDown(){
        if (layer==null)
            return;
        layer.toBack();
    }
    @FXML
    protected void setopacity(){
        System.out.println(opacity.getValue());
        if (layer==null)
            return;
        layer.setOpacity(opacity.getValue());
//        mapSize.setText(String.valueOf(opacity.getValue()));
    }
    @FXML
    protected void setvisible(){
        if (layer==null)
            return;
        if (visible.isSelected()){
            layer.setVisible(true);
        }
        else
            layer.setVisible(false);
    }
    @FXML
    protected void setinsect(){
    }
}
