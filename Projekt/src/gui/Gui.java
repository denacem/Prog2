package gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class Gui extends Application {;

    @Override
    public void start(Stage stage) throws Exception {

        ImagePane pictureHolder = new ImagePane();
        ControlPane menu = new ControlPane(pictureHolder);

        /* panes, scene and stage */
        BorderPane base = new BorderPane();
        base.setCenter(pictureHolder);
        base.setLeft(menu);

        Scene scene = new Scene(new Group(base), 730, 650);

        scene.getStylesheets().add("/stylessheet/UiStylesheet.css");

        stage.setScene(scene);
        stage.setTitle("Hallo");
        stage.show();

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println(stage.getWidth());
            double SCALE_FACTOR = (stage.getWidth()-230)*1/500;

        pictureHolder.setPrefWidth(scene.getWidth() * 1/SCALE_FACTOR);

        scene.widthProperty().addListener(observable -> {
            pictureHolder.setPrefWidth(scene.getWidth() * 1/SCALE_FACTOR);
        });

        pictureHolder.setPrefHeight(scene.getHeight() * 1/SCALE_FACTOR);
        scene.heightProperty().addListener(observable -> {
            base.setPrefHeight(scene.getHeight() * 1/SCALE_FACTOR);
        });

        Scale scale = new Scale(SCALE_FACTOR, SCALE_FACTOR);
        scale.setPivotX(0);
        scale.setPivotY(0);
        pictureHolder.getTransforms().setAll(scale);
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
