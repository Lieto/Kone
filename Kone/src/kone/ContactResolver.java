package kone;

import java.util.Vector;

import kone.core.Vector3d;

public class ContactResolver {
	
	protected int velocityIterations;
	protected int positionIterations;
	protected double velocityEpsilon;
	protected double positionEpsilon;

	public int velocityIterationsUsed;
	public int positionIterationsUsed;
	
	private boolean validSettings;
	
	public ContactResolver(int i) {
		setIterations(i, i);
	
	}
	
	public void setIterations(int velocityIterations, int positionIterations)
	{
		this.velocityIterations = velocityIterations;
		this.positionIterations = positionIterations;
		positionEpsilon = 0.01d;
		velocityEpsilon = 0.01d;
		
	}

	public void resolveContacts(Vector<Contact> contacts,
			double duration) {
		if (contacts.size() == 0) return;
		if (!isValid()) return;
		
		prepareContacts(contacts, duration);
		
		adjustPositions(contacts, duration);
		
		adjustVelocities(contacts, duration);
		
	}

	private void adjustVelocities(Vector<Contact> contacts,
			double duration) 
	{
		Vector3d[] velocityChange = new Vector3d[2];
		Vector3d[] rotationChange = new Vector3d[2];
		
		velocityChange[0] = new Vector3d();
		velocityChange[1] = new Vector3d();
		
		rotationChange[0] = new Vector3d();
		rotationChange[1] = new Vector3d();
		
		Vector3d deltaVel = new Vector3d();
		
		//velocityIterations = contacts.size();
		velocityIterations = 1;
		velocityIterationsUsed = 0;
		
		while (velocityIterationsUsed < velocityIterations)
		{
			double max = velocityEpsilon;
			int index = contacts.size();
			
			
			for (int i = 0; i < contacts.size(); i++)
			{
				if (contacts.get(i).desiredDeltaVelocity > max)
				{
					max = contacts.get(i).desiredDeltaVelocity;
					index = i;
				}
			}
			
			if (index == contacts.size()) break;
		
			contacts.get(index).matchAwakeState();
			
			contacts.get(index).applyVelocityChange(velocityChange, rotationChange);
			
			
			for (int i = 0; i < contacts.size(); i++)
			{
				for (int j = 0; j < 2; j++)
				{
					if (contacts.get(i).body[j] != null)
					{
						for (int k = 0; k < 2; k++)
						{
							if (contacts.get(i).body[j] == contacts.get(index).body[k])
							{
								deltaVel = new Vector3d(velocityChange[k]);
								deltaVel.Add(rotationChange[k].CrossProduct(contacts.get(i).relativeContactPosition[j]));
								

								double sign = 0.0d;
								
								if (j == 1) sign = -1.0d;
								else sign = 1.0d;
								
								Vector3d tmp = new Vector3d(contacts.get(i).contactToWorld.TransformTranspose(deltaVel));
								tmp.Multiply(sign);
								
								contacts.get(i).contactVelocity.Add(tmp);
								contacts.get(i).calculateDesiredDeltaVelocity(duration);
								
							}
						}
						
					}
				}
			}
			
			velocityIterationsUsed++;
		}
	}
		


	private void adjustPositions(Vector<Contact> contacts,
			double duration) 
	{
		
		int index;
		
		Vector3d[] linearChange = new Vector3d[2];
		Vector3d[] angularChange = new Vector3d[2];
		
		linearChange[0] = new Vector3d();
		linearChange[1] = new Vector3d();
		
		angularChange[0] = new Vector3d();
		angularChange[1] = new Vector3d();
		
		double max;
		
		Vector3d deltaPosition = new Vector3d();
		

		positionIterations = 1;
		positionIterationsUsed = 0;
		
		while (positionIterationsUsed < positionIterations)
		{
			max = positionEpsilon;
			index = contacts.size();
			
			
			for (int i = 0; i < contacts.size(); i++)
			{
				if (contacts.get(i).penetration > max)
				{
					max = contacts.get(i).penetration;
					index = i;
				}
			}
			
	
			if (index == contacts.size()) break;
			
			contacts.get(index).matchAwakeState();
			contacts.get(index).applyPositionChange( linearChange, angularChange, max);
			
			
			
			for (int i = 0; i < contacts.size(); i++)
			{
				for (int b = 0; b < 2; b++)
				{
					if (contacts.get(i).body[b] != null)
					{
						for (int d = 0; d < 2; d++)
						{
							if (contacts.get(i).body[b] == contacts.get(index).body[d])
							{
								deltaPosition = new Vector3d(linearChange[d]);
								deltaPosition.Add(angularChange[d].CrossProduct(contacts.get(i).relativeContactPosition[b]));
								
								if (b == 0)
								{
									contacts.get(i).penetration -= deltaPosition.ScalarProduct(contacts.get(i).contactNormal);
								}
								else
								{
									contacts.get(i).penetration += deltaPosition.ScalarProduct(contacts.get(i).contactNormal);
								}
							}
						}
					}
				}
			}
				
			positionIterationsUsed++;
				
				
		}
		
		
	}

	private void prepareContacts(Vector<Contact> contacts,
			double duration) {
		
		
		for (int i = 0; i < contacts.size(); i++)
		{
			contacts.get(i).calculateInternals(duration);
		}
		
	}

	private boolean isValid() {
		
		return (velocityIterations > 0) &&
				(positionIterations > 0) &&
				(positionEpsilon >= 0.0d) &&
				(velocityEpsilon >= 0.0d);
	}

}
