package kone.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class IntervalManager {
	
	List<Interval> intervals;
	List<Endpoint> endPoints;
	Set<Pair> overLaps;
	List<Integer> lookUp;
	
	public Set<Pair> getOverLaps()
	{
		return overLaps;
	}
	
	public IntervalManager(List<Interval> inters)
	{
		intervals = new ArrayList<Interval>();
		intervals.addAll(inters);
		
		endPoints = new ArrayList<Endpoint>();
		
		
		for (int i = 0; i < intervals.size(); ++i)
		{
			
			Endpoint endPoint = new Endpoint();
			endPoint.type = Endpoint.Type.BEGIN;
			endPoint.value = intervals.get(i).end[0].value;
			endPoint.index = i;
			
			endPoints.add(2*i, endPoint);
			
			endPoint = new Endpoint();
			endPoint.type = Endpoint.Type.END;
			endPoint.value = intervals.get(i).end[1].value;
			endPoint.index = i;
			
			endPoints.add(2*i + 1, endPoint);
		}
		
		/*
		for (int i = 0; i < endPoints.size(); i++)
		{
			System.out.print(endPoints.get(i).toString() + ",");
			System.out.println();
		}
		*/
		Collections.sort(endPoints);
		
		/*
		for (int i = 0; i < endPoints.size(); i++)
		{
			System.out.print(endPoints.get(i).toString() + ",");
			System.out.println();
		}
		*/
		lookUp = new ArrayList<Integer>();
		
		for (int j = 0; j < endPoints.size(); j++)
		{
			lookUp.add(null);
		}
		
		for (int j = 0; j < endPoints.size(); ++j)
		{
			
			lookUp.set(2*endPoints.get(j).index + 
					endPoints.get(j).type.ordinal(), new Integer(j));
			
		}
		
		/*
		for (int i = 0; i < endPoints.size(); i++)
		{
			System.out.print(lookUp.get(i).toString() + ",");
			System.out.println();
		}
		*/
		Set<Integer> active = new LinkedHashSet<Integer>();
		
		overLaps = new LinkedHashSet<Pair>();
		
		for (int i = 0; i < endPoints.size(); ++i)
		{
			Endpoint endpoint = new Endpoint(endPoints.get(i));
			Integer index = new Integer(endpoint.index);
			
			if (endpoint.type.ordinal() == Endpoint.Type.BEGIN.ordinal())
			{
				Iterator<Integer> iterator = active.iterator();
				
				while (iterator.hasNext())
				{
					Integer activeIndex = iterator.next(); 
					
					if (activeIndex < index)
					{
						overLaps.add(new Pair(activeIndex, index));
					}
					else
					{
						overLaps.add(new Pair(index, activeIndex));
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
	
	public void Set(int i, Interval interval)
	{
		intervals.set(i, interval);
		
		Endpoint endpoint = endPoints.get(lookUp.get(2*i));
		endpoint.value = interval.end[0].value;
		endPoints.set(lookUp.get(2*i), endpoint);
		
		endpoint = endPoints.get(lookUp.get(2*i+1));
		endpoint.value = interval.end[1].value;
		endPoints.set(lookUp.get(2*i+1), endpoint);
		
	}
	
	public void Update()
	{
		for (int j = 1; j < endPoints.size(); ++j)
		{
			Endpoint key = new Endpoint(endPoints.get(j));
			int i = j - 1;
			
			while (i >= 0 && key.compareTo(endPoints.get(i)) == -1)
			{
				Endpoint end0 = new Endpoint(endPoints.get(i));
				Endpoint end1 = new Endpoint(endPoints.get(i+1));
				
				if (end0.type == Endpoint.Type.BEGIN)
				{
					if (end1.type == Endpoint.Type.END)
					{
						overLaps.remove(new Pair(end0.index, end1.index));
						
					}
				}
				else
				{
					if (end1.type == Endpoint.Type.BEGIN)
					{
						Interval interval0 = new Interval(intervals.get(end0.index));
						Interval interval1 = new Interval(intervals.get(end1.index));
						
						if (interval0.end[0].compareTo(interval1.end[1]) <= 0)
						{
							overLaps.add(new Pair(end0.index, end1.index));
							
						}
					}
				}
				
				endPoints.set(i, end1);
				endPoints.set(i + 1, end0);
				
				lookUp.set(2*end1.index + end1.type.ordinal(), new Integer(i));
				lookUp.set(2*end0.index + end0.type.ordinal(), new Integer(i+1));
				
				--i;
			}
			
			endPoints.set(i+1, key);
			lookUp.set(2*key.index + key.type.ordinal(), new Integer(i+1));
		}
	}

}
