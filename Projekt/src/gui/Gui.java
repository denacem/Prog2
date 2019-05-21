package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Gui extends Application {

    @Override
    public void start(Stage stage) throws Exception {


        /* elements, buttons etc. */
        Button uploadButton = new Button("upload image");
        Button lengthButton = new Button("length");
        Button angleButton = new Button("angle");
        Button circumferenceButton = new Button("circumference");

        /* Testbild um UI zu gestalten */
        Image image1 = new Image("/image-data/hand-xray.jpg");
        ImageView iv1 = new ImageView();
        iv1.setFitWidth(400);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(true);
        iv1.setCache(true);
        iv1.setImage(image1);

        /* vboxes for menu and image window*/
        VBox menu = new VBox();
        menu.getChildren().addAll(uploadButton, lengthButton, angleButton, circumferenceButton);
        menu.setAlignment(Pos.TOP_LEFT);
        menu.setSpacing(10);
        menu.setPadding(new Insets(5, 5, 5, 5));
        menu.setStyle("-fx-background-color: green;");

        VBox imageWindow = new VBox();
        imageWindow.getChildren().add(iv1);
        menu.setAlignment(Pos.TOP_RIGHT);
        menu.setSpacing(10);
        menu.setPadding(new Insets(5, 5, 5, 5));
        menu.setStyle("-fx-background-color: red;");

        /* Hbox to hold the Vboxes menu and imageWindow */
        HBox workspace = new HBox();
        workspace.getChildren().addAll(menu, imageWindow);
        workspace.setAlignment(Pos.CENTER);
        workspace.setSpacing(10);
        workspace.setPadding(new Insets(5, 5, 5, 5));
        workspace.setStyle("-fx-background-color: blue;");

        /* panes, scene and stage */
        StackPane base = new StackPane();
        base.getChildren().addAll(workspace);

        Scene scene = new Scene(base, 600, 600);

        stage.setScene(scene);
        stage.setTitle("Hallo");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
