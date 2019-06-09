package gui;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Gui extends Application {

    private static final double SCALE_FACTOR= 1;

    @Override
    public void start(Stage stage) throws Exception {

        ImagePane pictureHolder = new ImagePane();
        ControlPane menu = new ControlPane(pictureHolder);

        /* panes, scene and stage */
        BorderPane base = new BorderPane();
        base.setCenter(pictureHolder);
        base.setLeft(menu);

        Scene scene = new Scene(new Group(base), 800, 650);
        base.setPrefWidth(scene.getWidth() * 1/SCALE_FACTOR);

        scene.widthProperty().addListener(observable -> {
            base.setPrefWidth(scene.getWidth() * 1/SCALE_FACTOR);
        });

        base.setPrefHeight(scene.getHeight() * 1/SCALE_FACTOR);
        scene.heightProperty().addListener(observable -> {
            base.setPrefHeight(scene.getHeight() * 1/SCALE_FACTOR);
        });

        scene.getStylesheets().add("/stylessheet/UiStylesheet.css");

        stage.setScene(scene);
        stage.setTitle("Hallo");
        stage.show();

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 4);

        Scale scale = new Scale(SCALE_FACTOR, SCALE_FACTOR);
        scale.setPivotX(0);
        scale.setPivotY(0);
        base.getTransforms().setAll(scale);

        /* Calling of Methods from other classes
        MeasureLength.start(measureableArea, lengthLine, canvas, graphicsContext, drawArea, lengthButton); */
    }


    public static void main(String[] args) {
        launch(args);
    }
}
