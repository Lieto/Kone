package kone;

import java.util.Vector;


import kone.core.Matrix4d;
import kone.core.Vector3d;


public class CollisionDetector {
	
	public double smallestPenetration;
	public int smallestCase;
	
	public static double transformToAxis(CollisionBox box, Vector3d axis)
	{
		return box.halfSize.x * Math.abs(axis.ScalarProduct(box.getAxis(0))) +
				box.halfSize.y * Math.abs(axis.ScalarProduct(box.getAxis(1))) + 
				box.halfSize.z * Math.abs(axis.ScalarProduct(box.getAxis(2)));
				
	}
	
	private static double penetrationOnAxis(CollisionBox one, CollisionBox two,
			Vector3d axis, Vector3d toCenter)
	{
		double oneProject = transformToAxis(one, axis);
		double twoProject = transformToAxis(two, axis);
		
		double distance = Math.abs(toCenter.ScalarProduct(axis));
		
		return oneProject + twoProject - distance;
	}
	
	private boolean tryAxis(CollisionBox one, CollisionBox two, Vector3d axis, Vector3d toCenter, int index)
	{
		double penetration = penetrationOnAxis(one, two, axis, toCenter);
		
		if (penetration < 0.0d) return false;
		if (penetration < smallestPenetration)
		{
			smallestPenetration = penetration;
			smallestCase = index;
		}
		
		return true;
	}
	
