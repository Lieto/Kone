package kone.core.unittests;

import static org.junit.Assert.*;
import kone.core.Matrixd;

import org.junit.Test;

public class TestMatrixd {

	@Test
	public void test() {
		
		Matrixd mat = new Matrixd(3, 3);
		
		mat.entry[0][0] = 1.0;
		mat.entry[0][1] = 7.0;
		mat.entry[0][2] = 3.0;
		mat.entry[1][0] = 7.0;
		mat.entry[1][1] = 4.0;
		mat.entry[1][2] = -5.0;
		mat.entry[2][0] = 3.0;
		mat.entry[2][1] = -5.0;
		mat.entry[2][2] = 6.0;
		
		boolean expectedResult = true;
		
		boolean actualResult = mat.isSymmetric();
		
		assertEquals(expectedResult, actualResult);
	}

}
