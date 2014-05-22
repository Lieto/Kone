package kone;

import kone.core.Matrix3d;
import kone.core.Matrix4d;
import kone.core.Quaternion;
import kone.core.Vector3d;

public class Body {
	/**
     * A rigid body is the basic simulation object in the physics
     * core.
     *
     * It has position and orientation data, along with first
     * derivatives. It can be integrated forward through time, and
     * have forces, torques and impulses (linear or angular) applied
     * to it. The rigid body manages its state and allows access
     * through a set of methods.
     *
     * A ridid body contains 64 words (the size of which is given
     * by the precision: sizeof(real)). It contains no virtual
     * functions, so should take up exactly 64 words in memory. Of
     * this total 15 words are padding, distributed among the
     * Vector3 data members.
     */
	
	/**
     * @name Characteristic Data and State
     *
     * This data holds the state of the rigid body. There are two
     * sets of data: characteristics and state.
     *
     * Characteristics are properties of the rigid body
     * independent of its current kinematic situation. This
     * includes mass, moment of inertia and damping
     * properties. Two identical rigid bodys will have the same
     * values for their characteristics.
     *
     * State includes all the characteristics and also includes
     * the kinematic situation of the rigid body in the current
     * simulation. By setting the whole state data, a rigid body's
     * exact game state can be replicated. Note that state does
     * not include any forces applied to the body. Two identical
     * rigid bodies in the same simulation will not share the same
     * state values.
     *
     * The state values make up the smallest set of independent
     * data for the rigid body. Other state data is calculated
     * from their current values. When state data is changed the
     * dependent values need to be updated: this can be achieved
     * either by integrating the simulation, or by calling the
     * calculateInternals function. This two stage process is used
     * because recalculating internals can be a costly process:
     * all state changes should be carried out at the same time,
     * allowing for a single call.
     *
     * @see calculateInternals
     */
    /*@{*/
    /**
     * Holds the inverse of the mass of the rigid body. It
     * is more useful to hold the inverse mass because
     * integration is simpler, and because in real time
     * simulation it is more useful to have bodies with
     * infinite mass (immovable) than zero mass
     * (completely unstable in numerical simulation).
     */
	
	protected double inverseMass;
	
	protected double sleepEpsilon = 0.3d;
	
	public void setMass(double mass)
	{
		if (mass != 0) inverseMass = 1.0d / mass;
	}
	
	public double getMass()
	{
		if (inverseMass == 0)
		{
			return Double.MAX_VALUE;
		}
		else
		{
			return 1.0d / inverseMass;
		}
	}
	
	public void setInverseMass(double inverseMass)
	{
		this.inverseMass = inverseMass;
	}
	
	public double getInverseMass()
	{
		return inverseMass;
	}
	
	public boolean hasFiniteMass()
	{
		if (inverseMass >= 0.0d) return true;
		return false;
	}
	
	public void setInertiaTensor(Matrix3d inertiaTensor)
	{
		inverseInertiaTensor.SetInverse(inertiaTensor);
		CheckInverseInertiaTensor(inverseInertiaTensor);
	}
	
	public void getInertiaTensor(Matrix3d inertiaTensor)
	{
		inertiaTensor.SetInverse(inverseInertiaTensor);
	}
	
	public Matrix3d getInertiaTensor()
	{
		Matrix3d it = new Matrix3d();
		getInertiaTensor(it);
		return it;
	}
	
	public void getInertiaTensorWorld(Matrix3d inertiaTensor)
	{
		inertiaTensor.SetInverse(inverseInertiaTensorWorld);
	}
	
	public Matrix3d getInertiaTensorWorld()
	{
		Matrix3d it = new Matrix3d();
		getInertiaTensorWorld(it);
		return it;
	}
	
	
	
	public Matrix3d getInverseInertiaTensor() {
		return inverseInertiaTensor;
	}


	public void setInverseInertiaTensor(Matrix3d inverseInertiaTensor) {
		CheckInverseInertiaTensor(inverseInertiaTensor);
		this.inverseInertiaTensor = inverseInertiaTensor;
	}


	public double getLinearDamping() {
		return linearDamping;
	}


