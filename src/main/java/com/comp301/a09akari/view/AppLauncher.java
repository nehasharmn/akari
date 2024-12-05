package com.comp301.a09akari.view;

import javafx.application.Application;
import javafx.stage.Stage;
import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.model.*;
import com.comp301.a09akari.controller.*;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    PuzzleLibrary library = new PuzzleLibraryImpl();

    Puzzle puzzle1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle puzzle2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle puzzle3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle puzzle4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle puzzle5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);

    library.addPuzzle(puzzle1);
    library.addPuzzle(puzzle2);
    library.addPuzzle(puzzle3);
    library.addPuzzle(puzzle4);
    library.addPuzzle(puzzle5);

    Model model = new ModelImpl(library);
    ClassicMvcController controller = new ControllerImpl(model);

    View view = new View(controller, model);
    model.addObserver(view);
    view.getScene().getStylesheets().add("main.css");
    stage.setScene(view.getScene());
    stage.sizeToScene();

    stage.setTitle("Akari Game");
    stage.show();
  }
}
