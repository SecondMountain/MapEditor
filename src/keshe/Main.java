package keshe;/**
 * Created by zyf on 2016/10/25.
 */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static Print.Print.println;
import static keshe.LLRW.*;
import static keshe.Util.rotate;

public class Main extends Application {
    private double width = 60;
    private double radius=30;
    private double length=15;
    private Image image = new Image("keshe/1167930.png");
    private Image arrow = new Image("keshe/1167908.png");
    private double point[][];
    private String path;
    private TableView tableView;
    Random random = new Random(47);
    Callback<TableColumn<Map, String>, TableCell<Map, String>> cellFactoryForMap = (TableColumn<Map, String> p) -> new TextFieldTableCell(new StringConverter() {
                @Override
                public String toString(Object t) {
                    return t.toString();
                }
                @Override
                public Object fromString(String string) {
                    return string;
                }
            });

    public static void main(String[] args) {launch(args);}
    @Override
    public void start(Stage primaryStage)throws Exception {
        VBox vBox = new VBox(10);
        TextArea textArea = new TextArea();
        textArea.setMinHeight(150);
        Button button = new Button("选择文件");
        Button start = new Button("开始执行");
        Label message = new Label();
        tableView = new TableView<>();
        tableView.setMinHeight(150);
        tableView.setEditable(true);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        HBox hBox = new HBox(button,start,message);
        Canvas canvas = new Canvas(700,700);
        GraphicsContext gc=canvas.getGraphicsContext2D();
        vBox.getChildren().addAll(textArea,hBox,tableView,canvas);
        primaryStage.setTitle("左线性文法分析及图示");
        primaryStage.setScene(new Scene(vBox, 700, 700));
        primaryStage.show();
        draw(gc,"src/keshe/LLRW.txt");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(primaryStage);
                path = file.getAbsolutePath();

                try {
                    gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
                    tableView.getColumns().clear();
                    draw(gc,path);
                }
                catch (Exception e){
                    message.setText("解析错误");
                    e.printStackTrace();
                }
            }
        });
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = new File("src/keshe/data.txt");
                try {
                    BufferedWriter bufferedReader = new BufferedWriter(new FileWriter(file));
                    bufferedReader.write(textArea.getText());
                    bufferedReader.close();
                    gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
                    tableView.getColumns().clear();
//                    tableView.setItems(FXCollections.observableList(new ArrayList<>()));
                    draw(gc,"src/keshe/data.txt");
                    textArea.setText("");
                } catch (IOException e) {
                    message.setText("解析错误");
                }
            }
        });
    }
    public  void draw(GraphicsContext gc,String filePath){
//        GraphicsContext gc=canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.setLineWidth(2);
        gc.setStroke(Color.BLACK);
        gc.setFont(Font.font(32));

        /*
        drawpoint(gc,300,200,"A");
        drawpoint(gc,500,500,"B");
        drawLine(gc,300,200,500,500,"0");
        drawpoint(gc,450,400,"C");
        drawpoint(gc,400,500,"D");
        drawLine(gc,450,400,400,500,"1");
        drawpoint(gc,300,400,"A");
        drawpoint(gc,400,300,"B");
        drawLine(gc,300,400,400,300,"0");
        drawpoint(gc,300,400,"A");
        drawpoint(gc,200,200,"B");
        drawLine(gc,300,400,200,200,"0");
        drawpoint(gc,600,100,"E");
        drawpoint(gc,600,300,"F");
        drawLine(gc,600,100,600,300,"3");
        drawpoint(gc,500,200,"G");
        drawpoint(gc,300,200,"H");
        drawLine(gc,300,200,500,200,"2");
        */
        try {
            remove();
            readLLRW(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tableView.setItems(FXCollections.observableList(new ArrayList<>()));
        addClomuns(tableView);
        point = new double[nodes.size()][2];
        for (int i = 0;i<nodes.size();i++){
            double angle = Math.PI*2/nodes.size()*i;
            double ponit1 = 300+200*Math.cos(angle);
            double ponit2 = 300+200* Math.sin(angle);
            drawpoint(gc,ponit1,ponit2,nodes.get(i));
            point[i][0] = ponit1;
            point[i][1] = ponit2;
        }
        for (int i =0;i<ponits.length;i++){
            for (int j=0;j<ponits[i].length;j++){
                int index=nodes.indexOf(nodes.get(i));
                int indexEnd = nodes.indexOf(ponits[i][j]);
                drawLine(gc,point[index][0],point[index][1],point[indexEnd][0],point[indexEnd][1],alpha.get(j));
            }
        }
        gc.stroke();
    }
    public void drawpoint(GraphicsContext gc,double x,double y,String text){
        gc.strokeArc(x,y,width,width,0,360, ArcType.CHORD);
        if (endNode.contains(text))
            gc.strokeArc(x+5,y+5,width-10,width-10,0,360, ArcType.CHORD);
        gc.fillText(text,x+20,y+35);
    }
    public void drawLine(GraphicsContext gc,double startX,double startY,double desX,double desY,String text){
        if (desX>startX && desY >startY) {
            println(Math.atan((desX - startX) / (desY - startY)));
            double angle = Math.atan((desY - startY)/(desX - startX));
            double desAngle = Math.PI / 2 - angle;
            double x = startX + radius + radius * Math.cos(angle);
            double y = startY + radius + radius * Math.sin(angle);
            double dX = desX + radius - radius * Math.sin(desAngle);
            double dY = desY + radius - radius * Math.cos(desAngle);
            gc.strokeLine(x, y, dX, dY);
            gc.drawImage(rotate(180/Math.PI*desAngle+30,image),dX-20,dY-22,30,30);
            /*
            if (desAngle>Math.PI/4){
                gc.strokeLine(dX, dY, dX+length*Math.cos(Math.PI/2+desAngle-Math.PI/4), dY-length*Math.sin(Math.PI/2+desAngle-Math.PI/4));
                gc.strokeLine(dX, dY, dX+length*Math.cos(Math.PI/2+Math.PI/4+desAngle), dY-length*Math.sin(Math.PI/2+Math.PI/4+desAngle));
            }
            else {
                gc.strokeLine(dX, dY, dX - length, dY);
                gc.strokeLine(dX, dY, dX, dY - length);
            }
            */
            gc.fillText(text, (startX + desX+2*radius) / 2, (startY + desY+2*radius) / 2);
        }
        if (desX>startX && desY <startY) {
            println(Math.atan((startY-desY)/(desX - startX)));
            double angle = Math.atan((startY-desY)/(desX - startX));
            double desAngle = Math.PI / 2 - angle;
            double x = startX + radius + radius * Math.cos(angle);
            double y = startY + radius - radius * Math.sin(angle);
            double dX = desX + radius - radius * Math.sin(desAngle);
            double dY = desY + radius + radius * Math.cos(desAngle);
            gc.strokeLine(x, y, dX, dY);
            gc.drawImage(rotate(180/Math.PI*desAngle-90,image),dX-23,dY-10,30,30);
            gc.fillText(text, (startX + desX+2*radius) / 2, (startY + desY+2*radius) / 2);
        }
        if (desX<startX && desY <startY) {
            println(Math.atan((startY-desY)/(startX-desX) ));
            double angle = Math.atan((startX-desX )/(startY-desY));
            double desAngle = Math.PI / 2 - angle;
            double x = startX + radius - radius * Math.sin(angle);
            double y = startY + radius - radius * Math.cos(angle);
            double dX = desX + radius + radius * Math.cos(desAngle);
            double dY = desY + radius + radius * Math.sin(desAngle);
            gc.strokeLine(x, y, dX, dY);
            gc.drawImage(rotate(180/Math.PI*desAngle+180,image),dX-10,dY-8,30,30);
            gc.fillText(text, (startX + desX+2*radius) / 2-10, (startY + desY+2*radius) / 2+25);
        }
        if (desX<startX && desY >startY) {
            println(Math.atan( (desY-startY)/(startX-desX)));
            double angle = Math.atan((desY-startY)/(startX-desX ));
            double desAngle = Math.PI / 2 - angle;
            double x = startX + radius - radius * Math.cos(angle);
            double y = startY + radius + radius * Math.sin(angle);
            double dX = desX + radius + radius * Math.sin(desAngle);
            double dY = desY + radius - radius * Math.cos(desAngle);
            gc.strokeLine(x, y, dX, dY);
            gc.drawImage(rotate(180/Math.PI*desAngle+90,image),dX-10,dY-23,30,30);
//            gc.strokeLine(dX, dY, dX + length, dY);
//            gc.strokeLine(dX, dY, dX, dY - length);
            gc.fillText(text, (startX + desX+2*radius) / 2, (startY + desY+2*radius) / 2);
        }

        if (desX==startX && desY>startY) {
            gc.strokeLine(startX+radius, startY+width, desX+radius, desY);
            gc.strokeLine(desX+radius, desY, desX+radius+length*Math.cos(Math.PI/4), desY-length*Math.sin(Math.PI/4));
            gc.strokeLine(desX+radius, desY, desX+radius+length*Math.cos(Math.PI/4*3), desY-length*Math.sin(Math.PI/4*3));
            gc.fillText(text, (startX + desX+2*radius) / 2, (startY + desY+2*radius) / 2);
        }
        if (desX==startX && desY<startY) {
            gc.strokeLine(startX+radius, startY, desX+radius, desY+width);
            gc.strokeLine(desX+radius, desY+width, desX+radius-length*Math.sin(Math.PI/8*10), desY+width-length*Math.cos(Math.PI/8*10));
            gc.strokeLine(desX+radius, desY+width, desX+radius-length*Math.cos(Math.PI/8*14), desY+width-length*Math.sin(Math.PI/8*14));
            gc.fillText(text, (startX + desX+2*radius) / 2, (startY + desY+2*radius) / 2);
        }
        if (desX>startX && desY==startY) {
            gc.strokeLine(startX+width, startY+radius, desX, desY+radius);
            gc.strokeLine(desX, desY+radius, desX+length*Math.cos(Math.PI/8*6), desY+radius-length*Math.sin(Math.PI/8*6));
            gc.strokeLine(desX, desY+radius, desX+length*Math.cos(Math.PI/8*10), desY+radius-length*Math.sin(Math.PI/8*10));
            gc.fillText(text, (startX + desX+2*radius) / 2, (startY + desY+2*radius) / 2);
        }
        if (desX<startX && desY==startY) {
            gc.strokeLine(startX, startY+radius, desX+width, desY+radius);
            gc.strokeLine(desX+width, desY+radius, desX+width+length*Math.sin(Math.PI/4), desY+radius-length*Math.cos(Math.PI/4));
            gc.strokeLine(desX+width, desY+radius, desX+width+length*Math.cos(Math.PI/8*14), desY+radius-length*Math.sin(Math.PI/8*14));
            gc.fillText(text, (startX + desX+2*radius) / 2, (startY + desY+2*radius) / 2);
        }
        if (desX ==startX && desY==startY){
            gc.drawImage(arrow,startX+width,startY+radius/2,45,45);
            gc.setFont(Font.font(20));
            gc.fillText(text,startX+width+45,startY+radius);
            gc.setFont(Font.font(32));
        }

    }
    public  void addClomuns(TableView tableView){
        tableView.setItems(generateDataInMap());
        TableColumn<Map,String> first = new TableColumn<>("状态");
        first.setCellValueFactory(new MapValueFactory<>(" "));
        first.setCellFactory(cellFactoryForMap);
        tableView.getColumns().add(first);
        alpha.stream().forEach((item)->{
            TableColumn<Map,String> add = new TableColumn<>(item);
            add.setCellValueFactory(new MapValueFactory<>(item));
            add.setCellFactory(cellFactoryForMap);
            tableView.getColumns().add(add);
        });
    }
    private ObservableList<Map> generateDataInMap() {
        ObservableList<Map> allData = FXCollections.observableArrayList();
        for (int i = 0; i < nodes.size(); i++) {

            Map<String, String> dataRow = new HashMap<>();
            dataRow.put(" ", nodes.get(i));
            for (int j=0;j<alpha.size();j++) {
                dataRow.put(alpha.get(j), ponits[i][j]);
            }
            allData.add(dataRow);
        }
        return allData;
    }

}
