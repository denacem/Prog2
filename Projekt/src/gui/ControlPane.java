package gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;

public class ControlPane extends Pane {

    public ControlPane () {

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

        /* Textfeld zur Darstellung der Bildinformationen */
        Text infos = new Text(10, 10, "something");
        infos.setWrappingWidth(200);

        /* vbox for buttons*/
        VBox menuButtons = new VBox();
        menuButtons.getChildren().addAll(uploadButton, lengthButton, angleButton, circumferenceButton);

        /* hbox for the thickness settings. */
        HBox thicknessSettings = new HBox();
        thicknessSettings.getChildren().addAll(thicknessLabel, thicknessSlider);

        /* hbox for color settings. */
        HBox colorSettings = new HBox();
        colorSettings.getChildren().addAll(colorLabel, colorSlider);

        /* main menu vbox to hold all the menu elements */
        VBox menu = new VBox();
        menu.setMaxWidth(200);
        menu.getChildren().addAll(menuButtons, adjustmentLabel, thicknessSettings, colorSettings, descriptonLabel, infos);

        /* adds menu to ControlPane */
        getChildren().addAll(menu);

        /* Styles */
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

        uploadButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);

//        Image image = new Image("file:" + selectedFile.getAbsolutePath());

            String metaFilePath = selectedFile.getAbsolutePath();
            String metaFolderPath = metaFilePath.substring(0, metaFilePath.lastIndexOf('/'));
            String metaFileType = metaFilePath.substring(metaFilePath.lastIndexOf('.') + 1);

            PictureLoader loader = null;


            if (metaFileType.equals("txt")) {
                loader = new TextPictureLoader();
            } else if (metaFileType.equals("xml")) {
                loader = new XmlPictureLoader();
            } else {
                System.out.println("File type not supported");
            }

            if (loader != null) {

                PictureData picture = loader.loadPicture(metaFilePath);

                if (picture != null) {
                    Image loadedImage = new Image("file:" + metaFolderPath + "/" + picture.getPictureFileName());

                    System.out.println(metaFolderPath);
                    System.out.println(picture.getPictureFileName());

                    ImagePane.changeImage(loadedImage);

                    infos.setText("Filename: " + String.valueOf(picture.getPictureFileName()) + "\n" +
                            "Description: " + String.valueOf(picture.getPictureDescription()) + "\n" +
                            "Resolution: " + String.valueOf(picture.getPictureResolutionValue()) + " " + String.valueOf(picture.getPictureResolutionUnit()));

                }
            }
        });


    }
}
