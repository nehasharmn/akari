package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainView implements FXComponent, ModelObserver {
    private final FXComponent puzView;
    private final FXComponent mesView;
    private final FXComponent bView;

    private final Scene scene;
    private final Model model;

    public MainView(ClassicMvcController controller, Model model) {
        this.model = model;
        model.addObserver(this);

        puzView = new PuzzleView(controller, model);
        mesView = new MessageView(controller, model);
        bView = new ButtonView(controller, model);
        scene = new Scene(this.render(), 800, 600);
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public Parent render() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-alignment: center; -fx-padding: 20;");

        StackPane gameStack = new StackPane();
        gameStack.getChildren().add(puzView.render());

        if (model.isSolved()) {
            gameStack.getChildren().add(mesView.render());
        }

        VBox.setVgrow(gameStack, Priority.ALWAYS);

        if (shouldShowHeader()) {
            Label header = new Label("Akari");
            header.setStyle("-fx-font-size: 40px; -fx-text-fill: orange; -fx-font-weight: bold; -fx-padding: 10;");
            layout.getChildren().add(header);
        }

        layout.getChildren().add(gameStack);
        layout.getChildren().add(bView.render());

        return layout;
    }

    private boolean shouldShowHeader() {
        int gridHeight = model.getThePuzzle().getHeight();
        int gridWidth = model.getThePuzzle().getWidth();
        return gridHeight < 10 && gridWidth < 10;
    }

    @Override
    public void update(Model model) {
        scene.setRoot(render());
    }
}
