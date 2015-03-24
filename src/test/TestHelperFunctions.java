package test;

import static org.junit.Assert.*;

import org.junit.Test;

import smart.SolvingPredicate;

public class TestHelperFunctions {

	@Test
	public void testMakeNBytesLong(){
		byte[] test1 = {(byte) 0xff, (byte) 0x00, (byte) 0x23, (byte) 0x99};
		byte[] test2 = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x23, (byte) 0x99};
		byte[] test3 = {(byte) 0x99};
		byte[] test4 = {(byte) 0xff, (byte) 0x29};
		
		int n1 = 2;
		int n2 = 3;
		int n3 = 4;
		int n4 = 2;
		
		byte[] expected1 = {(byte) 0x23, (byte) 0x99};
		byte[] expected2 = {(byte) 0x00, (byte) 0x23, (byte) 0x99};
		byte[] expected3 = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x99};
		byte[] expected4 = {(byte) 0xff, (byte) 0x29};
		
		assertArrayEquals(expected1, SolvingPredicate.makeNBytesLong(test1, n1));
		assertArrayEquals(expected2, SolvingPredicate.makeNBytesLong(test2, n2));
		assertArrayEquals(expected3, SolvingPredicate.makeNBytesLong(test3, n3));
		assertArrayEquals(expected4, SolvingPredicate.makeNBytesLong(test4, n4));
	}
}
