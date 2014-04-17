package kone;

import kone.Function;
import kone.core.Vector3d;

public class GravityFunction extends Function {
	
	public double mass;
	
	public GravityFunction(Vector3d input, double mass)
	{
		super();
		this.input = input;
		this.mass = mass;
	}
	
	public GravityFunction() {
		super();
		mass = 0.0d;
	}

	public Vector3d solve()
	{
		Vector3d unitVector = new Vector3d(input);
		double r2 = input.SquareMagnitude();
		unitVector.Normalise();
		unitVector.Invert();
		
		
		output = unitVector.NewVectorMultiply(50*9.81d*5/r2);
		
		return output;
	}

}
