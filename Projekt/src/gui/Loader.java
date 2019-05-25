package gui;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class Loader {
    static void Loader(Stage stage, Button uploadButton, ImageView iv1, Text infos) {
        uploadButton.setOnAction(event -> {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(stage);

        Image image = new Image("file:" + selectedFile.getAbsolutePath());

        String metaFilePath = selectedFile.getAbsolutePath();
        String folderPath = metaFilePath.substring(0,metaFilePath.lastIndexOf('/'));
        String metaFileType = metaFilePath.substring(metaFilePath.lastIndexOf('.')+1);
        AtomicReference<String> description = new AtomicReference<>("");
        AtomicReference<String> imageFileName = new AtomicReference<>("");
        AtomicReference<String> resolution = new AtomicReference<>("");

        System.out.println(folderPath);
        System.out.println(metaFileType);

        try(Stream<String> stream = Files.lines(Paths.get(metaFilePath))) {
            stream.forEach(String->{
                if(String.contains("description")) {
                    description.set(String.substring(String.indexOf(' ') + 1)); /* Possible error source: +1 means one char after ' ', if there's no space, the name will be omitted */
                    System.out.println(description.get());
                }
                else if(String.contains("image-file")) {
                    imageFileName.set(String.substring(String.indexOf(' ') + 1));
                    System.out.println(imageFileName.get());
                }
                else if (String.contains("resolution")) {
                    resolution.set(String.substring(String.indexOf(' ')+1));
                    System.out.println(resolution);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


        Image loadedImage = new Image("file:"+folderPath+"/"+imageFileName);

            System.out.println(folderPath+"/"+imageFileName);


        iv1.setImage(loadedImage);
        infos.setText("Filename: "+ String.valueOf(imageFileName) + "\n" +
                "Description: " + String.valueOf(description) + "\n" +
                "Resolution: " + String.valueOf(resolution));

    });
    }
}
