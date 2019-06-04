package gui;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.Vector;

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

        /* upload Button */
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

        /* lengthButton */
        lengthButton.setOnAction(event -> {

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

            Ball ball1 = new Ball(100, 200, 15);
            ball1.setFill(Color.RED);

            Ball ball2 = new Ball(300, 200, 15);
            ball2.setFill(Color.RED);

            Connection connection = new Connection(ball1, ball2);
            connection.setStroke(Color.CYAN);
            connection.setStrokeWidth(5);

            Text text = new Text();

            Group lengthLineG = new Group(ball1,ball2,connection,text);
            ImagePane.addLine(lengthLineG);

            DoubleBinding distance = Bindings.createDoubleBinding(() -> {

                        Point2D start = new Point2D(ball1.getCenterX(), ball1.getCenterY());
                        Point2D end = new Point2D(ball2.getCenterX(), ball2.getCenterY());
                        return start.distance(end);
                    }, ball1.centerXProperty(), ball1.centerYProperty(),
                    ball2.centerXProperty(), ball2.centerYProperty());

            text.textProperty().bind(distance.asString("Distance: %f"));
            text.xProperty().bind(ball1.centerXProperty().add(ball2.centerXProperty()).divide(2));
            text.yProperty().bind(ball1.centerYProperty().add(ball2.centerYProperty()).divide(2));
        });

        /* angleButton */
        angleButton.setOnAction(event -> {

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

            Ball ball1 = new Ball(100, 200, 15);
            ball1.setFill(Color.RED);

            Ball ball2 = new Ball(300, 200, 15);
            ball2.setFill(Color.RED);

            Ball ball3 = new Ball(320, 300, 15);
            ball3.setFill(Color.RED);

            Connection connection = new Connection(ball1, ball2);
            connection.setStroke(Color.CYAN);
            connection.setStrokeWidth(5);

            Connection secondConnection = new Connection(ball2,ball3);
            secondConnection.setStroke(Color.CYAN);
            secondConnection.setStrokeWidth(5);

            Text text = new Text();

            Group angleLine = new Group(ball1,ball2,ball3,connection,secondConnection,text);
            ImagePane.addLine(angleLine);

            DoubleBinding measureAngle = Bindings.createDoubleBinding(() -> {

                        Point2D start = new Point2D(ball1.getCenterX(), ball1.getCenterY());
                        Point2D middle = new Point2D(ball2.getCenterX(), ball2.getCenterY());
                        Point2D end = new Point2D(ball3.getCenterX(),ball3.getCenterY());

                        return middle.angle(start,end);
                    }, ball1.centerXProperty(), ball1.centerYProperty(),
                    ball2.centerXProperty(),ball2.centerYProperty(),
                    ball3.centerXProperty(), ball3.centerYProperty());

            text.textProperty().bind(measureAngle.asString("Angle: %f"));
            text.xProperty().bind(ball1.centerXProperty().add(ball2.centerXProperty()).divide(2));
            text.yProperty().bind(ball1.centerYProperty().add(ball2.centerYProperty()).divide(2));
        });

    }
}
