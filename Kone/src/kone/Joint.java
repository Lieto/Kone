package kone;

import java.util.Vector;

import kone.core.Vector3d;

public class Joint {
	
	public Body[] body;
	
	public Vector3d[] position;
	
	public double error;

	public Joint()
	{
		body = new Body[2];
		position = new Vector3d[2];
	}
	
	public void set(Body a, Vector3d aPos, Body b,
			Vector3d bPos, float error) {
		
		body[0] = a;
		body[1] = b;
		
		position[0] = aPos;
		position[1] = bPos;
		
		this.error = error;
		
	
		
	}
	
	public int addContact(Vector<Contact> contacts)
	{
		Vector3d aPosWorld = new Vector3d(body[0].GetPointInWorldSpace(position[0]));
		Vector3d bPosWorld = new Vector3d(body[1].GetPointInWorldSpace(position[1]));
		
		Vector3d aToB = new Vector3d(bPosWorld);
		aToB.Substract(aPosWorld);
		
		Vector3d normal = new Vector3d(aToB);
		normal.Normalise();
		double length = aToB.Magnitude();
		
		if (Math.abs(length) > error)
		{
			Contact contact = new Contact();
			contact.body[0] = body[0];
			contact.body[1] = body[1];
			contact.contactNormal = normal;
			
			Vector3d cP = new Vector3d(aPosWorld);
			cP.Add(bPosWorld);
			cP.Multiply(0.5d);
			
			contact.contactPoint = new Vector3d(cP);
			contact.penetration = length - error;
			contact.friction = 1.0d;
			contact.restitution = 0.0d;
			
			contacts.add(contact);
			
			return 1;
		}
		
		return 0;
		
	}

}
