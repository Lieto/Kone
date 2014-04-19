package kone.core.unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import kone.core.Endpoint;
import kone.core.Endpoint.Type;
import kone.core.Interval;
import kone.core.IntervalManager;
import kone.core.Pair;
import kone.core.Vector3d;

import org.junit.Test;

public class TestIntervalManager {

	@Test
	public void test() {
		
		// Define endpoints
		Endpoint endpoint11 = new Endpoint();
		endpoint11.index = 1;
		endpoint11.type = Endpoint.Type.BEGIN;
		endpoint11.value = new Vector3d(1, 0, 0);
		
		Endpoint endpoint12 = new Endpoint();
		endpoint12.index = 1;
		endpoint12.type = Endpoint.Type.BEGIN;
		endpoint12.value = new Vector3d(4, 0, 0);
		
		Endpoint endpoint21 = new Endpoint();
		endpoint21.index = 2;
		endpoint21.type = Endpoint.Type.BEGIN;
		endpoint21.value = new Vector3d(2, 0, 0);
		
		Endpoint endpoint22 = new Endpoint();
		endpoint22.index = 2;
		endpoint22.type = Endpoint.Type.BEGIN;
		endpoint22.value = new Vector3d(8, 0, 0);
		
		Endpoint endpoint31 = new Endpoint();
		endpoint31.index = 3;
		endpoint31.type = Endpoint.Type.BEGIN;
		endpoint31.value = new Vector3d(0, 0, 0);
		
		Endpoint endpoint32 = new Endpoint();
		endpoint32.index = 3;
		endpoint32.type = Endpoint.Type.BEGIN;
		endpoint32.value = new Vector3d(3, 0, 0);
		
		Endpoint endpoint41 = new Endpoint();
		endpoint41.index = 4;
		endpoint41.type = Endpoint.Type.BEGIN;
		endpoint41.value = new Vector3d(6, 0, 0);
		
		Endpoint endpoint42 = new Endpoint();
		endpoint42.index = 4;
		endpoint42.type = Endpoint.Type.BEGIN;
		endpoint42.value = new Vector3d(10, 0, 0);
	
		
		// Define intervals
		Interval interval1 = new Interval();
		interval1.end[0] = endpoint11;
		interval1.end[1] = endpoint12;
		Interval interval2 = new Interval();
		interval2.end[0] = endpoint21;
		interval2.end[1] = endpoint22;
		Interval interval3 = new Interval();
		interval3.end[0] = endpoint31;
		interval3.end[1] = endpoint32;
		Interval interval4 = new Interval();
		interval4.end[0] = endpoint41;
		interval4.end[1] = endpoint42;
		
		// Make list
		List<Interval> intervals = new ArrayList<Interval>();
		intervals.add(interval1);
		intervals.add(interval2);
		intervals.add(interval3);
		intervals.add(interval4);
		
		// There should be 4 unique intersections
		// {0,2}, {1,2}, {1,3}, {0.1}
		Set<Pair> expectedPairs = new LinkedHashSet<Pair>();
		Pair pair1 = new Pair(0,2);
		Pair pair2 = new Pair(1,2);
		Pair pair3 = new Pair(0,1);
		Pair pair4 = new Pair(1,3);
				
		expectedPairs.add(pair1);
		expectedPairs.add(pair2);
		expectedPairs.add(pair3);
		expectedPairs.add(pair4);
		
		IntervalManager manager = new IntervalManager(intervals);
		
		
		
		Set<Pair> overLaps = manager.getOverLaps();
		
		Object[] exArray = expectedPairs.toArray();
		
		Object[] acArray = overLaps.toArray();
		
		for (int i = 0; i < acArray.length; i++)
		{
			Pair expectedPair = (Pair) exArray[i];
			Pair actualPair = (Pair) acArray[i];
			
			System.out.println(actualPair.toString());
			
			assertEquals(expectedPair.i0, actualPair.i0);
			assertEquals(expectedPair.i1, actualPair.i1);
		}
		
		// Move I1 to 2,5 and I4 5,9
		endpoint11 = new Endpoint();
		endpoint11.index = 1;
		endpoint11.type = Endpoint.Type.BEGIN;
		endpoint11.value = new Vector3d(2, 0, 0);
		
		endpoint12 = new Endpoint();
		endpoint12.index = 1;
		endpoint12.type = Endpoint.Type.BEGIN;
		endpoint12.value = new Vector3d(5, 0, 0);
		
		endpoint41 = new Endpoint();
		endpoint41.index = 4;
		endpoint41.type = Endpoint.Type.BEGIN;
		endpoint41.value = new Vector3d(5, 0, 0);
		
		endpoint42 = new Endpoint();
		endpoint42.index = 4;
		endpoint42.type = Endpoint.Type.BEGIN;
		endpoint42.value = new Vector3d(9, 0, 0);
		
		interval1 = new Interval();
		interval1.end[0] = endpoint11;
		interval1.end[1] = endpoint12;
		
		interval4 = new Interval();
		interval4.end[0] = endpoint41;
		interval4.end[1] = endpoint42;
		
		manager.Set(0, interval1);
		manager.Set(3, interval4);
		
		manager.Update();
		
		// There should be 5 unique intersections
				// {0,2}, {1,2}, {1,3}, {0.1}
		expectedPairs = new LinkedHashSet<Pair>();
		pair1 = new Pair(0,2);
		pair2 = new Pair(1,2);
		pair3 = new Pair(0,1);
		pair4 = new Pair(1,3);
		Pair pair5 = new Pair(0,3);
						
		expectedPairs.add(pair1);
		expectedPairs.add(pair2);
		expectedPairs.add(pair3);
		expectedPairs.add(pair4);
		expectedPairs.add(pair5);
		
		overLaps = manager.getOverLaps();
		
		exArray = expectedPairs.toArray();
		
		acArray = overLaps.toArray();
		
		for (int i = 0; i < acArray.length; i++)
		{
			Pair expectedPair = (Pair) exArray[i];
			Pair actualPair = (Pair) acArray[i];
			
			System.out.println(actualPair.toString());
			
			assertEquals(expectedPair.i0, actualPair.i0);
			assertEquals(expectedPair.i1, actualPair.i1);
		}
	}
		
	

}
