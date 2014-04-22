package kone;

import java.util.List;
import java.util.Vector;

import kone.core.Particle;

public class ParticleForceRegistry {
	
	protected List<ParticleForceRegistration> registrations;
	
	public ParticleForceRegistry()
	{
		registrations = new Vector<ParticleForceRegistration>();
	}
	
	public void Add(Particle particle, ParticleForceGenerator fg)
	{
		ParticleForceRegistration reg = new ParticleForceRegistration(particle, fg);
		registrations.add(reg);
	}
	
	public void Remove(Particle particle, ParticleForceGenerator fg)
	{
		for (int i = 0; i < registrations.size(); i++)
		{
			if ((registrations.get(i).particle == particle) && (registrations.get(i).fg == fg))
			{
				registrations.remove(i);
			}
		}
		
		
	}
	
	public void Clear()
	{
		registrations.removeAll(registrations);
		
	}
	
	public void UpdateForces(float duration)
	{
		for (int i = 0; i < registrations.size(); i++)
		{
			ParticleForceRegistration reg = registrations.get(i);
			reg.fg.UpdateForce(reg.particle, duration);
		}
		
	}

}
