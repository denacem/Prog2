package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ImagePane extends Pane {
    private static ImageView iv1;

    public ImagePane() {
        iv1 = new ImageView();

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
        VBox imageWindow = new VBox();
        imageWindow.getChildren().addAll(iv1);

        getChildren().addAll(iv1);

    }

    public static void changeImage(Image loadedImage) {
        iv1.setImage(loadedImage);
    }
}