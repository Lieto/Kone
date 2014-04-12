package primitives;

import javax.media.opengl.GL2;

import kone.Body;
import kone.CollisionBox;
import kone.core.Matrix3d;
import kone.core.Quaternion;
import kone.core.Random;
import kone.core.Vector3d;

import com.jogamp.opengl.util.gl2.GLUT;



public class Box extends CollisionBox {
	
	public boolean isOverlapping;
	
	public Box()
	{
		body = new Body();
	}
	
	public void render(GL2 gl2)
	{
		double[] mat = new double[16];
		body.getGLTransform(mat);
		
		gl2.glPushMatrix();
		gl2.glMultMatrixd(mat, 0);
		gl2.glScaled(halfSize.x*2, halfSize.y*2, halfSize.z*2);
		
		GLUT glut = new GLUT();
		glut.glutSolidCube(1.0f);
		gl2.glPopMatrix();
		
	}
	
	public void renderShadow(GL2 gl2)
	{
		double[] mat = new double[16];
		body.getGLTransform(mat);
		
		gl2.glPushMatrix();
		gl2.glScaled(1.0d,  0.0d,  1.0d);
		gl2.glMultMatrixd(mat, 0);
		gl2.glScaled(halfSize.x*2, halfSize.y*2, halfSize.z*2);
		
		GLUT glut = new GLUT();
		glut.glutSolidCube(1.0f);
		gl2.glPopMatrix();
		
		
	}

	public void setState(Vector3d position,
			Quaternion orientation,
			Vector3d extents,
			Vector3d velocity)
	{
		body.setPosition(position);
		body.setOrientation(orientation);
		body.setVelocity(velocity);
		body.setRotation(new Vector3d());
		halfSize = extents;
		
		double mass = halfSize.x*halfSize.y*halfSize.z*8.0d;
		
		body.setMass(mass);
		
		Matrix3d tensor = new Matrix3d();
		
		tensor.SetBlockInertiaTensor(halfSize, mass);
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
		final Vector3d minSize = new Vector3d(0.5d, 0.5d, 0.5d);
		final Vector3d maxSize = new Vector3d(4.5d, 1.5d, 1.5d);
		
		setState(Random.Vector(minPos, maxPos),
				Random.Quaternion(),
				Random.Vector(minSize, maxSize),
				new Vector3d());
	}

}
