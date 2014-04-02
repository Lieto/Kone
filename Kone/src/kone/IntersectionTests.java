package kone;

import kone.core.Vector3d;


public class IntersectionTests {
	
	public static boolean overlapOnAxis(CollisionBox one, CollisionBox two,
			Vector3d axis, Vector3d toCenter)
	{
		double oneProject = CollisionDetector.transformToAxis(one, axis);
		double twoProject = CollisionDetector.transformToAxis(two, axis);
		
		double distance = Math.abs(toCenter.ScalarProduct(axis));
		
		return (distance < oneProject+twoProject);
	}
	
	public static boolean boxAndBox(CollisionBox one, CollisionBox two)
	{
		Vector3d toCenter = new Vector3d(two.getAxis(3));
		toCenter.Substract(one.getAxis(3));
		
		return (
		overlapOnAxis(one, two, one.getAxis(0), toCenter) &&
		overlapOnAxis(one, two, one.getAxis(1), toCenter) &&
		overlapOnAxis(one, two, one.getAxis(2), toCenter) &&
		
		overlapOnAxis(one, two, two.getAxis(0), toCenter) &&
		overlapOnAxis(one, two, two.getAxis(1), toCenter) &&
		overlapOnAxis(one, two, two.getAxis(2), toCenter) &&
		
		overlapOnAxis(one, two, one.getAxis(0).CrossProduct(two.getAxis(0)), toCenter) &&
		overlapOnAxis(one, two, one.getAxis(0).CrossProduct(two.getAxis(1)), toCenter) &&
		overlapOnAxis(one, two, one.getAxis(0).CrossProduct(two.getAxis(2)), toCenter) &&
		overlapOnAxis(one, two, one.getAxis(1).CrossProduct(two.getAxis(0)), toCenter) &&
		overlapOnAxis(one, two, one.getAxis(1).CrossProduct(two.getAxis(1)), toCenter) &&
		overlapOnAxis(one, two, one.getAxis(1).CrossProduct(two.getAxis(2)), toCenter) &&
		overlapOnAxis(one, two, one.getAxis(2).CrossProduct(two.getAxis(0)), toCenter) &&
		overlapOnAxis(one, two, one.getAxis(2).CrossProduct(two.getAxis(1)), toCenter) &&
		overlapOnAxis(one, two, one.getAxis(2).CrossProduct(two.getAxis(2)), toCenter));
		
		
	}
	
	public static boolean boxAndHalfSpace(CollisionBox box, CollisionPlane plane) {
		double projectedRadius = transformToAxis(box, plane.direction);
		
		double boxDistance = plane.direction.ScalarProduct(box.getAxis(3)) - 
				projectedRadius;
		
		return boxDistance <= plane.offset;
		
	}

	private static double transformToAxis(CollisionBox box, Vector3d direction) {
		
		return box.halfSize.x * Math.abs(direction.ScalarProduct(box.getAxis(0))) +
				box.halfSize.y * Math.abs(direction.ScalarProduct(box.getAxis(1))) + 
				box.halfSize.z * Math.abs(direction.ScalarProduct(box.getAxis(2)));
	}

}
