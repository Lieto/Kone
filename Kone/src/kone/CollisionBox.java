package kone;

import kone.core.Vector3d;

public class CollisionBox extends CollisionPrimitive {

	public Vector3d halfSize;
	
	public CollisionBox()
	{
		body = new Body();
		halfSize = new Vector3d();
	}
}
