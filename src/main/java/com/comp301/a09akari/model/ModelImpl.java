package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
    private final PuzzleLibrary library;
    private int puzzleIndex;
    private Puzzle thePuzzle;
    private int[][] lampPlace;
    List<ModelObserver> theObservers;

    public ModelImpl(PuzzleLibrary library) {
        if (library == null) {
            throw new IllegalArgumentException();
        }
        theObservers = new ArrayList<>();
        this.library = library;
        this.puzzleIndex = 0;
        this.thePuzzle = library.getPuzzle(puzzleIndex);
        lampPlace = new int[thePuzzle.getHeight()][thePuzzle.getWidth()];
        this.resetPuzzle();
    }

    public void addLamp(int r, int c) {
        checkIndexBounds(r, c, true);
        if (!isLamp(r, c)) {
            lampPlace[r][c] = 1;
            notifyObservers();
        }
    }

    public void removeLamp(int r, int c) {
        checkIndexBounds(r, c, true);
        if (lampPlace[r][c] == 0) {
            throw new IllegalArgumentException("no lamp");
        }
        lampPlace[r][c] = 0;
        notifyObservers();
    }

    public boolean isLit(int r, int c) {
        checkIndexBounds(r, c, true);
        if (isLamp(r, c)) return true;

        for (int i = c - 1; i >= 0; i--) {
            if (library.getPuzzle(puzzleIndex).getCellType(r, i) != CellType.CORRIDOR) break;
            if (isLamp(r, i)) return true;
        }
        for (int i = c + 1; i < library.getPuzzle(puzzleIndex).getWidth(); i++) {
            if (library.getPuzzle(puzzleIndex).getCellType(r, i) != CellType.CORRIDOR) break;
            if (isLamp(r, i)) return true;
        }

        for (int i = r - 1; i >= 0; i--) {
            if (library.getPuzzle(puzzleIndex).getCellType(i, c) != CellType.CORRIDOR) break;
            if (isLamp(i, c)) return true;
        }
        for (int i = r + 1; i < library.getPuzzle(puzzleIndex).getHeight(); i++) {
            if (library.getPuzzle(puzzleIndex).getCellType(i, c) != CellType.CORRIDOR) break;
            if (isLamp(i, c)) return true;
        }

        return false;
    }

    public boolean isLamp(int r, int c) {
        checkIndexBounds(r, c, true);
        return lampPlace[r][c] == 1;
    }

    public boolean isLampIllegal(int r, int c) {
        checkIndexBounds(r, c, false);
        if (!isLamp(r, c)) {
            throw new IllegalArgumentException();
        }

        int width = thePuzzle.getWidth();
        int height = thePuzzle.getHeight();

        // the left
        for (int i = c - 1; i >= 0; i--) {
            if (!isCorridor(r, i)) break;
            if (isLamp(r, i)) return true;
        }
        // the right
        for (int i = c + 1; i < width; i++) {
            if (!isCorridor(r, i)) break;
            if (isLamp(r, i)) return true;
        }

        // this is for up
        for (int i = r - 1; i >= 0; i--) {
            if (!isCorridor(i, c)) break;
            if (isLamp(i, c)) return true;
        }
        // this is for down
        for (int i = r + 1; i < height; i++) {
            if (!isCorridor(i, c)) break;
            if (isLamp(i, c)) return true;
        }

        return false;
    }

    private boolean isCorridor(int r, int c) {
        return thePuzzle.getCellType(r, c) == CellType.CORRIDOR;
    }

    public Puzzle getThePuzzle() {
        return library.getPuzzle(puzzleIndex);
    }

    public int getPuzzleIndex() {
        return this.puzzleIndex;
    }

    public void setPuzzleIndex(int index) {
        if (index < 0 || index >= library.size()) {
            throw new IndexOutOfBoundsException();
        }
        this.puzzleIndex = index;
        thePuzzle = library.getPuzzle(puzzleIndex);
        resetPuzzle();
    }

    public int getPuzzleLibrarySize() {
        return library.size();
    }

    public void resetPuzzle() {
        lampPlace = new int[thePuzzle.getHeight()][thePuzzle.getWidth()];
        notifyObservers();
    }

    public boolean isSolved() {
        System.out.println("Checking if puzzle is solved");
        for (int r = 0; r < thePuzzle.getHeight(); r++) {
            for (int c = 0; c < thePuzzle.getWidth(); c++) {
                CellType cellType = thePuzzle.getCellType(r, c);
                switch (cellType) {
                    case CORRIDOR:
                        if (!isLit(r, c)) {
                            System.out.println("Puzzle not solved");
                            return false;
                        }
                        if (isLamp(r, c) && isLampIllegal(r, c)) {
                            System.out.println("Puzzle not solved");
                            return false;
                        }
                        break;
                    case CLUE:
                        if (!isClueSatisfied(r, c)) {
                            System.out.println("Puzzle not solved");
                            return false;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
        System.out.println("Puzzle is solved!");
        return true;
    }


    public void addObserver(ModelObserver observer) {
        theObservers.add(observer);
    }

    public void removeObserver(ModelObserver observer) {
        theObservers.remove(observer);
    }

    public boolean isClueSatisfied(int r, int c) {
        checkIndexBounds(r, c, false);
        if (library.getPuzzle(puzzleIndex).getCellType(r, c) != CellType.CLUE) {
            throw new IllegalArgumentException();
        }
        int requiredLamps = library.getPuzzle(puzzleIndex).getClue(r, c);

        int surroundinglamps = 0;

        if (r > 0 && lampPlace[r - 1][c] == 1) {
            surroundinglamps++;
        }
        if (r < thePuzzle.getHeight() - 1 && lampPlace[r + 1][c] == 1) {
            surroundinglamps++;
        }
        if (c > 0 && lampPlace[r][c - 1] == 1) {
            surroundinglamps++;
        }
        if (c < thePuzzle.getWidth() - 1 && lampPlace[r][c + 1] == 1) {
            surroundinglamps++;
        }

        return requiredLamps == surroundinglamps;
    }

    private void checkIndexBounds(int r, int c, boolean checkCorridor) {
        if (r >= library.getPuzzle(puzzleIndex).getHeight()
                || c >= library.getPuzzle(puzzleIndex).getWidth()
                || r < 0
                || c < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (checkCorridor) {
            if (library.getPuzzle(puzzleIndex).getCellType(r, c) != CellType.CORRIDOR) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void notifyObservers() {
        System.out.println("Notifying observers");
        for (ModelObserver o : theObservers) {
            o.update(this);
        }
    }

}



