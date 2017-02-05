package test;

import sudoku.SudokuGrid;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

/**
 * Created by javier on 16-11-18.
 */
public class SudokuGridTest {
    SudokuGrid grid;
    @org.junit.Before
    public void setUp() throws Exception {
        grid = new SudokuGrid();
    }

    @org.junit.After
    public void tearDown() throws Exception {
        grid = null;
    }

    @org.junit.Test
    public void getValue() throws Exception {
        assertEquals("getValue didn´t return correct digit for an empty grind", 0, grid.getValue(5, 5));
        for (int i = 0; i < 9; i++) {
            grid.setValue(i, i, i);
            assertEquals("getValue didn´t return correct digit", i, grid.getValue(i, i));
        }
    }

    @org.junit.Test
    public void getRow() throws Exception {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            grid.setValue(5, i-1, i);
            list.add(i);
        }
        assertEquals("getRow didn´t return back correct integer list", list, grid.getRow(5));
    }

    @org.junit.Test
    public void solveWithData() throws Exception {
        int[][] inData = new int[][] {
                new int[] {0,9,0,   4,0,8,  0,0,7},
                new int[] {3,0,0,   7,9,0,  0,6,0},
                new int[] {0,0,5,   0,1,0,  0,0,0},

                new int[] {0,0,1,   0,0,0,  0,2,0},
                new int[] {2,4,8,   0,0,0,  1,9,6},
                new int[] {0,3,0,   0,0,0,  5,0,0},

                new int[] {0,0,0,   0,8,0,  3,0,0},
                new int[] {0,8,0,   0,2,7,  0,0,9},
                new int[] {7,0,0,   3,0,4,  0,8,0},
        };

        int[][] outData = new int[][] {
                new int[] {1,9,6,   4,3,8,  2,5,7},
                new int[] {3,2,4,   7,9,5,  8,6,1},
                new int[] {8,7,5,   2,1,6,  9,3,4},

                new int[] {6,5,1,   8,4,9,  7,2,3},
                new int[] {2,4,8,   5,7,3,  1,9,6},
                new int[] {9,3,7,   1,6,2,  5,4,8},

                new int[] {4,6,2,   9,8,1,  3,7,5},
                new int[] {5,8,3,   6,2,7,  4,1,9},
                new int[] {7,1,9,   3,5,4,  6,8,2},
        };

        for (int x=0; x < 9; x++) {
            for (int y=0; y < 9; y++) {
                grid.setValue(x, y, inData[x][y]);
            }
        }

        grid.solve();

        System.out.print(grid);

        assertArrayEquals("Incorrect solution for the Sudoku grid", outData, grid.getGrid());
    }

    @org.junit.Test
    public void solveEmpty() throws Exception {
        int[][] inData = new int[][] {
                new int[] {0,0,0,   0,0,0,  0,0,0},
                new int[] {0,0,0,   0,0,0,  0,0,0},
                new int[] {0,0,0,   0,0,0,  0,0,0},

                new int[] {0,0,0,   0,0,0,  0,0,0},
                new int[] {0,0,0,   0,0,0,  0,0,0},
                new int[] {0,0,0,   0,0,0,  0,0,0},

                new int[] {0,0,0,   0,0,0,  0,0,0},
                new int[] {0,0,0,   0,0,0,  0,0,0},
                new int[] {0,0,0,   0,0,0,  0,0,0},
        };

        int[][] outDataA = new int[][] {
                new int[] {1,2,3,   4,5,6,  7,8,9},
                new int[] {4,5,6,   7,8,9,  1,2,3},
                new int[] {7,8,9,   1,2,3,  4,5,6},

                new int[] {2,1,4,   3,6,5,  8,9,7},
                new int[] {3,6,5,   8,9,7,  2,1,4},
                new int[] {8,9,7,   2,1,4,  3,6,5},

                new int[] {5,3,1,   6,4,2,  9,7,8},
                new int[] {6,4,2,   9,7,8,  5,3,1},
                new int[] {9,7,8,   5,3,1,  6,4,2},
        };

        for (int x=0; x < 9; x++) {
            for (int y=0; y < 9; y++) {
                grid.setValue(x, y, inData[x][y]);
            }
        }

        grid.solve();
        assertArrayEquals("Incorrect solution for the Sudoku grid", outDataA, grid.getGrid());
    }

    @org.junit.Test
    public void clear() throws Exception {
        grid.setValue(5, 5, 8);
        grid.clear();
        assertEquals("clear didn´t return correct digit for an empty grind", 0, grid.getValue(5, 5));
    }

}