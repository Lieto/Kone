package kone;

import kone.core.Particle;

public interface ParticleForceGenerator {
	
	void UpdateForce(Particle particle, float duration);

}
