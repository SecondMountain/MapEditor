package Planet;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 * Created by zyf on 2016/10/15.
 */
public class Util {
    private static Image image;
    private static ImageView imageView;
    private static SnapshotParameters parameters = new SnapshotParameters();
    public static Image rotate(double angle,Image image1){
        if (image==null) {
            image = image1;
            imageView = new ImageView(image);
        }
        imageView.setRotate(angle);
//        imageView.scale
        parameters.setFill(Color.TRANSPARENT);
        parameters.setViewport(new Rectangle2D(0,0,image.getWidth(),image.getHeight()));
        Image rotated = imageView.snapshot(parameters,null);
        return rotated;
    }
}
