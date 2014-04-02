package kone.core;

public class Matrix4d {
	
	public double[] data;
	
	public Matrix4d()
	{
		data = new double[12];
		
		data[1] = data[2] = data[3] = data[4] = data[6] =
                data[7] = data[8] = data[9] = data[11] = 0.0d;
            data[0] = data[5] = data[10] = 1.0d;
	}
	
	public Matrix4d(Matrix4d other)
	{
		data = new double[12];
		
		for (int i = 0; i < 12; i++)
		{
			data[i] = other.data[i];
		}
		
	}
	public void SetDiagonal(double a, double b, double c)
	{
		data[0] = a;
		data[5] = b;
		data[10] = c;
	}
	
	public Matrix4d Multiply(Matrix4d o)
	{
		Matrix4d result = new Matrix4d();
		
		result.data[0] = (o.data[0]*data[0]) + (o.data[4]*data[1]) + (o.data[8]*data[2]);
        result.data[4] = (o.data[0]*data[4]) + (o.data[4]*data[5]) + (o.data[8]*data[6]);
        result.data[8] = (o.data[0]*data[8]) + (o.data[4]*data[9]) + (o.data[8]*data[10]);

        result.data[1] = (o.data[1]*data[0]) + (o.data[5]*data[1]) + (o.data[9]*data[2]);
        result.data[5] = (o.data[1]*data[4]) + (o.data[5]*data[5]) + (o.data[9]*data[6]);
        result.data[9] = (o.data[1]*data[8]) + (o.data[5]*data[9]) + (o.data[9]*data[10]);

        result.data[2] = (o.data[2]*data[0]) + (o.data[6]*data[1]) + (o.data[10]*data[2]);
        result.data[6] = (o.data[2]*data[4]) + (o.data[6]*data[5]) + (o.data[10]*data[6]);
        result.data[10] = (o.data[2]*data[8]) + (o.data[6]*data[9]) + (o.data[10]*data[10]);

        result.data[3] = (o.data[3]*data[0]) + (o.data[7]*data[1]) + (o.data[11]*data[2]) + data[3];
        result.data[7] = (o.data[3]*data[4]) + (o.data[7]*data[5]) + (o.data[11]*data[6]) + data[7];
        result.data[11] = (o.data[3]*data[8]) + (o.data[7]*data[9]) + (o.data[11]*data[10]) + data[11];
        
        return result;
	}
	
	public Vector3d Multiply(Vector3d vector)
	{
		return new Vector3d(
			     vector.x * data[0] +
	                vector.y * data[1] +
	                vector.z * data[2] + data[3],

	                vector.x * data[4] +
	                vector.y * data[5] +
	                vector.z * data[6] + data[7],

	                vector.x * data[8] +
	                vector.y * data[9] +
	                vector.z * data[10] + data[11]
	            );
	}
	
	public Vector3d Transform(Vector3d vector)
	{
		return this.Multiply(vector);
	}
	
	public double GetDeterminant()
	{
		return data[8]*data[5]*data[2]+
	        data[4]*data[9]*data[2]+
	        data[8]*data[1]*data[6]-
	        data[0]*data[9]*data[6]-
	        data[4]*data[1]*data[10]+
	        data[0]*data[5]*data[10];
		
		
	}
	
