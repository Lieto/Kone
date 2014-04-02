package kone;

public class CollisionSphere extends CollisionPrimitive {
	
	public double radius;
	
	public CollisionSphere()
	{
		body = new Body();
		radius = 0.0d;
	}
	
}
