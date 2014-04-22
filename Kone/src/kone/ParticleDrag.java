package kone;

import kone.core.Particle;
import kone.core.Vector3f;

public class ParticleDrag implements ParticleForceGenerator 
{
	public float k1;
	public float k2;
	
	public ParticleDrag(float k1, float k2)
	{
		this.k1 = k1;
		this.k2 = k2;
	}
	
	public void UpdateForce(Particle particle, float duration)
	{
		Vector3f force;
		force = particle.getVelocity();
		
		float dragCoeff = force.Magnitude();
		dragCoeff = k1*dragCoeff + k2*dragCoeff;
		
		force.Normalise();
		force.Multiply(-dragCoeff);
		particle.AddForce(force);
	}

}
