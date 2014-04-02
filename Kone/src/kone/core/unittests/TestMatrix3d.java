package kone.core.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import kone.core.Matrix3d;

public class TestMatrix3d {

	@Test
	public void testDeterminant() {
		
		Matrix3d mat = new Matrix3d(1.0, 0.0, 0.0, 
								0.0, 1.0, 0.0, 
								0.0, 0.0, 1.0);
		
		double tolerance = 0.001;
		double expectedResult = 1.0;
		
		double actualResult = mat.Determinant();
		
		assertEquals(expectedResult, actualResult, tolerance);
		
		
	}

}
