import junit.framework.TestCase;


public class BoardTest extends TestCase {
	Board b;
	Piece pyr1, pyr2, pyr3, pyr4, s, sRotated;

	// This shows how to build things in setUp() to re-use
	// across tests.
	
	// In this case, setUp() makes shapes,
	// and also a 3X6 board, with pyr placed at the bottom,
	// ready to be used by tests.
	
	protected void setUp() throws Exception {
		b = new Board(3, 6);
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
		
		b.place(pyr1, 0, 0);
	}
	
	// Check the basic width/height/max after the one placement
	public void testSample1() {
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getMaxHeight());
		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
	}
	
	// Place sRotated into the board, then check some measures
	public void testSample2() {
		b.commit();
		int result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(1, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());
	}
	
	// Makre  more tests, by putting together longer series of 
	// place, clearRows, undo, place ... checking a few col/row/max
	// numbers that the board looks right after the operations.

	public void testClearRows(){
		b.clearRows();
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(1, b.getMaxHeight());

		b.commit();
		int result = b.place(pyr2, 1, 0);
		assertEquals(Board.PLACE_OK, result);
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(3, b.getMaxHeight());


		b.commit();
		result = b.place(pyr4, 0, 1);
		assertEquals(Board.PLACE_ROW_FILLED, result);
		assertEquals(4, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(4, b.getMaxHeight());

		b.clearRows();
		assertEquals(2, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(1, b.getColumnHeight(2));
		assertEquals(2, b.getMaxHeight());


		for (int i = 0; i < b.getWidth(); i++) {
			for (int j = 0; j < b.getHeight(); j++) {
				if(i == 0 && j == 1 || i == 1 && j == 0 || i == 2 && j == 0){
					assertTrue(b.getGrid(i, j));
				} else{
					assertFalse(b.getGrid(i, j));
				}
			}
		}
		b.commit();

		Piece square = new Piece(Piece.SQUARE_STR);
		result = b.place(square, 1,1);
		assertEquals(Board.PLACE_ROW_FILLED, result);
		assertEquals(2, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));
		assertEquals(3, b.getMaxHeight());

		b.clearRows();
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(2, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(2, b.getMaxHeight());
	}

	public void testUndo1(){
		b.undo();
		assertEquals(0, b.getColumnHeight(0));
		assertEquals(0, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
		assertEquals(0, b.getMaxHeight());

		assertEquals(0, b.getRowWidth(0));
		assertEquals(0, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(0, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));
	}

	public void testUndo2(){
		b.commit();
		b.clearRows();
		b.undo();
		testSample1();
	}

	public void testUndo3(){
		b.clearRows();
		b.commit();
		int result = b.place(sRotated, 1, 0);
		assertEquals(Board.PLACE_OK, result);
		b.commit();
		result = b.place(sRotated, 1, 1);
		assertEquals(Board.PLACE_BAD, result);
		b.undo();

		assertEquals(0, b.getColumnHeight(0));
		assertEquals(3, b.getColumnHeight(1));
		assertEquals(2, b.getColumnHeight(2));
		assertEquals(3, b.getMaxHeight());

		assertEquals(2, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(0, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));
	}

	public void testAll(){
		b.commit();
		Piece square = new Piece(Piece.SQUARE_STR);
		int y = b.dropHeight(square, 1);
		assertEquals(2, y);
		int result = b.place(square, 1, 2);
		assertEquals(Board.PLACE_OK, result);

		b.commit();

		Piece verticalStick = new Piece(Piece.STICK_STR);
		assertEquals(1, verticalStick.getSkirt().length);
		y = b.dropHeight(verticalStick, 0);
		assertEquals(1, y);
		result = b.place(verticalStick, 0, 1);
		assertEquals(Board.PLACE_ROW_FILLED, result);

		b.commit();
		testAllPrevState();

		b.clearRows();

		assertEquals(2, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));

		assertEquals(2, b.getColumnHeight(0));
		assertEquals(1, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));

		b.undo();
		testAllPrevState();
	}

	private void testAllPrevState(){
		assertEquals(3, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(3, b.getRowWidth(2));
		assertEquals(3, b.getRowWidth(3));
		assertEquals(1, b.getRowWidth(4));

		assertEquals(5, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(4, b.getColumnHeight(2));
	}

	public void testEdgeClearRow(){
		b.commit();
		Piece verticalStick = new Piece(Piece.STICK_STR);
		int result = b.place(verticalStick, 0, 1);
		assertEquals(Board.PLACE_OK, result);

		b.commit();

		result = b.place(pyr3, 0, 4);
		assertEquals(Board.PLACE_ROW_FILLED, result);

		assertEquals(3, b.getRowWidth(0));
		assertEquals(2, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(1, b.getRowWidth(3));
		assertEquals(2, b.getRowWidth(4));
		assertEquals(3, b.getRowWidth(5));

		assertEquals(6, b.getColumnHeight(0));
		assertEquals(6, b.getColumnHeight(1));
		assertEquals(6, b.getColumnHeight(2));

		b.clearRows();

		assertEquals(2, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(1, b.getRowWidth(2));
		assertEquals(2, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));

		assertEquals(4, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(0, b.getColumnHeight(2));
	}

	public void testPlaying(){
		b.commit();
		int result = b.place(pyr1, 0, 4);
		assertEquals(Board.PLACE_ROW_FILLED, result);
		assertEquals(6, b.getMaxHeight());

		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(0, b.getRowWidth(3));
		assertEquals(3, b.getRowWidth(4));
		assertEquals(1, b.getRowWidth(5));

		assertEquals(5, b.getColumnHeight(0));
		assertEquals(6, b.getColumnHeight(1));
		assertEquals(5, b.getColumnHeight(2));

		b.undo();
		testSample1();

		result = b.place(pyr1, 0, 3);
		assertEquals(Board.PLACE_ROW_FILLED, result);

		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(0, b.getRowWidth(2));
		assertEquals(3, b.getRowWidth(3));
		assertEquals(1, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));

		assertEquals(4, b.getColumnHeight(0));
		assertEquals(5, b.getColumnHeight(1));
		assertEquals(4, b.getColumnHeight(2));

		System.out.println(b.toString());
		b.undo();
		testSample1();

		result = b.place(pyr1, 0, 2);
		assertEquals(Board.PLACE_ROW_FILLED, result);

		assertEquals(3, b.getRowWidth(0));
		assertEquals(1, b.getRowWidth(1));
		assertEquals(3, b.getRowWidth(2));
		assertEquals(1, b.getRowWidth(3));
		assertEquals(0, b.getRowWidth(4));
		assertEquals(0, b.getRowWidth(5));

		assertEquals(3, b.getColumnHeight(0));
		assertEquals(4, b.getColumnHeight(1));
		assertEquals(3, b.getColumnHeight(2));


		b.undo();
		testSample1();

		result = b.place(pyr1, 0, 1);
		assertEquals(Board.PLACE_BAD, result);

		b.undo();
		testSample1();
	}
}