	/*
	private boolean CheckOverlap(Vector3d axis, int index)
	{
		if (!tryAxis(one, two, axis, toCenter, index, pen, best)) return 0;
	}	
	*/
	public int boxAndBox(CollisionBox one, CollisionBox two, CollisionData data)
	{
		Vector3d toCenter = new Vector3d(two.getAxis(3));
		toCenter.Substract(one.getAxis(3));
		
		smallestPenetration = Double.MAX_VALUE;
		smallestCase = 0xffffff;
		//int best = 0;
		
		tryAxis(one, two, one.getAxis(0), toCenter, 0);
		tryAxis(one, two, one.getAxis(1), toCenter, 1);
		tryAxis(one, two, one.getAxis(2), toCenter, 2);
		tryAxis(one, two, one.getAxis(0), toCenter, 3);
		tryAxis(one, two, one.getAxis(1), toCenter, 4);
		tryAxis(one, two, one.getAxis(2), toCenter, 5);
		
		int bestSingleAxis = smallestCase;
		
		tryAxis(one, two, one.getAxis(0).CrossProduct(two.getAxis(0)), toCenter, 6);
		tryAxis(one, two, one.getAxis(0).CrossProduct(two.getAxis(1)), toCenter, 7);
		tryAxis(one, two, one.getAxis(0).CrossProduct(two.getAxis(3)), toCenter, 8);
		tryAxis(one, two, one.getAxis(1).CrossProduct(two.getAxis(0)), toCenter, 9);
		tryAxis(one, two, one.getAxis(1).CrossProduct(two.getAxis(1)), toCenter, 10);
		tryAxis(one, two, one.getAxis(1).CrossProduct(two.getAxis(2)), toCenter, 11);
		tryAxis(one, two, one.getAxis(2).CrossProduct(two.getAxis(0)), toCenter, 12);
		tryAxis(one, two, one.getAxis(2).CrossProduct(two.getAxis(1)), toCenter, 13);
		tryAxis(one, two, one.getAxis(2).CrossProduct(two.getAxis(2)), toCenter, 14);
		
		if (smallestCase != 0xffffff)
		{
			if (smallestCase < 3)
			{
				fillPointFaceBoxBox(one, two, toCenter, data, smallestCase, smallestPenetration);
				data.addContacts(1);
				data.contactArrayIndex++;
				return 1;
			}
			else if (smallestCase < 6)
			{
				fillPointFaceBoxBox(two, one, toCenter.NewVectorMultiply(-1.0d), data, smallestCase-3, smallestPenetration);
				data.addContacts(1);
				data.contactArrayIndex++;
				return 1;
			}
			else
			{
				smallestCase -= 6;
				int oneAxisIndex = (int) (smallestCase / 3);
				int twoAxisIndex = smallestCase % 3;
				
				Vector3d oneAxis = new Vector3d(one.getAxis(oneAxisIndex));
				Vector3d twoAxis = new Vector3d(two.getAxis(twoAxisIndex));
				Vector3d axis = oneAxis.CrossProduct(twoAxis);
				axis.Normalise();
				
				if (axis.ScalarProduct(toCenter) > 0.0d) axis.Multiply(-1.0d);
				
				Vector3d ptOnOneEdge = new Vector3d(one.halfSize);
				Vector3d ptOnTwoEdge = new Vector3d(two.halfSize);
				
				
				if (0 == oneAxisIndex) ptOnOneEdge.x = 0;
				else if (one.getAxis(0).ScalarProduct(axis) > 0.0d) ptOnOneEdge.x = -ptOnOneEdge.x;
					
				if (0 == twoAxisIndex) ptOnOneEdge.x = 0;
				else if (two.getAxis(0).ScalarProduct(axis) < 0.0d) ptOnOneEdge.x = -ptOnOneEdge.x;
				
				if (1 == oneAxisIndex) ptOnOneEdge.y = 0;
				else if (one.getAxis(1).ScalarProduct(axis) > 0.0d) ptOnOneEdge.y = -ptOnOneEdge.y;
					
				if (1 == twoAxisIndex) ptOnOneEdge.y = 0;
				else if (two.getAxis(1).ScalarProduct(axis) < 0.0d) ptOnOneEdge.y = -ptOnOneEdge.y;
				
				if (2 == oneAxisIndex) ptOnOneEdge.z = 0;
				else if (one.getAxis(2).ScalarProduct(axis) > 0.0d) ptOnOneEdge.z = -ptOnOneEdge.z;
					
				if (2 == twoAxisIndex) ptOnOneEdge.z = 0;
				else if (two.getAxis(2).ScalarProduct(axis) < 0.0d) ptOnOneEdge.z = -ptOnOneEdge.z;
				
				Matrix4d tmp = new Matrix4d(one.transform);
				ptOnOneEdge = tmp.Multiply(ptOnOneEdge);
					

				tmp = new Matrix4d(two.transform);
				ptOnTwoEdge = tmp.Multiply(ptOnTwoEdge);
				
				Vector3d vertex = new Vector3d();
				
				if (oneAxisIndex == 0 && twoAxisIndex == 0)
				{
					vertex = new Vector3d(contactPoint(
						ptOnOneEdge, oneAxis, one.halfSize.x,
						ptOnTwoEdge, twoAxis, two.halfSize.x,
						bestSingleAxis > 2));
				}
				else if (oneAxisIndex == 0 && twoAxisIndex == 1)
				{
					vertex = new Vector3d(contactPoint(
						ptOnOneEdge, oneAxis, one.halfSize.x,
						ptOnTwoEdge, twoAxis, two.halfSize.y,
						bestSingleAxis > 2));
				}
				else if (oneAxisIndex == 0 && twoAxisIndex == 2)
				{
					vertex = new Vector3d(contactPoint(
						ptOnOneEdge, oneAxis, one.halfSize.x,
						ptOnTwoEdge, twoAxis, two.halfSize.z,
						bestSingleAxis > 2));
				}
				else if (oneAxisIndex == 1 && twoAxisIndex == 0)
				{
					vertex = new Vector3d(contactPoint(
						ptOnOneEdge, oneAxis, one.halfSize.y,
						ptOnTwoEdge, twoAxis, two.halfSize.x,
						bestSingleAxis > 2));
				}
				else if (oneAxisIndex == 1 && twoAxisIndex == 1)
				{
					vertex = new Vector3d(contactPoint(
						ptOnOneEdge, oneAxis, one.halfSize.y,
						ptOnTwoEdge, twoAxis, two.halfSize.y,
						bestSingleAxis > 2));
				}
				else if (oneAxisIndex == 1 && twoAxisIndex == 2)
				{
					vertex = new Vector3d(contactPoint(
						ptOnOneEdge, oneAxis, one.halfSize.y,
						ptOnTwoEdge, twoAxis, two.halfSize.z,
						bestSingleAxis > 2));
				}
				else if (oneAxisIndex == 2 && twoAxisIndex == 0)
				{
					vertex = new Vector3d(contactPoint(
						ptOnOneEdge, oneAxis, one.halfSize.z,
						ptOnTwoEdge, twoAxis, two.halfSize.x,
						bestSingleAxis > 2));
				}
				else if (oneAxisIndex == 2 && twoAxisIndex == 1)
				{
					vertex = new Vector3d(contactPoint(
						ptOnOneEdge, oneAxis, one.halfSize.z,
						ptOnTwoEdge, twoAxis, two.halfSize.y,
						bestSingleAxis > 2));
				}
				else if (oneAxisIndex == 2 && twoAxisIndex == 2)
				{
					vertex = new Vector3d(contactPoint(
						ptOnOneEdge, oneAxis, one.halfSize.z,
						ptOnTwoEdge, twoAxis, two.halfSize.z,
						bestSingleAxis > 2));
				}
				
				Contact contact = new Contact();
				contact.penetration = smallestPenetration;
				contact.contactNormal = axis;
				contact.contactPoint = vertex;
				contact.setBodyData(one.body, two.body, data.friction, data.restitution);
				
				data.contacts.add(contact);
				data.addContacts(1);
				data.contactArrayIndex++;
				
				return 1;
				
			}
			
		
			
		}
		
		return 0;
	
	}
	public int sphereAndSphere(CollisionSphere one, CollisionSphere two, CollisionData data)
	{
		
		Vector3d positionOne = new Vector3d(one.getAxis(3));
		Vector3d positionTwo = new Vector3d(two.getAxis(3));
		
		Vector3d midline = new Vector3d(positionOne);
		midline.Substract(positionTwo);
		double size = midline.Magnitude();
			
		if (size <= 0.0d || size >= one.radius + two.radius)
		{
			return 0;
		}
		
		Vector3d normal = new Vector3d(midline);
		normal.Multiply(1.0d/size);
		
		Contact contact = new Contact();
		contact.contactNormal = normal;
		contact.contactPoint = new Vector3d(positionOne);
		contact.contactPoint.Add(midline.NewVectorMultiply(0.5d));
		contact.penetration = one.radius + two.radius - size;
		
		contact.body[0] = one.body;
		contact.body[1] = two.body;
		contact.restitution = data.restitution;
		contact.friction = data.friction;
		
		data.contacts.add(contact);
		data.contactArrayIndex++;
		data.addContacts(1);
		
		return 1;
		
	}
	
