// StringCodeTest
// Some test code is provided for the early HW1 problems,
// and much is left for you to add.

import junit.framework.TestCase;
import org.junit.Test;

public class StringCodeTest extends TestCase {
	//
	// blowup
	//

	public void testBlowup1() {
		// basic cases
		assertEquals("xxaaaabb", StringCode.blowup("xx3abb"));
		assertEquals("xxxZZZZ", StringCode.blowup("2x3Z"));
		assertEquals("abbcd", StringCode.blowup("a1bcd"));
		assertEquals("EFGGGHII", StringCode.blowup("EF1GGH1I"));
		assertEquals("look", StringCode.blowup("l1ok"));
	}

	public void testBlowup2() {
		// things with digits
		
		// digit at end
		assertEquals("axxx", StringCode.blowup("a2x3"));
		
		// digits next to each other
		assertEquals("a33111", StringCode.blowup("a231"));
		
		// try a 0
		assertEquals("aabb", StringCode.blowup("aa0bb"));

		assertEquals("aaaaabb", StringCode.blowup("aa1aabb"));
		assertEquals("axxxxxxx", StringCode.blowup("a2x3x4"));

	}
	
	public void testBlowup3() {
		// weird chars, empty string
		assertEquals("AB&&,- ab", StringCode.blowup("AB&&,- ab"));
		assertEquals("", StringCode.blowup(""));
		
		// string with only digits
		assertEquals("", StringCode.blowup("2"));
		assertEquals("33", StringCode.blowup("23"));

		assertEquals("12334445555", StringCode.blowup("112345"));
	}
	
	
	//
	// maxRun
	//
	public void testRun1() {
		assertEquals(2, StringCode.maxRun("hoopla"));
		assertEquals(3, StringCode.maxRun("hoopllla"));
		assertEquals(1, StringCode.maxRun("kaloche"));
		assertEquals(2, StringCode.maxRun("vaax"));
		assertEquals(1, StringCode.maxRun("ababab"));
	}
	
	public void testRun2() {
		assertEquals(3, StringCode.maxRun("abbcccddbbbxx"));
		assertEquals(0, StringCode.maxRun(""));
		assertEquals(3, StringCode.maxRun("hhhooppoo"));

		assertEquals(2, StringCode.maxRun("aabbccddf"));
		assertEquals(3, StringCode.maxRun("abbccc"));
		assertEquals(3, StringCode.maxRun("aaabbc"));
	}
	
	public void testRun3() {
		// "evolve" technique -- make a series of test cases
		// where each is change from the one above.
		assertEquals(1, StringCode.maxRun("123"));
		assertEquals(2, StringCode.maxRun("1223"));
		assertEquals(2, StringCode.maxRun("112233"));
		assertEquals(3, StringCode.maxRun("1112233"));
		assertEquals(4, StringCode.maxRun("1111222333"));
		assertEquals(5, StringCode.maxRun("1111122223333"));
	}

	// Need test cases for stringIntersect

	public void testStringIntersect1(){
//		test positive cases
		assertEquals(true, StringCode.stringIntersect("aaa", "aa", 1));
		assertEquals(true, StringCode.stringIntersect("aba", "aa", 1));
		assertEquals(true, StringCode.stringIntersect("kaxa", "kaka", 2));
		assertEquals(true, StringCode.stringIntersect("nika", "luka", 2));
	}

	public void testStringIntersect2(){
//		test negative cases
		assertEquals(false, StringCode.stringIntersect("aaa", "bb", 1));
		assertEquals(false, StringCode.stringIntersect("aba", "cdc", 1));
		assertEquals(false, StringCode.stringIntersect("bla", "ki", 1));
		assertEquals(false, StringCode.stringIntersect("foo", "bar", 1));
		assertEquals(false, StringCode.stringIntersect("dad", "man", 2));
	}

	public void testStringIntersect3(){
		assertEquals(true, StringCode.stringIntersect("stomach", "bromance", 1));
		assertEquals(true, StringCode.stringIntersect("stomach", "bromance", 2));
		assertEquals(true, StringCode.stringIntersect("stomach", "bromance", 3));
		assertEquals(false, StringCode.stringIntersect("stomach", "bromance", 4));
		assertEquals(false, StringCode.stringIntersect("stomach", "bromance", 5));
	}
	
}
