package application;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import sudoku.SudokuGrid;

import java.util.Locale;

/**
 * Created by javier on 16-11-21.
 */
public class SudokuMenu extends MenuBar {

    private SudokuGrid grid;
    private SudokuGUI gui;

    /**
     * The menu for the application.
     * @param grid The Sudoku grid.
     * @param gui The graphical user interface for the Sudoku application.
     */
    public SudokuMenu(SudokuGrid grid, SudokuGUI gui) {
        this.grid = grid;
        this.gui = gui;

        final Menu menuFile = new Menu("_File");
        menuFile.setMnemonicParsing(true);

        final Menu menuTools = new Menu("_Tools");
        menuTools.setMnemonicParsing(true);

        final MenuItem menuAbout = new MenuItem("_About...");
        menuAbout.setOnAction(e -> about());
        menuAbout.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.SHORTCUT_DOWN));

        final MenuItem menuQuit = new MenuItem("_Quit");
        menuQuit.setOnAction(e -> Platform.exit());
        menuQuit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.SHORTCUT_DOWN));

        final MenuItem menuSolve = new MenuItem("_Solve");
        menuSolve.setOnAction(e -> gui.solveGrid());
        menuSolve.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));

        final MenuItem menuClear = new MenuItem("_Clear");
        menuClear.setOnAction(e -> gui.clearGrid());
        menuClear.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHORTCUT_DOWN));

        menuTools.getItems().addAll(menuSolve, menuClear);

        String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        if (OS.indexOf("mac") >= 0) {
            // The submenu About item is under the File menu in macOS.
            menuFile.getItems().addAll(menuAbout, menuQuit);
            getMenus().addAll(menuFile, menuTools);
        } else {
            // The submenu About item is under the Help menu in Win, Linux and other OS.
            final Menu menuHelp = new Menu("_Help");
            menuHelp.setMnemonicParsing(true);
            menuFile.getItems().addAll(menuQuit);
            menuHelp.getItems().addAll(menuAbout);
            getMenus().addAll(menuFile, menuTools, menuHelp);
        }

        //setUseSystemMenuBar(true);
    }

    private void about() {
        Dialogs.aboutTheApp("About...",
                "The Sudoku Solver v1.0",
                "This app was made by Javier Poremski and Björn Wadmark as " +
                "an assignment for Java Programming Course (EDAA30) " +
                "at the LTH School of Engineering at Campus Helsingborg.\n\n"+
                "November 22, 2016.");
    }
}
