
// Test cases for CharGrid -- a few basic tests are provided.

import junit.framework.TestCase;

public class CharGridTest extends TestCase {
	
	public void testCharArea1() {
		char[][] grid = new char[][] {
				{'a', 'y', ' '},
				{'x', 'a', 'z'},
			};
		
		
		CharGrid cg = new CharGrid(grid);
		assertEquals(4, cg.charArea('a'));
		assertEquals(1, cg.charArea('z'));
	}
	
	
	public void testCharArea2() {
		char[][] grid = new char[][] {
				{'c', 'a', ' '},
				{'b', ' ', 'b'},
				{' ', ' ', 'a'}
			};
		
		CharGrid cg = new CharGrid(grid);
		
		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(1, cg.charArea('c'));
	}

	public void testCharArea3(){
		char[][] grid = new char[][]{
				{'a', 'b', 'c'},
				{'a', 'a', 'c'},
				{'a', 'c', 'c'},
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(6, cg.charArea('a'));
		assertEquals(1, cg.charArea('b'));
		assertEquals(6, cg.charArea('c'));
	}

	public void testCharArea4(){
		char[][] grid = new char[][]{
				{'a', 'b', 'c'},
				{'c', 'a', 'c'},
				{'a', 'b', 'c'},
		};

		CharGrid cg = new CharGrid(grid);

		assertEquals(6, cg.charArea('a'));
		assertEquals(3, cg.charArea('b'));
		assertEquals(9, cg.charArea('c'));
		assertEquals(0, cg.charArea('h'));
	}

	public void testCountPlus1(){
		char [][] grid = new char[][]{
				{'b', 'a', 'c'},
				{'a', 'a', 'a'},
				{'c', 'a', 'b'}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(1,cg.countPlus());
	}


	public void testCountPlus2(){
		char [][] grid = new char[][]{
				{'b', 'b', 'a', 'b', 'b', 'c', 'b', ' ', 'c'},
				{'b', 'b', 'a', 'b', 'b', 'c', 'b', 'x', 'c'},
				{'a', 'a', 'a', 'a', 'a', ' ', 'x', 'x', 'x'},
				{'b', 'b', 'a', 'b', 'b', 'c', 'b', 'x', 'c'},
				{'b', 'b', 'a', 'b', 'b', 'c', 'b', ' ', 'c'},
				{'b', 'b', ' ', 'b', 'b', 'c', 'b', 'x', 'c'},
				{'b', 'b', 'b', 'b', 'b', 'c', 'b', 'x', 'c'},
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(2,cg.countPlus());
	}

	public void testCountPlus3(){
		char [][] grid = new char[][]{
				{'b', 'a', 'c', 'b'},
				{'a', 'a', 'a', 'a'},
				{'c', 'a', 'b', 'c'}
		};

		CharGrid cg = new CharGrid(grid);
		assertEquals(0,cg.countPlus());
	}
}
