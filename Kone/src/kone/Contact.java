package kone;

import kone.core.Matrix3d;
import kone.core.Quaternion;
import kone.core.Vector3d;

public class Contact {
	
	static final double velocityLimit = 0.25d;
	static final double angularLimit = 0.2d;
	
	public Body[] body;
	
	public double friction;
	
	public double restitution;
	
	public Vector3d contactPoint;
	
	public Vector3d contactNormal;
	
	public double penetration;
	
	protected Matrix3d contactToWorld;
	
	protected Vector3d contactVelocity;
	
	protected double desiredDeltaVelocity;
	
	protected Vector3d[] relativeContactPosition;
	
	public Contact()
	{
		body = new Body[2];
		contactPoint = new Vector3d();
		contactNormal = new Vector3d();
		contactToWorld = new Matrix3d();
		contactVelocity = new Vector3d();
		relativeContactPosition = new Vector3d[2];
	}
	
	public void setBodyData(Body one, Body two,
			double friction, double restitution)
	{
		body[0] = one;
		body[1] = two;
		this.friction = friction;
		this.restitution = restitution;
	}

	public void calculateInternals(double duration) {
		
		if (body[0] == null) swapBodies();
		
		if (body[0] != null)
		{
			calculateContactBasis();
			
			relativeContactPosition[0] = new Vector3d(contactPoint);
			relativeContactPosition[0].Substract(body[0].getPosition());
			
			if (body[1] != null)
			{
				relativeContactPosition[1] = new Vector3d(contactPoint);
				relativeContactPosition[1].Substract(body[1].getPosition());
			}
			
			contactVelocity = calculateLocalVelocity(0, duration);
			
			if (body[1] != null)
			{
				contactVelocity.Substract(calculateLocalVelocity(1, duration));
			}
			
			calculateDesiredDeltaVelocity(duration);
		}
		
	}

	void calculateDesiredDeltaVelocity(double duration) {
		
		double velocityFromAcc = 0.0d;
		
		if (body[0].getAwake())
		{
			Vector3d lfa = new Vector3d(body[0].getLastFrameAcceleration());
			lfa.Multiply(duration);
			velocityFromAcc = lfa.ScalarProduct(contactNormal);
			//body[0].setLastFrameAcceleration(lfa);
			
		}
		
		if (body[1] != null && body[1].getAwake())
		{
			Vector3d lfa = new Vector3d(body[1].getLastFrameAcceleration());
			lfa.Multiply(duration);
			
			velocityFromAcc -= lfa.ScalarProduct(contactNormal);
		}
		
		double thisRestitution = restitution;
		
		if (Math.abs(contactVelocity.x) < velocityLimit)
		{
			thisRestitution = 0.0d;
		}
		
		desiredDeltaVelocity = -contactVelocity.x - thisRestitution* (contactVelocity.x - velocityFromAcc);
	}
	

	private Vector3d calculateLocalVelocity(int i, double duration) {
		
		Body thisBody = body[i];
		
		Vector3d velocity = new Vector3d(thisBody.getRotation().CrossProduct(relativeContactPosition[i]));
		velocity.Add(thisBody.getVelocity());
		
		Vector3d contactVelocity = contactToWorld.TransformTranspose(velocity);
		
		Vector3d accVelocity = thisBody.getLastFrameAcceleration().NewVectorMultiply(duration);
		
		accVelocity = contactToWorld.TransformTranspose(accVelocity);
		
		accVelocity.x = 0.0d;
		
		contactVelocity.Add(accVelocity);
		
		return contactVelocity;
		
		
		
		
	}

	private void calculateContactBasis() {
		
		Vector3d[] contactTangent = new Vector3d[2];
		contactTangent[0] = new Vector3d();
		contactTangent[1] = new Vector3d();
		
		if (Math.abs(contactNormal.x) > Math.abs(contactNormal.y))
		{
			double s = 1.0d/Math.sqrt(contactNormal.z*contactNormal.z + 
					contactNormal.x*contactNormal.x);
			
			
			contactTangent[0].x = contactNormal.z*s;
			contactTangent[0].y = 0.0d;
			contactTangent[0].z = -contactNormal.x*s;
			
			contactTangent[1].x = contactNormal.z*contactTangent[0].x;
			contactTangent[1].y = contactNormal.z*contactTangent[0].x -
					contactNormal.x*contactTangent[0].z;
			contactTangent[1].z = -contactNormal.y*contactTangent[0].x;
			contactTangent[0].z = -contactNormal.x*s;
			
		}
		else
		{
			double s = 1.0d/Math.sqrt(contactNormal.z*contactNormal.z + 
					contactNormal.y*contactNormal.y);
			
			contactTangent[0].x = 0.0d;
			contactTangent[0].y = -contactNormal.z*s;
			contactTangent[0].z = contactNormal.y*s;
			
			contactTangent[1].x = contactNormal.y*contactTangent[0].z-
					contactNormal.z*contactTangent[0].y;
			contactTangent[1].y = -contactNormal.x*contactTangent[0].z;
			contactTangent[1].z = contactNormal.x*contactTangent[0].y;
			
		}
		
		contactToWorld.SetComponents(contactNormal, contactTangent[0], contactTangent[1]);
		
	}

