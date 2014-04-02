package kone.core;

public class Vector3f {

	public static final Vector3f GRAVITY = new Vector3f(0.0f, -9.81f, 0.0f);
	public static final Vector3f UP = new Vector3f(0.0f, 1.0f, 0.0f);
	public float x;
	public float y;
	public float z;
	
	public Vector3f()
	{
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
	}
	
	public Vector3f(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f(Vector3f velocity) {
		x = velocity.x;
		y = velocity.y;
		z = velocity.z;
	}


	public void clear() {
	
		x = 0.0f;
		y = 0.0f;
		z = 0.0f;
		
	}
	

	public void Multiply(float scale)
	{
		x *= scale;
		y *= scale;
		z *= scale;
	}
	
	public Vector3f NewVectorMultiply(float scale)
	{
		return new Vector3f(x*scale, y*scale, z*scale);
	}
	
	
	public void addScaledVector(Vector3f velocity, float duration) {
		x += velocity.x * duration;
		y += velocity.y * duration;
		z += velocity.z * duration;
	}
	
	public void Add(Vector3f other)
	{
		x += other.x;
		y += other.y;
		z += other.z;
	}

	public float Magnitude() {
		
		return (float) Math.sqrt((double) (x*x + y*y + z*z));
	}
	
	public float SquareMagnitude()
	{
		return x*x + y*y + z*z;
		
	}

	public void Normalise() {
		float l = Magnitude();
		
		if (l > 0.0f)
		{
			this.Multiply(((float) 1) / l);
		}
		
	}

	public void Substract(Vector3f position) {
		x -= position.x;
		y -= position.y;
		z -= position.z;
		
	}
	
	public float ScalarProduct(Vector3f vector)
	{
		return x*vector.x + y*vector.y + z*vector.z;
	}

	

}
