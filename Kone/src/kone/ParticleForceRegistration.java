package kone;

import kone.core.Particle;

public class ParticleForceRegistration {
	
	public Particle particle;
	public ParticleForceGenerator fg;
	
	public ParticleForceRegistration()
	{
		particle = new Particle();
		fg = null;
	}
	
	public ParticleForceRegistration(Particle particle, 
			ParticleForceGenerator fg)
	{
		this.particle = particle;
		this.fg = fg;
	}

}