	private void swapBodies() {
		
		contactNormal.Multiply(-1.0d);
		
		Body temp = body[0];
		body[0] = body[1];
		body[1] = temp;
		
	}

	public void matchAwakeState() {
		
		if (body[1] == null) return;
		
		boolean body0awake = body[0].getAwake();
		boolean body1awake = body[1].getAwake();
		
		if (body0awake ^ body1awake)
		{
			if (body0awake) body[1].setAwake();
			else
				body[0].setAwake();
		}
		
	}

	public void applyPositionChange(Vector3d[] linearChange,
			Vector3d[] angularChange, double max) {
		
		double[] angularMove = new double[2];
		double[] linearMove = new double[2];
		
		double totalInertia = 0.0d;
		
		double[] linearInertia = new double[2];
		double[] angularInertia = new double[2];
		
		for (int i = 0; i < 2; i++)
		{
			if (body[i] != null)
			{
				Matrix3d inverseInertiaTensor = new Matrix3d();
				inverseInertiaTensor = body[i].getInverseInertiaTensorWorld();
				
				Vector3d angularInertiaWorld = new Vector3d(relativeContactPosition[i]);
				angularInertiaWorld = angularInertiaWorld.CrossProduct(contactNormal);
				angularInertiaWorld = inverseInertiaTensor.Transform(angularInertiaWorld);
				angularInertiaWorld = angularInertiaWorld.CrossProduct(relativeContactPosition[i]);
				angularInertia[i] = angularInertiaWorld.ScalarProduct(contactNormal);
				
				linearInertia[i] = body[i].getInverseMass();
				
				totalInertia += linearInertia[i] + angularInertia[i];
			}
		}
		
		for (int i = 0; i < 2; i++)
		{
			if (body[i] != null)
			{
				double sign = (i == 0) ? 1.0d : -1.0d;
				
				angularMove[i] = sign * penetration * (angularInertia[i]/totalInertia);
				linearMove[i] = sign * penetration * (linearInertia[i]/totalInertia);
				
				Vector3d projection = new Vector3d(relativeContactPosition[i]);
				projection.addScaledVector(contactNormal, -relativeContactPosition[i].ScalarProduct(contactNormal));
				
				double maxMagnitude = angularLimit * projection.Magnitude();
				
				if (angularMove[i] < -maxMagnitude)
				{
					double totalMove = angularMove[i] + linearMove[i];
					angularMove[i] = -maxMagnitude;
					linearMove[i] = totalMove - angularMove[i];
				}
				else if (angularMove[i] > maxMagnitude)
				{
					double totalMove = angularMove[i] + linearMove[i];
					angularMove[i] = maxMagnitude;
					linearMove[i] = totalMove - angularMove[i];
					
				}
				
				if (angularMove[i] == 0.0d)
				{
					angularChange[i].clear();
				}
				else
				{
					Vector3d targetAngularDirection = relativeContactPosition[i].CrossProduct(contactNormal);
					
					Matrix3d inverseInertiaTensor = new Matrix3d();
					inverseInertiaTensor = body[i].getInverseInertiaTensorWorld();
					Vector3d transform = new Vector3d(inverseInertiaTensor.Transform(targetAngularDirection));
					transform.Multiply(angularMove[i]/angularInertia[i]);
					angularChange[i] = new Vector3d(transform);
					
							
				}
				
				linearChange[i] = contactNormal.NewVectorMultiply(linearMove[i]);
				
				Vector3d pos = new Vector3d(body[i].getPosition());
				pos.addScaledVector(contactNormal, linearMove[i]);
				body[i].setPosition(pos);
				
				Quaternion q = new Quaternion(body[i].getOrientation());
				q.AddScaledVector(angularChange[i],  1.0d);
				body[i].setOrientation(q);
				
				if (!body[i].getAwake()) body[i].CalculateDerivedData();
				
			}
		}
	}

	public void applyVelocityChange(Vector3d[] velocityChange,
			Vector3d[] rotationChange) {
		
		Matrix3d[] inverseInertiaTensor = new Matrix3d[2];
		
		inverseInertiaTensor[0] = new Matrix3d();
		inverseInertiaTensor[0] = body[0].getInverseInertiaTensorWorld();
		
		if (body[1] != null)
		{
			inverseInertiaTensor[1] = new Matrix3d();
			inverseInertiaTensor[1] = body[1].getInverseInertiaTensorWorld();
		}
		
		Vector3d impulseContact;
		
		
		if (friction == 0.0d)
		{
			impulseContact = new Vector3d(calculateFrictionlessImpulse(inverseInertiaTensor));
			
		}
		else
		{
			impulseContact = new Vector3d(calculateFrictionImpulse(inverseInertiaTensor));
		}
		
		Vector3d impulse = new Vector3d(contactToWorld.Transform(impulseContact));
		//Vector3d impulse = new Vector3d(calculateFrictionImpulse(inverseInertiaTensor));
		
		Vector3d impulsiveTorque = relativeContactPosition[0].CrossProduct(impulse);
		rotationChange[0] = new Vector3d(inverseInertiaTensor[0].Transform(impulsiveTorque));
		velocityChange[0] = new Vector3d();
		velocityChange[0].addScaledVector(impulse, body[0].getInverseMass());
		
		body[0].addVelocity(velocityChange[0]);
		body[0].addRotation(rotationChange[0]);
		
		if (body[1] != null)
		{
			
			impulsiveTorque = impulse.CrossProduct(relativeContactPosition[1]);
			rotationChange[1] = inverseInertiaTensor[1].Transform(impulsiveTorque);
			velocityChange[1] = new Vector3d();
			velocityChange[1].addScaledVector(impulse, -body[1].getInverseMass());
			
			body[1].addVelocity(velocityChange[1]);
			body[1].addRotation(rotationChange[1]);
			
		}
		
	}

