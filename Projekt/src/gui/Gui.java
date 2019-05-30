package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Gui extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        ImagePane pane = new ImagePane();
        ControlPane pane2 = new ControlPane();

        /* panes, scene and stage */
        Pane measureableArea = new Pane();
        StackPane drawArea = new StackPane(pane,measureableArea);

        BorderPane base = new BorderPane();
        base.setCenter(drawArea);
        base.setLeft(pane2);

        Scene scene = new Scene(base, 650, 650);
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
