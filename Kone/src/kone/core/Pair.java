package kone.core;

public class Pair {
	
	public int i0, i1;
	
	public Pair(int i0, int i1)
	{
		if (i0 < i1)
		{
			this.i0 = i0;
			this.i1 = i1;
		}
		else
		{
			this.i0 = i1;
			this.i1 = i0;
		}
	}
	
	public String toString()
	{
		StringBuffer buffer = new StringBuffer("");
		buffer.append("{");
		buffer.append(Integer.toString(i0));
		buffer.append(",");
		buffer.append(Integer.toString(i1));
		buffer.append("}");
		return buffer.toString();
		
	}

}
