package kone.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class BoxManager {

	private Set<Pair> overLaps;
	private Set<Integer> active;
	private List<Endpoint> xEndPoints;
	private List<Integer> xLookUp;
	private List<Endpoint> yEndPoints;
	private List<Integer> yLookUp;
	private List<Box> boxes;
	private List<Endpoint> zEndPoints;
	private List<Integer> zLookUp;


	public BoxManager(List<Box> bs) {
		
		boxes = new ArrayList<Box>();
		boxes.addAll(bs);
		
		xEndPoints = new ArrayList<Endpoint>();
		yEndPoints = new ArrayList<Endpoint>();
		zEndPoints = new ArrayList<Endpoint>();
		for (int i = 0; i < 2*boxes.size(); i++)
		{
			xEndPoints.add(null);
			yEndPoints.add(null);
			zEndPoints.add(null);
		}
		
		for (int i = 0; i < boxes.size(); ++i)
		{
			Endpoint endpoint = new Endpoint();
			endpoint.type = Endpoint.Type.BEGIN;
			endpoint.value = boxes.get(i).xEnd[0].value;
			endpoint.index = i;
			xEndPoints.set(2*i, endpoint);
			
			endpoint = new Endpoint();
			endpoint.type = Endpoint.Type.BEGIN;
			endpoint.value = boxes.get(i).yEnd[0].value;
			endpoint.index = i;
			yEndPoints.set(2*i, endpoint);
			
			endpoint = new Endpoint();
			endpoint.type = Endpoint.Type.BEGIN;
			endpoint.value = boxes.get(i).zEnd[0].value;
			endpoint.index = i;
			zEndPoints.set(2*i, endpoint);
			
			endpoint = new Endpoint();
			endpoint.type = Endpoint.Type.END;
			endpoint.value = boxes.get(i).xEnd[1].value;
			endpoint.index = i;
			xEndPoints.set(2*i+1, endpoint);
			
			endpoint = new Endpoint();
			endpoint.type = Endpoint.Type.END;
			endpoint.value = boxes.get(i).yEnd[1].value;
			endpoint.index = i;
			yEndPoints.set(2*i+1, endpoint);
			
			endpoint = new Endpoint();
			endpoint.type = Endpoint.Type.END;
			endpoint.value = boxes.get(i).zEnd[1].value;
			endpoint.index = i;
			zEndPoints.set(2*i+1, endpoint);
			
		}
		
		Collections.sort(xEndPoints);
		Collections.sort(yEndPoints);
		Collections.sort(zEndPoints);
		
		xLookUp = new ArrayList<Integer>();
		yLookUp = new ArrayList<Integer>();
		zLookUp = new ArrayList<Integer>();
		
		for (int i = 0; i < xEndPoints.size(); i++)
		{
			xLookUp.add(null);
			yLookUp.add(null);
			zLookUp.add(null);
		}
		
		for (int j = 0; j < xEndPoints.size(); ++j)
		{
			xLookUp.set(2*xEndPoints.get(j).index + xEndPoints.get(j).type.ordinal(), new Integer(j));
			yLookUp.set(2*yEndPoints.get(j).index + yEndPoints.get(j).type.ordinal(), new Integer(j));
			zLookUp.set(2*zEndPoints.get(j).index + zEndPoints.get(j).type.ordinal(), new Integer(j));
		}
		
		active = new LinkedHashSet<Integer>();
		overLaps = new LinkedHashSet<Pair>();
		
		for (int i = 0; i < xEndPoints.size(); i++)
		{
			Endpoint end = xEndPoints.get(i);
			int index = end.index;
			
			if (end.type.ordinal() == Endpoint.Type.BEGIN.ordinal())
			{
				Iterator<Integer> iterator = active.iterator();
				
				while (iterator.hasNext())
				{
					Integer activeIndex = iterator.next(); 
					
					Box b0 = boxes.get(activeIndex);
					Box b1 = boxes.get(index);
					
					if (b0.hasYOverlap(b1) && b0.hasZOverlap(b1))
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

	public void set(int i, Box box) {
		
		boxes.set(i, box);
		
		Endpoint endpoint = xEndPoints.get(xLookUp.get(2*i));
		endpoint.value = box.xEnd[0].value;
		xEndPoints.set(xLookUp.get(2*i), endpoint);
		endpoint = yEndPoints.get(yLookUp.get(2*i));
		endpoint.value = box.yEnd[0].value;
		yEndPoints.set(yLookUp.get(2*i), endpoint);
		endpoint = zEndPoints.get(zLookUp.get(2*i));
		endpoint.value = box.zEnd[0].value;
		zEndPoints.set(zLookUp.get(2*i), endpoint);
		
		endpoint = xEndPoints.get(xLookUp.get(2*i+1));
		endpoint.value = box.xEnd[1].value;
		xEndPoints.set(xLookUp.get(2*i+1), endpoint);
		endpoint = yEndPoints.get(yLookUp.get(2*i+1));
		endpoint.value = box.yEnd[1].value;
		yEndPoints.set(yLookUp.get(2*i+1), endpoint);
		endpoint = zEndPoints.get(zLookUp.get(2*i+1));
		endpoint.value = box.zEnd[1].value;
		zEndPoints.set(zLookUp.get(2*i+1), endpoint);
		
		
		
	}

	public void update() {
		InsertionSort(xEndPoints, xLookUp);
		InsertionSort(yEndPoints, yLookUp);
		InsertionSort(zEndPoints, zLookUp);
		
	}

	private void InsertionSort(List<Endpoint> endPoints,
			List<Integer> lookUp) {
		int endpSize = endPoints.size();
		
		for (int j = 1; j < endpSize; ++j)
		{
			Endpoint key = endPoints.get(j);
			int i = j - 1;
			
			while (i >= 0 && key.compareTo(endPoints.get(i)) == -1)
			{
				Endpoint e0 = endPoints.get(i);
				Endpoint e1 = endPoints.get(i+1);
				
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
						Box b0 = boxes.get(e0.index);
						Box b1 = boxes.get(e1.index);
						
						if (b0.TestIntersection(b1))
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
