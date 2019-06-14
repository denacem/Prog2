package loader;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import java.nio.file.Files;

public class TextPictureLoader implements PictureLoader {

    public PictureData loadPicture(String metaFilePath) {

        AtomicReference<String> pictureFileName = new AtomicReference<>("");
        AtomicReference<String> pictureDescription = new AtomicReference<>("");
        AtomicReference<String> pictureResolutionValue = new AtomicReference<>("1");
        double pictureResolutionValueDouble;
        AtomicReference<String> pictureResolutionUnit = new AtomicReference<>("px");

        try (Stream<String> stream = Files.lines(Paths.get(metaFilePath))) {
            stream.forEach(String -> {
                if (String.contains("description")) {
                    pictureDescription.set(String.substring(String.indexOf(' ') + 1));
                } else if (String.contains("image-file")) {
                    pictureFileName.set(String.substring(String.indexOf(' ') + 1));
                } else if (String.contains("resolution")) {
                    pictureResolutionValue.set(String.substring(String.indexOf(' ') + 1, String.lastIndexOf(' ')));
                    pictureResolutionUnit.set(String.substring(String.lastIndexOf(' ') + 1));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        pictureResolutionValueDouble = Double.valueOf(String.valueOf(pictureResolutionValue));

        return new PictureData(pictureFileName, pictureDescription, pictureResolutionValue, pictureResolutionValueDouble, pictureResolutionUnit);
    }
}
