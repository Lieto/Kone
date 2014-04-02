package kone.core;

public class Random {
	public static int Int(int max)
	{
		return (int) Math.random() * max;
	}
	
	public static float Float(float max)
	{
		return (float) Math.random() * max;
	}
	
	public static double Double(double max)
	{
		return Math.random() * max;
	}
	
	public static double Double()
	{
		return Math.random();
	}
	
	public static double Binomial(double max)
	{
		return Double(max) - Double(max);
	}
	
	
	public static float Binomial(float max)
	{
		return Float(max) - Float(max);
	}
	
	public static boolean Boolean()
	{
		return (Int(2) % 2 == 0);
	}

	public static float Float(float minAge, float maxAge) {
		
		return minAge + Float(maxAge-minAge);
	}

	
	public static double Double(double minAge, double maxAge) {
		
		return minAge + Double(maxAge-minAge);
	}

	public static Vector3f Vector(Vector3f minVelocity, Vector3f maxVelocity) {
		
		return new Vector3f(Random.Float(minVelocity.x, maxVelocity.x),
				Random.Float(minVelocity.y, maxVelocity.y),
				Random.Float(minVelocity.z, maxVelocity.z)
				);
	}
	
	public static Vector3d Vector(Vector3d minVelocity, Vector3d maxVelocity) {
		
		return new Vector3d(Double(minVelocity.x, maxVelocity.x),
				Double(minVelocity.y, maxVelocity.y),
				Double(minVelocity.z, maxVelocity.z)
				);
	}
	
	public static Vector3d XZVector(double scale)
	{
		return new Vector3d(Binomial(scale),
				0.0d,
				Binomial(scale));
	}

	public static Quaternion Quaternion() {
		
		Quaternion q = new Quaternion(Double(), Double(), Double(), Double());
		q.Normalise();
		
		return q;
	}

}
