package kone.core;

public class Box {

	public Endpoint[] xEnd;
	public Endpoint[] yEnd;
	public Endpoint[] zEnd;
	
	public Box()
	{
		xEnd = new Endpoint[2];
		xEnd[0] = new Endpoint();
		xEnd[1] = new Endpoint();
		
		yEnd = new Endpoint[2];
		yEnd[0] = new Endpoint();
		yEnd[1] = new Endpoint();
		
		zEnd = new Endpoint[2];
		zEnd[0] = new Endpoint();
		zEnd[1] = new Endpoint();
	}
	
	public Box(Box other)
	{
		xEnd = new Endpoint[2];
		xEnd[0] = new Endpoint(other.xEnd[0]);
		xEnd[1] = new Endpoint(other.xEnd[1]);

		yEnd = new Endpoint[2];
		yEnd[0] = new Endpoint(other.yEnd[0]);
		yEnd[1] = new Endpoint(other.yEnd[1]);
		
		zEnd = new Endpoint[2];
		zEnd[0] = new Endpoint(other.zEnd[0]);
		zEnd[1] = new Endpoint(other.zEnd[1]);
	
	}
	
	public boolean hasYOverlap(Box other)
	{
		return (yEnd[1].value >= other.yEnd[0].value && yEnd[0].value <= other.yEnd[1].value);
	}
	
	public boolean hasZOverlap(Box other)
	{
		return (zEnd[1].value >= other.zEnd[0].value && zEnd[0].value <= other.zEnd[1].value);
	}

	public boolean TestIntersection(Box other) {
		
		if (xEnd[1].value < other.xEnd[0].value || xEnd[0].value > other.xEnd[1].value)
		{
			return false;
		}
		if (yEnd[1].value < other.yEnd[0].value || yEnd[0].value > other.yEnd[1].value)
		{
			return false;
		}
		if (zEnd[1].value < other.zEnd[0].value || zEnd[0].value > other.zEnd[1].value)
		{
			return false;
		}
		return true;
	}


}
