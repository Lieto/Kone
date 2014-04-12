package kone.core.unittests;

import static org.junit.Assert.*;
import kone.CollisionData;
import kone.CollisionDetector;
import kone.CollisionPlane;
import kone.ContactResolver;
import kone.core.Quaternion;
import kone.core.Vector3d;

import org.junit.Test;

import primitives.Box;


public class TestBoxAndHalfSpace {

	@Test
	public void testMovelessContact() {
		
		Box box = new Box();
		CollisionPlane plane = new CollisionPlane();
		CollisionData data  = new CollisionData();
		
		// Boxdata
		box.setState(new Vector3d(0.0d, 0.8d, 0.0d), 
				new Quaternion(), 
				new Vector3d(1.0d, 1.0d, 1.0d),
				new Vector3d(0.0d, 0.0d, 0.0d));
		
		box.calculateInternals();
		
		plane.direction = new Vector3d(0.0d, 1.0d, 0.0d);
		plane.offset = 0.0d;
		
		data.reset();
		data.friction = 0.0d;
		
		// How much collision keeps kinetic energy, 1.0 = all 0.0 = none
		data.restitution = 1.0d;
		data.tolerance = 0.01d;
		
		// detect collision with ground plane
		CollisionDetector detector = new CollisionDetector();
		int actualResult = detector.boxAndHalfSpace(box, plane, data);
		
		// we should detect  one contact
		int expectedResult = 1;
		
		assertEquals(expectedResult, actualResult);
		
		ContactResolver resolver = new ContactResolver(1);
		resolver.resolveContacts(data.contacts, 1.0d / 60.0d);
		
		double tolerance = 0.01d;
		
		// Box without kinetic energy should "raise" to y = 1.0
		assertEquals(1.0d, data.contacts.get(0).body[0].getPosition().y, tolerance);
		
	}
	
	@Test
	public void testMovingCollision()
	{
		Box box = new Box();
		CollisionPlane plane = new CollisionPlane();
		CollisionData data  = new CollisionData();
		
		// Boxdata
		box.setState(new Vector3d(0.0d, 0.8d, 0.0d), 
				new Quaternion(), 
				new Vector3d(1.0d, 1.0d, 1.0d),
				new Vector3d(0.0d, -10.0d, 0.0d));
		
		box.calculateInternals();
		
		plane.direction = new Vector3d(0.0d, 1.0d, 0.0d);
		plane.offset = 0.0d;
		
		data.reset();
		data.friction = 0.0d;
		
		// How much collision keeps kinetic energy, 1.0 = all 0.0 = none
		data.restitution = 1.0d;
		data.tolerance = 0.01d;
		
		// detect collision with ground plane
		CollisionDetector detector = new CollisionDetector();
		int actualResult = detector.boxAndHalfSpace(box, plane, data);
		
		// we should detect  one contact
		int expectedResult = 1;
		
		assertEquals(expectedResult, actualResult);
		
		ContactResolver resolver = new ContactResolver(1);
		resolver.resolveContacts(data.contacts, 1.0d / 60.0d);
		
		double tolerance = 0.01d;
		
		// Box without kinetic energy should "raise" to y = 1.0
		assertEquals(1.0d, data.contacts.get(0).body[0].getPosition().y, tolerance);
		assertEquals(10.0d, data.contacts.get(0).body[0].getVelocity().y, tolerance);
		
	}
	
	@Test
	public void testAcceleratedCollision()
	{
		Box box = new Box();
		CollisionPlane plane = new CollisionPlane();
		CollisionData data  = new CollisionData();
		
		// Boxdata
		box.setState(new Vector3d(0.0d, 0.8d, 0.0d), 
				new Quaternion(), 
				new Vector3d(1.0d, 1.0d, 1.0d),
				new Vector3d(0.0d, -10.0d, 0.0d));
		
		box.body.setAcceleration(0.0d, -10.0d, 10.0d);
		
		box.calculateInternals();
		
		plane.direction = new Vector3d(0.0d, 1.0d, 0.0d);
		plane.offset = 0.0d;
		
		data.reset();
		data.friction = 0.0d;
		
		// How much collision keeps kinetic energy, 1.0 = all 0.0 = none
		data.restitution = 1.0d;
		data.tolerance = 0.01d;
		
		// detect collision with ground plane
		CollisionDetector detector = new CollisionDetector();
		int actualResult = detector.boxAndHalfSpace(box, plane, data);
		
		// we should detect  one contact
		int expectedResult = 1;
		
		assertEquals(expectedResult, actualResult);
		
		ContactResolver resolver = new ContactResolver(1);
		resolver.resolveContacts(data.contacts, 1.0d / 60.0d);
		
		double tolerance = 0.01d;
		
		// Box without kinetic energy should "raise" to y = 1.0
		assertEquals(1.0d, data.contacts.get(0).body[0].getPosition().y, tolerance);
		assertEquals(10.0d, data.contacts.get(0).body[0].getVelocity().y, tolerance);
		assertEquals(-10.0d, data.contacts.get(0).body[0].getAcceleration().y, tolerance);
		
	}

}
