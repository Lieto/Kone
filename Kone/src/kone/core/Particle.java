package kone.core;

public class Particle {
	
	public Vector3f position;
	public float inverseMass;
	public Vector3f velocity;
	public Vector3f acceleration;
	public float damping;
	public Vector3f forceAccum;
	
	public Particle()
	{
		position = new Vector3f();
		velocity = new Vector3f();
		acceleration = new Vector3f();
		forceAccum = new Vector3f();
	}
	
	/*
	public void getPosition(Vector3 position)
	{
		position = this.position;
		
	}
	*/
	
	public Vector3f getPosition() {
		
		return position;
	}


	public void setMass(float f) {
		if (f == 0.0f)
		{
			inverseMass = Float.POSITIVE_INFINITY;
		}
		else
		{
			inverseMass = 1/f;
		}
	}

	public void setVelocity(float f, float g, float h) {
		velocity.x = f;
		velocity.y = g;
		velocity.z = h;
		
	}
	
	public void setVelocity(Vector3f vel)
	{
		velocity = vel;
	}
	
	public Vector3f getVelocity()
	{
		return velocity;
	}

	public void setAcceleration(float f, float g, float h) {
		acceleration.x = f;
		acceleration.y = g;
		acceleration.z = h;
		
		
	}
	
	public void setAcceleration(Vector3f acc)
	{
		acceleration = acc;
		
	}

	public void setDamping(float f) {
		damping = f;
		
	}

	public void setPosition(float f, float g, float h) {
		position.x = f;
		position.y = g;
		position.z = h;
	}
	
	public void setPosition(Vector3f pos)
	{
		position = pos;
	}

	public void clearAccumulator() {
		
		forceAccum.clear();
	}

	public void AddForce(Vector3f force)
	{
		forceAccum.Add(force);
	}
	
	public void integrate(float duration) {
		
		if (inverseMass <= 0.0f) return;
		
		if (duration > 0.0f)
		{
			position.addScaledVector(velocity, duration);
			
			Vector3f resultingAcc = acceleration;
			resultingAcc.addScaledVector(forceAccum, inverseMass);
			
			velocity.addScaledVector(resultingAcc, duration);
			
			velocity.Multiply((float) Math.pow(damping, duration));
			
			clearAccumulator();
		}
		
	}

	public boolean hasFiniteMass() {
		
		return inverseMass >= 0.0f;
	}

	public float getMass() {
		if (inverseMass == 0.0f)
		{
			return Float.POSITIVE_INFINITY;
		}
		else
		{
			return ((float) 1.0)/ inverseMass;
		}
	}

	
}
