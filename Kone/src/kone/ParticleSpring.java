package kone;

import kone.core.Particle;
import kone.core.Vector3f;

public class ParticleSpring implements ParticleForceGenerator {

	Particle other;
	
	float springConstant;
	
	float restLength;
	
	public ParticleSpring(Particle particle1, float k, float restLength) {
		other = particle1;
		springConstant = k;
		this.restLength = restLength;
		
	}

	@Override
	public void UpdateForce(Particle particle1, float duration) {
	
		Vector3f force = particle1.getPosition();
		force.Substract(other.getPosition());
		
		float magnitude = force.Magnitude();
		magnitude = Math.abs(magnitude - restLength);
		magnitude *= springConstant;
		
		force.Normalise();
		force.Multiply(-magnitude);
		
		particle1.AddForce(force);
	}

}
