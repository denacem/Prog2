package gui;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.swing.text.html.ImageView;
import java.util.concurrent.atomic.AtomicReference;

public class MeasureLength {

    static void start(Pane measureableArea, Line lengthLine, Canvas canvas, GraphicsContext graphicsContext, StackPane drawArea, Button lengthButton) {



        lengthButton.setOnAction(event -> {

/* only line no circles
            base.setOnMouseClicked(event1 -> {
                lengthLine.setStartX(event1.getX());
                lengthLine.setStartY(event1.getY());
            });

            base.setOnMouseMoved(event2 -> {
                lengthLine.setEndX(event2.getX());
                lengthLine.setEndY(event2.getY());
            });

            base.setOnMouseClicked(event3 -> {
                lengthLine.setEndX(event3.getX());
                lengthLine.setEndY(event3.getY());
            });

 */
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
                    measureableArea.getChildren().add(lengthLineG);

                    DoubleBinding distance = Bindings.createDoubleBinding(() -> {

                        Point2D start = new Point2D(ball1.getCenterX(), ball1.getCenterY());
                        Point2D end = new Point2D(ball2.getCenterX(), ball2.getCenterY());
                        return start.distance(end);
                    }, ball1.centerXProperty(), ball1.centerYProperty(),
                    ball2.centerXProperty(), ball2.centerYProperty());

                    text.textProperty().bind(distance.asString("Distance: %f"));
                    text.xProperty().bind(ball1.centerXProperty().add(ball2.centerXProperty()).divide(2));
                    text.yProperty().bind(ball1.centerYProperty().add(ball2.centerYProperty()).divide(2));



            /*
            canvas.setOnMousePressed(event1 -> {
                graphicsContext.beginPath();
                graphicsContext.moveTo(event1.getX(), event1.getY());
                graphicsContext.stroke();

            });

            canvas.setOnMouseReleased(event2 -> {
                graphicsContext.lineTo(event2.getX(), event2.getY());
                graphicsContext.stroke();

            });

        });
    }*/
 /*
    public static void initDraw(GraphicsContext gc){
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();

        gc.setFill(Color.LIGHTGRAY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);

        gc.fill();
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle

        gc.setFill(Color.RED);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);

    } */
    });
}
}