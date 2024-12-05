package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;

import java.util.Random;


public class ControllerImpl implements ClassicMvcController {
    private final Model model;

    public ControllerImpl(Model model) {
        if (model == null) {
            throw new IllegalArgumentException("null");
        }
        this.model = model;
    }

    public void clickNextPuzzle() {
        if (model.getPuzzleIndex() < model.getPuzzleLibrarySize() - 1) {
            model.setPuzzleIndex(model.getPuzzleIndex() + 1);
        }
    }

    public void clickPrevPuzzle() {
        if (model.getPuzzleIndex() != 0) {
            model.setPuzzleIndex(model.getPuzzleIndex() - 1);
        }
    }

    public void clickRandPuzzle() {
        Random rand = new Random();
        int r = rand.nextInt(model.getPuzzleLibrarySize());
        model.setPuzzleIndex(r);
    }

    public void clickResetPuzzle() {
        model.resetPuzzle();
    }

    public void clickCell(int r, int c) {
        if (model.getThePuzzle().getCellType(r, c) != CellType.CORRIDOR) {
            System.out.println();
        } else if (model.isLamp(r, c)) {
            model.removeLamp(r, c);
        } else {
            model.addLamp(r, c);
        }
    }
}





