package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.ModelObserver;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class View implements FXComponent, ModelObserver {
    private final FXComponent gameView;
    private final FXComponent textView;
    private final FXComponent buttonView;

    private final Scene scene;
    private final Model model;

    public View(ClassicMvcController controller, Model model) {
        this.model = model;
        model.addObserver(this);

        gameView = new PuzzleView(controller, model);
        textView = new MessageView(controller, model);
        buttonView = new GridView(controller, model);
        scene = new Scene(this.render());
    }

    public Scene getScene() {
        return scene;
    }

    @Override
    public Parent render() {
        Pane layout = new VBox();
        layout.setMinWidth(700);
        layout.setMinHeight(700);

        layout.getChildren().add(gameView.render());
        layout.getChildren().add(buttonView.render());

        if (model.isSolved()) {
            layout.getChildren().add(textView.render());
        }

        return layout;
    }

    @Override
    public void update(Model model) {
        System.out.println("Update called.... Is puzzle solved? " + model.isSolved());
        scene.setRoot(render());
    }
}

