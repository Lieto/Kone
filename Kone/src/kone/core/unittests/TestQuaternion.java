package kone.core.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import kone.core.Quaternion;

public class TestQuaternion {

	@Test
	public void test() {
		
		Quaternion i = new Quaternion(0, 1, 0, 0);
		Quaternion j = new Quaternion(0, 0, 1, 0);
		Quaternion k = new Quaternion(0, 0, 0, 1);
		
		double tolerance = 0.001;
		
		assertEquals(0.0d, i.r, tolerance);
		assertEquals(1.0d, i.i, tolerance);
		assertEquals(0.0d, i.j, tolerance);
		assertEquals(0.0d, i.k, tolerance);
		
		double expectedResult = -1.0d;
		
		double actualResult1 = i.Multiply(i).r;
		double actualResult2 = j.Multiply(j).r;
		double actualResult3 = k.Multiply(k).r;
		double actualResult4 = i.Multiply(j).Multiply(k).r;
		
		assertEquals(expectedResult, actualResult1, tolerance);
		assertEquals(expectedResult, actualResult2, tolerance);
		assertEquals(expectedResult, actualResult3, tolerance);
		assertEquals(expectedResult, actualResult4, tolerance);
		
	}

}