	public void setLinearDamping(double linearDamping) {
		this.linearDamping = linearDamping;
	}


	public double getAngularDamping() {
		return angularDamping;
	}


	public void setAngularDamping(double angularDamping) {
		this.angularDamping = angularDamping;
	}


	public Vector3d getPosition() {
		return position;
	}


	public void setPosition(Vector3d position) {
		this.position = position;
	}


	public Quaternion getOrientation() {
		return orientation;
	}


	public void setOrientation(Quaternion orientation) {
		this.orientation = orientation;
	}


	public Vector3d getVelocity() {
		return velocity;
	}


	public void setVelocity(Vector3d velocity) {
		this.velocity = velocity;
	}


	public Vector3d getRotation() {
		return rotation;
	}


	public void setRotation(Vector3d rotation) {
		this.rotation = rotation;
	}


	public Matrix3d getInverseInertiaTensorWorld() {
		return inverseInertiaTensorWorld;
	}


	public void setInverseInertiaTensorWorld(Matrix3d inverseInertiaTensorWorld) {
		this.inverseInertiaTensorWorld = inverseInertiaTensorWorld;
	}


	public double getMotion() {
		return motion;
	}


	public void setMotion(double motion) {
		this.motion = motion;
	}


	public boolean isAwake() {
		return isAwake;
	}


	public void setAwake(boolean isAwake) {
		this.isAwake = isAwake;
	}


	public boolean isCanSleep() {
		return canSleep;
	}


	public void setCanSleep(boolean canSleep) {
		this.canSleep = canSleep;
	}


	public Matrix4d getTransformMatrix() {
		return transformMatrix;
	}


	public void setTransformMatrix(Matrix4d transformMatrix) {
		this.transformMatrix = transformMatrix;
	}


	public Vector3d getForceAccum() {
		return forceAccum;
	}


	public void setForceAccum(Vector3d forceAccum) {
		this.forceAccum = forceAccum;
	}


	public Vector3d getTorqueAccum() {
		return torqueAccum;
	}


	public void setTorqueAccum(Vector3d torqueAccum) {
		this.torqueAccum = torqueAccum;
	}


	public Vector3d getAcceleration() {
		return acceleration;
	}


	public void setAcceleration(Vector3d acceleration) {
		this.acceleration = new Vector3d(acceleration);
	}


	public Vector3d getLastFrameAcceleration() {
		return lastFrameAcceleration;
	}


	public void setLastFrameAcceleration(Vector3d lastFrameAcceleration) {
		this.lastFrameAcceleration = lastFrameAcceleration;
	}
	
	public void getTransform(Matrix4d transform)
	{
		transform = new Matrix4d(transformMatrix);
	
	}
	
	public void getTransform(double[] matrix)
	{
		for (int i = 0; i < 12; i++)
		{
			matrix[i] = transformMatrix.data[i];
		}
		
		matrix[12] = matrix[14] = matrix[14] = 0.0d;
		matrix[15] = 1.0d;
		
	}
	
	public void getGLTransform(double[] matrix)
	{
		matrix[0] = transformMatrix.data[0];
	    matrix[1] = transformMatrix.data[4];
	    matrix[2] = transformMatrix.data[8];
	    matrix[3] = 0.0d;

	    matrix[4] = transformMatrix.data[1];
	    matrix[5] = transformMatrix.data[5];
	    matrix[6] = transformMatrix.data[9];
	    matrix[7] = 0.0d;

	    matrix[8] = transformMatrix.data[2];
	    matrix[9] = transformMatrix.data[6];
	    matrix[10] = transformMatrix.data[10];
	    matrix[11] = 0.0d;

	    matrix[12] = transformMatrix.data[3];
	    matrix[13] = transformMatrix.data[7];
	    matrix[14] = transformMatrix.data[11];
	    matrix[15] = 1.0d;
		
		
	}
	
	public Matrix4d getTransform()
	{
		return transformMatrix;
		
	}


