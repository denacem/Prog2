package gui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import loader.PictureData;
import loader.PictureLoader;
import loader.TextPictureLoader;
import loader.XmlPictureLoader;

import java.io.File;
import java.util.ArrayList;

import static javafx.scene.paint.Color.RED;

public class ControlPane extends Pane {

    private static PictureData picture;
    private static Color color = Color.RED;
    private static Color textColor = Color.WHITE;
    private static ColorPicker colorPicker = new ColorPicker(RED);
    private static double thickness = 1.5;
    private static Group lengthLineGroup = new Group();
    private static Group angleLineGroup = new Group();
    private static Group circumferenceLineGroup = new Group();
    private static Text measurements = new Text();
    private static double importScaleFactor = 1;

    public ControlPane(ImagePane imagePane) {

        Button uploadButton = new Button("upload image");
        Button lengthButton = new Button("length");
        Button angleButton = new Button("angle");
        Button circumferenceButton = new Button("circumference");
        Button resetButton = new Button("reset");

        Label adjustmentLabel = new Label("Adjustment");
        Label thicknessLabel = new Label("thickness");
        Label colorLabel = new Label("color");
        Label infoLabel = new Label("Info");
        Label measurementsLabel = new Label("Measurements");

        Slider thicknessSlider = new Slider(1, 5, 3);

        Text infos = new Text(10, 10, "Filename: -\nDescription: -\nResolution: -");
        measurements = new Text(10, 10, "-");
        infos.setWrappingWidth(200);

        VBox menuButtons = new VBox();
        menuButtons.getChildren().addAll(uploadButton, lengthButton, angleButton, circumferenceButton, resetButton);

        HBox thicknessSettings = new HBox();
        thicknessSettings.getChildren().addAll(thicknessLabel, thicknessSlider);

        HBox colorSettings = new HBox();
        colorSettings.getChildren().addAll(colorLabel, colorPicker);

        VBox menu = new VBox();
        menu.setMaxWidth(230);
        menu.getChildren().addAll(menuButtons, adjustmentLabel, thicknessSettings, colorSettings, infoLabel, infos, measurementsLabel, measurements);

        getChildren().addAll(menu);

        menu.getStyleClass().add("menu");
        colorSettings.getStyleClass().add("menu");
        thicknessSettings.getStyleClass().add("menu");
        menuButtons.getStyleClass().add("menu");
        uploadButton.getStyleClass().add("uploadButton");
        lengthButton.getStyleClass().add("settingsButtons");
        angleButton.getStyleClass().add("settingsButtons");
        circumferenceButton.getStyleClass().add("settingsButtons");
        resetButton.getStyleClass().add("settingsButtons");
        adjustmentLabel.getStyleClass().add("mainLabel");
        infoLabel.getStyleClass().add("mainLabel");
        measurementsLabel.getStyleClass().add("mainLabel");
        infos.getStyleClass().add("text");

        uploadButton.setOnAction(event -> {

            resetter(imagePane);

            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);

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

                picture = loader.loadPicture(metaFilePath);

                if (picture != null) {
                    Image loadedImage = new Image("file:" + metaFolderPath + "/" + picture.getPictureFileName());

                    importScaleFactor = 500 / loadedImage.getWidth();

                    ImagePane.changeImage(loadedImage);

                    infos.setText("Filename: " + String.valueOf(picture.getPictureFileName()) + "\n" +
                            "Description: " + String.valueOf(picture.getPictureDescription()) + "\n" +
                            "Resolution: " + String.valueOf(picture.getPictureResolutionValue()) + " " + String.valueOf(picture.getPictureResolutionUnit()) + "\n" +
                            "Size: " + Math.round(loadedImage.getHeight() * picture.getPictureResolutionValueDouble() * 100.0) / 100.0 + " by " + Math.round(loadedImage.getWidth() * picture.getPictureResolutionValueDouble() * 100.00) / 100.00 + " " + picture.getPictureResolutionUnit());
                }
            }
        });

        lengthButton.setOnAction(event -> {

            resetter(imagePane);

            class Ball extends Circle {
                private double dragBaseX;
                private double dragBaseY;

                public Ball(double centerX, double centerY, double radius) {
                    super(centerX, centerY, radius);

                    setOnMousePressed(event13 -> {
                        dragBaseX = event13.getSceneX() - getCenterX();
                        dragBaseY = event13.getSceneY() - getCenterY();
                    });

                    setOnMouseDragged(event14 -> {
                        setCenterX(event14.getSceneX() - dragBaseX);
                        setCenterY(event14.getSceneY() - dragBaseY);
                    });
                }
            }

            class Connection extends Line {
                public Connection(Ball startBall, Ball endBall) {
                    startXProperty().bind(startBall.centerXProperty());
                    startYProperty().bind(startBall.centerYProperty());
                    endXProperty().bind(endBall.centerXProperty());
                    endYProperty().bind(endBall.centerYProperty());
                }
            }

            Ball ball1 = new Ball(100, 200, 5 * thickness);
            ball1.setFill(color);

            Ball ball2 = new Ball(300, 200, 5 * thickness);
            ball2.setFill(color);

            Connection connection = new Connection(ball1, ball2);
            connection.setStroke(color);
            lengthLineGroup.getChildren().add(0, connection);

            Text text = new Text();
            text.setFill(textColor);

            lengthLineGroup.getChildren().addAll(ball1, ball2, text);
            ImagePane.addLine(lengthLineGroup);

            DoubleBinding distance = Bindings.createDoubleBinding(() -> {

                        Point2D start = new Point2D(ball1.getCenterX(), ball1.getCenterY());
                        Point2D end = new Point2D(ball2.getCenterX(), ball2.getCenterY());
                        return start.distance(end) * picture.getPictureResolutionValueDouble() / importScaleFactor;
                    }, ball1.centerXProperty(), ball1.centerYProperty(),
                    ball2.centerXProperty(), ball2.centerYProperty());

            text.textProperty().bind(distance.asString("Distance: %.2f " + picture.getPictureResolutionUnit()));
            text.xProperty().bind(ball1.centerXProperty().add(ball2.centerXProperty()).divide(2));
            text.yProperty().bind(ball1.centerYProperty().add(ball2.centerYProperty()).divide(2));

            measurements.textProperty().bind(distance.asString("Distance: %.2f " + picture.getPictureResolutionUnit()));

            thicknessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                ball1.setRadius(5 * thickness);
                ball2.setRadius(5 * thickness);
                connection.setStrokeWidth(thickness);
            });

            colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                ball1.setFill(colorPicker.getValue());
                ball2.setFill(colorPicker.getValue());
                connection.setStroke(colorPicker.getValue());
                brightness();
                text.setFill(textColor);
            });
        });

        angleButton.setOnAction(event -> {

            resetter(imagePane);

            class Ball extends Circle {
                private double dragBaseX;
                private double dragBaseY;

                public Ball(double centerX, double centerY, double radius) {
                    super(centerX, centerY, radius);

                    setOnMousePressed(event1 -> {
                        dragBaseX = event1.getSceneX() - getCenterX();
                        dragBaseY = event1.getSceneY() - getCenterY();
                    });

                    setOnMouseDragged(event12 -> {
                        setCenterX(event12.getSceneX() - dragBaseX);
                        setCenterY(event12.getSceneY() - dragBaseY);
                    });
                }
            }

            class Connection extends Line {
                public Connection(Ball startBall, Ball endBall) {
                    startXProperty().bind(startBall.centerXProperty());
                    startYProperty().bind(startBall.centerYProperty());
                    endXProperty().bind(endBall.centerXProperty());
                    endYProperty().bind(endBall.centerYProperty());
                }
            }

            Ball ball1 = new Ball(100, 200, 5 * thickness);
            ball1.setFill(color);

            Ball ball2 = new Ball(300, 200, 5 * thickness);
            ball2.setFill(color);

            Ball ball3 = new Ball(320, 300, 5 * thickness);
            ball3.setFill(color);

            Connection connection1 = new Connection(ball1, ball2);
            connection1.setStroke(color);
            angleLineGroup.getChildren().add(0, connection1);

            Connection connection2 = new Connection(ball2, ball3);
            connection2.setStroke(color);
            angleLineGroup.getChildren().add(0, connection2);

            Text text = new Text();
            text.setFill(textColor);

            angleLineGroup.getChildren().addAll(ball1, ball2, ball3, text);
            ImagePane.addLine(angleLineGroup);

            DoubleBinding measureAngle = Bindings.createDoubleBinding(() -> {

                        Point2D start = new Point2D(ball1.getCenterX(), ball1.getCenterY());
                        Point2D middle = new Point2D(ball2.getCenterX(), ball2.getCenterY());
                        Point2D end = new Point2D(ball3.getCenterX(), ball3.getCenterY());

                        return middle.angle(start, end);
                    }, ball1.centerXProperty(), ball1.centerYProperty(),
                    ball2.centerXProperty(), ball2.centerYProperty(),
                    ball3.centerXProperty(), ball3.centerYProperty());

            text.textProperty().bind(measureAngle.asString("Angle: %.2f"));
            text.xProperty().bind(ball1.centerXProperty().add(ball2.centerXProperty()).divide(2));
            text.yProperty().bind(ball1.centerYProperty().add(ball2.centerYProperty()).divide(2));
            measurements.textProperty().bind(measureAngle.asString("Angle: %.2f°"));

            thicknessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                ball1.setRadius(5 * thickness);
                ball2.setRadius(5 * thickness);
                ball3.setRadius(5 * thickness);
                connection1.setStrokeWidth(thickness);
                connection2.setStrokeWidth(thickness);
            });

            colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                ball1.setFill(colorPicker.getValue());
                ball2.setFill(colorPicker.getValue());
                ball3.setFill(colorPicker.getValue());
                connection1.setStroke(colorPicker.getValue());
                connection2.setStroke(colorPicker.getValue());
                brightness();
                text.setFill(textColor);
            });
        });

        circumferenceButton.setOnAction(event -> {

            resetter(imagePane);

            Text text = new Text();
            text.setFill(textColor);

            class Ball extends Circle {

                public Ball(double centerX, double centerY, double radius) {
                    super(centerX, centerY, radius);
                }
            }

            class Connection extends Line {
                public Connection(Ball startBall, Ball endBall) {
                    startXProperty().bind(startBall.centerXProperty());
                    startYProperty().bind(startBall.centerYProperty());
                    endXProperty().bind(endBall.centerXProperty());
                    endYProperty().bind(endBall.centerYProperty());
                }
            }

            ArrayList<Ball> balls = new ArrayList<Ball>();
            ArrayList<Double> distances = new ArrayList<Double>();

            circumferenceLineGroup.getChildren().addAll(text);

            EventHandler<MouseEvent> mouseHandler = mouseEvent -> {

                if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {

                    Ball ball = new Ball(mouseEvent.getX(), mouseEvent.getY(), 5 * thickness);
                    ball.setFill(color);
                    balls.add(ball);

                    thicknessSlider.valueProperty().addListener((observable, oldValue, newValue) -> ball.setRadius(5 * thickness));

                    colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> ball.setFill(colorPicker.getValue()));

                    circumferenceLineGroup.getChildren().addAll(ball);

                    if (balls.size() > 1) {
                        Connection connection = new Connection(balls.get(balls.size() - 2), balls.get(balls.size() - 1));
                        connection.setStroke(colorPicker.getValue());
                        connection.setStrokeWidth(thickness);
                        circumferenceLineGroup.getChildren().add(0, connection);

                        thicknessSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                            ball.setRadius(5 * thickness);
                            connection.setStrokeWidth(thickness);
                        });

                        colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
                            ball.setFill(colorPicker.getValue());
                            connection.setStroke(colorPicker.getValue());
                            brightness();
                            text.setFill(textColor);
                        });

                        DoubleBinding distance = Bindings.createDoubleBinding(() -> {

                                    Point2D start = new Point2D(balls.get(balls.size() - 2).getCenterX(), (balls.get(balls.size() - 2).getCenterY()));
                                    Point2D end = new Point2D(balls.get(balls.size() - 1).getCenterX(), balls.get(balls.size() - 1).getCenterY());
                                    return start.distance(end) * picture.getPictureResolutionValueDouble() / importScaleFactor;
                                }, (balls.get(balls.size() - 2).centerXProperty()), (balls.get(balls.size() - 2).centerYProperty()),
                                balls.get(balls.size() - 1).centerXProperty(), balls.get(balls.size() - 1).centerYProperty());

                        distances.add(distance.getValue());

                        double fullDistance = 0;
                        for (Double d : distances)
                            fullDistance += d;

                        text.setText(String.valueOf(Math.round(fullDistance * 100.00) / 100.00) + picture.getPictureResolutionUnit());
                        text.xProperty().bind(balls.get(balls.size() - 1).centerXProperty().add(20));
                        text.yProperty().bind(balls.get(balls.size() - 1).centerYProperty().add(20));

                        measurements.textProperty().unbind();
                        measurements.setText("Distance: " + String.valueOf(Math.round(fullDistance * 100.00) / 100.00) + picture.getPictureResolutionUnit());
                    }

                }
            };

            imagePane.setOnMouseClicked(mouseHandler);

            ImagePane.addLine(circumferenceLineGroup);

        });

        resetButton.setOnAction(event -> {
            resetter(imagePane);
        });

        thicknessSlider.valueProperty().addListener((observable, oldValue, newValue) -> thickness = thicknessSlider.getValue());

        colorPicker.setOnAction(event -> {
            color = colorPicker.getValue();
        });
    }

    public void brightness() {
        if (colorPicker.getValue().getBrightness() > 0.4) {
            textColor = Color.BLACK;
        } else {
            textColor = Color.WHITE;
        }
    }

    public void resetter(ImagePane imagePane) {
        ImagePane.removeLine(lengthLineGroup);
        ImagePane.removeLine(angleLineGroup);
        ImagePane.removeLine(circumferenceLineGroup);
        imagePane.setOnMouseClicked(null);
        measurements.textProperty().unbind();
        measurements.setText("-");
    }
}