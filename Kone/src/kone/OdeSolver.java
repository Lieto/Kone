package kone;

public class OdeSolver {
	
	protected int dim;
	protected double step;
	protected Function function;
	protected Object[] userData;
	protected double fValue;
	
	public OdeSolver(int dim, double step, Function function, Object[] userData)
	{
		this.dim = dim;
		this.step = step;
		this.function = function;
		this.userData = userData;
		
		fValue = (double) dim;
	}
	
	public void Update(double tIn, double[] xIn, double tOut, double[] xOut)
	{
		
	}
	
	public void setStepSize(double step)
	{
		this.step = step;
	}
	
	public double getStepSize()
	{
		return step;
	}
	
	public void setUserData(Object[] userData)
	{
		this.userData = userData;
	}
	
	public Object[] getUserData()
	{
		return userData;
	}

}
