// HW1 2-d array Problems
// CharGrid encapsulates a 2-d grid of chars and supports
// a few operations on the grid.

public class CharGrid {
	private char[][] grid;

	/**
	 * Constructs a new CharGrid with the given grid.
	 * Does not make a copy.
	 * @param grid
	 */
	public CharGrid(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Returns the area for the given char in the grid. (see handout).
	 * @param ch char to look for
	 * @return area for given char
	 */
	public int charArea(char ch) {
		int left = -1;
		int right = -1;
		int top = -1;
		int bottom = -1;
		int area = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				char curr = grid[i][j];
				if(ch == curr){
					if(top == -1){
						top = i;
					}
					bottom = i;
					if(left == -1){
						left = j;
					} else if(j < left){
						left = j;
					}
					if(j > right){
						right = j;
					}
				}
			}
		}
		if(left == -1){
			return 0;
		}
		return (bottom - top + 1) * (right - left + 1);
	}
	
	/**
	 * Returns the count of '+' figures in the grid (see handout).
	 * @return number of + in grid
	 */
	public int countPlus() {
		int result = 0;
		for (int i = 1; i < grid.length - 1; i++) {
			for (int j = 1; j < grid[0].length - 1; j++) {
				char ch = grid[i][j];
				int left = countChar(ch, j, i, -1, 0);
				int top = countChar(ch, j, i, 0, -1);
				int right = countChar(ch, j, i, 1, 0);
				int bottom = countChar(ch, j, i, 0, 1);
				if(left == right && top == bottom && left == top && left > 1){
					result++;
				}
			}
		}
		return result; // YOUR CODE HERE
	}

	int countChar(char ch, int x, int y, int dx, int dy){
		int result = 0;
		while(x >= 0 && x < grid[0].length && y >= 0 && y < grid.length){
			char curr = grid[y][x];
			if(curr == ch){
				result++;
			} else{
				return result;
			}
			x += dx;
			y += dy;
		}
		return result;
	}
	
}
