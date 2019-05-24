package gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.concurrent.atomic.AtomicReference;

public class MeasureLength {

    static void start(Canvas canvas, GraphicsContext graphicsContext, StackPane drawArea, Button lengthButton) {


        lengthButton.setOnAction(event -> {

            canvas.setOnMousePressed(event1 -> {
                graphicsContext.beginPath();
                graphicsContext.lineTo(event1.getX(), event1.getY());
                graphicsContext.stroke();


            });

            canvas.setOnMouseReleased(event2 -> {
                graphicsContext.lineTo(event2.getX(), event2.getY());
                graphicsContext.stroke();

            });

        });
    }

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

    }
}