	public int boxAndHalfSpace(CollisionBox box, CollisionPlane plane,
			CollisionData data)
	{
		Vector<Integer> cVerts = new Vector<Integer>();
		
	
		box.calculateInternals();
		
		if (!IntersectionTests.boxAndHalfSpace(box, plane))
		{
			return 0;
		}
		
		double[][] mults = {{1.0d,1.0d,1.0d},{-1.0d,1.0d,1.0d},{1.0d,-1.0d,1.0d},{-1.0d,-1.0d,1.0d},
                {1.0d,1.0d,-1.0d},{-1.0d,1.0d,-1.0d},{1.0d,-1.0d,-1.0d},{-1.0d,-1.0d,-1.0d}};
		
		int contactsUsed = 0;
		
		Vector3d[] vPos = new Vector3d[8];
		
		for (int i = 0; i < 8; i++)
		{
			vPos[i] = new Vector3d(mults[i][0], mults[i][1], mults[i][2]);
			vPos[i].ComponentProductUpdate(box.halfSize);
			vPos[i] = box.transform.Transform(vPos[i]);
			
			double vertexDistance = vPos[i].ScalarProduct(plane.direction);
			
			
			if (vertexDistance <= plane.offset + data.tolerance
					) 
				{
				contactsUsed++;
				cVerts.add(i);
				}
			
		}
		
		if (contactsUsed == 4)
		{
			Contact contact = new Contact();
			
			Vector3d sum = new Vector3d();
			
			for (int i = 0; i < 4; i++)
			{
				sum.Add(vPos[cVerts.get(i)]);
			}
			sum.Multiply(1.0d/4.0d);
			
			double vertexDistance = sum.ScalarProduct(plane.direction);
			
			contact.contactPoint = new Vector3d(sum);
			contact.contactNormal = plane.direction;
			contact.penetration = plane.offset - vertexDistance;
			contact.setBodyData(box.body, null, data.friction, data.restitution);
			
			data.contacts.add(contact);
			data.contactArrayIndex++;
			contactsUsed -= 3;;
			
		}
		else
		{
					
		for (int i = 0; i < 8; i++)
		{
			Vector3d vertexPos = new Vector3d(mults[i][0], mults[i][1], mults[i][2]);
			vertexPos.ComponentProductUpdate(box.halfSize);
			vertexPos = box.transform.Transform(vertexPos);
			
			double vertexDistance = vertexPos.ScalarProduct(plane.direction);
			
			
			if (vertexDistance <= plane.offset + data.tolerance
					)
			{
				Contact contact = new Contact();
				contact.contactPoint = new Vector3d(plane.direction);
				//contact.contactPoint.Multiply((vertexDistance-plane.offset)/2.0d);
				contact.contactPoint.Multiply((vertexDistance-plane.offset));
				//contact.contactPoint.Add(vertexPos);
				contact.contactPoint = new Vector3d(vertexPos);
				contact.contactNormal = new Vector3d(plane.direction);
				contact.penetration = plane.offset - vertexDistance;
				
				contact.setBodyData(box.body, null, data.friction, data.restitution);
				
				data.contacts.add(contact);
				data.contactArrayIndex++;
				contactsUsed++;
				
			}
			
		}
		}
		
		
		data.addContacts(contactsUsed);
		
		return contactsUsed;
		
		
	}

