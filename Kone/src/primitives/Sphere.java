package primitives;

import javax.media.opengl.GL2;

import kone.Body;
import kone.CollisionSphere;
import kone.core.Matrix3d;
import kone.core.Vector3d;

import com.jogamp.opengl.util.gl2.GLUT;


public class Sphere extends CollisionSphere {
	
	public Sphere()
	{
		body = new Body();
	
	}
	
	public void render(GL2 gl2)
	{
		double[] mat = new double[16];
		body.getGLTransform(mat);
		
		gl2.glPushMatrix();
		gl2.glMultMatrixd(mat, 0);
		
		GLUT glut = new GLUT();
		glut.glutSolidSphere(radius, 20, 20);
		
		gl2.glPopMatrix();
		
	}
	
	public void setState()
	{
		body.setMass(1.0d);
		body.setVelocity(new Vector3d(0.0d, 0.0d, 0.0d));
		body.setAcceleration(new Vector3d(0.0d, 0.0d, 0.0d));
		body.setDamping(0.99d,  0.8d);
		radius = 1.0d;
		
		body.setCanSleep(false);
		body.setAwake(true);
		
		Matrix3d tensor = new Matrix3d();
		double coeff = 0.4d*body.getMass()*radius*radius;
		tensor.SetInertiaTensorCoeffs(coeff, coeff, coeff);
		body.setInertiaTensor(tensor);
		
		body.setPosition(new Vector3d(0.0d, 0.0d, 0.0d));
		
		//startTime = 
		body.CalculateDerivedData();
		calculateInternals();
		
		
	}
	

}
