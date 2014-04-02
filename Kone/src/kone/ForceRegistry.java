package kone;

import java.util.Vector;

public class ForceRegistry {
	
	protected Vector<ForceRegistration> registrations;
	
	public ForceRegistry()
	{
		registrations = new Vector<ForceRegistration>();
	}
	
	public void Add(Body body, ForceGenerator fg)
	{
		ForceRegistration registration = new ForceRegistration();
		registration.body = body;
		registration.fg = fg;
		registrations.add(registration);
		
	}
	
	public void Remove(Body body, ForceGenerator fg)
	{
		
	}
	
	public void Clear()
	{
		registrations.clear();
	}
	
	public void UpdateForces(double duration)
	{
		for (int i = 0; i < registrations.size(); i++)
		{
			registrations.get(i).fg.UpdateForce(registrations.get(i).body, duration);
		}
		
	}

}
