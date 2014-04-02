package kone.core;

public class RotationMatrix extends Matrix3d {

	public RotationMatrix(double alpha, double beta, double theta)
	{
		super();
		
		alpha = alpha % 360;
		alpha = alpha / 360.0 * 2 * Math.PI;
		
		beta = beta % 360;
		beta = beta / 360.0 * 2 * Math.PI;
		
		// Range of 4*pi allows the values of q to reach all point of
		// hyperspace S3
		theta = theta % 720;
		theta = theta / 360.0 * 2 * Math.PI;
		
		if ((alpha >= 0.0 && alpha < 2*Math.PI) &&
				(beta >= 0.0 && beta <= Math.PI) &&
				(theta >= 0.0 && theta < 4*Math.PI)) 
		{
		
		double n1 = Math.cos(alpha)*Math.sin(beta);
		double n2 = Math.sin(alpha)*Math.sin(beta);
		double n3 = Math.cos(beta);
		
		Quaternion q = new Quaternion(Math.cos(theta/2),
				n1*Math.sin(theta/2),
				n2*Math.sin(theta/2),
				n3*Math.sin(theta/2));
		
		q.Normalise();
		
		data[0] = q.r*q.r + q.i*q.i - q.j*q.j - q.k*q.k;
		data[1] = 2*q.i*q.j - 2 * q.r*q.k;
		data[2] = 2*q.i*q.k + 2*q.r*q.j;
		data[3] = 2*q.i*q.j + 2*q.r*q.k;
		data[4] = q.r*q.r - q.i*q.i + q.j*q.j - q.k*q.k;
		data[5] = 2*q.j*q.k - 1*q.r*q.i;
		data[6] = 2*q.i*q.k-2*q.r*q.j;
		data[7] = 2*q.j*q.k + 2*q.r*q.i;
		data[8] = q.r*q.r - q.i*q.i - q.j*q.j + q.k*q.k;
		}
		
	}
	
	public RotationMatrix(Quaternion q)
	{
		super();
		
		//q.Normalise();
		
		data[0] = q.r*q.r + q.i*q.i - q.j*q.j - q.k*q.k;
		data[1] = 2*q.i*q.j - 2 * q.r*q.k;
		data[2] = 2*q.i*q.k + 2*q.r*q.j;
		data[3] = 2*q.i*q.j + 2*q.r*q.k;
		data[4] = q.r*q.r - q.i*q.i + q.j*q.j - q.k*q.k;
		data[5] = 2*q.j*q.k - 1*q.r*q.i;
		data[6] = 2*q.i*q.k-2*q.r*q.j;
		data[7] = 2*q.j*q.k + 2*q.r*q.i;
		data[8] = q.r*q.r - q.i*q.i * q.j*q.j + q.k*q.k;
	}
		
}