	/**
     * Holds the inverse of the body's inertia tensor. The
     * intertia tensor provided must not be degenerate
     * (that would mean the body had zero inertia for
     * spinning along one axis). As long as the tensor is
     * finite, it will be invertible. The inverse tensor
     * is used for similar reasons to the use of inverse
     * mass.
     *
     * The inertia tensor, unlike the other variables that
     * define a rigid body, is given in body space.
     *
     * @see inverseMass
     */
	protected Matrix3d inverseInertiaTensor;
	
	/**
     * Holds the amount of damping applied to linear
     * motion.  Damping is required to remove energy added
     * through numerical instability in the integrator.
     */
	protected double linearDamping;
	
	/**
     * Holds the amount of damping applied to angular
     * motion.  Damping is required to remove energy added
     * through numerical instability in the integrator.
     */
	protected double angularDamping;
	
	protected Vector3d position;
	
	protected Quaternion orientation;
	
	protected Vector3d velocity;
	
	protected Vector3d rotation;
	
	/*@}*/


    /**
     * @name Derived Data
     *
     * These data members hold information that is derived from
     * the other data in the class.
     */
    /*@{*/

    /**
     * Holds the inverse inertia tensor of the body in world
     * space. The inverse inertia tensor member is specified in
     * the body's local space.
     *
     * @see inverseInertiaTensor
     */
	protected Matrix3d inverseInertiaTensorWorld;
	
	/**
     * Holds the amount of motion of the body. This is a recency
     * weighted mean that can be used to put a body to sleap.
     */
	protected double motion;
	
	/**
     * A body can be put to sleep to avoid it being updated
     * by the integration functions or affected by collisions
     * with the world.
     */
	protected boolean isAwake;
	
	 /**
     * Some bodies may never be allowed to fall asleep.
     * User controlled bodies, for example, should be
     * always awake.
     */
	protected boolean canSleep;
	
	/**
     * Holds a transform matrix for converting body space into
     * world space and vice versa. This can be achieved by calling
     * the getPointIn*Space functions.
     *
     * @see getPointInLocalSpace
     * @see getPointInWorldSpace
     * @see getTransform
     */
	protected Matrix4d transformMatrix;
	
	/*@}*/


    /**
     * @name Force and Torque Accumulators
     *
     * These data members store the current force, torque and
     * acceleration of the rigid body. Forces can be added to the
     * rigid body in any order, and the class decomposes them into
     * their constituents, accumulating them for the next
     * simulation step. At the simulation step, the accelerations
     * are calculated and stored to be applied to the rigid body.
     */
    /*@{*/

    /**
     * Holds the accumulated force to be applied at the next
     * integration step.
     */
	protected Vector3d forceAccum;
	
	/**
     * Holds the accumulated torque to be applied at the next
     * integration step.
     */
	protected Vector3d torqueAccum;
	
	/**
     * Holds the acceleration of the rigid body.  This value
     * can be used to set acceleration due to gravity (its primary
     * use), or any other constant acceleration.
     */
	protected Vector3d acceleration;
	
	/**
     * Holds the linear acceleration of the rigid body, for the
     * previous frame.
     */
	protected Vector3d lastFrameAcceleration;
	
	/*@}*/
	
	/**
     * @name Constructor and Destructor
     *
     * There are no data members in the rigid body class that are
     * created on the heap. So all data storage is handled
     * automatically.
     */
    /*@{*/
	public Body()
	{
		inverseInertiaTensor = new Matrix3d();
		position = new Vector3d();
		orientation = new Quaternion();
		velocity = new Vector3d();
		rotation = new Vector3d();
		inverseInertiaTensorWorld = new Matrix3d();
		transformMatrix = new Matrix4d();
		forceAccum = new Vector3d();
		torqueAccum = new Vector3d();
		acceleration = new Vector3d();
		lastFrameAcceleration = new Vector3d();
	}

    /*@}*/


    /**
     * @name Integration and Simulation Functions
     *
     * These functions are used to simulate the rigid body's
     * motion over time. A normal application sets up one or more
     * rigid bodies, applies permanent forces (i.e. gravity), then
     * adds transient forces each frame, and integrates, prior to
     * rendering.
     *
     * Currently the only integration function provided is the
     * first order Newton Euler method.
     */
    /*@{*/

