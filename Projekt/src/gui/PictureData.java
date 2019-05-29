package gui;

import java.util.concurrent.atomic.AtomicReference;

public class PictureData {
    private AtomicReference<String> pictureDescription;
    private AtomicReference<String> pictureFileName;
    private AtomicReference<String> pictureResolutionUnit;
    private AtomicReference<String> pictureResolutionValue;

    public PictureData(AtomicReference<String> pictureDescription, AtomicReference<String> pictureFileName, AtomicReference<String> pictureResolutionUnit, AtomicReference<String> pictureResolutionValue) {
        this.pictureFileName = pictureFileName;
        this.pictureDescription = pictureDescription;
        this.pictureResolutionUnit = pictureResolutionUnit;
        this.pictureResolutionValue = pictureResolutionValue;
    }

    public AtomicReference<String> getPictureFileName() { return pictureFileName; }
    public AtomicReference<String> getPictureResolutionUnit() { return pictureResolutionUnit; }
    public AtomicReference<String> getPictureResolutionValue() { return pictureResolutionValue; }
    public AtomicReference<String> getPictureDescription() { return pictureDescription; }

}
