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
import javafx.scene.shape.Line;
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

        /* line to separate menu elements */
        Line dividerLine = new Line();

        /* Testbild um UI zu gestalten */
        Image image1 = new Image("/image-data/hand-xray.jpg");
        ImageView iv1 = new ImageView();
        iv1.setImage(image1);
        iv1.setFitWidth(400);
        iv1.setPreserveRatio(true);
        iv1.setSmooth(true);
        iv1.setCache(true);

        /* Test-Textfeld um UI zu gestalten */
        Text testText = new Text(10, 50,
                "description: x-ray human hand\n" +
                "image-file: test.png\n" +
                "resolution: 1500mm");

        /* vbox for buttons*/
        VBox menuButtons = new VBox();
        menuButtons .getChildren().addAll(uploadButton, lengthButton, angleButton, circumferenceButton);

        /* hbox for the thickness settings. */
        HBox thicknessSettings = new HBox();
        thicknessSettings.getChildren().addAll(thicknessLabel,thicknessSlider);

        /* hbox for color settings. */
        HBox colorSettings = new HBox();
        colorSettings.getChildren().addAll(colorLabel,colorSlider);

        /* main menu vbox to hold all the menu elements */
        VBox menu = new VBox();
        menu.getChildren().addAll(menuButtons, adjustmentLabel, thicknessSettings, colorSettings, descriptonLabel,testText);

        /* vbox for the uploaded image */
        VBox imageWindow = new VBox();
        imageWindow.getChildren().add(iv1);

        /* main hbox to hold the vboxes menu and imageWindow */
        HBox workspace = new HBox();
        workspace.getChildren().addAll(menu, imageWindow);


        /* styles */
        workspace.getStyleClass().add("workspace");
        imageWindow.getStyleClass().add("imageWindow");
        menu.getStyleClass().add("menu");
        colorSettings.getStyleClass().add("menu");
        thicknessSettings.getStyleClass().add("menu");
        menuButtons.getStyleClass().add("menu");
        uploadButton.getStyleClass().add("uploadButton");
        lengthButton.getStyleClass().add("settingsButtons");
        angleButton.getStyleClass().add("settingsButtons");
        circumferenceButton.getStyleClass().add("settingsButtons");
        adjustmentLabel.getStyleClass().add("mainLabel");
        descriptonLabel.getStyleClass().add("mainLabel");

        /* panes, scene and stage */
        GridPane base = new GridPane();
        base.getChildren().addAll(workspace);

        Scene scene = new Scene(base, 650, 650);
        scene.getStylesheets().add("/stylessheet/UiStylesheet.css");

        stage.setScene(scene);
        stage.setTitle("Hallo");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