    /**
     * Calculates internal data from state data. This should be called
     * after the body's state is altered directly (it is called
     * automatically during integration). If you change the body's state
     * and then intend to integrate before querying any data (such as
     * the transform matrix), then you can ommit this step.
     */
	
	public void CalculateDerivedData()
	{
		orientation.Normalise();
		
		CalculateTransformMatrix(transformMatrix, position, orientation);
		
		TransformInertiaTensor(inverseInertiaTensorWorld,
				orientation,
				inverseInertiaTensor,
				transformMatrix);
	}
	

    private void TransformInertiaTensor(Matrix3d iitWorld,
			Quaternion q, Matrix3d iitBody,
			Matrix4d rotmat)
    {
    		double t4 = rotmat.data[0]*iitBody.data[0]+
    	        rotmat.data[1]*iitBody.data[3]+
    	        rotmat.data[2]*iitBody.data[6];
    	    double t9 = rotmat.data[0]*iitBody.data[1]+
    	        rotmat.data[1]*iitBody.data[4]+
    	        rotmat.data[2]*iitBody.data[7];
    	    double t14 = rotmat.data[0]*iitBody.data[2]+
    	        rotmat.data[1]*iitBody.data[5]+
    	        rotmat.data[2]*iitBody.data[8];
    	    double t28 = rotmat.data[4]*iitBody.data[0]+
    	        rotmat.data[5]*iitBody.data[3]+
    	        rotmat.data[6]*iitBody.data[6];
    	    double t33 = rotmat.data[4]*iitBody.data[1]+
    	        rotmat.data[5]*iitBody.data[4]+
    	        rotmat.data[6]*iitBody.data[7];
    	    double t38 = rotmat.data[4]*iitBody.data[2]+
    	        rotmat.data[5]*iitBody.data[5]+
    	        rotmat.data[6]*iitBody.data[8];
    	    double t52 = rotmat.data[8]*iitBody.data[0]+
    	        rotmat.data[9]*iitBody.data[3]+
    	        rotmat.data[10]*iitBody.data[6];
    	    double t57 = rotmat.data[8]*iitBody.data[1]+
    	        rotmat.data[9]*iitBody.data[4]+
    	        rotmat.data[10]*iitBody.data[7];
    	    double t62 = rotmat.data[8]*iitBody.data[2]+
    	        rotmat.data[9]*iitBody.data[5]+
    	        rotmat.data[10]*iitBody.data[8];

    	    iitWorld.data[0] = t4*rotmat.data[0]+
    	        t9*rotmat.data[1]+
    	        t14*rotmat.data[2];
    	    iitWorld.data[1] = t4*rotmat.data[4]+
    	        t9*rotmat.data[5]+
    	        t14*rotmat.data[6];
    	    iitWorld.data[2] = t4*rotmat.data[8]+
    	        t9*rotmat.data[9]+
    	        t14*rotmat.data[10];
    	    iitWorld.data[3] = t28*rotmat.data[0]+
    	        t33*rotmat.data[1]+
    	        t38*rotmat.data[2];
    	    iitWorld.data[4] = t28*rotmat.data[4]+
    	        t33*rotmat.data[5]+
    	        t38*rotmat.data[6];
    	    iitWorld.data[5] = t28*rotmat.data[8]+
    	        t33*rotmat.data[9]+
    	        t38*rotmat.data[10];
    	    iitWorld.data[6] = t52*rotmat.data[0]+
    	        t57*rotmat.data[1]+
    	        t62*rotmat.data[2];
    	    iitWorld.data[7] = t52*rotmat.data[4]+
    	        t57*rotmat.data[5]+
    	        t62*rotmat.data[6];
    	    iitWorld.data[8] = t52*rotmat.data[8]+
    	        t57*rotmat.data[9]+
    	        t62*rotmat.data[10];
		
		
	}


