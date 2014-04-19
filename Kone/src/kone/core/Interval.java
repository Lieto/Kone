package kone.core;

public class Interval {
	
	public Endpoint[] end;
	
	public Interval()
	{
		end = new Endpoint[2];
		end[0] = new Endpoint();
		end[1] = new Endpoint();
	}
	
	public Interval(Interval other)
	{
		end = new Endpoint[2];
		end[0] = new Endpoint(other.end[0]);
		end[1] = new Endpoint(other.end[1]);
	}

}
