package kone;

import java.util.List;
import java.util.Vector;

import kone.core.Matrix3d;
import kone.core.Matrixd;
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
	
	public void DoCollisionResponse(double t, double dt,
			List<Body> bodies, Vector<Contact> contacts)
	{
		Matrixd A = new Matrixd(contacts.size(), contacts.size());
		Vector<Double> preRelVel = new Vector<Double>();
		Vector<Double> postRelVel = new Vector<Double>();
		double impulseMag;
		Vector3d restingB, relAcc, restingMag;
		
		ComputeLCPMatrix(contacts, A);
		ComputePreImpulseVelocity(contacts, preRelVel);
		//Minimize(A, preRelVel, postRelVel, impulseMag);
	}

	private void Minimize(Matrixd a, Vector<Double> preRelVel,
			Vector3d postRelVel, Vector3d impulseMag) {
		// TODO Auto-generated method stub
		
	}

	private void ComputePreImpulseVelocity(Vector<Contact> contacts,
			Vector<Double> preRelVel) {
		
		for (int i = 0; i < contacts.size(); ++i)
		{
			Contact ci = contacts.get(i);
			Body A = ci.body[0];
			Body B = ci.body[1];
			
			Vector3d rAi = new Vector3d(ci.contactPoint);
			rAi.Substract(A.position);
			

			Vector3d rBi = new Vector3d(ci.contactPoint);
			rBi.Substract(B.position);
			
			Vector3d velA = new Vector3d(A.velocity);
			velA.Add(A.rotation.CrossProduct(rAi));
			

			Vector3d velB = new Vector3d(B.velocity);
			velB.Add(B.rotation.CrossProduct(rBi));
			
			Vector3d tmp = new Vector3d(velA);
			tmp.Substract(velB);
			
			preRelVel.add(ci.contactNormal.ScalarProduct(tmp));
		}
		
		
	}

	private void ComputeLCPMatrix(Vector<Contact> contacts, Matrixd A) {
		
		for (int i = 0; i < contacts.size(); ++i)
		{
			Contact ci = contacts.get(i);
			
			Vector3d tmp = new Vector3d(ci.contactPoint);
			tmp.Substract(ci.body[0].position);
			Vector3d rANi = tmp.CrossProduct(ci.contactNormal);
			
			tmp = new Vector3d(ci.contactPoint);
			tmp.Substract(ci.body[1].position);
			Vector3d rBNi = tmp.CrossProduct(ci.contactNormal);
			
			for (int j = 0; j < contacts.size(); ++j)
			{
				Contact cj = contacts.get(j);
				
				tmp = new Vector3d(cj.contactPoint);
				tmp.Substract(cj.body[0].position);
				Vector3d rANj = tmp.CrossProduct(cj.contactNormal);
				
				tmp = new Vector3d(cj.contactPoint);
				tmp.Substract(cj.body[1].position);
				Vector3d rBNj = tmp.CrossProduct(cj.contactNormal);
				
				double a = 0.0d;
				
				if (ci.body[0] == cj.body[0])
				{
					a += ci.body[0].inverseMass*(ci.contactNormal.ScalarProduct(cj.contactNormal));
					a += rANi.ScalarProduct(ci.body[0].inverseInertiaTensor.Transform(rANj));
				}
				else if (ci.body[0] == cj.body[1])
				{
					a -= ci.body[0].inverseMass*(ci.contactNormal.ScalarProduct(cj.contactNormal));
					a -= rANi.ScalarProduct(ci.body[0].inverseInertiaTensor.Transform(rANj));
					
				}
				
				if (ci.body[1] == cj.body[0])
				{
					a -= ci.body[1].inverseMass*(ci.contactNormal.ScalarProduct(cj.contactNormal));
					a -= rBNi.ScalarProduct(ci.body[1].inverseInertiaTensor.Transform(rBNj));
					
				}
				else if (ci.body[1] == cj.body[1])
				{
					a += ci.body[1].inverseMass*(ci.contactNormal.ScalarProduct(cj.contactNormal));
					a += rBNi.ScalarProduct(ci.body[1].inverseInertiaTensor.Transform(rBNj));
					
				}
				
				A.entry[i][j] = a;
				
			}
			
		}
		
		
	}

}
