package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GridView implements FXComponent {
    private final ClassicMvcController controller;
    private final Model model;

    public GridView(ClassicMvcController controller, Model model) {
        this.controller = controller;
        this.model = model;
    }

    @Override
    public Parent render() {
        HBox pane = new HBox(5);

        pane.setStyle("-fx-padding: 20 0 0 0; " +
                "-fx-spacing: 10; " +
                "-fx-alignment: center;");

        pane.getChildren().clear();
        pane.getChildren().add(renderButtons());


        Label puzzleNum = new Label((model.getActivePuzzleIndex() + 1) + "/" + model.getPuzzleLibrarySize());
        puzzleNum.setStyle("-fx-font-size: 14px; -fx-text-fill: black; -fx-padding: 10;");
        pane.getChildren().add(puzzleNum);

        return pane;
    }


    private HBox renderButtons() {
        HBox layout = new HBox(10);

        layout.setStyle("-fx-padding: 20 0 0 0;");

        // Previous button
        Button prev = new Button("Previous");
        prev.setStyle("-fx-font-size: 16px; " +
                "-fx-padding: 10 20; " +
                "-fx-background-color: #e81123; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;");
        prev.setOnAction((ActionEvent event) -> controller.clickPrevPuzzle());
        layout.getChildren().add(prev);

        // Next button
        Button next = new Button("Next");
        next.setStyle("-fx-font-size: 16px; " +
                "-fx-padding: 10 20; " +
                "-fx-background-color: #107c10; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;");
        next.setOnAction((ActionEvent event) -> controller.clickNextPuzzle());
        layout.getChildren().add(next);

        // Random button
        Button rand = new Button("Random");
        rand.setStyle("-fx-font-size: 16px; " +
                "-fx-padding: 10 20; " +
                "-fx-background-color: purple; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;");
        rand.setOnAction((ActionEvent event) -> {
            int before = model.getActivePuzzleIndex();
            controller.clickRandPuzzle();
            int after = model.getActivePuzzleIndex();
            while (before == after) {
                before = model.getActivePuzzleIndex();
                controller.clickRandPuzzle();
                after = model.getActivePuzzleIndex();
            }
        });
        layout.getChildren().add(rand);

        // Reset button
        Button resetButton = new Button("Reset");
        resetButton.setStyle("-fx-font-size: 16px; " +
                "-fx-padding: 10 20; " +
                "-fx-background-color: #004e8c; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;");
        resetButton.setOnAction((ActionEvent event) -> controller.clickResetPuzzle());
        layout.getChildren().add(resetButton);

        return layout;
    }

}