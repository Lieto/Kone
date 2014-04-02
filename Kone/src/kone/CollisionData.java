package kone;

import java.util.Vector;

public class CollisionData {
	
	public int contactArrayIndex;
	
	public Vector<Contact> contacts;
	
	public double friction;
	
	public double restitution;
	
	public double tolerance;
	
	public CollisionData()
	{
		contacts = new Vector<Contact>();
		
	}
	
	public boolean hasMoreContacts()
	{
		return true;
	}
	
	public void reset()
	{
	
		contacts.clear();
	}
	
	public void addContacts(int count)
	{
	
	}

}
