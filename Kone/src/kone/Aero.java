package kone;

import kone.core.Matrix3d;
import kone.core.Vector3d;
import kone.core.Matrix4d;

public class Aero implements ForceGenerator {
	
	Matrix3d tensor;
	
	Vector3d position;
	
	Vector3d windspeed;
	
	public Aero(Matrix3d tensor, Vector3d position, Vector3d windspeed)
	{
		this.tensor = tensor;
		this.position = position;
		this.windspeed = windspeed;
		
	}
	
	public void setWindSpeed(Vector3d ws)
	{
		windspeed = new Vector3d(ws);
	}

	@Override
	public void UpdateForce(Body body, double duration) {
		
		UpdateForceFromTensor(body, duration, tensor);
	}

	public void UpdateForceFromTensor(Body body, double duration, Matrix3d tensor)
	{
		Vector3d velocity = new Vector3d(body.getVelocity());
		velocity.Add(windspeed);
		
		Vector3d bodyVel = new Vector3d(body.getTransform().TransformInverseDirection(velocity));
		
		Vector3d bodyForce = new Vector3d(tensor.Transform(bodyVel));
		
		Vector3d force = new Vector3d(body.getTransform().TransformDirection(bodyForce));
		
		body.AddForceAtBodyPoint(force, position);
		
		
	}
}
