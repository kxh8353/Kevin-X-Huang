package puzzles.hoppers.model;

import puzzles.common.Coordinates;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.solver.Hoppers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


import java.util.Collection;

public class HoppersModel {
    /** the collection of observers of this model */
    private final List<Observer<HoppersModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private HoppersConfig currentConfig;
    private String filename; // original filename
    private Configuration configurationConfig;
    private Coordinates coordinates;

    // instead of passing in a new configutaiuon into select, you want to pass a coordinate into the select function
    // isvalid finctun belongs in the hoppersconfig
    // check if the coordinates are valid in the current config
    // model takes in a coordinate


    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String msg) {
        for (var observer : observers) {
            observer.update(this, msg);
        }
    }


    public HoppersModel(String filename) throws IOException {
        this.filename = filename;
        currentConfig = new HoppersConfig(filename);

    }

    /**
     *  if the current state of the puzzle is solvable, the puzzle should
     *  advance to the next step in the solution with an indication that it was successful.
     *  Otherwise the puzzle should remain in the same state and indicate there is no solution.
     */
    public void hint() throws IOException {
        ArrayList<Configuration> path = (ArrayList<Configuration>) Solver.getShortestPath(configurationConfig);
        if (!path.isEmpty()){
            currentConfig = (HoppersConfig) path.get(1);
            System.out.println("successful");
        }
        System.err.println("No solution");
    }

    /**
     * When loading, the user will provide the path and name of a puzzle file for the game to load.
     * If the file is readable it is guaranteed to be a valid puzzle file and the new puzzle file
     * should be loaded and displayed, along with an indication of success.
     * If the file cannot be read, an error message should be displayed and the previous puzzle file should remain loaded.
     */
    public void load(String filename){ // new filename
        try {
            currentConfig = new HoppersConfig(filename);
            this.filename = filename;
        }catch(IOException e){
            System.err.println("puzzle not found");

        }
    }

    /**
     * Part 1:
     * For the first selection, the user should be able to select a cell on the board with the intention of
     * selecting the piece at that location. If there is a piece there, there should be an indication and selection
     * should advance to the second part. Otherwise if there is no piece there an error message should be displayed
     * and selection has ended.
     *
     * Part 2:
     * For the second selection, the user should be able to select another cell on the board with the intention of moving
     * the previously selected piece to this location. If the move is valid, it should be made and the board should be
     * updated and with an appropriate indication. If the move is invalid, and error message should be displayed.
     */
    public void select(HoppersConfig selection, HoppersConfig secondSelection) throws IOException {
        // is the coordinates specified in the grid
        // in the current configuration, there is a valid piece at the row, col
        // ex: this.currentconfig.isvalidmove(selection)
        Scanner in = new Scanner(System.in);
        // part 1
        if (selection.isValidMove()){ // if there is a green turtle on the coordinate
            this.currentConfig.isValidMove();
        }else{
            System.err.println("no piece");
        }
        // part 2
        if (secondSelection.isValidMove()){ /// checks if there is a green turtle in the coordinate
            this.currentConfig.isValidMove();
        }else{
            System.err.println("invalid move");
        }
    }

    /**
     * The user can quit from and end the program.
     */
    public static void quit(){
        System.exit(0);
    }

    /**
     * The previously loaded file should be reloaded, causing the puzzle
     * to return to its initial state. An indication of the reset should be informed to the user.
     */
    public void reset() throws IOException {
        currentConfig = new HoppersConfig(filename);
    }
}