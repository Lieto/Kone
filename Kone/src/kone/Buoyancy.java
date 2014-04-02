package kone;

import kone.core.Vector3d;

public class Buoyancy implements ForceGenerator {
	
	double maxDepth;
	
	double volume;
	
	double waterHeight;
	
	double liquidDensity;
	
	Vector3d centerOfBuoyancy;
	
	public Buoyancy(Vector3d cOfB, double maxDepth, double volume, double waterHeight,
			double liquidDensity)
	{
		centerOfBuoyancy = cOfB;
		this.liquidDensity = liquidDensity;
		this.maxDepth = maxDepth;
		this.volume = volume;
		this.waterHeight = waterHeight;
		
	}

	@Override
	public void UpdateForce(Body body, double rotation) {
		
		Vector3d pointInWorld = new Vector3d(body.GetPointInWorldSpace(centerOfBuoyancy));
		double depth = pointInWorld.y;
		
		if (depth >= waterHeight + maxDepth) return;
		
		Vector3d force = new Vector3d();
		
		if (depth <= waterHeight - maxDepth)
		{
			force.y = liquidDensity * volume;
			body.AddForceAtBodyPoint(force, centerOfBuoyancy);
			return;
		}
		
		force.y = liquidDensity * volume * (depth - maxDepth - waterHeight) / 2 * maxDepth;
		body.AddForceAtBodyPoint(force, centerOfBuoyancy);
		
	}

}
