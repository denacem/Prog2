package gui;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;

public class ImagePane extends StackPane {
    private static ImageView iv1;
    private static Pane drawingArea;
    private static VBox imageWindow;

    public ImagePane() {
        iv1 = new ImageView();
        drawingArea = new Pane();
        imageWindow = new VBox();

        /* Testbild um UI zu gestalten */
        Image image1 = new Image("/image-data/hand-xray.jpg");
        iv1.setImage(image1);
        iv1.setFitWidth(400);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(true);
        iv1.setCache(true);

        /* Textfeld zur Darstellung der Bildinformationen */
        Text infos = new Text(10,10, "something");
        infos.setWrappingWidth(200);

        /* vbox for the uploaded image */
        imageWindow.getChildren().addAll(iv1);

        getChildren().addAll(imageWindow,drawingArea);

    }

    public static void changeImage(Image loadedImage) {
        iv1.setImage(loadedImage);
    }

    public static void addLine(Group i) {
        drawingArea.getChildren().addAll(i);
    }

    public static void addCircumference(Polyline i) {drawingArea.getChildren().addAll(i);}

}