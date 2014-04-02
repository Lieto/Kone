package kone;

import kone.core.Matrix3d;
import kone.core.Quaternion;
import kone.core.Vector3d;

public class AngledAero extends Aero {
	
	public AngledAero(Matrix3d tensor, Vector3d position, Vector3d windspeed) {
		super(tensor, position, windspeed);
		// TODO Auto-generated constructor stub
	}

	Quaternion orientation;
	
	public void setOrientation()
	{
		
	}
	
	@Override
	public void UpdateForce(Body body, double duration)
	{
		
	}

}
