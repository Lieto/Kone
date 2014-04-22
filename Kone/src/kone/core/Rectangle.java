package kone.core;

public class Rectangle {
	
	public Endpoint[] xEnd;
	public Endpoint[] yEnd;
	
	public Rectangle()
	{
		xEnd = new Endpoint[2];
		xEnd[0] = new Endpoint();
		xEnd[1] = new Endpoint();
		
		yEnd = new Endpoint[2];
		yEnd[0] = new Endpoint();
		yEnd[1] = new Endpoint();
	}
	
	public Rectangle(Rectangle other)
	{
		xEnd = new Endpoint[2];
		xEnd[0] = new Endpoint(other.xEnd[0]);
		xEnd[1] = new Endpoint(other.xEnd[1]);

		yEnd = new Endpoint[2];
		yEnd[0] = new Endpoint(other.yEnd[0]);
		yEnd[1] = new Endpoint(other.yEnd[1]);
	
	}
	
	public boolean hasYOverlap(Rectangle other)
	{
		return (yEnd[1].value >= other.yEnd[0].value && yEnd[0].value <=  other.yEnd[1].value);
		
	}
	
	public boolean TestIntersection(Rectangle other)
	{
		if (xEnd[1].value < other.xEnd[0].value || xEnd[0].value > other.xEnd[1].value)
		{
			return false;
		}
		if (yEnd[1].value < other.yEnd[0].value || yEnd[0].value > other.yEnd[1].value)
		{
			return false;
		}
		return true;
	}

}
