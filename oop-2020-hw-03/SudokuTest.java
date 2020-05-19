import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class SudokuTest {
    private Sudoku sudoku;

    @BeforeEach
    public void setup(){
        sudoku = new Sudoku(Sudoku.easyGrid);
    }

    @Test
    public void testSolvableSpotsSize(){
        int numZeros = countZeros(Sudoku.easyGrid);
//        assertEquals(numZeros, sudoku.getSolvableSpotsSize());
    }

    @Test
    public void testToString(){
        String toString = sudoku.toString();
        int [][] grid = Sudoku.textToGrid(toString);
        assertArrayEquals(Sudoku.easyGrid, grid);
    }

    @Test
    public void testConstructor(){
        String toString = sudoku.toString();
        Sudoku newSudoku = new Sudoku(toString);
        assertEquals(toString, newSudoku.toString());
    }

    @Test
    public void testSolving(){
        sudoku.solve();
        String solution = sudoku.getSolutionText();
        assertEquals("1 6 4 7 9 5 3 8 2 \n" +
                "2 8 7 4 6 3 9 1 5 \n" +
                "9 3 5 2 8 1 4 6 7 \n" +
                "3 9 1 8 7 6 5 2 4 \n" +
                "5 4 6 1 3 2 7 9 8 \n" +
                "7 2 8 9 5 4 1 3 6 \n" +
                "8 1 9 6 4 7 2 5 3 \n" +
                "6 7 3 5 2 9 8 4 1 \n" +
                "4 5 2 8 3 1 6 7 9 \n", solution);
    }

    private int countZeros(int[][] grid){
        int result = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0){
                    result++;
                }
            }
        }
        return result;
    }

}