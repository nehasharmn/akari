package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class AppLauncher extends Application {
    @Override
    public void start(Stage stage) {
        PuzzleLibrary puzzles = new PuzzleLibraryImpl();

        Puzzle p1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
        Puzzle p2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
        Puzzle p3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
        Puzzle p4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
        Puzzle p5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);

        puzzles.addPuzzle(p1);
        puzzles.addPuzzle(p2);
        puzzles.addPuzzle(p3);
        puzzles.addPuzzle(p4);
        puzzles.addPuzzle(p5);

        Model model = new ModelImpl(puzzles);
        ClassicMvcController controller = new ControllerImpl(model);

        MainView view = new MainView(controller, model);
        model.addObserver(view);
        view.getScene().getStylesheets().add("main.css"); // doesnt work
        stage.setScene(view.getScene());
        stage.sizeToScene();

        stage.setTitle("Akari Game");
        stage.show();
    }
}
