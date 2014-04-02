package kone.core;

/**
 * Holds an inertia tensor, consisting of a 3x3 row-major matrix.
 * This matrix is not padding to produce an aligned structure, since
 * it is most commonly used with a mass (single real) and two
 * damping coefficients to make the 12-element characteristics array
 * of a rigid body.
 */

public class Matrix3d {
	
	public double[] data;
	
	public Matrix3d()
	{
		data = new double[9];
		data[0] = data[1] = data[2] =data[3] =data[4] 
				=data[5] =data[6] =data[7] =data[8] = 0.0d; 
	}
	
	public Matrix3d(Matrix3d other)
	{
		data = new double[9];
		data[0] = other.data[0];
		data[1] = other.data[1];
		data[2] = other.data[2];
		data[3] = other.data[3];
		data[4] = other.data[4];
		data[5] = other.data[5];
		data[6] = other.data[6];
		data[7] = other.data[7];
		data[8] = other.data[8];
		
	}
	
	public Matrix3d(Vector3d compOne, Vector3d compTwo, Vector3d compThree)
	{
		data = new double[9];
		SetComponents(compOne, compTwo, compThree);
	}
	
	public Matrix3d(double c0,double c1,double c2,double c3,double c4,
			double c5,double c6,double c7,double c8)
	{
		data = new double[9];
		data[0] = c0;
		data[1] = c1;
		data[2] = c2;
		data[3] = c3;
		data[4] = c4;
		data[5] = c5;
		data[6] = c6;
		data[7] = c7;
		data[8] = c8;
		
	}
	
	public void SetDiagonal(double a, double b, double c)
	{
		SetInertiaTensorCoeffs(a, b, c);
	}
	
	public void SetInertiaTensorCoeffs(double ix, double iy, double iz)
	{
		data[0] = ix;
		data[1] = 0.0d;
		data[2] = 0.0d;
		data[3] = 0.0d;
		data[4] = iy;
		data[5] = 0.0d;
		data[6] = 0.0d;
		data[7] = 0.0d;
		data[8] = iz;
	}
	
	public void SetInertiaTensorCoeffs(double ix, double iy, double iz,
			double ixy, double ixz, double iyz)
	{
		data[0] = ix;
		data[1] = -ixy;
		data[2] = -ixz;
		data[3] = -ixy;
		data[4] = iy;
		data[5] = -iyz;
		data[6] = -ixz;
		data[7] = -iyz;
		data[8] = iz;
	}
	
	public void SetBlockInertiaTensor(Vector3d halfSizes, double mass)
	{
		Vector3d squares = new Vector3d(halfSizes.ComponentProduct(halfSizes));
		SetInertiaTensorCoeffs(0.3d*mass*(squares.y + squares.z),
				0.3d*mass*(squares.x + squares.z),
				0.3d*mass*(squares.x + squares.y));
	}
	
	public void SetSkewSymmetric(Vector3d vector)
	{
		data[0] = 0.0d;
		data[1] = -vector.z;
		data[2] = vector.y;
		data[3] = vector.z;
		data[4] = 0.0d;
		data[5] = -vector.x;
		data[6] = -vector.y;
		data[7] = vector.x;
		data[8] = 0.0d;
	}
	
	public void SetComponents(Vector3d compOne, Vector3d compTwo, Vector3d compThree)
	{
		data[0] = compOne.x;
		data[1] = compTwo.x;
		data[2] = compThree.x;
		data[3] = compOne.y;
		data[4] = compTwo.y;
		data[5] = compThree.y;
		data[6] = compOne.z;
		data[7] = compTwo.z;
		data[8] = compThree.z;
		
	}
	
	public Vector3d Transform(Vector3d vector)
	{
		return new Vector3d(
				vector.x*data[0]+vector.y*data[1]+vector.z*data[2],
				vector.x*data[3]+vector.y*data[4]+vector.z*data[5],
				vector.x*data[6]+vector.y*data[7]+vector.z*data[8]);
	}
	
	public Vector3d TransformTranspose(Vector3d vector)
	{
		return new Vector3d(
				vector.x*data[0]+vector.y*data[3]+vector.z*data[6],
				vector.x*data[1]+vector.y*data[4]+vector.z*data[7],
				vector.x*data[2]+vector.y*data[5]+vector.z*data[8]);
		
	}
	
	public Vector3d GetRowVector(int i)
	{
		return new Vector3d(data[i*3], data[i*3+1], data[i*3+2]);
	}
	
	public Vector3d GetAxisVector(int i)
	{
		return new Vector3d(data[i], data[i*+3], data[i+6]);
	}
	
	public void SetInverse(Matrix3d m)
	{
		double t4 = m.data[0]*m.data[4];
		double t6 = m.data[0]*m.data[5];
		double t8 = m.data[1]*m.data[3];
		double t10 = m.data[2]*m.data[3];
		double t12 = m.data[1]*m.data[6];
		double t14= m.data[2]*m.data[6];
		
		double t16 = (t4*m.data[8] - t6*m.data[7] - t8*m.data[8]+
                t10*m.data[7] + t12*m.data[5] - t14*m.data[4]);
		
		if (t16 == 0.0d) return;
		
		double t17 = 1/t16;
		
		 data[0] = (m.data[4]*m.data[8]-m.data[5]*m.data[7])*t17;
         data[1] = -(m.data[1]*m.data[8]-m.data[2]*m.data[7])*t17;
         data[2] = (m.data[1]*m.data[5]-m.data[2]*m.data[4])*t17;
         data[3] = -(m.data[3]*m.data[8]-m.data[5]*m.data[6])*t17;
         data[4] = (m.data[0]*m.data[8]-t14)*t17;
         data[5] = -(t6-t10)*t17;
         data[6] = (m.data[3]*m.data[7]-m.data[4]*m.data[6])*t17;
         data[7] = -(m.data[0]*m.data[7]-t12)*t17;
         data[8] = (t4-t8)*t17;
		
	}
	