	public int sphereAndHalfSpace(CollisionSphere sphere, CollisionPlane plane,
			CollisionData data) {
		
		
		Vector3d position = new Vector3d(sphere.getAxis(3));
		
		double ballDistance = plane.direction.ScalarProduct(position)- sphere.radius-plane.offset;
		
		if (ballDistance >= 0.0d) return 0;
		
		Contact contact = new Contact();
		contact.contactNormal = plane.direction;
		contact.penetration = -ballDistance;
		
		Vector3d contactP = new Vector3d(position);
		contactP.Substract(plane.direction.NewVectorMultiply(ballDistance +  sphere.radius));
		contact.contactPoint = new Vector3d(contactP);
		contact.body[0] = sphere.body;
		contact.body[1] = null;
		contact.restitution = data.restitution;
		contact.friction = data.friction;
		
		data.contacts.add(contact);
		data.contactArrayIndex++;
		data.addContacts(1);
		
		return 1;
		
		
	}

	public int sphereAndTruePlane(CollisionSphere sphere, CollisionPlane plane,
			CollisionData data) {
		
	
		
		Vector3d position = sphere.getAxis(3);
		
		double centerDistance = plane.direction.ScalarProduct(position) - plane.offset;
		
		if (centerDistance*centerDistance > sphere.radius*sphere.radius)
		{
			return 0;
		}
		
		Vector3d normal = new Vector3d(plane.direction);
		double penetration = -centerDistance;
		
		if (centerDistance < 0)
		{
			normal.Multiply(-1.0d);
			penetration = -penetration;
		}
		
		penetration += sphere.radius;
		
		Contact contact = new Contact();
		contact.contactNormal = normal;
		contact.penetration = penetration;
		
		Vector3d cP = new Vector3d(position);
		cP.Substract(plane.direction.NewVectorMultiply(centerDistance));
		contact.contactPoint = new Vector3d(cP);
		
		contact.body[0] = sphere.body;
		contact.body[1] = null;
		contact.restitution = data.restitution;
		contact.friction = data.friction;
		
		data.contacts.add(contact);
		data.contactArrayIndex++;
		data.addContacts(1);
		
		return 1;
	}

