package kone.core;

public class Quaternion {
	public double r;
	public double i;
	public double j;
	public double k;
	
	public double[] data;
	
	public Quaternion()
	{
		r = 1.0d;
		i = 0.0d;
		j = 0.0d;
		k = 0.0d;
		
		data = new double[4];
		
	}
	
	public Quaternion(Quaternion other)
	{
		r = other.r;
		i = other.i;
		j = other.j;
		k = other.k;
		
		data = new double[4];
		
	}
	
	public Quaternion(double r, double i, double j, double k)
	{
		this.r = r;
		this.i = i;
		this.j = j;
		this.k = k;
		
		data = new double[4];
	}
	
	public void Normalise()
	{
		double d = r*r+i*i+j*j+k*k;
		
		if (d == 0)
		{
			r = 1;
			return;
		}
		
		d = 1.0d / Math.sqrt(d);
		
		r*=d;
		i*=d;
		j*=d;
		k*=d;
		
	}
	
	
	public void thisMultiply(Quaternion multiplier)
	{
		Quaternion q = this;
		
		r = q.r*multiplier.r - q.i*multiplier.i -
                q.j*multiplier.j - q.k*multiplier.k;
            i = q.r*multiplier.i + q.i*multiplier.r +
                q.j*multiplier.k - q.k*multiplier.j;
            j = q.r*multiplier.j + q.j*multiplier.r +
                q.k*multiplier.i - q.i*multiplier.k;
            k = q.r*multiplier.k + q.k*multiplier.r +
                q.i*multiplier.j - q.j*multiplier.i;
	}
	
	public Quaternion Multiply(Quaternion multiplier)
	{
		Quaternion q = this;
		
		return new Quaternion(
				q.r*multiplier.r - q.i*multiplier.i -
                q.j*multiplier.j - q.k*multiplier.k,
                q.r*multiplier.i + q.i*multiplier.r +
                q.j*multiplier.k - q.k*multiplier.j,
                q.r*multiplier.j + q.j*multiplier.r +
                q.k*multiplier.i - q.i*multiplier.k,
                q.r*multiplier.k + q.k*multiplier.r +
                q.i*multiplier.j - q.j*multiplier.i
            );
	}
	
	public Quaternion Scale(double scale)
	{
		return new Quaternion(this.r * scale,
				this.i * scale,
				this.j * scale,
				this.k * scale
				);
		
	}
	public double InnerProduct(Quaternion multiplier)
	{
		Quaternion q = this;
		
		return q.r*multiplier.r +
				q.i*multiplier.i +
				q.j*multiplier.j +
				q.k*multiplier.k;
		
	}
	
	public Quaternion Conjugate()
	{
		return new Quaternion(this.r, -this.i, -this.j, -this.k);
	}
	
	public void AddScaledVector(Vector3d vector, double scale)
	{
		Quaternion q = new Quaternion(0.0d, 
				vector.x * scale,
				vector.y * scale,
				vector.z * scale);
		
		q.Multiply(this);
		
		r += q.r * 0.5d;
		i += q.i * 0.5d;
		j += q.j * 0.5d;
		k += q.k * 0.5d;
	}
	
	public void RotateByVector(Vector3d vector)
	{
		Quaternion q = new Quaternion(0, vector.x, vector.y, vector.z);
		this.Multiply(q);
	}
	

}
