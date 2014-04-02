package kone;

import kone.core.Vector3d;

public class Spring implements ForceGenerator {
	
	Vector3d connectionPoint;
	
	Vector3d otherConnectionPoint;
	
	Body other;
	
	double springConstant;
	
	double restLength;
	
	public Spring(Vector3d localConnectionPt, Body other,
			Vector3d otherConnectionPt,
			double springConstant,
			double restLength)
	{
		connectionPoint = localConnectionPt;
		this.other = other;
		otherConnectionPoint = otherConnectionPt;
		this.springConstant = springConstant;
		this.restLength = restLength;
		
	}

	@Override
	public void UpdateForce(Body body, double rotation) {
		
		Vector3d lws = new Vector3d(body.GetPointInWorldSpace(connectionPoint));
		Vector3d ows = new Vector3d(other.GetPointInWorldSpace(otherConnectionPoint));
		
		Vector3d force = new Vector3d(lws);
		force.Substract(ows);
		
		double magnitude = force.Magnitude();
		magnitude = Math.abs(magnitude-restLength);
		magnitude *= springConstant;
		
		force.Normalise();
		force.Multiply(magnitude);
		
		body.AddForceAtPoint(force, lws);
		
	}

}
