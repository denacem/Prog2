package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Gui extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ImagePane pictureHolder = new ImagePane();
        ControlPane menu = new ControlPane(pictureHolder);

        /* panes, scene and stage */
        BorderPane base = new BorderPane();
        base.setCenter(pictureHolder);
        base.setLeft(menu);

        Scene scene = new Scene(base, 800, 650);
        scene.getStylesheets().add("/stylessheet/UiStylesheet.css");

        stage.setScene(scene);
        stage.setTitle("Hallo");
        stage.show();

        /* Calling of Methods from other classes
        MeasureLength.start(measureableArea, lengthLine, canvas, graphicsContext, drawArea, lengthButton); */
    }


    public static void main(String[] args) {
        launch(args);
    }
}
