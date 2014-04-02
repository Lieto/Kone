package kone;

import kone.core.Vector3d;

public class Gravity implements ForceGenerator {
	
	Vector3d gravity;
	
	public Gravity(Vector3d gravity)
	{
		this.gravity = new Vector3d(gravity);
		
	}

	@Override
	public void UpdateForce(Body body, double rotation) {
		
		if (!body.hasFiniteMass()) return;
		
		body.AddForce(gravity.NewVectorMultiply(body.getMass()));
		
	}
	

}
