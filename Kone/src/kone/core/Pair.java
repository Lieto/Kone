package kone.core;

public class Pair {
	
	public int i0, i1;
	
	public Pair(int i0, int i1)
	{
		super();
		
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i0;
		result = prime * result + i1;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (i0 != other.i0)
			return false;
		if (i1 != other.i1)
			return false;
		return true;
	}


}
