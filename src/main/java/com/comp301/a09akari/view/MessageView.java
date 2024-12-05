package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;


public class MessageView implements FXComponent {
    private final ClassicMvcController controller;
    private final Model model;

    public MessageView(ClassicMvcController controller, Model model) {
        this.controller = controller;
        this.model = model;
    }

    @Override
    public Parent render() {
        StackPane layout = new StackPane();
        layout.setPrefSize(700, 700);

        if (model.isSolved()) {
            int puzzle = model.getActivePuzzleIndex() + 1;
            Label instructions = new Label("YAY you solved the puzzle " + puzzle + "!");

            instructions.setStyle("-fx-font-size: 20px; " +
                    "-fx-text-fill: white; " +
                    "-fx-padding: 20; " +
                    "-fx-alignment: center; " +
                    "-fx-background-color: magenta;" +
                    "-fx-background-radius: 5;"
            );

            layout.getChildren().add(instructions);

            StackPane.setAlignment(instructions, javafx.geometry.Pos.CENTER);
        }

        return layout;
    }
}
