import junit.framework.TestCase;

import java.util.*;

/*
  Unit test for Piece class -- starter shell.
 */
public class PieceTest extends TestCase {
	// You can create data to be used in the your
	// test cases like this. For each run of a test method,
	// a new PieceTest object is created and setUp() is called
	// automatically by JUnit.
	// For example, the code below sets up some
	// pyramid and s pieces in instance variables
	// that can be used in tests.
	private Piece pyr1, pyr2, pyr3, pyr4;
	private Piece s, sRotated;

	protected void setUp() throws Exception {
		super.setUp();
		
		pyr1 = new Piece(Piece.PYRAMID_STR);
		pyr2 = pyr1.computeNextRotation();
		pyr3 = pyr2.computeNextRotation();
		pyr4 = pyr3.computeNextRotation();
		
		s = new Piece(Piece.S1_STR);
		sRotated = s.computeNextRotation();
	}
	
	// Here are some sample tests to get you started
	
	public void testSampleSize() {
		// Check size of pyr piece
		assertEquals(3, pyr1.getWidth());
		assertEquals(2, pyr1.getHeight());
		
		// Now try after rotation
		// Effectively we're testing size and rotation code here
		assertEquals(2, pyr2.getWidth());
		assertEquals(3, pyr2.getHeight());
		
		// Now try with some other piece, made a different way
		Piece l = new Piece(Piece.STICK_STR);
		assertEquals(1, l.getWidth());
		assertEquals(4, l.getHeight());
	}
	
	
	// Test the skirt returned by a few pieces
	public void testSampleSkirt() {
		// Note must use assertTrue(Arrays.equals(... as plain .equals does not work
		// right for arrays.
		assertTrue(Arrays.equals(new int[] {0, 0, 0}, pyr1.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0, 1}, pyr3.getSkirt()));
		
		assertTrue(Arrays.equals(new int[] {0, 0, 1}, s.getSkirt()));
		assertTrue(Arrays.equals(new int[] {1, 0}, sRotated.getSkirt()));

		assertTrue(Arrays.equals(new int[]{1, 0}, pyr2.getSkirt()));
		assertTrue(Arrays.equals(new int[]{0, 1}, pyr4.getSkirt()));

	}

	public void testSize(){
		assertEquals(pyr1.getHeight(), pyr2.getWidth());
		assertEquals(pyr1.getWidth(), pyr2.getHeight());

		assertEquals(pyr1.getWidth(), 3);
		assertEquals(pyr1.getHeight(), 2);

		assertEquals(s.getHeight(), sRotated.getWidth());
		assertEquals(s.getWidth(), sRotated.getHeight());

		assertEquals(pyr1.getHeight(), pyr3.getHeight());
		assertEquals(pyr1.getWidth(), pyr3.getWidth());

		assertEquals(pyr4.getHeight(), pyr3.getWidth());
		assertEquals(pyr4.getWidth(), pyr3.getHeight());
	}

	public void testEquals(){
		assertTrue(pyr4.computeNextRotation().equals(pyr1));
		assertTrue(pyr3.computeNextRotation().equals(pyr4));
		assertTrue(pyr2.computeNextRotation().equals(pyr3));
		assertTrue(pyr1.computeNextRotation().equals(pyr2));

		assertFalse(pyr1.computeNextRotation().equals(pyr3));

		assertTrue(s.computeNextRotation().equals(sRotated));

		assertTrue(new Piece(Piece.SQUARE_STR).computeNextRotation().equals(new Piece(Piece.SQUARE_STR)));

	}

	public void testFastRotation(){
		Piece[] pieces = Piece.getPieces();
		assertTrue(pieces[3].equals(s));
		assertTrue(pieces[3].fastRotation().equals(sRotated));
		assertTrue(pieces[6].equals(pyr1));
		assertTrue(pieces[6].fastRotation().equals(pyr2));
		assertTrue(pieces[6].fastRotation().fastRotation().equals(pyr3));
	}
	
}
