package kone.core;

public class Vector3d {

	public static final Vector3d GRAVITY = new Vector3d(0.0d, -9.81d, 0.0d);
	public static final Vector3d UP = new Vector3d(0.0d, 1.0d, 0.0d);
	public double x;
	public double y;
	public double z;
	
	public Vector3d()
	{
		x = 0.0d;
		y = 0.0d;
		z = 0.0d;
	}
	
	public Vector3d(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3d(Vector3d velocity) {
		x = velocity.x;
		y = velocity.y;
		z = velocity.z;
	}


	public void clear() {
	
		x = 0.0d;
		y = 0.0d;
		z = 0.0d;
		
	}
	

	public void Multiply(double scale)
	{
		x *= scale;
		y *= scale;
		z *= scale;
	}
	
	public Vector3d NewVectorMultiply(double scale)
	{
		return new Vector3d(x*scale, y*scale, z*scale);
	}
	
	
	public void addScaledVector(Vector3d velocity, double duration) {
		x += velocity.x * duration;
		y += velocity.y * duration;
		z += velocity.z * duration;
	}
	
	public void Add(Vector3d other)
	{
		x += other.x;
		y += other.y;
		z += other.z;
	}

	public double Magnitude() {
		
		return (double) Math.sqrt((double) (x*x + y*y + z*z));
	}
	
	public double SquareMagnitude()
	{
		return x*x + y*y + z*z;
		
	}

	public void Normalise() {
		double l = Magnitude();
		
		if (l > 0.0d)
		{
			this.Multiply(1.0d / l);
		}
		
	}

	public void Substract(Vector3d position) {
		x -= position.x;
		y -= position.y;
		z -= position.z;
		
	}
	
	public double ScalarProduct(Vector3d vector)
	{
		return x*vector.x + y*vector.y + z*vector.z;
	}

	public Vector3d ComponentProduct(Vector3d vector) {
		
		return new Vector3d(x*vector.x, y*vector.y, z*vector.z);
	}

	public Vector3d CrossProduct(Vector3d vector) {
		
		return new Vector3d(
				y*vector.z-z*vector.y,
                z*vector.x-x*vector.z,
                x*vector.y-y*vector.x);
	}
	
	public void SetCrossProduct(Vector3d vector) {

				x = y*vector.z-z*vector.y;
                y = z*vector.x-x*vector.z;
                z = x*vector.y-y*vector.x;
	}

	public void ComponentProductUpdate(Vector3d vector) {
		
		x = x*vector.x;
		y = y*vector.y;
		z = z*vector.z;
		
		
	}

	public void Invert() {
		x = -x;
		y = -y;
		z = -z;
		
	}

	

}

