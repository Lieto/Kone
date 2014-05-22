package kone.core.unittests;

import static org.junit.Assert.*;
import kone.core.Complex;
import kone.core.MatrixComplex;

import org.junit.Test;

public class TestMatrixComplex {

	@Test
	public void test() {
		
		MatrixComplex pauli2 = new MatrixComplex(2, 2);
		pauli2.entry[0][0] = new Complex();
		pauli2.entry[0][1] = new Complex(0, -1);
		pauli2.entry[1][0] = new Complex(0, 1);
		pauli2.entry[1][1] = new Complex();
		
		boolean expectedResult = true;
		
		boolean actualResult = pauli2.isHermitian();
		
		assertEquals(expectedResult, actualResult);
		
		MatrixComplex pos = new MatrixComplex(2, 2);
		pos.entry[0][0] = new Complex(1, 0);
		pos.entry[0][1] = new Complex(1, 0);
		pos.entry[1][0] = new Complex(-1, 0);
		pos.entry[1][1] = new Complex(1, 0);
		
		MatrixComplex v = new MatrixComplex(2, 1);
		v.entry[0][0] = new Complex(1, 0);
		v.entry[1][0] = new Complex(1, 0);
		
		expectedResult = true;
		actualResult = pos.isPositive(v);
		
		assertEquals(expectedResult, actualResult);
		
		v = new MatrixComplex(2, 1);
		v.entry[0][0] = new Complex(1, -1);
		v.entry[1][0] = new Complex(1, 1);
		
		expectedResult = false;
		actualResult = pos.isPositive(v);
		
		assertEquals(expectedResult, actualResult);
	}

}
