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

import java.io.File;
import java.util.ArrayList;

public class ControlPane extends Pane {

    private static PictureData picture;
    private static Color color = Color.RED;
    private static Color textColor = Color.WHITE;
    private static double thickness = 1;
    private static Group lengthLineGroup = new Group();

    public ControlPane(ImagePane imagePane) {

        /* elements, buttons, labels etc. */
        Button uploadButton = new Button("upload image");
        Button lengthButton = new Button("length");
        Button angleButton = new Button("angle");
        Button circumferenceButton = new Button("circumference");

        Label adjustmentLabel = new Label("Adjustment");
        Label thicknessLabel = new Label("thickness");
        Label colorLabel = new Label("color");
        Label infoLabel = new Label("Info");

        Slider thicknessSlider = new Slider(1,5,1);
        ColorPicker colorPicker = new ColorPicker(Color.RED);

        /* Textfeld zur Darstellung der Bildinformationen */
        Text infos = new Text(10, 10, "Filename: -\nDescription: -\nResolution: -");
        Text measurements = new Text(10, 10, "");
        infos.setWrappingWidth(200);

        /* vbox for buttons*/
        VBox menuButtons = new VBox();
        menuButtons.getChildren().addAll(uploadButton, lengthButton, angleButton, circumferenceButton);

        /* hbox for the thickness settings. */
        HBox thicknessSettings = new HBox();
        thicknessSettings.getChildren().addAll(thicknessLabel, thicknessSlider);

        /* hbox for color settings. */
        HBox colorSettings = new HBox();
        colorSettings.getChildren().addAll(colorLabel, colorPicker);

        /* main menu vbox to hold all the menu elements */
        VBox menu = new VBox();
        menu.setMaxWidth(200);
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
        adjustmentLabel.getStyleClass().add("mainLabel");
        infoLabel.getStyleClass().add("mainLabel");

        /* upload Button */
        uploadButton.setOnAction(event -> {

            ImagePane.removeLine(lengthLineGroup);

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

                picture = loader.loadPicture(metaFilePath);

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

        /* lengthButton */
        lengthButton.setOnAction(event -> {

            ImagePane.removeLine(lengthLineGroup);

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
                        return start.distance(end)*picture.getPictureResolutionValueLong();
                    }, ball1.centerXProperty(), ball1.centerYProperty(),
                    ball2.centerXProperty(), ball2.centerYProperty());

            text.textProperty().bind(distance.asString("Distance: %f " + picture.getPictureResolutionUnit()));
            text.xProperty().bind(ball1.centerXProperty().add(ball2.centerXProperty()).divide(2));
            text.yProperty().bind(ball1.centerYProperty().add(ball2.centerYProperty()).divide(2));
            // measurements.setText("Distance: "+distance.toString()+" " + picture.getPictureResolutionUnit());
        });

        /* angleButton */
        angleButton.setOnAction(event -> {

            ImagePane.removeLine(lengthLineGroup);

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

            Group angleLine = new Group();

            Ball ball1 = new Ball(100, 200, 5*thickness);
            ball1.setFill(color);

            Ball ball2 = new Ball(300, 200, 5*thickness);
            ball2.setFill(color);

            Ball ball3 = new Ball(320, 300, 5*thickness);
            ball3.setFill(color);

            Connection connection = new Connection(ball1, ball2);
            connection.setStroke(color);
            connection.setStrokeWidth(thickness);
            angleLine.getChildren().add(0, connection);


            Connection secondConnection = new Connection(ball2, ball3);
            secondConnection.setStroke(color);
            secondConnection.setStrokeWidth(thickness);
            angleLine.getChildren().add(0, secondConnection);

            Text text = new Text();
            text.setFill(textColor);

            angleLine.getChildren().addAll(ball1, ball2, ball3, text);
            ImagePane.addLine(angleLine);

            DoubleBinding measureAngle = Bindings.createDoubleBinding(() -> {

                        Point2D start = new Point2D(ball1.getCenterX(), ball1.getCenterY());
                        Point2D middle = new Point2D(ball2.getCenterX(), ball2.getCenterY());
                        Point2D end = new Point2D(ball3.getCenterX(), ball3.getCenterY());

                        return middle.angle(start, end);
                    }, ball1.centerXProperty(), ball1.centerYProperty(),
                    ball2.centerXProperty(), ball2.centerYProperty(),
                    ball3.centerXProperty(), ball3.centerYProperty());

            text.textProperty().bind(measureAngle.asString("Angle: %f"));
            text.xProperty().bind(ball1.centerXProperty().add(ball2.centerXProperty()).divide(2));
            text.yProperty().bind(ball1.centerYProperty().add(ball2.centerYProperty()).divide(2));
        });


        /* circumferenceButton */
        circumferenceButton.setOnAction(event -> {

            ImagePane.removeLine(lengthLineGroup);

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

            Group circumferenceLine = new Group();
            ArrayList<Ball> balls = new ArrayList<Ball>();

            EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if (mouseEvent.getEventType() == MouseEvent.MOUSE_CLICKED) {
                        Ball ball1 = new Ball(mouseEvent.getX(),mouseEvent.getY(), 15);
                        ball1.setFill(color);
                        balls.add(ball1);

                        if (balls.size() > 1) {

                            Connection c1 = new Connection(balls.get(balls.size()-2), balls.get(balls.size()-1));
                            circumferenceLine.getChildren().addAll(c1);

                        }
                        circumferenceLine.getChildren().addAll(ball1);

                    }

                }

            };

            imagePane.setOnMouseClicked(mouseHandler);
            ImagePane.addLine(circumferenceLine);


                   /* imagePane.setOnMouseClicked(event1 ->

                    {
                        Group circumferenceLine = new Group();
                        Ball ball1 = new Ball(event1.getX(), event1.getY(), 15);
                        ball1.setFill(color);

                        imagePane.setOnMouseClicked(event2 ->

                        {
                            Ball ball2 = new Ball(event2.getX(), event2.getY(), 15);
                            ball2.setFill(color);

                            Connection connection = new Connection(ball1, ball2);
                            connection.setStroke(color);
                            connection.setStrokeWidth(5);
                            circumferenceLine.getChildren().add(0, connection);

                            circumferenceLine.getChildren().addAll(ball1, ball2);
                            ImagePane.addLine(circumferenceLine);

                        });
                    });*/

        });

        /*thicknessSlider.setOnDragDropped(event -> {
            thickness = thicknessSlider.getValue();
            System.out.println(thickness);
        });*/

        thicknessSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                thickness = thicknessSlider.getValue();
                System.out.println(thickness);
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
}
