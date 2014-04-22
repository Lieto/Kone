package kone.core.unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import kone.core.Endpoint;
import kone.core.Pair;
import kone.core.Rectangle;
import kone.core.RectangleManager;
import kone.core.Vector3d;

import org.junit.Test;

public class TestRectangleManager {

	@Test
	public void test() {
		
		//Define rectangles
		
		Endpoint endpoint1xStart = new Endpoint();
		endpoint1xStart.index = 0;
		endpoint1xStart.type = Endpoint.Type.BEGIN;
		endpoint1xStart.value = 1.0;
		Endpoint endpoint1yStart = new Endpoint();
		endpoint1yStart.index = 0;
		endpoint1yStart.type = Endpoint.Type.BEGIN;
		endpoint1yStart.value = 1.0;

		Endpoint endpoint1xEnd = new Endpoint();
		endpoint1xEnd.index = 0;
		endpoint1xEnd.type = Endpoint.Type.END;
		endpoint1xEnd.value = 3.0;
		Endpoint endpoint1yEnd = new Endpoint();
		endpoint1yEnd.index = 0;
		endpoint1yEnd.type = Endpoint.Type.END;
		endpoint1yEnd.value = 3.0;
		
		Endpoint endpoint2xStart = new Endpoint();
		endpoint2xStart.index = 1;
		endpoint2xStart.type = Endpoint.Type.BEGIN;
		endpoint2xStart.value = 2.0;
		Endpoint endpoint2yStart = new Endpoint();
		endpoint2yStart.index = 0;
		endpoint2yStart.type = Endpoint.Type.BEGIN;
		endpoint2yStart.value = 2.0;

		Endpoint endpoint2xEnd = new Endpoint();
		endpoint2xEnd.index = 0;
		endpoint2xEnd.type = Endpoint.Type.END;
		endpoint2xEnd.value = 4.0;
		Endpoint endpoint2yEnd = new Endpoint();
		endpoint2yEnd.index = 0;
		endpoint2yEnd.type = Endpoint.Type.END;
		endpoint2yEnd.value = 4.0;
		
		Rectangle rectangle1 = new Rectangle();
		Rectangle rectangle2 = new Rectangle();
		
		rectangle1.xEnd[0] = endpoint1xStart;
		rectangle1.xEnd[1] = endpoint1xEnd;

		rectangle1.yEnd[0] = endpoint1yStart;
		rectangle1.yEnd[1] = endpoint1yEnd;
		

		rectangle2.xEnd[0] = endpoint2xStart;
		rectangle2.xEnd[1] = endpoint2xEnd;
		

		rectangle2.yEnd[0] = endpoint2yStart;
		rectangle2.yEnd[1] = endpoint2yEnd;
		
		
		List<Rectangle> rectangles = new ArrayList<Rectangle>();
		rectangles.add(rectangle1);
		rectangles.add(rectangle2);
		
		RectangleManager manager = new RectangleManager(rectangles);
		
		// There should be 1 unique intersections
		// {0,1}
		Pair expectedPair1 = new Pair(0,1);
		
		Set<Pair> actualPairs = manager.getOverlaps();
		
		Object[] acArray = actualPairs.toArray();
		Pair actualPair = (Pair) acArray[0];
		
		assertEquals(expectedPair1.i0, actualPair.i0);
		assertEquals(expectedPair1.i1, actualPair.i1);
		
		// Move rectangle 2
		endpoint2xStart = new Endpoint();
		endpoint2xStart.index = 1;
		endpoint2xStart.type = Endpoint.Type.BEGIN;
		endpoint2xStart.value = 4.0;
		endpoint2yStart = new Endpoint();
		endpoint2yStart.index = 0;
		endpoint2yStart.type = Endpoint.Type.BEGIN;
		endpoint2yStart.value = 4.0;

		endpoint2xEnd = new Endpoint();
		endpoint2xEnd.index = 0;
		endpoint2xEnd.type = Endpoint.Type.END;
		endpoint2xEnd.value = 6.0;
		endpoint2yEnd = new Endpoint();
		endpoint2yEnd.index = 0;
		endpoint2yEnd.type = Endpoint.Type.END;
		endpoint2yEnd.value = 6.0;
		
		rectangle2.xEnd[0] = endpoint2xStart;
		rectangle2.xEnd[1] = endpoint2xEnd;
		

		rectangle2.yEnd[0] = endpoint2yStart;
		rectangle2.yEnd[1] = endpoint2yEnd;
		
		manager.set(1, rectangle2);
		
		manager.update();
		
		actualPairs = manager.getOverlaps();
		
		assertEquals(true, actualPairs.isEmpty() );
		
		
	}

}
