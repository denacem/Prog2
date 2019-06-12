package gui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
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

public class ControlPane extends Pane {

    private static PictureData picture;
    private static Color color = Color.RED;
    private static Color textColor = Color.WHITE;
    private static double thickness = 1.5;
    private static Group lengthLineGroup = new Group();
    private static Group angleLineGroup = new Group();
    private static Group circumferenceLineGroup = new Group();
    private static Text measurements = new Text();
    private static double importScaleFactor = 1;

    public ControlPane(ImagePane imagePane) {

        /* elements, buttons, labels etc. */
        Button uploadButton = new Button("upload image");
        Button lengthButton = new Button("length");
        Button angleButton = new Button("angle");
        Button circumferenceButton = new Button("circumference");
        Button resetButton = new Button("reset");

        Label adjustmentLabel = new Label("Adjustment");
        Label thicknessLabel = new Label("thickness");
        Label colorLabel = new Label("color");
        Label infoLabel = new Label("Info");

        Slider thicknessSlider = new Slider(1,5,3);
        ColorPicker colorPicker = new ColorPicker(Color.RED);

        /* Textfeld zur Darstellung der Bildinformationen */
        Text infos = new Text(10, 10, "Filename: -\nDescription: -\nResolution: -");
        measurements = new Text(10, 10, "Measurements");
        infos.setWrappingWidth(200);

        /* vbox for buttons*/
        VBox menuButtons = new VBox();
        menuButtons.getChildren().addAll(uploadButton, lengthButton, angleButton, circumferenceButton, resetButton);

        /* hbox for the thickness settings. */
        HBox thicknessSettings = new HBox();
        thicknessSettings.getChildren().addAll(thicknessLabel, thicknessSlider);

        /* hbox for color settings. */
        HBox colorSettings = new HBox();
        colorSettings.getChildren().addAll(colorLabel, colorPicker);

        /* main menu vbox to hold all the menu elements */
        VBox menu = new VBox();
        menu.setMaxWidth(230);
        menu.getChildren().addAll(menuButtons, adjustmentLabel, thicknessSettings, colorSettings, infoLabel, infos, measurements);

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
        resetButton.getStyleClass().add("settingsButtons");
        adjustmentLabel.getStyleClass().add("mainLabel");
        infoLabel.getStyleClass().add("mainLabel");

        /* upload Button */
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

                    importScaleFactor = 500/loadedImage.getWidth();

                    System.out.println(metaFolderPath);
                    System.out.println(picture.getPictureFileName());

                    ImagePane.changeImage(loadedImage);

                    infos.setText("Filename: " + String.valueOf(picture.getPictureFileName()) + "\n" +
                            "Description: " + String.valueOf(picture.getPictureDescription()) + "\n" +
                            "Resolution: " + String.valueOf(picture.getPictureResolutionValue()) + " " + String.valueOf(picture.getPictureResolutionUnit())+ "\n" +
                            "Size: " + Math.round(loadedImage.getHeight()*picture.getPictureResolutionValueDouble()*100.0)/100.0 + " by " + Math.round(loadedImage.getWidth()*picture.getPictureResolutionValueDouble()*100.00)/100.00 + " " + picture.getPictureResolutionUnit());
                }
            }
        });

        /* lengthButton */
        lengthButton.setOnAction(event -> {

            resetter(imagePane);

            class Ball extends Circle {
                private double dragBaseX;
                private double dragBaseY;

                public Ball(double centerX, double centerY, double radius) {
                    super(centerX, centerY, radius);

                    setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            dragBaseX = event.getSceneX() - getCenterX();
                            dragBaseY = event.getSceneY() - getCenterY();
                        }
                    });

                    setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            setCenterX(event.getSceneX() - dragBaseX);
                            setCenterY(event.getSceneY() - dragBaseY);
                        }
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

            //Group lengthLineGroup = new Group();

            Ball ball1 = new Ball(100, 200, 5*thickness);
            ball1.setFill(color);

            Ball ball2 = new Ball(300, 200, 5*thickness);
            ball2.setFill(color);

            Connection connection = new Connection(ball1, ball2);
            connection.setStroke(color);
            connection.setStrokeWidth(thickness);
            lengthLineGroup.getChildren().add(0,connection);

            Text text = new Text();
            text.setFill(textColor);

            lengthLineGroup.getChildren().addAll(ball1, ball2, text);
            ImagePane.addLine(lengthLineGroup);

            DoubleBinding distance = Bindings.createDoubleBinding(() -> {

                        Point2D start = new Point2D(ball1.getCenterX(), ball1.getCenterY());
                        Point2D end = new Point2D(ball2.getCenterX(), ball2.getCenterY());
                        return start.distance(end)*picture.getPictureResolutionValueDouble()/importScaleFactor;
                    }, ball1.centerXProperty(), ball1.centerYProperty(),
                    ball2.centerXProperty(), ball2.centerYProperty());

            text.textProperty().bind(distance.asString("Distance: %.2f " + picture.getPictureResolutionUnit()));
            text.xProperty().bind(ball1.centerXProperty().add(ball2.centerXProperty()).divide(2));
            text.yProperty().bind(ball1.centerYProperty().add(ball2.centerYProperty()).divide(2));

            measurements.textProperty().bind(distance.asString("Distance: %.2f " + picture.getPictureResolutionUnit()));
        });

        /* angleButton */
        angleButton.setOnAction(event -> {

            resetter(imagePane);

            class Ball extends Circle {
                private double dragBaseX;
                private double dragBaseY;

                public Ball(double centerX, double centerY, double radius) {
                    super(centerX, centerY, radius);

                    setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            dragBaseX = event.getSceneX() - getCenterX();
                            dragBaseY = event.getSceneY() - getCenterY();
                        }
                    });

                    setOnMouseDragged(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            setCenterX(event.getSceneX() - dragBaseX);
                            setCenterY(event.getSceneY() - dragBaseY);
                        }
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


            Ball ball1 = new Ball(100, 200, 5*thickness);
            ball1.setFill(color);

            Ball ball2 = new Ball(300, 200, 5*thickness);
            ball2.setFill(color);

            Ball ball3 = new Ball(320, 300, 5*thickness);
            ball3.setFill(color);

            Connection connection = new Connection(ball1, ball2);
            connection.setStroke(color);
            connection.setStrokeWidth(thickness);
            angleLineGroup.getChildren().add(0, connection);


            Connection secondConnection = new Connection(ball2, ball3);
            secondConnection.setStroke(color);
            secondConnection.setStrokeWidth(thickness);
            angleLineGroup.getChildren().add(0, secondConnection);

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
        });


        /* circumferenceButton */
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

            EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {

                        Ball ball1 = new Ball(mouseEvent.getX(),mouseEvent.getY(), 5*thickness);
                        ball1.setFill(color);
                        balls.add(ball1);

                        if (balls.size() > 1) {

                            Connection c1 = new Connection(balls.get(balls.size()-2), balls.get(balls.size()-1));
                            c1.setStroke(color);
                            c1.setStrokeWidth(thickness);
                            circumferenceLineGroup.getChildren().add(0, c1);

                        }
                        circumferenceLineGroup.getChildren().addAll(ball1);

                        DoubleBinding distance = Bindings.createDoubleBinding(() -> {

                                    Point2D start = new Point2D(balls.get(balls.size()-2).getCenterX(), (balls.get(balls.size()-2).getCenterY()));
                                    Point2D end = new Point2D(balls.get(balls.size()-1).getCenterX(), balls.get(balls.size()-1).getCenterY());
                                    return start.distance(end)*picture.getPictureResolutionValueDouble()/importScaleFactor;
                                }, (balls.get(balls.size()-2).centerXProperty()), (balls.get(balls.size()-2).centerYProperty()),
                                balls.get(balls.size()-1).centerXProperty(), balls.get(balls.size()-1).centerYProperty());


                        distances.add(distance.getValue());

                        double fullDistance = 0;
                        for(Double d : distances)
                            fullDistance += d;

                        text.setText(String.valueOf(Math.round(fullDistance*100.00)/100.00)+picture.getPictureResolutionUnit());
                        text.xProperty().bind(balls.get(balls.size()-1).centerXProperty());
                        text.yProperty().bind(balls.get(balls.size()-1).centerYProperty());
                        measurements.textProperty().unbind();
                        measurements.setText("Distance: "+String.valueOf(Math.round(fullDistance*100.00)/100.00)+picture.getPictureResolutionUnit());
                    }
                }
            };

            imagePane.setOnMouseClicked(mouseHandler);

            ImagePane.addLine(circumferenceLineGroup);


        });

        resetButton.setOnAction(event -> {
            resetter(imagePane);
        });


        thicknessSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                thickness = thicknessSlider.getValue();
            }
        });


        colorPicker.setOnAction(event -> {
            color = colorPicker.getValue();
            System.out.println(color.getBrightness());
            if (color.getBrightness() > 0.5) {
                textColor = Color.BLACK;
            }
            else {
                textColor = Color.WHITE;
            }
        });
    }

    public void resetter(ImagePane imagePane) {
        ImagePane.removeLine(lengthLineGroup);
        ImagePane.removeLine(angleLineGroup);
        ImagePane.removeLine(circumferenceLineGroup);
        imagePane.setOnMouseClicked(null);
        measurements.textProperty().unbind();
        measurements.setText("Measurements");
    }
}