	public Matrix3d Inverse()
	{
		Matrix3d result = new Matrix3d();
		result.SetInverse(this);
		return result;
		
	}
	
	public void Invert()
	{
		SetInverse(this);
	}
	
	public void SetTranspose(Matrix3d m)
	{
		data[0] = m.data[0];
		data[1] = m.data[3];
		data[2] = m.data[6];
		data[3] = m.data[1];
		data[4] = m.data[4];
		data[5] = m.data[7];
		data[6] = m.data[2];
		data[7] = m.data[5];
		data[8] = m.data[8];
	}
	
	public Matrix3d Transpose()
	{
		Matrix3d result = new Matrix3d();
		result.SetTranspose(this);
		return result;
	}
	
	public Matrix3d Multiply(Matrix3d o)
	{
		return new Matrix3d(
				data[0]*o.data[0] + data[1]*o.data[3] + data[2]*o.data[6],
                data[0]*o.data[1] + data[1]*o.data[4] + data[2]*o.data[7],
                data[0]*o.data[2] + data[1]*o.data[5] + data[2]*o.data[8],

                data[3]*o.data[0] + data[4]*o.data[3] + data[5]*o.data[6],
                data[3]*o.data[1] + data[4]*o.data[4] + data[5]*o.data[7],
                data[3]*o.data[2] + data[4]*o.data[5] + data[5]*o.data[8],

                data[6]*o.data[0] + data[7]*o.data[3] + data[8]*o.data[6],
                data[6]*o.data[1] + data[7]*o.data[4] + data[8]*o.data[7],
                data[6]*o.data[2] + data[7]*o.data[5] + data[8]*o.data[8]
                );
	}
	
	public void SetMultiply(Matrix3d o)
	{
		double t1;
		double t2;
		double t3;
		
		 t1 = data[0]*o.data[0] + data[1]*o.data[3] + data[2]*o.data[6];
         t2 = data[0]*o.data[1] + data[1]*o.data[4] + data[2]*o.data[7];
         t3 = data[0]*o.data[2] + data[1]*o.data[5] + data[2]*o.data[8];
         data[0] = t1;
         data[1] = t2;
         data[2] = t3;

         t1 = data[3]*o.data[0] + data[4]*o.data[3] + data[5]*o.data[6];
         t2 = data[3]*o.data[1] + data[4]*o.data[4] + data[5]*o.data[7];
         t3 = data[3]*o.data[2] + data[4]*o.data[5] + data[5]*o.data[8];
         data[3] = t1;
         data[4] = t2;
         data[5] = t3;

         t1 = data[6]*o.data[0] + data[7]*o.data[3] + data[8]*o.data[6];
         t2 = data[6]*o.data[1] + data[7]*o.data[4] + data[8]*o.data[7];
         t3 = data[6]*o.data[2] + data[7]*o.data[5] + data[8]*o.data[8];
         data[6] = t1;
         data[7] = t2;
         data[8] = t3;
		
		
	}
	
	public void Multiply(double scalar)
	{
		data[0] *= scalar; data[1] *= scalar; data[2] *= scalar;
        data[3] *= scalar; data[4] *= scalar; data[5] *= scalar;
        data[6] *= scalar; data[7] *= scalar; data[8] *= scalar;
		
	}
	
	public void Add(Matrix3d o)
	{
		data[0] += o.data[0]; data[1] += o.data[1]; data[2] += o.data[2];
        data[3] += o.data[3]; data[4] += o.data[4]; data[5] += o.data[5];
        data[6] += o.data[6]; data[7] += o.data[7]; data[8] += o.data[8];
		
	}
	
	public void SetOrientation(Quaternion q)
	{
		data[0] = 1 - (2*q.j*q.j + 2*q.k*q.k);
        data[1] = 2*q.i*q.j + 2*q.k*q.r;
        data[2] = 2*q.i*q.k - 2*q.j*q.r;
        data[3] = 2*q.i*q.j - 2*q.k*q.r;
        data[4] = 1 - (2*q.i*q.i  + 2*q.k*q.k);
        data[5] = 2*q.j*q.k + 2*q.i*q.r;
        data[6] = 2*q.i*q.k + 2*q.j*q.r;
        data[7] = 2*q.j*q.k - 2*q.i*q.r;
        data[8] = 1 - (2*q.i*q.i  + 2*q.j*q.j);
		
	}
	
	public static Matrix3d LinearInterpolate(Matrix3d a, Matrix3d b, double prop)
	{
		Matrix3d result = new Matrix3d();
		
		for (int i = 0; i < 9; i++)
		{
			result.data[i] = a.data[i] * (1-prop) + b.data[i] * prop;
		}
		
		return result;
		
	}
	
	
	
	
	
	

}
