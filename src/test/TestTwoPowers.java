package test;

import static org.junit.Assert.*;
import smart.SolvingPredicate;

import org.junit.Test;

public class TestTwoPowers {

	@Test
	public void testTwoPow(){
		assertEquals(1, SolvingPredicate.twoPow(0));
		assertEquals(8, SolvingPredicate.twoPow(3));
		assertEquals(128, SolvingPredicate.twoPow(7));
	}
	
	@Test
	public void testTwoPowPow(){
		assertEquals(2, SolvingPredicate.twoPowPow(0).intValue());
		assertEquals(16, SolvingPredicate.twoPowPow(2).intValue());
		assertEquals(65536, SolvingPredicate.twoPowPow(4).intValue());
	}
	
}
