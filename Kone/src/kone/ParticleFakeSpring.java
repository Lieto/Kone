package kone;

import kone.core.Particle;
import kone.core.Vector3f;
import kone.core.Vector3d;

public class ParticleFakeSpring implements ParticleForceGenerator {
	
	Vector3f anchor;
	
	float springConstant;
	
	float damping;
	
	public ParticleFakeSpring(Vector3f anchor, float springConstant, 
			float damping)
	{
		this.anchor = anchor;
		this.springConstant = springConstant;
		this.damping = damping;
		
	}

	@Override
	public void UpdateForce(Particle particle, float duration) {
		
		if (!particle.hasFiniteMass()) return;
		
		Vector3f position = particle.getPosition();
		position.Substract(anchor);
		
		double gamma = 0.5 * Math.sqrt((double) (4*springConstant-damping*damping));
		
		if (gamma == 0.0) return;
		
		Vector3d c = new Vector3d(position.x, position.y, position.z);
		c.Multiply(damping/ (2.0 * gamma));
		Vector3d v = new Vector3d(particle.getVelocity().x, particle.getVelocity().y, particle.getVelocity().z);
		v.Multiply(1.0 / gamma);
		c.Add(v);
		
		Vector3d target = new Vector3d(position.x, position.y, position.z);
		target.Multiply((float) Math.cos((double) (gamma*duration))); 
		c.Multiply((float) Math.sin((double) (gamma*duration)));
		target.Add(c);
		
		target.Multiply(Math.exp(-0.5*duration*damping));
		
		Vector3f accel = new Vector3f((float) target.x, (float) target.y, (float) target.z);
		accel.Substract(position);
		accel.Multiply(1.0f/duration*duration);
		
		Vector3f v2 = new Vector3f(particle.getVelocity());
		v2.Multiply(duration);
		
		accel.Substract(v2);
		accel.Multiply(particle.getMass());
		
		particle.AddForce(accel);
		

	}

}
