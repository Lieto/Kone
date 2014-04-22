package kone;

import kone.core.Particle;
import kone.core.Vector3f;

public class ParticleBuoyancy implements ParticleForceGenerator {
	
	float maxDepth;
	
	float volume;
	
	float waterHeight;
	
	float liquidDensity;
	
	public ParticleBuoyancy(float maxDepth, float volume, float waterHeight,
			float liquidDensity)
	{
		this.maxDepth = maxDepth;
		this.volume = volume;
		this.waterHeight = waterHeight;
		this.liquidDensity = liquidDensity;
	}

	@Override
	public void UpdateForce(Particle particle, float duration) {
		
		float depth = particle.getPosition().y;
		
		if (depth >= waterHeight + maxDepth) return;
		
		Vector3f force = new Vector3f();
		
		if (depth <= waterHeight - maxDepth)
		{
			force.y = liquidDensity * volume;
			particle.AddForce(force);
			return;
		}
		
		force.y = liquidDensity * volume * 
				(depth - maxDepth - waterHeight) / 2 * maxDepth;
		
		particle.AddForce(force);

	}

}
