package kone.core.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import kone.core.RotationMatrix;

public class TestRotationMatrix {

	@Test
	public void testDeterminant() {
		
		RotationMatrix mat = new RotationMatrix(45.0, 45.0, 90.0);
		
		double expectedResult = 1.0;
		
		double tolerance = 0.1;
		
		
		double actualResult1 = mat.data[0]*mat.data[0] + mat.data[1]*mat.data[1] + mat.data[2]*mat.data[2];
		double actualResult2 = mat.data[3]*mat.data[3] + mat.data[4]*mat.data[4] + mat.data[5]*mat.data[5];
		double actualResult3 = mat.data[6]*mat.data[6] + mat.data[7]*mat.data[7] + mat.data[8]*mat.data[8];
		double actualResult4 = mat.data[0]*mat.data[0] + mat.data[3]*mat.data[3] + mat.data[6]*mat.data[6];
		double actualResult5 = mat.data[1]*mat.data[1] + mat.data[4]*mat.data[4] + mat.data[7]*mat.data[7];
		double actualResult6 = mat.data[2]*mat.data[2] + mat.data[5]*mat.data[5] + mat.data[8]*mat.data[8];
		
		assertEquals(expectedResult, actualResult1, tolerance);
		assertEquals(expectedResult, actualResult2, tolerance);
		assertEquals(expectedResult, actualResult3, tolerance);
		assertEquals(expectedResult, actualResult4, tolerance);
		assertEquals(expectedResult, actualResult5, tolerance);
		assertEquals(expectedResult, actualResult6, tolerance);
		
		double actualResult7 = mat.Determinant();
		assertEquals(expectedResult, actualResult7, tolerance);
		
		double expectedResult2 = 0.0d;
		
		double actualResult = mat.data[0]*mat.data[1] +
				mat.data[3]*mat.data[4] +
				mat.data[6]*mat.data[7];
		
		assertEquals(expectedResult2, actualResult, 0.001);
	}

}