	private void CalculateTransformMatrix(Matrix4d transformMatrix,
			Vector3d position, Quaternion orientation2)
	{
		 transformMatrix.data[0] = 1-2*orientation.j*orientation.j-
			        2*orientation.k*orientation.k;
			    transformMatrix.data[1] = 2*orientation.i*orientation.j -
			        2*orientation.r*orientation.k;
			    transformMatrix.data[2] = 2*orientation.i*orientation.k +
			        2*orientation.r*orientation.j;
			    transformMatrix.data[3] = position.x;

			    transformMatrix.data[4] = 2*orientation.i*orientation.j +
			        2*orientation.r*orientation.k;
			    transformMatrix.data[5] = 1-2*orientation.i*orientation.i-
			        2*orientation.k*orientation.k;
			    transformMatrix.data[6] = 2*orientation.j*orientation.k -
			        2*orientation.r*orientation.i;
			    transformMatrix.data[7] = position.y;

			    transformMatrix.data[8] = 2*orientation.i*orientation.k -
			        2*orientation.r*orientation.j;
			    transformMatrix.data[9] = 2*orientation.j*orientation.k +
			        2*orientation.r*orientation.i;
			    transformMatrix.data[10] = 1-2*orientation.i*orientation.i-
			        2*orientation.j*orientation.j;
			    transformMatrix.data[11] = position.z;
		
	}
	
	private void CheckInverseInertiaTensor(Matrix3d iitWorld)
	{
		
	}


	/**
     * Integrates the rigid body forward in time by the given amount.
     * This function uses a Newton-Euler integration method, which is a
     * linear approximation to the correct integral. For this reason it
     * may be inaccurate in some cases.
     */
	public void Integrate(double duration, IntegrationMethod method)
	{
		if (!isAwake) return;
		
		lastFrameAcceleration = new Vector3d(acceleration);
		
		lastFrameAcceleration.addScaledVector(forceAccum, inverseMass);
		
		Vector3d lastFrameVelocity = new Vector3d(getVelocity());
		
		if (method == IntegrationMethod.EULER)
		{
			//Newton-Euler numerical integration
				
			Vector3d angularAcceleration = new Vector3d(inverseInertiaTensorWorld.Transform(torqueAccum));
		
			velocity.addScaledVector(lastFrameAcceleration, duration);
		
			rotation.addScaledVector(angularAcceleration, duration);
		
			velocity.Multiply(Math.pow(linearDamping, duration));
			rotation.Multiply(Math.pow(angularDamping, duration));
		
			position.addScaledVector(velocity, duration);
			orientation.AddScaledVector(rotation, duration);
		
			velocity.Multiply(Math.pow(linearDamping, duration));
			rotation.Multiply(Math.pow(angularDamping, duration));
		}
		else
		{
			Vector3d angularAcceleration = new Vector3d(inverseInertiaTensorWorld.Transform(torqueAccum));
			//Runge-Kutta numerical integration
			Vector3d k1, k2, k3, k4;
			
			k1 = new Vector3d(lastFrameAcceleration);
			
			
			k2 = new Vector3d(lastFrameAcceleration);
			k2.addScaledVector(k1, duration/2.0d);
			
			k3 = new Vector3d(lastFrameAcceleration);
			k3.addScaledVector(k2, duration/2.0d);
			
			k4 = new Vector3d(lastFrameAcceleration);
			k4.addScaledVector(k3, duration);
			
			Vector3d k = new Vector3d(k1);
			k.addScaledVector(k2, 2);
			k.addScaledVector(k3, 2);
			k.Add(k4);
			k.Multiply(1.0d/6.0d);
			
			//The acceleration is constant in this interval so we can write directly
			velocity.addScaledVector(k, duration);
			rotation.addScaledVector(angularAcceleration, duration);
			
			velocity.Multiply(Math.pow(linearDamping, duration));
			rotation.Multiply(Math.pow(angularDamping, duration));
			
			// Position is different: dx/dt = ;
			k1 = new Vector3d(lastFrameVelocity);
			
			
			k2 = new Vector3d(lastFrameVelocity);
			k2.addScaledVector(k1, duration/2.0d);
			
			k3 = new Vector3d(lastFrameVelocity);
			k3.addScaledVector(k2, duration/2.0d);
			
			k4 = new Vector3d(lastFrameVelocity);
			k4.addScaledVector(k3, duration);
			
			k = new Vector3d(k1);
			k.addScaledVector(k2, 2);
			k.addScaledVector(k3, 2);
			k.Add(k4);
			k.Multiply(1.0d/6.0d);
			
			position.addScaledVector(k, duration);
			orientation.AddScaledVector(rotation, duration);
			
			velocity.Multiply(Math.pow(linearDamping, duration));
			rotation.Multiply(Math.pow(angularDamping, duration));
			
			
			
		}
		CalculateDerivedData();
		
		/*
		
		Vector3d vel = new Vector3d(velocity);
		double mag = vel.Magnitude();
		
		if (mag > 50.0d)
		{
			vel.Normalise();
			vel.Multiply(50.0d);
			setVelocity(vel);
		}
		*/
		ClearAccumulators();
		
		if (canSleep)
		{
			double currentMotion = velocity.ScalarProduct(velocity) + rotation.ScalarProduct(rotation);
			double bias = Math.pow(0.5d,  duration);
			motion = bias*motion + (1-bias)*currentMotion;
			
			if (motion < sleepEpsilon) setAwake(false);
			else if (motion > 10 * sleepEpsilon) motion = 10 * sleepEpsilon;
		}
		
		
	}
	
