package application;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import sudoku.SudokuGrid;
import java.util.Arrays;

/**
 * Created by javier on 16-11-21.
 */
public class SudokuGUI extends BorderPane {

    private SudokuGrid grid;
    private TextField[][] textFields;
    private Button btnSolve;
    private Button btnClear;

    public SudokuGUI(SudokuGrid grid) {
        this.grid = grid;
        textFields = new TextField[9][9];

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: #E6F2FF");
        vBox.getChildren().addAll(getSudokuGrid());
        vBox.getChildren().add(getButtonPanel());

        setTop(vBox);
    }

    /**
     * Clearing the Sudoku grid.
     */
    public void clearGrid() {
        grid.clear();
        Arrays.stream(textFields).forEach(e -> Arrays.stream(e).forEach(i -> i.setText("")));
    }

    /**
     * Solving the Sudoku grid.
     */
    public void solveGrid() {
        grid.clear();
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                String strValue = textFields[y][x].getText();
                int integerValue;
                if (!strValue.equals("")) {
                    integerValue = Integer.parseInt(strValue);
                } else {
                    integerValue = 0;
                }
                grid.setValue(x, y, integerValue);
            }
        }
        if (grid.solve()) {
            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    int value = grid.getValue(x, y);
                    if (value != 0) {
                        textFields[y][x].setText(Integer.toString(value));
                    } else {
                        textFields[y][x].setText("");
                    }
                }
            }
        } else {
            Dialogs.alert("No solution", null, "The Sudoku grid could not be solved.");
        }
    }

    /**
     * Returns array of horizontal boxes containing the Sudoku grid.
     * @return Array of type horizontal boxes.
     */
    private HBox[] getSudokuGrid() {
        HBox[] hBoxes = new HBox[]{
                new HBox(getGridPane(false, 0, 0), getGridPane(true, 0, 3), getGridPane(false, 0, 6)),
                new HBox(getGridPane(true, 3, 0), getGridPane(false, 3, 3), getGridPane(true, 3, 6)),
                new HBox(getGridPane(false, 6, 0), getGridPane(true, 6, 3), getGridPane(false, 6, 6)),
        };
        return hBoxes;
    }

    /**
     * Returns a Sudoku subgrid containing text field elements.
     * @param background True sets the shadowed background colour, false gives standard colour.
     * @param xi Start coordinator for the subgrid at the X-axis.
     * @param yi Start coordinator for the subgrid at the Y-axis.
     * @return A grid panel containing the Sudoku subgrid with its text fields.
     */
    private GridPane getGridPane(boolean background, int xi, int yi) {
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-padding: 5 5 5 5");

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                TextField textField = getTextField(background);
                textFields[yi+y][xi + x] = textField;
                gridPane.setConstraints(textField, y, x);
                gridPane.getChildren().add(textField);
            }
        }

        return gridPane;
    }

    /**
     * Returns a text field for the subgrid.
     * @param background True sets the shadowed background colour, false gives standard colour.
     * @return A text field adjusted to work with the Sudoku subgrid.
     */
    private TextField getTextField(boolean background) {
        TextField textField = new TextField();
        textField.setStyle("-fx-alignment: center");
        textField.setPrefSize(20, 20);
        if (background) {
            textField.setStyle("-fx-background-color: #FFA366;" +
                    "-fx-alignment: center;" +
                    "-fx-padding: 1 1 1 1;" +
                    "-fx-border-insets: 1 1 1 1;" +
                    "-fx-background-insets: 1 1 1 1");
        } else {
            textField.setStyle("-fx-background-color: #FFFFFF;" +
                    "-fx-alignment: center;" +
                    "-fx-padding: 1 1 1 1;" +
                    "-fx-border-insets: 1 1 1 1;" +
                    "-fx-background-insets: 1 1 1 1");
        }
        textField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() > 1) {
                        textField.setText(newValue.substring(0, 1));
                    }
                });
        textField.addEventFilter(KeyEvent.KEY_TYPED,
                event -> {
                    if (!"123456789".contains(event.getCharacter())) event.consume();
                });
        return textField;
    }

    /**
     * Returns a horizontal box containing panel buttons.
     * @return A horizontal box with buttons to interact with the user.
     */
    private HBox getButtonPanel() {
        HBox hBox = new HBox();
        btnSolve = new Button("Solve");
        btnSolve.setOnAction(e -> solveGrid());
        btnClear = new Button("Clear");
        btnClear.setOnAction(e -> clearGrid());
        hBox.getChildren().addAll(btnSolve, btnClear);

        hBox.setStyle("-fx-padding: 5 5 5 5;-fx-spacing: 10;");
        hBox.getChildren()
                .stream()
                .filter(btn -> btn instanceof Button)
                .forEach(btn -> btn.setStyle("-fx-padding: 3 10 3 10"));
        return hBox;
    }
}
