package kone.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class RectangleManager {
	
	Set<Pair> overLaps;
	List<Rectangle> rectangles;
	List<Endpoint> xEndPoints;
	List<Endpoint> yEndPoints;
	
	List<Integer> xLookUp;
	List<Integer> yLookUp;

	
	public RectangleManager(List<Rectangle> recs)
	{
		rectangles = new LinkedList<Rectangle>();
		rectangles.addAll(recs);
		
		xEndPoints = new LinkedList<Endpoint>();
		yEndPoints = new LinkedList<Endpoint>();
		
		
		for (int i = 0; i < 2*rectangles.size(); i++)
		{
			xEndPoints.add(null);
			yEndPoints.add(null);
			
		}
		
		for (int i = 0; i < rectangles.size(); i++)
		{
			Endpoint endpoint = new Endpoint();
			endpoint.type = Endpoint.Type.BEGIN;
			endpoint.value = rectangles.get(i).xEnd[0].value;
			endpoint.index = i;
			xEndPoints.set(2*i, endpoint);
			
			endpoint = new Endpoint();
			endpoint.type = Endpoint.Type.BEGIN;
			endpoint.value = rectangles.get(i).yEnd[0].value;
			endpoint.index = i;
			yEndPoints.set(2*i, endpoint);
			
			endpoint = new Endpoint();
			endpoint.type = Endpoint.Type.END;
			endpoint.value = rectangles.get(i).xEnd[1].value;
			endpoint.index = i;
			xEndPoints.set(2*i+1, endpoint);
			
			endpoint = new Endpoint();
			endpoint.type = Endpoint.Type.END;
			endpoint.value = rectangles.get(i).yEnd[1].value;
			endpoint.index = i;
			yEndPoints.set(2*i+1, endpoint);
			
		
		
		}
		
		Collections.sort(xEndPoints);
		Collections.sort(yEndPoints);
		
		xLookUp = new LinkedList<Integer>();
		yLookUp = new LinkedList<Integer>();
		
		for (int i = 0; i < xEndPoints.size(); i++)
		{
			xLookUp.add(null);
			yLookUp.add(null);
		}
		
		for (int i = 0; i < xEndPoints.size(); ++i)
		{
			xLookUp.set(2*xEndPoints.get(i).index + xEndPoints.get(i).type.ordinal(), new Integer(i));
			yLookUp.set(2*yEndPoints.get(i).index + yEndPoints.get(i).type.ordinal(), new Integer(i));
		}
		
		Set<Integer> active = new LinkedHashSet<Integer>();
		overLaps = new LinkedHashSet<Pair>();
		
		for (int i = 0; i < xEndPoints.size(); ++i)
		{
			Endpoint end = xEndPoints.get(i);
			int index = end.index;
			
			if (end.type.ordinal() == Endpoint.Type.BEGIN.ordinal())
			{
				Iterator<Integer> iterator = active.iterator();
				
				while (iterator.hasNext())
				{
					Integer activeIndex = iterator.next(); 
					
					Rectangle r0 = rectangles.get(activeIndex);
					Rectangle r1 = rectangles.get(index);
					
					if (r0.hasYOverlap(r1))
					{
						if (activeIndex < index)
						{
							overLaps.add(new Pair(activeIndex, index));
						}
						else
						{
							overLaps.add(new Pair(index, activeIndex));
						}
					}
				}
				
				active.add(new Integer(index));
			}
			else
			{
				active.remove(new Integer(index));
			}
		}
		
	}

	public Set<Pair> getOverlaps() {
		
		return overLaps;
	}

	public void set(int i, Rectangle rectangle) {
		
		rectangles.set(i, rectangle);
		
		Endpoint endpoint = xEndPoints.get(xLookUp.get(2*i));
		endpoint.value = rectangle.xEnd[0].value;
		xEndPoints.set(xLookUp.get(2*i), endpoint);
		endpoint = yEndPoints.get(yLookUp.get(2*i));
		endpoint.value = rectangle.yEnd[0].value;
		yEndPoints.set(yLookUp.get(2*i), endpoint);
		
		endpoint = xEndPoints.get(xLookUp.get(2*i+1));
		endpoint.value = rectangle.xEnd[1].value;
		xEndPoints.set(xLookUp.get(2*i+1), endpoint);
		endpoint = yEndPoints.get(yLookUp.get(2*i+1));
		endpoint.value = rectangle.yEnd[1].value;
		yEndPoints.set(yLookUp.get(2*i+1), endpoint);
		
	}

	public void update() {
		
		InsertionSort(xEndPoints, xLookUp);
		InsertionSort(yEndPoints, yLookUp);
		
	}

	private void InsertionSort(List<Endpoint> endPoints,
			List<Integer> lookUp)
	{
		int endpSize = endPoints.size();
		
		for (int j = 1; j < endpSize; ++j)
		{
			Endpoint key = new Endpoint(endPoints.get(j));
			int i = j - 1;
			
			while (i >= 0 && key.compareTo(endPoints.get(i)) == -1)
			{
				Endpoint e0 = new Endpoint(endPoints.get(i));
				Endpoint e1 = new Endpoint(endPoints.get(i+1));
				
				if (e0.type.ordinal() == Endpoint.Type.BEGIN.ordinal())
				{
					if (e1.type.ordinal() == Endpoint.Type.END.ordinal())
					{
						if (e0.index < e1.index)
						{
							overLaps.remove(new Pair(e0.index, e1.index));
						}
						else
						{
							overLaps.remove(new Pair(e1.index, e0.index));
						}
						
					}
				}
				else
				{
					if (e1.type.ordinal() == Endpoint.Type.BEGIN.ordinal())
					{
						Rectangle r0 = new Rectangle(rectangles.get(e0.index));
						Rectangle r1 = new Rectangle(rectangles.get(e1.index));
						
						if (r0.TestIntersection(r1))
						{
							if (e0.index < e1.index)
							{
								overLaps.add(new Pair(e0.index, e1.index));
							}
							else
							{
								overLaps.add(new Pair(e1.index, e0.index));
							}
						
						}
						
					}
				}
				
				endPoints.set(i, e1);
				endPoints.set(i+1, e0);
				
				lookUp.set(2*e1.index + e1.type.ordinal(), new Integer(i));
				lookUp.set(2*e0.index + e0.type.ordinal(), new Integer(i+1));
				
				i--;
			}
			
			endPoints.set(i+1, key);
			lookUp.set(2*key.index + key.type.ordinal(), new Integer(i+1));
		}
		
		
	}

}
