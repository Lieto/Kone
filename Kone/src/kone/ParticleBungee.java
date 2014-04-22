package kone;

import kone.core.Particle;
import kone.core.Vector3f;

public class ParticleBungee implements ParticleForceGenerator {
	
	Particle other;
	
	float springConstant;
	
	float restLength;
	
	public ParticleBungee(Particle other, float springConstant, float restLength)
	{
		this.other = other;
		this.springConstant = springConstant;
		this.restLength = restLength;
	}

	@Override
	public void UpdateForce(Particle particle, float duration) {
		
		Vector3f force = particle.getPosition();
		force.Substract(other.getPosition());
		
		float magnitude = force.Magnitude();
		
		if (magnitude <= restLength) return;
		
		magnitude = springConstant * (restLength - magnitude);
		
		force.Normalise();
		force.Multiply(-magnitude);
		
		particle.AddForce(force);
		
	}

}