	/*@}*/

	/**
     * @name Force, Torque and Acceleration Set-up Functions
     *
     * These functions set up forces and torques to apply to the
     * rigid body.
     */
    /*@{*/

    /**
     * Clears the forces and torques in the accumulators. This will
     * be called automatically after each intergration step.
     */
	public void ClearAccumulators()
	{
		forceAccum.clear();
		torqueAccum.clear();
	}
	
	/**
     * Adds the given force to centre of mass of the rigid body.
     * The force is expressed in world-coordinates.
     *
     * @param force The force to apply.
     */
	public void AddForce(Vector3d force)
	{
		forceAccum.Add(force);
		isAwake = true;
	}
	
	/**
     * Adds the given force to the given point on the rigid body.
     * Both the force and the
     * application point are given in world space. Because the
     * force is not applied at the centre of mass, it may be split
     * into both a force and torque.
     *
     * @param force The force to apply.
     *
     * @param point The location at which to apply the force, in
     * world-coordinates.
     */
	public void AddForceAtPoint(Vector3d force, Vector3d point)
	{
		Vector3d pt = new Vector3d(point);
		pt.Substract(position);
		
		forceAccum.Add(force);
		torqueAccum.Add(pt.CrossProduct(force));
		
		isAwake = true;
		
	}
	
	 public Vector3d GetPointInWorldSpace(Vector3d point) {
		
		return transformMatrix.Transform(point);
	}

	/**
     * Adds the given force to the given point on the rigid body.
     * The direction of the force is given in world coordinates,
     * but the application point is given in body space. This is
     * useful for spring forces, or other forces fixed to the
     * body.
     *
     * @param force The force to apply.
     *
     * @param point The location at which to apply the force, in
     * body-coordinates.
     */
	public void AddForceAtBodyPoint(Vector3d force, Vector3d point)
	{
		Vector3d pt = new Vector3d(GetPointInWorldSpace(point));
		AddForceAtPoint(force, pt);
		
		isAwake = true;
	}
	
	/**
     * Adds the given torque to the rigid body.
     * The force is expressed in world-coordinates.
     *
     * @param torque The torque to apply.
     */
	public void AddTorque(Vector3d torque)
	{
		torqueAccum.Add(torque);
		isAwake = true;
	}
	
	
	/**
     * Sets the constant acceleration of the rigid body by component.
     *
     * @param x The x coordinate of the new acceleration of the rigid
     * body.
     *
     * @param y The y coordinate of the new acceleration of the rigid
     * body.
     *
     * @param z The z coordinate of the new acceleration of the rigid
     * body.
     */
	public void setAcceleration(double x, double y, double z)
	{
		acceleration.x = x;
		acceleration.y = y;
		acceleration.z = z;
	}
	
	/**
     * Fills the given vector with the acceleration of the rigid body.
     *
     * @param acceleration A pointer to a vector into which to write
     * the acceleration. The acceleration is given in world local space.
     */
	public void getAcceleration(Vector3d acceleration)
	{
		acceleration = this.acceleration;
	}