	private Vector3d calculateFrictionImpulse(Matrix3d[] inverseInertiaTensor) {
		
		Vector3d impulseContact = new Vector3d();
		
		double inverseMass = body[0].getInverseMass();
		
		Matrix3d impulseToTorque = new Matrix3d();
		
		impulseToTorque.SetSkewSymmetric(relativeContactPosition[0]);
		
		//Matrix3d deltaVelWorld = impulseToTorque;
		Matrix3d deltaVelWorld = new Matrix3d(impulseToTorque);
		//inverseInertiaTensor = new Matrix3d[2];
		//inverseInertiaTensor[0] = new Matrix3d();
		deltaVelWorld.SetMultiply(inverseInertiaTensor[0]);
		deltaVelWorld.SetMultiply(impulseToTorque);
		deltaVelWorld.Multiply(-1.0d);
		
		if (body[1] != null)
		{
			impulseToTorque.SetSkewSymmetric(relativeContactPosition[1]);
			
			//Matrix3d deltaVelWorld2 = impulseToTorque;
			Matrix3d deltaVelWorld2 = new Matrix3d(impulseToTorque);
			//inverseInertiaTensor[1] = new Matrix3d();
			deltaVelWorld2.SetMultiply(inverseInertiaTensor[1]);
			deltaVelWorld2.SetMultiply(impulseToTorque);
			deltaVelWorld2.Multiply(-1.0d);
			
			deltaVelWorld.Add(deltaVelWorld2);
			
			inverseMass += body[1].getInverseMass();

		}
		
		Matrix3d deltaVelocity = new Matrix3d(contactToWorld.Transpose());
		deltaVelocity.SetMultiply(deltaVelWorld);
		deltaVelocity.SetMultiply(contactToWorld);
		
		deltaVelocity.data[0] += inverseMass;
		deltaVelocity.data[4] += inverseMass;
		deltaVelocity.data[8] += inverseMass;
		
		Matrix3d impulseMatrix = new Matrix3d(deltaVelocity.Inverse());
		
		Vector3d velKill = new Vector3d(desiredDeltaVelocity, -contactVelocity.y, -contactVelocity.z);
		
		impulseContact = new Vector3d(impulseMatrix.Transform(velKill));
		
		double planarImpulse = Math.sqrt(impulseContact.y*impulseContact.y +
				impulseContact.z*impulseContact.z);
		
		if (planarImpulse > impulseContact.x*friction)
		{
			impulseContact.y /= planarImpulse;
			impulseContact.z /= planarImpulse;
			
			impulseContact.x = deltaVelocity.data[0] +
					deltaVelocity.data[1]*friction*impulseContact.y +
					deltaVelocity.data[2]*friction*impulseContact.z;
			impulseContact.x = desiredDeltaVelocity / impulseContact.x;
			impulseContact.y *= friction*impulseContact.x;
			impulseContact.z *= friction*impulseContact.x;
			
		}
		
		return impulseContact;
	}

	private Vector3d calculateFrictionlessImpulse(
			Matrix3d[] inverseInertiaTensor) {
		
		Vector3d impulseContact = new Vector3d();
		
		Vector3d deltaVelWorld = relativeContactPosition[0].CrossProduct(contactNormal);
		deltaVelWorld = inverseInertiaTensor[0].Transform(deltaVelWorld);
		deltaVelWorld = deltaVelWorld.CrossProduct(relativeContactPosition[0]);
		
		double deltaVelocity = deltaVelWorld.ScalarProduct(contactNormal);
		
		deltaVelocity += body[0].getInverseMass();
		
		if (body[1] != null)
		{
			deltaVelWorld = relativeContactPosition[1].CrossProduct(contactNormal);
			deltaVelWorld = inverseInertiaTensor[1].Transform(deltaVelWorld);
			deltaVelWorld = deltaVelWorld.CrossProduct(relativeContactPosition[1]);
			
			deltaVelocity += deltaVelWorld.ScalarProduct(contactNormal);
			
			deltaVelocity += body[1].getInverseMass();
			
			
		}
		
		impulseContact.x = desiredDeltaVelocity / deltaVelocity;
		impulseContact.y = 0.0d;
		impulseContact.z = 0.0d;
		
		return impulseContact;
	}

}
