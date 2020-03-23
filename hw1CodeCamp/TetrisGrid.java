//
// TetrisGrid encapsulates a tetris board and has
// a clearRows() capability.

public class TetrisGrid {

	private boolean[][] grid;
	
	/**
	 * Constructs a new instance with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public TetrisGrid(boolean[][] grid) {
		this.grid = grid;
	}
	
	
	/**
	 * Does row-clearing on the grid (see handout).
	 */
	public void clearRows() {
		for(int i = 0; i < grid[0].length; i++){
			if(isColumnTrue(i)){
				copyNextColumns(i);
				i--;
			}
		}
	}
	
	/**
	 * Returns the internal 2d grid array.
	 * @return 2d grid array
	 */
	boolean[][] getGrid() {
		return grid;
	}

	private boolean isColumnTrue(int column){
		for (int i = 0; i < grid.length; i++) {
			if(!grid[i][column]){
				return false;
			}
		}
		return true;
	}

	private void copyNextColumns(int column){
		for (int i = column; i < grid[0].length - 1; i++) {
			for (int j = 0; j < grid.length; j++) {
				grid[j][i] = grid[j][i + 1];
			}
		}
		for (int i = 0; i < grid.length; i++) {
			grid[i][grid[0].length - 1] = false;
		}
	}
}
