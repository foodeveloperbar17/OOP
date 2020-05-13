import java.util.*;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
    // Provided grid data for main/testing
    // The instance variable strategy is up to you.

    // Provided easy 1 6 grid
    // (can paste this text into the GUI too)
    public static final int[][] easyGrid = Sudoku.stringsToGrid(
            "1 6 4 0 0 0 0 0 2",
            "2 0 0 4 0 3 9 1 0",
            "0 0 5 0 8 0 4 0 7",
            "0 9 0 0 0 6 5 0 0",
            "5 0 0 1 0 2 0 0 8",
            "0 0 8 9 0 0 0 3 0",
            "8 0 9 0 4 0 2 0 0",
            "0 7 3 5 0 9 0 0 1",
            "4 0 0 0 0 0 6 7 9");


    // Provided medium 5 3 grid
    public static final int[][] mediumGrid = Sudoku.stringsToGrid(
            "530070000",
            "600195000",
            "098000060",
            "800060003",
            "400803001",
            "700020006",
            "060000280",
            "000419005",
            "000080079");

    // Provided hard 3 7 grid
    // 1 solution this way, 6 solutions if the 7 is changed to 0
    public static final int[][] hardGrid = Sudoku.stringsToGrid(
            "3 7 0 0 0 0 0 8 0",
            "0 0 1 0 9 3 0 0 0",
            "0 4 0 7 8 0 0 0 3",
            "0 9 3 8 0 0 0 1 2",
            "0 0 0 0 4 0 0 0 0",
            "5 2 0 0 0 6 7 9 0",
            "6 0 0 0 2 1 0 4 0",
            "0 0 0 5 3 0 9 0 0",
            "0 3 0 0 0 0 0 5 1");


    public static final int SIZE = 9;  // size of the whole 9x9 puzzle
    public static final int PART = 3;  // size of each 3x3 part
    public static final int MAX_SOLUTIONS = 100;

    // Provided various static utility methods to
    // convert data formats to int[][] grid.

    /**
     * Returns a 2-d grid parsed from strings, one string per row.
     * The "..." is a Java 5 feature that essentially
     * makes "rows" a String[] array.
     * (provided utility)
     *
     * @param rows array of row strings
     * @return grid
     */
    public static int[][] stringsToGrid(String... rows) {
        int[][] result = new int[rows.length][];
        for (int row = 0; row < rows.length; row++) {
            result[row] = stringToInts(rows[row]);
        }
        return result;
    }


    /**
     * Given a single string containing 81 numbers, returns a 9x9 grid.
     * Skips all the non-numbers in the text.
     * (provided utility)
     *
     * @param text string of 81 numbers
     * @return grid
     */
    public static int[][] textToGrid(String text) {
        int[] nums = stringToInts(text);
        if (nums.length != SIZE * SIZE) {
            throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
        }

        int[][] result = new int[SIZE][SIZE];
        int count = 0;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                result[row][col] = nums[count];
                count++;
            }
        }
        return result;
    }


    /**
     * Given a string containing digits, like "1 23 4",
     * returns an int[] of those digits {1 2 3 4}.
     * (provided utility)
     *
     * @param string string containing ints
     * @return array of ints
     */
    public static int[] stringToInts(String string) {
        int[] a = new int[string.length()];
        int found = 0;
        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                a[found] = Integer.parseInt(string.substring(i, i + 1));
                found++;
            }
        }
        int[] result = new int[found];
        System.arraycopy(a, 0, result, 0, found);
        return result;
    }


    // Provided -- the deliverable main().
    // You can edit to do easier cases, but turn in
    // solving hardGrid.
    public static void main(String[] args) {
        Sudoku sudoku;
        sudoku = new Sudoku(hardGrid);

        System.out.println(sudoku); // print the raw problem
        int count = sudoku.solve();
        System.out.println("solutions:" + count);
        System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
        System.out.println(sudoku.getSolutionText());
    }

    //	instance variables
    private List<Spot> solvableSpots = new ArrayList<>();
    private int[][] rows;
    private int[][] columns;
    private long elapsed;
    private String solutionText;


    /**
     * Sets up based on the given ints.
     */
    public Sudoku(int[][] ints) {
        this.rows = ints;
        columns = getColumnsFromRows(ints);
        fillSolvableSpots();
        sortSolvableSpots();
    }

    private void fillSolvableSpots() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (rows[i][j] != 0) {
                    solvableSpots.add(new Spot(i, j));
                }
            }
        }
    }

    private void sortSolvableSpots() {
        solvableSpots.sort(new Comparator<Spot>() {
            @Override
            public int compare(Spot o1, Spot o2) {
                return o1.getPossibleValues().size() - o2.getPossibleValues().size();
            }
        });
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                stringBuilder.append(rows[i][j] + " ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    /**
     * Solves the puzzle, invoking the underlying recursive search.
     */
    public int solve() {
        long start = System.currentTimeMillis();
        int result = solveRecursively(0);
        elapsed = System.currentTimeMillis() - start;
        return result; // YOUR CODE HERE
    }

    public String getSolutionText() {
        return solutionText; // YOUR CODE HERE
    }

    public long getElapsed() {
        return elapsed; // YOUR CODE HERE
    }

    private int solveRecursively(int depth) {
        if (depth == solvableSpots.size()) {
            if (solutionText == null) {
                solutionText = toString();
            }
            return 1;
        }
        int currSolutions = 0;
        Spot currSpot = solvableSpots.get(depth);
        for (int number : currSpot.getPossibleValues()) {
            currSpot.setValue(number);
            System.out.println("kaloche");
            currSolutions += solveRecursively(depth + 1);
            if (currSolutions >= MAX_SOLUTIONS) {
                return currSolutions;
            }
        }
        currSpot.setValue(0);
        return currSolutions;
    }

    private int[][] getColumnsFromRows(int[][] rows) {
        int[][] columns = new int[rows[0].length][rows.length];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                columns[j][i] = rows[i][j];
            }
        }
        return columns;
    }

    private class Spot {
        private int row;
        private int column;

        public Spot(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getValue() {
            return rows[row][column];
        }

        public void setValue(int value) {
            rows[row][column] = value;
        }

        public Set<Integer> getPossibleValues() {
            Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
            Set<Integer> possibleValues = new HashSet<>(Arrays.asList(arr));
            for (int i = 0; i < rows[row].length; i++) {
                possibleValues.remove(rows[row][i]);
            }
            for (int i = 0; i < columns[column].length; i++) {
                possibleValues.remove(columns[column][i]);
            }

            int currBoxRow = row / 3 * 3;
            int currBoxColumn = column / 3 * 3;
            for (int i = 0; i < PART; i++) {
                for (int j = 0; j < PART; j++) {
                    possibleValues.remove(rows[i + currBoxRow][j + currBoxColumn]);
                }
            }
            return possibleValues;
        }
    }

}
