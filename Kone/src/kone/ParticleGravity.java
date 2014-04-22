package kone;

import kone.core.Particle;
import kone.core.Vector3f;

public class ParticleGravity implements ParticleForceGenerator
{
	public Vector3f gravity;
	
	public ParticleGravity(Vector3f gravity)
	{
		this.gravity = gravity;
	}
	
	public void UpdateForce(Particle particle, float duration)
	{
		if (particle.hasFiniteMass()) return;
		
		particle.AddForce(gravity.NewVectorMultiply(particle.getMass()));
	}

}
