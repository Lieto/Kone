package kone;

import kone.core.Vector3d;

public class BoundingBox {
	
	Vector3d center;
	Vector3d halfSize;
	
	public BoundingBox()
	{
		center = new Vector3d();
		halfSize = new Vector3d();
	}

}
