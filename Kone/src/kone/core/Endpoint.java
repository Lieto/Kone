package kone.core;

public class Endpoint implements Comparable<Endpoint> {
	
	public enum Type {
		BEGIN,
		END
	};
	
	public Type type;
	public double value;
	
	// Index of interval containing this endpoint
	public int index;
	
	public Endpoint()
	{
		type = Type.BEGIN;
		value = 0.0d;
		
		index = 0;
	}
	
	public Endpoint(Endpoint other)
	{
		this.type = other.type;
		value = other.value;
		this.index = other.index;
	}
	
	
	public int compareTo(Endpoint other) {
		
		double length = Math.abs(value);
		double otherLength = Math.abs(other.value);
		
		double distance = length - otherLength;
		
		if (distance == 0.0d)
		{
			if (type.ordinal() < other.type.ordinal())
			{
				return -1;
			}
			else if (type.ordinal() == other.type.ordinal())
			{
				return 0;
			}
			else
			{
				return 1;
			}
		}
		else if (distance > 0.0d) return 1;
		else return -1;
		
	}
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer("");
		buffer.append(Double.toString(value));
		
		return buffer.toString();
	}

}
