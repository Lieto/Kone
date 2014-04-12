package primitives;

import javax.media.opengl.GL2;

import kone.Body;
import kone.CollisionSphere;
import kone.core.Matrix3d;
import kone.core.Quaternion;
import kone.core.Random;
import kone.core.Vector3d;

import com.jogamp.opengl.util.gl2.GLUT;


public class Ball extends CollisionSphere {
	
	public Ball()
	{
		body = new Body();
	}
	
	public void render(GL2 gl2)
	{
		double[] mat = new double[16];
		
		body.getGLTransform(mat);
		
		if (body.getAwake()) gl2.glColor3d(1.0d, 0.7d, 0.7d);
		else gl2.glColor3d(0.7d, 0.7d, 1.0d);
		
		gl2.glPushMatrix();
		gl2.glMultMatrixd(mat, 0);
		
		GLUT glut = new GLUT();
		glut.glutSolidSphere(radius, 20, 20);
		
		gl2.glPopMatrix();
	}
	
	public void renderShadow(GL2 gl2)
	{
		double[] mat = new double[16];
		
		body.getGLTransform(mat);
		
		gl2.glPushMatrix();
		gl2.glScaled(1.0d,  0.0d,  1.0d);
		gl2.glMultMatrixd(mat, 0);
		
		GLUT glut = new GLUT();
		glut.glutSolidSphere(radius, 20, 20);
		
		gl2.glPopMatrix();
	}
	
	public void setState(Vector3d position,
			Quaternion orientation,
			double radius,
			Vector3d velocity)
	{
		body.setPosition(position);
		body.setOrientation(orientation);
		body.setVelocity(velocity);
		body.setRotation(new Vector3d());
		
		this.radius = radius;
		
		double mass = 4.0d*0.3333d*3.1415d*radius*radius*radius;
		
		body.setMass(mass);
		
		Matrix3d tensor = new Matrix3d();
		double coeff = 0.4d*mass*radius*radius;
		
		tensor.SetInertiaTensorCoeffs(coeff, coeff, coeff);
		body.setInertiaTensor(tensor);
		
		body.setLinearDamping(0.95d);
		body.setAngularDamping(0.8d);
		body.ClearAccumulators();
		body.setAcceleration(0.0d, -10.0d, 0.0d);

		
		body.setAwake();
		
		body.CalculateDerivedData();
	}
	
	public void random()
	{
		final Vector3d minPos = new Vector3d(-5.0d, 5.0d, -5.0d);
		final Vector3d maxPos = new Vector3d(5.0d, 10.0d, 5.0d);
		
		setState(Random.Vector(minPos, maxPos),
				Random.Quaternion(),
				Random.Double(0.5d, 1.5d),
				new Vector3d());
	}

}
