package gui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class MeasureLength {
    static void MeasureLength(BorderPane base, Button lengthButton, Line lengthLine) {
        lengthButton.setOnAction(event -> {

            base.setOnMouseClicked(event1 -> {
                System.out.println(event1.getScreenX());
                System.out.println(event1.getScreenY());

                lengthLine.setStartX(event1.getScreenX());
                lengthLine.setStartY(event1.getScreenY());
                lengthLine.setEndX(100.0f);
                lengthLine.setEndY(100.0f);

            });
        });
    }
}