	public void SetInverse(Matrix4d m)
	{
		double det = GetDeterminant();
		
		if (det == 0.0d) return;
		
		det = 1.0d / det;
		
		data[0] = (-m.data[9]*m.data[6]+m.data[5]*m.data[10])*det;
	    data[4] = (m.data[8]*m.data[6]-m.data[4]*m.data[10])*det;
	    data[8] = (-m.data[8]*m.data[5]+m.data[4]*m.data[9]* m.data[15])*det;

	    data[1] = (m.data[9]*m.data[2]-m.data[1]*m.data[10])*det;
	    data[5] = (-m.data[8]*m.data[2]+m.data[0]*m.data[10])*det;
	    data[9] = (m.data[8]*m.data[1]-m.data[0]*m.data[9]* m.data[15])*det;

	    data[2] = (-m.data[5]*m.data[2]+m.data[1]*m.data[6]* m.data[15])*det;
	    data[6] = (+m.data[4]*m.data[2]-m.data[0]*m.data[6]* m.data[15])*det;
	    data[10] = (-m.data[4]*m.data[1]+m.data[0]*m.data[5]* m.data[15])*det;

	    data[3] = (m.data[9]*m.data[6]*m.data[3]
	               -m.data[5]*m.data[10]*m.data[3]
	               -m.data[9]*m.data[2]*m.data[7]
	               +m.data[1]*m.data[10]*m.data[7]
	               +m.data[5]*m.data[2]*m.data[11]
	               -m.data[1]*m.data[6]*m.data[11])*det;
	    data[7] = (-m.data[8]*m.data[6]*m.data[3]
	               +m.data[4]*m.data[10]*m.data[3]
	               +m.data[8]*m.data[2]*m.data[7]
	               -m.data[0]*m.data[10]*m.data[7]
	               -m.data[4]*m.data[2]*m.data[11]
	               +m.data[0]*m.data[6]*m.data[11])*det;
	    data[11] =(m.data[8]*m.data[5]*m.data[3]
	               -m.data[4]*m.data[9]*m.data[3]
	               -m.data[8]*m.data[1]*m.data[7]
	               +m.data[0]*m.data[9]*m.data[7]
	               +m.data[4]*m.data[1]*m.data[11]
	               -m.data[0]*m.data[5]*m.data[11])*det;
		
		
		
	}
	
	public Matrix4d Inverse()
	{
		Matrix4d result = new Matrix4d();
		result.SetInverse(this);
		return result;
	}
	
	public void Invert()
	{
		SetInverse(this);
	}
	
	public Vector3d TransformDirection(Vector3d vector)
	{
		return new Vector3d(
				  vector.x * data[0] +
	                vector.y * data[1] +
	                vector.z * data[2],

	                vector.x * data[4] +
	                vector.y * data[5] +
	                vector.z * data[6],

	                vector.x * data[8] +
	                vector.y * data[9] +
	                vector.z * data[10]
	            );
	}
	
	public Vector3d TransformInverseDirection(Vector3d vector)
	{
		return new Vector3d(
				  vector.x * data[0] +
	                vector.y * data[4] +
	                vector.z * data[8],

	                vector.x * data[1] +
	                vector.y * data[5] +
	                vector.z * data[9],

	                vector.x * data[2] +
	                vector.y * data[6] +
	                vector.z * data[10]
	            );
	}
	
	public Vector3d TransformInverse(Vector3d vector)
	{
		Vector3d tmp = new Vector3d(vector);
		tmp.x -= data[3];
        tmp.y -= data[7];
        tmp.z -= data[11];
        
        return new Vector3d(
        	    tmp.x * data[0] +
                tmp.y * data[4] +
                tmp.z * data[8],

                tmp.x * data[1] +
                tmp.y * data[5] +
                tmp.z * data[9],

                tmp.x * data[2] +
                tmp.y * data[6] +
                tmp.z * data[10]
            );
        }
	
	public Vector3d GetAxisVector(int i)
	{
		return new Vector3d(data[i], data[i+4], data[i+8]);

	}

	public void SetOrientationAndPos(Quaternion q, Vector3d pos)
	{
		data[0] = 1 - (2*q.j*q.j + 2*q.k*q.k);
        data[1] = 2*q.i*q.j + 2*q.k*q.r;
        data[2] = 2*q.i*q.k - 2*q.j*q.r;
        data[3] = pos.x;

        data[4] = 2*q.i*q.j - 2*q.k*q.r;
        data[5] = 1 - (2*q.i*q.i  + 2*q.k*q.k);
        data[6] = 2*q.j*q.k + 2*q.i*q.r;
        data[7] = pos.y;

        data[8] = 2*q.i*q.k + 2*q.j*q.r;
        data[9] = 2*q.j*q.k - 2*q.i*q.r;
        data[10] = 1 - (2*q.i*q.i  + 2*q.j*q.j);
        data[11] = pos.z;
		
	}
	
	public void FillGLArray(double[] array)
	{
		array[0] = data[0];
        array[1] = data[4];
        array[2] = data[8];
        array[3] = 0.0d;

        array[4] = data[1];
        array[5] = data[5];
        array[6] = data[9];
        array[7] = 0.0d;

        array[8] = data[2];
        array[9] = data[6];
        array[10] = data[10];
        array[11] = 0.0d;

        array[12] = data[3];
        array[13] = data[7];
        array[14] = data[11];
        array[15] = 1.0d;
		
		
	}
		
	}

