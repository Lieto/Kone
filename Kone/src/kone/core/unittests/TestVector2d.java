package kone.core.unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import kone.core.Vector2d;

public class TestVector2d {

	@Test
	public void testRotation() {
		
		Vector2d v1 = new Vector2d();
		v1.x = 1;
		v1.y = 1;
		
		double angle1 = 90;
		
		double expectedX = -1.0;
		double expectedY = 1.0;
		
		Vector2d result = v1.Rotate(angle1);
		
		double tolerance = 0.001;
		assertEquals(expectedX, result.x, tolerance);
		assertEquals(expectedY, result.y, tolerance);
		
		double angle2 = 720;
		
		expectedX = 1.0;
		expectedY = 1.0;
		
		result = v1.Rotate(angle2);
		
		
		assertEquals(expectedX, result.x, tolerance);
		assertEquals(expectedY, result.y, tolerance);
		
	}

}
