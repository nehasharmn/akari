package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class ButtonView implements FXComponent {
    private final ClassicMvcController controller;
    private final Model model;

    public ButtonView(ClassicMvcController controller, Model model) {
        this.controller = controller;
        this.model = model;
    }

    @Override
    public Parent render() {
        VBox mainContainer = new VBox(10);
        mainContainer.setStyle("-fx-alignment: center; -fx-padding: 10;");

        HBox buttonContainer = new HBox(10);
        buttonContainer.setStyle("-fx-alignment: center;");
        buttonContainer.getChildren().add(renderButtons());

        Label numofpuz = new Label((model.getPuzzleIndex() + 1) + "/" + model.getPuzzleLibrarySize());
        numofpuz.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        mainContainer.getChildren().addAll(buttonContainer, numofpuz);

        return mainContainer;
    }


    private HBox renderButtons() {
        HBox layout = new HBox(10);

        layout.setStyle("-fx-padding: 20 0 0 0;");

        Button prev = new Button("Previous");
        prev.setStyle("-fx-font-size: 16px; " +
                "-fx-padding: 10 20; " +
                "-fx-background-color: #e81123; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;");
        prev.setOnAction((ActionEvent event) -> controller.clickPrevPuzzle());
        layout.getChildren().add(prev);

        Button next = new Button("Next");
        next.setStyle("-fx-font-size: 16px; " +
                "-fx-padding: 10 20; " +
                "-fx-background-color: #107c10; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;");
        next.setOnAction((ActionEvent event) -> controller.clickNextPuzzle());
        layout.getChildren().add(next);

        Button rand = new Button("Random");
        rand.setStyle("-fx-font-size: 16px; " +
                "-fx-padding: 10 20; " +
                "-fx-background-color: purple; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;");
        rand.setOnAction((ActionEvent event) -> {
            int b4 = model.getPuzzleIndex();
            controller.clickRandPuzzle();
            int after = model.getPuzzleIndex();
            while (b4 == after) {
                b4 = model.getPuzzleIndex();
                controller.clickRandPuzzle();
                after = model.getPuzzleIndex();
            }
        });
        layout.getChildren().add(rand);

        Button reset = new Button("Reset");
        reset.setStyle("-fx-font-size: 16px; " +
                "-fx-padding: 10 20; " +
                "-fx-background-color: #004e8c; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 5; " +
                "-fx-cursor: hand;");
        reset.setOnAction((ActionEvent event) -> controller.clickResetPuzzle());
        layout.getChildren().add(reset);

        return layout;
    }

}