package kone;

import java.util.Vector;

public class World {
	
	Vector<Body> bodies;
	
	public World()
	{
		bodies = new Vector<Body>();
	}
	
	public void StartFrame()
	{
		for (int i = 0; i < bodies.size(); i++)
		{
			bodies.get(i).ClearAccumulators();
			bodies.get(i).CalculateDerivedData();
			
		}
		
	}
	
	public void RunPhysics(double duration)
	{
		for (int i = 0; i < bodies.size(); i++)
		{
			bodies.get(i).Integrate(duration, IntegrationMethod.EULER);
		}
	}

}
