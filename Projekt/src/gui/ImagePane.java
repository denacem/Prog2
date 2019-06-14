package gui;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ImagePane extends StackPane {
    private static ImageView imageView;
    private static Pane drawingArea;
    private static VBox imageWindow;

    public ImagePane() {
        imageView = new ImageView();
        drawingArea = new Pane();
        imageWindow = new VBox();

        Image image1 = new Image("/image-data/mri-spine.png");
        imageView.setImage(image1);
        imageView.setFitWidth(500);
        imageView.setPreserveRatio(true);

        imageWindow.getChildren().addAll(imageView);

        getChildren().addAll(imageWindow, drawingArea);

    }

    public static void changeImage(Image loadedImage) {
        imageView.setImage(loadedImage);
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