package kone.core.unittests;

import static org.junit.Assert.*;
import kone.CollisionData;
import kone.CollisionDetector;
import kone.ContactResolver;
import kone.core.Matrix3d;
import kone.core.Quaternion;
import kone.core.Vector3d;

import org.junit.Test;

import primitives.Box;
import primitives.Sphere;


public class TestBoxAndSphere {

		@Test
		public void testMovelessContact() {
			
			Box box = new Box();
			Sphere sphere = new Sphere();
			CollisionData data  = new CollisionData();
			
			
			// Boxdata
			box.setState(new Vector3d(0.0d, 2.0d, 0.0d), 
					new Quaternion(), 
					new Vector3d(1.0d, 1.0d, 1.0d),
					new Vector3d(0.0d, 0.0d, 0.0d));
			
			box.calculateInternals();
			
			sphere.body.setMass(0.1d);
			sphere.body.setVelocity(new Vector3d(0.0d, 0.0d, 0.0d));
			sphere.body.setAcceleration(new Vector3d(0.0d, 0.0d, 0.0d));
			sphere.body.setDamping(0.99d,  0.8d);
			sphere.radius = 0.2d;
			
			sphere.body.setCanSleep(false);
			sphere.body.setAwake(true);
			
			Matrix3d tensor = new Matrix3d();
			double coeff = 0.4d*sphere.body.getMass()*sphere.radius*sphere.radius;
			tensor.SetInertiaTensorCoeffs(coeff, coeff, coeff);
			sphere.body.setInertiaTensor(tensor);
			
			sphere.body.setPosition(new Vector3d(0.0d, 3.1d, 0.0d));
			
			//startTime = 
			sphere.body.CalculateDerivedData();
			
			sphere.calculateInternals();
			
			
			data.reset();
			data.friction = 0.0d;
			
			// How much collision keeps kinetic energy, 1.0 = all 0.0 = none
			data.restitution = 1.0d;
			data.tolerance = 0.01d;
			
			// detect collision with ground plane
			CollisionDetector detector = new CollisionDetector();
			int actualResult = detector.boxAndSphere(box, sphere, data);
			
			// we should detect  one contact
			int expectedResult = 1;
			
			assertEquals(expectedResult, actualResult);
			
			ContactResolver resolver = new ContactResolver(1);
			resolver.resolveContacts(data.contacts, 1.0d / 60.0d);
			
			double tolerance = 0.01d;
			
			// Box without kinetic energy should "raise" to y = 1.0
			assertEquals(3.2d, data.contacts.get(0).body[1].getPosition().y, tolerance);
			
	}
		
		@Test
		public void testMovingCollision()
		{
			Box box = new Box();
			Sphere sphere = new Sphere();
			CollisionData data  = new CollisionData();
			
			
			// Boxdata
			box.setState(new Vector3d(0.0d, 2.0d, 0.0d), 
					new Quaternion(), 
					new Vector3d(1.0d, 1.0d, 1.0d),
					new Vector3d(0.0d, 0.0d, 0.0d));
			
			box.calculateInternals();
			
			sphere.body.setMass(0.1d);
			sphere.body.setVelocity(new Vector3d(0.0d, -100.0d, 0.0d));
			sphere.body.setAcceleration(new Vector3d(0.0d, 0.0d, 0.0d));
			sphere.body.setDamping(0.99d,  0.8d);
			sphere.radius = 0.2d;
			
			sphere.body.setCanSleep(false);
			sphere.body.setAwake(true);
			
			Matrix3d tensor = new Matrix3d();
			double coeff = 0.4d*sphere.body.getMass()*sphere.radius*sphere.radius;
			tensor.SetInertiaTensorCoeffs(coeff, coeff, coeff);
			sphere.body.setInertiaTensor(tensor);
			
			sphere.body.setPosition(new Vector3d(0.0d, 3.1d, 0.0d));
			
			//startTime = 
			sphere.body.CalculateDerivedData();
			
			sphere.calculateInternals();
			
			
			data.reset();
			data.friction = 0.0d;
			
			// How much collision keeps kinetic energy, 1.0 = all 0.0 = none
			data.restitution = 1.0d;
			data.tolerance = 0.01d;
			
			
			// detect collision with ground plane
			CollisionDetector detector = new CollisionDetector();
			int actualResult = detector.boxAndSphere(box, sphere, data);
			
			// we should detect  one contact
			int expectedResult = 1;
			
			assertEquals(expectedResult, actualResult);
			
			ContactResolver resolver = new ContactResolver(1);
			resolver.resolveContacts(data.contacts, 1.0d / 60.0d);
			
			double tolerance = 0.01d;
			
			// Box without kinetic energy should "raise" to y = 1.0
			assertEquals(3.2d, data.contacts.get(0).body[1].getPosition().y, tolerance);
			assertEquals(100.0d, data.contacts.get(0).body[1].getVelocity().y, tolerance);
			
		}

}
