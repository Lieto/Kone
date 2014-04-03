package kone.core.unittests;

import static org.junit.Assert.*;
import kone.core.Complex;
import kone.core.Polar;

import org.junit.Test;

public class TestComplex {

	@Test
	public void testConstructor() {
		
		Complex a = new Complex(1, 1);
		
		double expectedReal = 1.0;
		double expectedImaginary = 1.0;
		
		double tolerance = 0.001;
		
		assertEquals(expectedReal, a.real, tolerance);
		assertEquals(expectedImaginary, a.imag, tolerance);
		
		expectedReal = 1.0;
		expectedImaginary = 0.0;
		
		Polar polar = new Polar(1.0, 0.0);
		Complex b = new Complex(polar);
		
		assertEquals(expectedReal, b.real, tolerance);
		assertEquals(expectedImaginary, b.imag, tolerance);
		
		
	}
	
	@Test
	public void testMultiply() {
		
		Complex a = new Complex(1, 1);
		Complex b = new Complex(1, 1);
		
		double expectedReal = 0.0;
		double expectedImaginary = 2.0;
		
		Complex mult = a.Multiply(b);
		
		double tolerance = 0.001;
		
		assertEquals(expectedReal, mult.real, tolerance);
		assertEquals(expectedImaginary, mult.imag, tolerance);
		
		
	}
	
	@Test
	public void testInverse() {
		
		Complex a = new Complex(0, 0);
	
		
		double expectedReal = 0.0;
		double expectedImaginary = 0.0;
		
		Complex inv = a.Inverse();
		
		
		
	}

}
