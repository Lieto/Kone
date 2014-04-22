package kone;

import kone.core.Particle;
import kone.core.Vector3f;
import junit.framework.TestCase;

public class ParticleForceRegistrationTests extends TestCase {
	
	public void TestRegistrationCreation()
	{
		Particle particle1 = new Particle();
		ParticleForceGenerator fg1 = new ParticleGravity(Vector3f.GRAVITY);
		ParticleForceRegistration reg1 = new ParticleForceRegistration(particle1, fg1);
		
		
	}

}
