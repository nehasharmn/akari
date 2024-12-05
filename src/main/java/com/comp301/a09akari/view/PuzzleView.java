package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PuzzleView implements FXComponent {
  private final ClassicMvcController controller;
  private final Model model;

  private Image lightbulbImg;
  private Image incorrectImg;
  private ImagePattern lightbulbP;
  private ImagePattern incorrectP;

  public PuzzleView(ClassicMvcController controller, Model model) {
    this.controller = controller;
    this.model = model;
    loadImages();
  }

  private void loadImages() {

    lightbulbImg = new Image(getClass().getResource("/light-bulb.png").toString());
    incorrectImg = new Image(getClass().getResource("/img.png").toString());
    lightbulbP = new ImagePattern(lightbulbImg);
    incorrectP = new ImagePattern(incorrectImg);
  }

  @Override
  public Parent render() {
    GridPane board = new GridPane();
    board.setHgap(10);
    board.setVgap(10);
    int cell = 50;
    board.setPadding(new javafx.geometry.Insets(50, 0, 0, 0));

    for (int i = 0; i < model.getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < model.getActivePuzzle().getWidth(); j++) {
        switch (model.getActivePuzzle().getCellType(i, j)) {
          case CLUE:
            clueCFormat(board, i, j, cell);
            break;
          case WALL:
            wCFormat(board, i, j, cell);
            break;
          case CORRIDOR:
            cCFormat(board, i, j, cell);
            break;
        }
      }
    }
    board.setStyle("-fx-alignment: center;");
    return board;
  }

  private void wCFormat(GridPane board, int i, int j, int size) {
    Rectangle cell = new Rectangle(size, size);
    cell.setFill(Color.BLACK);
    cell.setStroke(Color.BLACK);
    cell.setStrokeWidth(1);
    board.add(cell, j, i);
  }

  private void clueCFormat(GridPane board, int i, int j, int size) {
    StackPane cluePane = new StackPane();
    Rectangle rectangle = new Rectangle(size, size);
    rectangle.setStroke(Color.BLACK);
    rectangle.setStrokeWidth(1);

    Text text = new Text("" + model.getActivePuzzle().getClue(i, j));
    text.getStyleClass().add("white-text");
    text.setFill(Color.WHITE);
    rectangle.setFill(Color.BLACK);

    if (model.isClueSatisfied(i, j)) {
      rectangle.setFill(Color.rgb(73, 196, 106));
    }

    cluePane.getChildren().addAll(rectangle, text);
    board.add(cluePane, j, i);
  }

  private void cCFormat(GridPane board, int i, int j, int size) {
    Rectangle cell = new Rectangle(size, size);
    cell.setFill(Color.rgb(230, 230, 230));
    cell.setStroke(Color.BLACK);
    cell.setStrokeWidth(1);

    if (model.isLamp(i, j) && model.isLampIllegal(i, j)) {
      cell.setFill(incorrectP);
    } else if (model.isLamp(i, j)) {
      cell.setFill(lightbulbP);
    } else if (model.isLit(i, j)) {
      cell.setFill(Color.rgb(255, 255, 100));
    }

    cell.setOnMouseClicked(
        mouseEvent -> {
          lampInOut(cell, i, j);
        });
    board.add(cell, j, i);
  }

  private void lampInOut(Rectangle cell, int i, int j) {
    if (!model.isLamp(i, j)) {
      model.addLamp(i, j);
    } else {
      model.removeLamp(i, j);
      cell.setFill(Color.WHITE);
    }
  }
}
