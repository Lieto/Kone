package kone.core;

public class Complex {
	
	public double real;
	public double imag;
	
	public Complex()
	{
		real = 0.0;
		imag = 0.0;
	}
	
	public Complex(double x, double y)
	{
		real = x;
		imag = y;
	}
	
	public Complex(Complex other)
	{
		real = other.real;
		imag = other.imag;
		
	}
	
	public Complex(Polar polar)
	{
		real = polar.radius*Math.cos(polar.angle);
		imag = Math.sin(polar.angle);
	}
	
	public Polar Polar()
	{
		double radius = Math.sqrt(real*real + imag*imag);
		double phase = Math.atan(imag/real);
		
		return new Polar(radius, phase);
		
	}
	
	public boolean equals(Complex other)
	{
		if ((real == other.real) && (imag == other.imag))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public Complex Multiply(Complex other)
	{
		return new Complex(real*other.real - imag*other.imag,
				real*other.imag + other.real*imag);
	}
	
	public Complex Add(Complex other)
	{
		return new Complex(real + other.real,
			imag + other.imag);
	}
	
	public Complex Conjugate()
	{
		return new Complex(real,
				-imag);
	}
	
	public double Length()
	{
		return Math.sqrt(real*real + imag*imag);
	}
	
	public double LengthSquared()
	{
		return real*real + imag*imag;
	}
	
	public Complex Scale(double scale)
	{
		return new Complex(real/scale, imag/scale);
	}
	
	public Complex Inverse()
	{
		if (real == 0.0 && imag == 0.0)
		{
			System.out.println("Complex number can't be zero length");
			System.exit(1);
		}
		else
		{
			return Conjugate().Scale(1.0d/LengthSquared());
		}
		return null;
	}
	
	public static Complex Identity()
	{
		return new Complex(1.0, 0.0);
	}
	


}
