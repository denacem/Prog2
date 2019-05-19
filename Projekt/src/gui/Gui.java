package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Gui extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Button button = new Button("upload image");

        Pane menu = new Pane();
        menu.getChildren().add(button);

        Scene scene = new Scene(menu, 300, 600);

        stage.setScene(scene);
        stage.setTitle("Projektli");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
