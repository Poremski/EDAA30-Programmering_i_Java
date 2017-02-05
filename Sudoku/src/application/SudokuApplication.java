package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sudoku.SudokuGrid;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.util.Locale;

/**
 * Created by javier on 16-11-21.
 */
public class SudokuApplication extends Application {

    private SudokuGUI gui;
    private SudokuGrid grid;

    /**
     * The initialization of the graphical user interface for the Sudoku application.
     * @param args Incoming arguments from console.
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * The main entry point for the JavaFX application.
     * @param primaryStage The primary stage for this application, onto which the application scene is set.
     * @throws Exception Constructs a new exception with a message and a cause.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Locale.setDefault(Locale.ENGLISH);
        grid = new SudokuGrid();
        gui = new SudokuGUI(grid);

        BorderPane root = new BorderPane();
        root.setTop(new SudokuMenu(grid, gui));
        root.setCenter(gui);

        Scene scene = new Scene(root);
        primaryStage.setTitle("The Sudoku Solver");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("sudoku.png")));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
