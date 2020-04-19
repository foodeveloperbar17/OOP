// Board.java

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;

	private int widths[];
	private int heights[];


	private int xWidths[];
	private int xHeights[];
	private boolean[][] xGrid;
	
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		grid = new boolean[width][height];
		committed = true;
		
		widths = new int[height];
		heights = new int[width];

		xWidths = new int[height];
		xHeights = new int[width];

		xGrid = new boolean[width][height];
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {	 
		int result = 0;
		for (int i = 0; i < width; i++) {
			if (result < heights[i]){
				result = heights[i];
			}
		}
		return result;
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			for (int i = 0; i < width; i++) {
				if(heights[i] > getMaxHeight()){
					throw new RuntimeException("wrong max height");
				}
			}

			for (int i = 0; i < height; i++) {
				int filledBlocks = 0;
				for (int j = 0; j < width; j++) {
					if(getGrid(j, i)){
						filledBlocks++;
					}
				}
				if (filledBlocks != widths[i]){
					throw new RuntimeException("wrong widths, row " + i);
				}
			}
			for (int i = 0; i < width; i++) {
				int maxY = 0;
				for (int j = 0; j < height; j++) {
					if(getGrid(i, j)){
						if(j > heights[i]){
							throw new RuntimeException("wrong heights, height " + i);
						}
					}
				}
			}
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int y = 0;
		for (int i = 0; i < piece.getSkirt().length; i++) {
			int currY = heights[i + x] - piece.getSkirt()[i];
			if(currY > y){
				y = currY;
			}
		}
		return y;
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return heights[x];
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 return widths[y];
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height){
			return true;
		}
		return grid[x][y];
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		committed = false;
		copyCommitVariables();
			
		int result = PLACE_OK;

		for (int i = 0; i < piece.getBody().length; i++) {
			int pointX = piece.getBody()[i].x + x;
			int pointY = piece.getBody()[i].y + y;
			if(getGrid(pointX, pointY)){
				result = PLACE_BAD;
			} else {
				if (piece.getWidth() + x > width) {
					result = PLACE_OUT_BOUNDS;
				} else {
					widths[pointY]++;
					if(heights[pointX] < pointY + 1){
						heights[pointX] = pointY + 1;
					}
					if(widths[pointY] == width && result == PLACE_OK){
						result = PLACE_ROW_FILLED;
					}
					grid[pointX][pointY] = true;
				}
			}
		}
		return result;
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		committed = false;
		int rowsCleared = 0;
		int from = 0;
		int to = 0;
		while(to < getMaxHeight()){
		    while(from < height && widths[from] == width){
		        from++;
		        rowsCleared++;
            }
		    if (from >= height){
		    	widths[to] = 0;
				for (int i = 0; i < width; i++) {
					grid[i][to] = false;
				}
			} else{
				for (int j = 0; j < width; j++) {
					grid[j][to] = grid[j][from];
				}
				widths[to] = widths[from];
			}
		    to++;
		    from++;
        }
		for (int i = 0; i < width; i++) {
			int max = 0;
			for (int j = height - 1; j >= 0; j--) {
				if(getGrid(i, j)){
					max = j + 1;
					break;
				}
			}
			heights[i] = max;
		}

		sanityCheck();
		return rowsCleared;
	}



	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		if(!committed){
			int[] tempWidths = widths;
			widths = xWidths;
			xWidths = tempWidths;
			int[] tempHeights = heights;
			heights = xHeights;
			xHeights = tempHeights;

			boolean[][] tempGrid = grid;
			grid = xGrid;
			xGrid = tempGrid;
		}
		committed = true;
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		copyCommitVariables();
		committed = true;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}

	private void copyCommitVariables(){
		System.arraycopy(widths, 0, xWidths, 0, widths.length);
		System.arraycopy(heights, 0, xHeights, 0, heights.length);
		for (int i = 0; i < grid.length; i++) {
			System.arraycopy(grid[i], 0, xGrid[i], 0, grid[i].length);
		}
	}
}


