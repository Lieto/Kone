package kone.core.unittests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import junit.framework.TestCase;
import kone.core.Box;
import kone.core.BoxManager;
import kone.core.Endpoint;
import kone.core.Pair;
import kone.core.Rectangle;
import kone.core.RectangleManager;

public class TestBoxManager extends TestCase {

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
		Endpoint endpoint1zStart = new Endpoint();
		endpoint1zStart.index = 0;
		endpoint1zStart.type = Endpoint.Type.BEGIN;
		endpoint1zStart.value = 1.0;

		Endpoint endpoint1xEnd = new Endpoint();
		endpoint1xEnd.index = 0;
		endpoint1xEnd.type = Endpoint.Type.END;
		endpoint1xEnd.value = 3.0;
		Endpoint endpoint1yEnd = new Endpoint();
		endpoint1yEnd.index = 0;
		endpoint1yEnd.type = Endpoint.Type.END;
		endpoint1yEnd.value = 3.0;
		Endpoint endpoint1zEnd = new Endpoint();
		endpoint1zEnd.index = 0;
		endpoint1zEnd.type = Endpoint.Type.END;
		endpoint1zEnd.value = 3.0;
		
		Endpoint endpoint2xStart = new Endpoint();
		endpoint2xStart.index = 1;
		endpoint2xStart.type = Endpoint.Type.BEGIN;
		endpoint2xStart.value = 2.0;
		Endpoint endpoint2yStart = new Endpoint();
		endpoint2yStart.index = 0;
		endpoint2yStart.type = Endpoint.Type.BEGIN;
		endpoint2yStart.value = 2.0;
		Endpoint endpoint2zStart = new Endpoint();
		endpoint2zStart.index = 0;
		endpoint2zStart.type = Endpoint.Type.BEGIN;
		endpoint2zStart.value = 2.0;

		Endpoint endpoint2xEnd = new Endpoint();
		endpoint2xEnd.index = 0;
		endpoint2xEnd.type = Endpoint.Type.END;
		endpoint2xEnd.value = 4.0;
		Endpoint endpoint2yEnd = new Endpoint();
		endpoint2yEnd.index = 0;
		endpoint2yEnd.type = Endpoint.Type.END;
		endpoint2yEnd.value = 4.0;
		Endpoint endpoint2zEnd = new Endpoint();
		endpoint2zEnd.index = 0;
		endpoint2zEnd.type = Endpoint.Type.END;
		endpoint2zEnd.value = 4.0;
		
		
		Box box1 = new Box();
		Box box2 = new Box();
		
		box1.xEnd[0] = endpoint1xStart;
		box1.xEnd[1] = endpoint1xEnd;

		box1.yEnd[0] = endpoint1yStart;
		box1.yEnd[1] = endpoint1yEnd;
		
		box1.zEnd[0] = endpoint1zStart;
		box1.zEnd[1] = endpoint1zEnd;
		

		box2.xEnd[0] = endpoint2xStart;
		box2.xEnd[1] = endpoint2xEnd;
		

		box2.yEnd[0] = endpoint2yStart;
		box2.yEnd[1] = endpoint2yEnd;
		
		box2.zEnd[0] = endpoint2zStart;
		box2.zEnd[1] = endpoint2zEnd;
		
		List<Box> boxes = new ArrayList<Box>();
		boxes.add(box1);
		boxes.add(box2);
		
		BoxManager manager = new BoxManager(boxes);
		
		// There should be 1 unique intersections
		// {0,1}
		Pair expectedPair1 = new Pair(0,1);
		
		Set<Pair> actualPairs = manager.getOverlaps();
		
		Object[] acArray = actualPairs.toArray();
		Pair actualPair = (Pair) acArray[0];
		
		assertEquals(expectedPair1.i0, actualPair.i0);
		assertEquals(expectedPair1.i1, actualPair.i1);
		
		// Move box 2, no intersection
		endpoint2xStart = new Endpoint();
		endpoint2xStart.index = 1;
		endpoint2xStart.type = Endpoint.Type.BEGIN;
		endpoint2xStart.value = 4.0;
		endpoint2yStart = new Endpoint();
		endpoint2yStart.index = 0;
		endpoint2yStart.type = Endpoint.Type.BEGIN;
		endpoint2yStart.value = 4.0;
		endpoint2zStart = new Endpoint();
		endpoint2zStart.index = 0;
		endpoint2zStart.type = Endpoint.Type.BEGIN;
		endpoint2zStart.value = 4.0;

		endpoint2xEnd = new Endpoint();
		endpoint2xEnd.index = 0;
		endpoint2xEnd.type = Endpoint.Type.END;
		endpoint2xEnd.value = 6.0;
		endpoint2yEnd = new Endpoint();
		endpoint2yEnd.index = 0;
		endpoint2yEnd.type = Endpoint.Type.END;
		endpoint2yEnd.value = 6.0;
		endpoint2zEnd = new Endpoint();
		endpoint2zEnd.index = 0;
		endpoint2zEnd.type = Endpoint.Type.END;
		endpoint2zEnd.value = 6.0;
		
		box2.xEnd[0] = endpoint2xStart;
		box2.xEnd[1] = endpoint2xEnd;
		

		box2.yEnd[0] = endpoint2yStart;
		box2.yEnd[1] = endpoint2yEnd;
		
		box2.zEnd[0] = endpoint2zStart;
		box2.zEnd[1] = endpoint2zEnd;
		
		manager.set(1, box2);
		
		manager.update();
		
		actualPairs = manager.getOverlaps();
		
		assertEquals(true, actualPairs.isEmpty());
		
		
	}


}
