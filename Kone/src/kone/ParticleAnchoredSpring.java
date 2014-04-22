package kone;

import kone.core.Particle;
import kone.core.Vector3f;

public class ParticleAnchoredSpring implements ParticleForceGenerator {
	
	Vector3f anchor;
	
	float springConstant;
	
	float restLength;
	
	public ParticleAnchoredSpring(Vector3f anchor, float springConstant, float restLength)
	{
		this.anchor = anchor;
		this.springConstant = springConstant;
		this.restLength = restLength;
		
	}
	
	@Override
	public void UpdateForce(Particle particle, float duration)
	{
		Vector3f force = particle.getPosition();
		
		force.Substract(anchor);
		
		float magnitude = force.Magnitude();
		magnitude = Math.abs(magnitude - restLength);
		magnitude *= springConstant;
		
		force.Normalise();
		force.Multiply(-magnitude);
		
		particle.AddForce(force);
	}

}
