package primitives;

import javax.media.opengl.GL2;

import kone.Body;
import kone.CollisionSphere;
import kone.core.Matrix3d;
import kone.core.Vector3d;

import com.jogamp.opengl.util.gl2.GLUT;

public class AmmoRound extends CollisionSphere {

	public ShotType type;
	public int startTime;
	
	public AmmoRound()
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
	
	public void setState(ShotType shotType)
	{
		type = shotType;
		
		switch(type)
		{
			case PISTOL:
				body.setMass(1.5d);
				body.setVelocity(new Vector3d(0.0d, 0.0d, 20.0d));
				body.setAcceleration(new Vector3d(0.0d, -0.5d, 0.0d));
				body.setDamping(0.99d,  0.8d);
				radius = 0.2d;
				break;
				
			case ARTILLERY:
				body.setMass(200.0d);
				body.setVelocity(new Vector3d(0.0d, 30.0d, 40.0d));
				body.setAcceleration(new Vector3d(0.0d, -21.0d, 0.0d));
				body.setDamping(0.99d,  0.8d);
				radius = 0.4d;
				break;
				
			case FIREBALL:
				body.setMass(4.0d);
				body.setVelocity(new Vector3d(0.0d, -0.5d, 10.0d));
				body.setAcceleration(new Vector3d(0.0d, 0.3d, 0.0d));
				body.setDamping(0.9d,  0.8d);
				radius = 0.6d;
				break;
				
			case LASER:
				body.setMass(0.1d);
				body.setVelocity(new Vector3d(0.0d, 0.0d, 100.0d));
				body.setAcceleration(new Vector3d(0.0d, 0.0d, 0.0d));
				body.setDamping(0.99d,  0.8d);
				radius = 0.2d;
				break;
			
		}
		
		body.setCanSleep(false);
		body.setAwake(true);
		
		Matrix3d tensor = new Matrix3d();
		double coeff = 0.4d*body.getMass()*radius*radius;
		tensor.SetInertiaTensorCoeffs(coeff, coeff, coeff);
		body.setInertiaTensor(tensor);
		
		body.setPosition(new Vector3d(0.0d, 1.5d, 0.0d));
		
		//startTime = 
		body.CalculateDerivedData();
		calculateInternals();
		
		
	}
	
	

}
