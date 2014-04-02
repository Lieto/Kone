package kone;

import kone.core.Vector3d;

public class BoundingSphere {
	
	Vector3d center;
	double radius;
	
	public BoundingSphere()
	{
		center = new Vector3d();
		radius = 0.0d;
	}
	
	public BoundingSphere(Vector3d center, double radius)
	{
		this.center = center;
		this.radius = radius;
	}
	
	public BoundingSphere(BoundingSphere one, BoundingSphere two)
	{
		
	}
	
	public boolean overlaps(BoundingSphere other)
	{
		Vector3d d = new Vector3d(center);
		d.Substract(other.center);
		double distanceSquared = d.SquareMagnitude();
		
		
		return distanceSquared < (radius+other.radius)*(radius+other.radius);
		
	}
	

}
