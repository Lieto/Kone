package kone;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import kone.core.Particle;
import kone.core.Vector3f;

public class ParticleSpringTests {
	
	@Test
	public void ApplieSpringForceOnParticle()
	{
	
		Particle startParticle = new Particle();
		startParticle.position = new Vector3f(0.0f, 0.0f, 0.0f);
		
		Particle endParticle = new Particle();
		endParticle.position = new Vector3f(2.0f, 0.0f, 0.0f);
		
		float k = 0.5f;
		float restLength = 1.0f;
		
		ParticleSpring spring = new ParticleSpring(endParticle, k, restLength);
		
		float duration = 0.01f;
		
		spring.UpdateForce(startParticle, duration);
		
		assertEquals(startParticle.forceAccum.x, 0.5f, 0.001f);
	}

}
