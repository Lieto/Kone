package kone.core.unittests;

import static org.junit.Assert.*;
import kone.CollisionData;
import kone.CollisionDetector;
import kone.ContactResolver;
import kone.core.Quaternion;
import kone.core.Vector3d;

import org.junit.Test;

import primitives.Box;



public class TestBoxAndBox {

	@Test
	public void testMovelessContact() {
		
		Box box1 = new Box();
		Box box2 = new Box();
		
		CollisionData data  = new CollisionData();
		CollisionDetector detector = new CollisionDetector();
		
		// Boxdata
		box1.setState(new Vector3d(0.0d, 1.0d, 0.0d), 
				new Quaternion(), 
				new Vector3d(1.0d, 1.0d, 1.0d),
				new Vector3d(0.0d, 0.0d, 0.0d));
		
		box1.calculateInternals();
		
		box2.setState(new Vector3d(0.0d, 2.8d, 0.0d), 
				new Quaternion(), 
				new Vector3d(1.0d, 1.0d, 1.0d),
				new Vector3d(0.0d, 0.0d, 0.0d));
		
		box2.calculateInternals();
			
		data.reset();
		data.friction = 0.0d;
		
		// How much collision keeps kinetic energy, 1.0 = all 0.0 = none
		data.restitution = 1.0d;
		data.tolerance = 0.01d;
		
		// detect collision with ground plane
		int actualResult = detector.boxAndBox(box1, box2, data);
		
		// we should detect  one contact
		int expectedResult = 1;
		
		assertEquals(expectedResult, actualResult);
		
		ContactResolver resolver = new ContactResolver(1);
		resolver.resolveContacts(data.contacts, 1.0d / 60.0d);
		
		double tolerance = 0.01d;
		
		// Box without kinetic energy should "raise" to y = 1.0
		assertEquals(0.9d, data.contacts.get(0).body[0].getPosition().y, tolerance);
		assertEquals(2.9d, data.contacts.get(0).body[1].getPosition().y, tolerance);
	}

}
