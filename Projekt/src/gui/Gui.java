package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

public class Gui extends Application {

    @Override
    public void start(Stage stage) throws Exception {


        /* elements, buttons, labels etc. */
        Button uploadButton = new Button("upload image");
        Button lengthButton = new Button("length");
        Button angleButton = new Button("angle");
        Button circumferenceButton = new Button("circumference");

        Label adjustmentLabel = new Label("Adjustment");
        Label thicknessLabel = new Label("thickness");
        Label colorLabel = new Label("color");
        Label descriptonLabel = new Label("Description");

        Slider thicknessSlider = new Slider();
        Slider colorSlider = new Slider();

        /* Testbild um UI zu gestalten */
        Image image1 = new Image("/image-data/hand-xray.jpg");
        ImageView iv1 = new ImageView();
        iv1.setFitWidth(400);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(true);
        iv1.setCache(true);
        iv1.setImage(image1);

        /* Test-Textfeld um UI zu gestalten */
        Text testText = new Text(10, 50,
                "description: x-ray human hand\n" +
                "image-file: test.png\n" +
                "resolution: 1500mm");

        /* vbox for buttons*/
        VBox menuButtons = new VBox();
        menuButtons .getChildren().addAll(uploadButton, lengthButton, angleButton, circumferenceButton);
        menuButtons .setAlignment(Pos.TOP_LEFT);
        menuButtons .setSpacing(10);
        menuButtons .setPadding(new Insets(5, 5, 5, 5));
        menuButtons .setStyle("-fx-background-color: white;");

        /* hbox for the thickness settings. */
        HBox thicknessSettings = new HBox();
        thicknessSettings.getChildren().addAll(thicknessLabel,thicknessSlider);
        thicknessSettings.setAlignment(Pos.CENTER_LEFT);

        /* hbox for color settings. */
        HBox colorSettings = new HBox();
        colorSettings.getChildren().addAll(colorLabel,colorSlider);
        colorSettings.setAlignment(Pos.CENTER_LEFT);

        /* main menu vbox to hold all the menu elements */
        VBox menu = new VBox();
        menu.getChildren().addAll(menuButtons, adjustmentLabel, thicknessSettings, colorSettings, descriptonLabel,testText);
        menu.setAlignment(Pos.TOP_LEFT);
        menu.setSpacing(10);
        menu.setPadding(new Insets(5, 5, 5, 5));
        menu.setStyle("-fx-background-color: white;");

        /* vbox for the uploaded image */
        VBox imageWindow = new VBox();
        imageWindow.getChildren().add(iv1);
        imageWindow.setAlignment(Pos.TOP_RIGHT);
        imageWindow.setSpacing(10);
        imageWindow.setPadding(new Insets(5, 5, 5, 5));
        imageWindow.setStyle("-fx-background-color: green;");

        /* main hbox to hold the vboxes menu and imageWindow */
        HBox workspace = new HBox();
        workspace.getChildren().addAll(menu, imageWindow);
        workspace.setAlignment(Pos.CENTER_LEFT);
        workspace.setSpacing(10);
        workspace.setPadding(new Insets(5, 5, 5, 5));
        workspace.setStyle("-fx-background-color: blue;");

        /* panes, scene and stage */
        GridPane base = new GridPane();
        base.getChildren().addAll(workspace);

        Scene scene = new Scene(base, 650, 650);

        stage.setScene(scene);
        stage.setTitle("Hallo");
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
