package sudoku;

import java.util.Arrays;
import java.util.List;

/**
 * Created by javier on 16-11-18.
 */
public class SudokuGrid {
    private Integer[][] grid;

    /**
     * This constructor initializes the Sudoku grid.
     */
    public SudokuGrid() {
        clear();
    }

    /**
     * Numeric value returns from the Sudoku grid at
     * a given position.
     * @param row Row position (0 to 8)
     * @param column Column position (0 to 8)
     * @return Returns a digit. If the position is empty, it will return 0.
     *         If out of bound, it will return -1.
     */
    public int getValue(int row, int column) {
        if ( (row <= grid.length) && (column <= grid.length) ) {
            return grid[row][column];
        }
        return -1;
    }

    /**
     * Returns the Sudoku grid;
     * @return Two-dimensional array of the integer
     *         containing the Sudoku grid.
     */
    public Integer[][] getGrid() {
        return grid;
    }

    /**
     * Returns a integer list containing the digit values
     * of a given row.
     * @param row Row position (0 to 8)
     * @return Integer list with digit values for each
     *         column at a given grid row.
     */
    public List<Integer> getRow(int row) {
        return Arrays.asList(Arrays.asList(grid).get(row));
    }

    /**
     * Adds the digit to the Soduku grid at a given position.
     * Any existing digit in the grid will be overwritten.
     * @param row Row position (0 to 8)
     * @param column Column position (0 to 8)
     * @param digit Digit
     */
    public void setValue(int row, int column, int digit) {
        grid[row][column] = digit;
    }

    /**
     * Initiates a new Sudoku grid. It will overwrite/clear
     * the current grid table, if it exists.
     */
    public void clear() {
        // Just reassign a new array to the same variable.
        // Java will take care about collecting garbaged data.
        grid = new Integer[9][9];
        for (Integer[] row : grid) Arrays.fill(row, 0); // Empty grid corresponds to 0.
    }

    /**
     * Solve the Sudoku grid.
     * @return True if the grid was solved, otherwise false;
     */
    public boolean solve() {
        if (!checkRules()) {
            return false;
        }
        return solve(0,0);
    }

    /**
     * Solving the Sudoku grid.
     * @param row Row position (1 to 9)
     * @param col Column position (1 to 9)
     * @return Return true if the Sudoku was solved, otherwise false.
     */
    private boolean solve(int row, int col) {

        if (row > 8) return true;

        int nextCol = col == 8 ? 0 : (col + 1);
        int nextRow = col == 8 ? (row + 1) : row;

        if (!isEmpty(row, col)) {
            // The cell is not empty -> continue to next cell.
            return solve(nextRow, nextCol);
        } else {
            // The cell is emptu -> do calculation.
            for (int value = 1; value <= 9; value++) {
                // Try from 1 to 9.
                setValue(row, col, value);
                if (!checkRules(row, col)) {
                    setValue(row, col, 0);
                } else if (solve(nextRow, nextCol)) {
                    return true;
                }

            }
        }

        setValue(row, col, 0); // Invalid integer -> set to 0 and return false.

        return false;
    }

    /**
     * Checking if the Sudoku grid follows the rules.
     * @return Returns true if the grid follows the rules, otherwise false.
     */
    private boolean checkRules() {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
                if (!isEmpty(x,y) && !checkRules(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checking if the Sudoku grid at given position follows the rules.
     * @param row Row position.
     * @param col Column position.
     * @return Returns true if the grid follows the rules, otherwise false.
     */
    private boolean checkRules(int row, int col) {
        for (int i = 0; i < grid.length; i++) {
            // Checking row rules: the same single integer may not appear twice in the same row.
            if (row != i && getValue(row, col) == getValue(i, col)) {
                return false;
            }
            // Checking column rules: the same single integer may not appear twice in the same column.
            if (col != i && getValue(row, col) == getValue(row, i)) {
                return false;
            }
        }

        int rowStart = row - (row % 3); // {0, 1, 2} => 0; {3, 4, 5} => 3; {6, 7, 8} => 6
        int rowEnd = rowStart + 2; // {0, 1, 2} => 2; {3, 4, 5} => 5; {6, 7, 8} => 8
        int columnStart = col - (col % 3);
        int columnEnd = columnStart + 2;

        // Checking subgrid rules: the same single integer may not appear twice in the same 3×3 subregion.
        for (int r = rowStart; r <= rowEnd; r++) {
            for (int c = columnStart; c <= columnEnd; c++) {
                if (row != r && col != c && getValue(row, col) == getValue(r, c)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns ture if the grid cell at a given position is empty; otherwise false.
     * @param row Row postion (0 to 8)
     * @param col Column position (0 to 8)
     * @return Return true if empty; otherwise false.
     */
    private boolean isEmpty(int row, int col) {
        return getValue(row, col) == 0;
    }

    /**
     * Returns the Sudoku grids as string.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (Integer[] y : grid) {
            for (int x : y) {
                sb.append(x + " ");
                if (count%3 == 0) {
                    // Horizontal spacing between subgrids
                    sb.append("  ");
                }
                if (count%(3*3) == 0) {
                    // New row line
                    sb.append("\n");
                }
                if (count%(3*3*3) == 0) {
                    // Vertical spacing between subgrids
                    sb.append("\n");
                }
                count++;
            }
        }
        return sb.toString();
    }
}