package gui;

import java.util.concurrent.atomic.AtomicReference;

public class PictureData {
    private AtomicReference<String> pictureFileName;
    private AtomicReference<String> pictureDescription;
    private AtomicReference<String> pictureResolutionValue;
    private AtomicReference<String> pictureResolutionUnit;

    public PictureData(AtomicReference<String> pictureFileName, AtomicReference<String> pictureDescription, AtomicReference<String> pictureResolutionValue, AtomicReference<String> pictureResolutionUnit) {
        this.pictureFileName = pictureFileName;
        this.pictureDescription = pictureDescription;
        this.pictureResolutionValue = pictureResolutionValue;
        this.pictureResolutionUnit = pictureResolutionUnit;
    }

    public AtomicReference<String> getPictureFileName() { return pictureFileName; }
    public AtomicReference<String> getPictureDescription() { return pictureDescription; }
    public AtomicReference<String> getPictureResolutionValue() { return pictureResolutionValue; }
    public AtomicReference<String> getPictureResolutionUnit() { return pictureResolutionUnit; }

}
