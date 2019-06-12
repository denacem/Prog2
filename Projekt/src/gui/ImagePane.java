package gui;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ImagePane extends StackPane {
    private static ImageView iv1;
    private static Pane drawingArea;
    private static VBox imageWindow;

    public ImagePane() {
        iv1 = new ImageView();
        drawingArea = new Pane();
        imageWindow = new VBox();

        /* image placeholder */
        Image image1 = new Image("/image-data/mri-spine.png");
        iv1.setImage(image1);
        iv1.setFitWidth(500);
        iv1.setPreserveRatio(true);

        /* vbox for the uploaded image */
        imageWindow.getChildren().addAll(iv1);

        getChildren().addAll(imageWindow, drawingArea);

    }

    public static void changeImage(Image loadedImage) {
        iv1.setImage(loadedImage);
    }

    public static void addLine(Group i) {
        drawingArea.getChildren().addAll(i);
    }

    public static void removeLine(Group i) {
        drawingArea.getChildren().removeAll(i);
        drawingArea.getChildren().clear();
        i.getChildren().removeAll();
        i.getChildren().clear();
    }
}