	public void setDamping(double d, double e) {
		linearDamping = d;
		angularDamping = e;
		
	}

	public boolean getAwake() {
		
		return isAwake;
	}

	public void setAwake() {
		
		isAwake = true;
		
	}

	public void addVelocity(Vector3d vector3d) {
		velocity.Add(vector3d);
		
	}

	public void addRotation(Vector3d vector3d) {
		
		rotation.Add(vector3d);
	}

	public Vector3d GetPointInLocalSpace(Vector3d point) {
		
		return transformMatrix.TransformInverse(point);
	}

	public Vector3d GetDirectionInLocalSpace(Vector3d direction) {
		
		return transformMatrix.TransformInverseDirection(direction);
	}

	public void RungeKuttaIntegration(double duration, Vector3dFunction forceFunction) {
		
		double halfStep = duration/2;
		
		if (!isAwake) return;
		
		lastFrameAcceleration = new Vector3d(acceleration);
		
		Vector3d angularAcceleration = new Vector3d(inverseInertiaTensorWorld.Transform(torqueAccum));
		
		//lastFrameAcceleration.addScaledVector(forceAccum, inverseMass);
		
		Vector3d lastFrameVelocity = new Vector3d(getVelocity());
		
		forceFunction.input = getPosition();
		Vector3d force = new Vector3d(forceFunction.solve());
		lastFrameAcceleration.addScaledVector(force, inverseMass);
		
		Vector3d k1 = lastFrameAcceleration.NewVectorMultiply(duration);
		
		
		forceFunction.input = getPosition();
		forceFunction.input.addScaledVector(k1, 0.5);
		force = new Vector3d(forceFunction.solve());
		lastFrameAcceleration = new Vector3d(acceleration);
		lastFrameAcceleration.addScaledVector(force, inverseMass);
		
		Vector3d k2 = lastFrameAcceleration.NewVectorMultiply(duration);
		
		
		
		forceFunction.input = getPosition();
		forceFunction.input.addScaledVector(k2, 0.5);
		force = new Vector3d(forceFunction.solve());
		lastFrameAcceleration = new Vector3d(acceleration);
		lastFrameAcceleration.addScaledVector(force, inverseMass);
		
		Vector3d k3 = lastFrameAcceleration.NewVectorMultiply(duration);
		
		
		
		forceFunction.input = getPosition();
		forceFunction.input.Add(k3);
		force = new Vector3d(forceFunction.solve());
		lastFrameAcceleration = new Vector3d(acceleration);
		lastFrameAcceleration.addScaledVector(force, inverseMass);
		
		Vector3d k4 = lastFrameAcceleration.NewVectorMultiply(duration);
		
		Vector3d v = new Vector3d(k1);
		v.addScaledVector(k2, 2);
		v.addScaledVector(k3, 2);
		v.Add(k4);
		v.Multiply(1.0d/6.0d);
		
		v.Add(getVelocity());
		setVelocity(v);
		
		rotation.addScaledVector(angularAcceleration, duration);
	
		velocity.Multiply(Math.pow(linearDamping, duration));
		rotation.Multiply(Math.pow(angularDamping, duration));
		
		k1 = v.NewVectorMultiply(duration);
		k2 = new Vector3d(k1);
		k3 = new Vector3d(k1);
		k4 = new Vector3d(k1);
		
		Vector3d newPos = new Vector3d(k1);
		newPos.addScaledVector(k2, 2);
		newPos.addScaledVector(k3, 2);
		newPos.Add(k4);
		newPos.Multiply(1.0d/6.0d);
		newPos.Add(getPosition());
		
		setPosition(newPos);
		orientation.AddScaledVector(rotation, duration);
		
		velocity.Multiply(Math.pow(linearDamping, duration));
		rotation.Multiply(Math.pow(angularDamping, duration));
		
	}
	
	public void Update(double duration)
	{
		double halfduration = 0.5*duration;
		double sixthduration = duration / 6.0d;
		
		Vector3d newPosition, newVelocity, newRotation;
		Quaternion newOrientation;
		
		Matrix3d newRotationMatrix;
		
		//A1 = G(t, S0), B1 = S0 + (dt /3 ) * A)
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
