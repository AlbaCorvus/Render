package jschimera.loc.render.physics;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import jschimera.loc.render.physics.observe.Observable;

public interface PhysicalObject extends Observable{
	
	public void defineContactResponse(PhysicalObject object, Contact contact, Vector2f hitNormal);
	
	float getAngularDamping();
	
	float getLinearDamping();
	
	boolean isFixedRotation();
	
	boolean isContinuousCollision();
	
	float getGravityScale();
	
	float getAngularVelocity();
	
	float getMass();
	
	void setRawBody(Body body);
	
	Body getBody();
	
	Vector2f getOffset();
	
	float getFriction();
	
	boolean isSensor();
	
	Vector2f getOrigin();
	
	BodyType getBodyType();
	
	Vector2f getScale();
	
	float getRotation();
	
	Vector2f getPosition();
	
	void setCustomCallback(Runnable runnable);
	
	void reset();
	
	Physics2D.STATE getState();
	
	void setState(Physics2D.STATE state);
	
}
