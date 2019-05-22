package gui;

import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;

public class Loader extends Gui {
    uploadButton.setOnAction(event -> {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        Image image = new Image("file:" + selectedFile.getAbsolutePath());
        imageView.setImage(image);
    });
}