	public int boxAndSphere(CollisionBox box, CollisionSphere sphere,
			CollisionData data) {
		
		Vector3d center = new Vector3d(sphere.getAxis(3));
		Vector3d relCenter = new Vector3d(box.transform.TransformInverse(center));
		
		if (Math.abs(relCenter.x) - sphere.radius > box.halfSize.x ||
				Math.abs(relCenter.y) - sphere.radius > box.halfSize.y ||
				Math.abs(relCenter.z) - sphere.radius > box.halfSize.z)
		{
			return 0;
		}
		
		Vector3d closestPt = new Vector3d();
		double dist;
		
		dist = relCenter.x;
		if (dist > box.halfSize.x) dist = box.halfSize.x;
		if (dist < -box.halfSize.x) dist = -box.halfSize.x;
		closestPt.x = dist;
		
		dist = relCenter.y;
		if (dist > box.halfSize.y) dist = box.halfSize.y;
		if (dist < -box.halfSize.y) dist = -box.halfSize.y;
		closestPt.y = dist;
		
		dist = relCenter.z;
		if (dist > box.halfSize.z) dist = box.halfSize.z;
		if (dist < -box.halfSize.z) dist = -box.halfSize.z;
		closestPt.z = dist;
		
		Vector3d diff = new Vector3d(closestPt);
		diff.Substract(relCenter);
		
		dist = diff.SquareMagnitude();
		
		if (dist > sphere.radius * sphere.radius) return 0;
		
		Vector3d closestPtWorld = box.transform.Transform(closestPt);
		
		Contact contact = new Contact();
		
		Vector3d c = new Vector3d(closestPtWorld);
		c.Substract(center);
		c.Normalise();
		contact.contactNormal = new Vector3d(c);
		contact.contactPoint = closestPtWorld;
		contact.penetration = sphere.radius -Math.sqrt(dist);
		contact.setBodyData(box.body, sphere.body, data.friction, data.restitution);
		
		data.contacts.add(contact);
		
		data.contactArrayIndex++;
		data.addContacts(1);
		
		return 1;
	}
	
	public void fillPointFaceBoxBox(CollisionBox one, CollisionBox two,
			Vector3d toCenter, CollisionData data,
			int best, double pen)
	{
		Contact contact = new Contact();
		
		Vector3d normal = one.getAxis(best);
		
		if (one.getAxis(best).ScalarProduct(toCenter) > 0.0d)
		{
			normal.Multiply(-1.0d);
		}
		
		Vector3d vertex = new Vector3d(two.halfSize);
		
		if (two.getAxis(0).ScalarProduct(normal) < 0.0d) vertex.x = -vertex.x;
		if (two.getAxis(1).ScalarProduct(normal) < 0.0d) vertex.y = -vertex.y;
		if (two.getAxis(2).ScalarProduct(normal) < 0.0d) vertex.z = -vertex.z;
		
		contact.contactNormal = normal;
		contact.penetration  = pen;
		
		Matrix4d tmp = new Matrix4d(two.getTransform());
		contact.contactPoint = new Vector3d(tmp.Multiply(vertex));
		contact.setBodyData(one.body, two.body, data.friction, data.restitution);
		data.contacts.add(contact);
		
	}
	
	public Vector3d contactPoint(Vector3d pOne, Vector3d dOne, 
			double oneSize, Vector3d pTwo, Vector3d dTwo, double twoSize, boolean useOne)
	{
		Vector3d toSt = new Vector3d();
		Vector3d cOne = new Vector3d();
		Vector3d cTwo = new Vector3d();
		
		double dpStaOne, dpStaTwo, dpOneTwo, smOne, smTwo;
		double denom, mua, mub;
		
		smOne = dOne.SquareMagnitude();
		smTwo = dTwo.SquareMagnitude();
		dpOneTwo = dTwo.ScalarProduct(dOne);
		
		toSt = new Vector3d(pOne);
		toSt.Substract(pTwo);
		dpStaOne = dOne.ScalarProduct(toSt);
		dpStaTwo = dTwo.ScalarProduct(toSt);
		
		denom = smOne * smTwo - dpOneTwo * dpOneTwo;
		
		if (Math.abs(denom) < 0.0001d)
		{
			return useOne ? pOne : pTwo;
		}
		
		mua = (dpOneTwo * dpStaTwo - smTwo * dpStaOne) / denom;
		mub = (smOne * dpStaTwo - dpOneTwo * dpStaOne) / denom;
		
		if (mua > oneSize ||
				mua < -oneSize ||
				mub > twoSize ||
				mub < -twoSize)
		{
			return useOne ? pOne : pTwo;
		}
		else
		{
			cOne = new Vector3d(pOne);
			cOne.addScaledVector(dOne, mua);
			
			cTwo = new Vector3d(pTwo);
			cTwo.addScaledVector(dTwo, mub);
			
			cOne.Multiply(0.5d);
			cOne.addScaledVector(cTwo, 0.5d);
			
			return cOne;
	
			
		}
		
	}

	

}
