package kone;

import kone.core.Matrix3d;
import kone.core.Vector3d;

public class AeroControl extends Aero implements ForceGenerator {
	
	

	protected Matrix3d maxTensor;
	
	protected Matrix3d minTensor;
	
	protected double controlSetting;
	
	public AeroControl(Matrix3d base, 
			Matrix3d min, Matrix3d max,
			Vector3d position, Vector3d windspeed) {
		super(base, position, windspeed);
		
		minTensor = min;
		maxTensor = max;
		controlSetting = 0.0d;
		
	}
	
	private Matrix3d getTensor()
	{
		if (controlSetting <= -1.0d) return minTensor;
		else if (controlSetting >= 1.0d) return maxTensor;
		else if (controlSetting < 0.0d)
		{
			return Matrix3d.LinearInterpolate(minTensor, tensor, controlSetting + 1.0d);
			
		}
		else if (controlSetting > 0.0d)
		{
			return Matrix3d.LinearInterpolate(tensor, maxTensor, controlSetting);
		}
		else return tensor;
		
	}
	
	public void setControl(double value)
	{
		controlSetting = value;
		
	}
	
	@Override
	public void UpdateForce(Body body, double duration) {
	
		Matrix3d tensor = getTensor();
		super.UpdateForceFromTensor(body, duration, tensor);
	}

	
	
	

}
