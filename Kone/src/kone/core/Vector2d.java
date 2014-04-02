package kone.core;

public class Vector2d {
	
	public double x;
	public double y;
	
	public Vector2d()
	{
		x = 0.0;
		y = 0.0;
	}
	
	public Vector2d(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2d Rotate(double angle)
	{
		angle = angle % 360.0;
		// to radians
		angle = (angle /360.0) * (2 * Math.PI);
		
		return new Vector2d(x*Math.cos(angle)-y*Math.sin(angle),
				x*Math.sin(angle)+y*Math.cos(angle)
				);
	}
	
	



}
