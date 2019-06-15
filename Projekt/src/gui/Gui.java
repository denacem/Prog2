package gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class Gui extends Application {
    ;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        ImagePane pictureHolder = new ImagePane();
        ControlPane menu = new ControlPane(pictureHolder);

        BorderPane base = new BorderPane();
        base.setCenter(pictureHolder);
        base.setLeft(menu);

        Scene scene = new Scene(new Group(base), 750, 650);

        scene.getStylesheets().add("/stylessheet/UiStylesheet.css");

        stage.setScene(scene);
        stage.setTitle("Picture Measure Project");
        stage.show();

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            double scaleFactor = (stage.getWidth() - 240) * 1 / 500;

            pictureHolder.setPrefWidth(stage.getWidth() * 1 / scaleFactor);

            Scale scale = new Scale(scaleFactor, scaleFactor);
            scale.setPivotX(0);
            scale.setPivotY(0);
            pictureHolder.getTransforms().setAll(scale);
        });

        double scaleFactorPicture = (pictureHolder.getWidth() / pictureHolder.getHeight());

        stage.minHeightProperty().bind(stage.widthProperty().multiply(scaleFactorPicture).subtract(60));
        stage.maxHeightProperty().bind(stage.widthProperty().multiply(scaleFactorPicture).subtract(60));
    }
}
