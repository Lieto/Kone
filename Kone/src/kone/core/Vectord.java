package kone.core;

public class Vectord {
	
	public int dimension;
	
	public double[] entry;
		
	public Vectord(int dimension)
	{
		this.dimension = dimension;
		entry = new double[dimension];
	}
	
	public Vectord(Vectord other) {
		this.dimension = other.dimension;
		entry = new double[dimension];
		
		for (int i = 0; i < dimension; i++)
		{
			entry[i] = other.entry[i];
		}
	}

	public void setAll(double value)
	{
		for (int i = 0; i < dimension; i++)
		{
			entry[i] = value;
		}
	}

	public void Substract(Vectord other) {
		
		for (int i = 0; i < dimension; i++)
		{
			entry[i] -= other.entry[i];
		}
		
	}

	public void abs() {
		
		for (int i = 0; i < dimension; i++)
		{
			entry[i] = Math.abs(entry[i]);
		}
		
		
	}

	public void Multiply(double scale) {
		
		for (int i = 0; i < dimension; i++)
		{
			entry[i] = scale * entry[i];
		}
		
		
	}

	public void AddScaledVector(Vectord other, double scale) {
		
		for (int i = 0; i < dimension; i++)
		{
			entry[i] += scale * other.entry[i];
		}
		
		
		
	}

	public void Add(Vectord other) {
		

		for (int i = 0; i < dimension; i++)
		{
			entry[i] += other.entry[i];
		}
		
	}
	
	public double ScalarProduct(Vectord other)
	{
		double sum = 0.0d;
		
		for (int i = 0; i < dimension; i++)
		{
			sum += entry[i]*other.entry[i];
			
		}
		
		return sum;
	}
	
	public boolean isOrthogonal(Vectord other)
	{
		if (ScalarProduct(other) == 0.0d)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

}
