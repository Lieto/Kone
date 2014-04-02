package kone;

import kone.core.Matrix4d;
import kone.core.Vector3d;

public class CollisionPrimitive {
	
	public Body body;
	
	public Matrix4d offset;
	
	protected Matrix4d transform;
	
	public void calculateInternals()
	{
		transform = new Matrix4d();
		transform = body.getTransform();
		//offset = new Matrix4d();
		//transform.Multiply(offset);
		
	}
	
	public Vector3d getAxis(int index)
	{
		return transform.GetAxisVector(index);
	}
	
	public Matrix4d getTransform()
	{
		return transform;
		
	}

}
