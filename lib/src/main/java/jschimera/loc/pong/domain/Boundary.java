package jschimera.loc.pong.domain;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import jschimera.loc.render.physics.PhysicalObject;
import jschimera.loc.render.physics.Physics2D.STATE;

public class Boundary implements PhysicalObject{

	private final Vector2f position;
	private final Vector2f scaler;
	private Body body;
	protected Runnable runnable;

	public Boundary(Vector2f position, Vector2f scaler) {
		this.position = position;
		this.scaler = scaler;
	}

	@Override
	public void receiveUpdate() {

	}

	@Override
	public void defineContactResponse(PhysicalObject object, Contact contact, Vector2f hitNormal) {
		object.getBody().setLinearVelocity(
				new Vec2(object.getBody().getLinearVelocity().x, object.getBody().getLinearVelocity().y * -1.0f));
	}

	@Override
	public float getAngularDamping() {
		return 0;
	}

	@Override
	public float getLinearDamping() {
		return 0;
	}

	@Override
	public boolean isFixedRotation() {
		return false;
	}

	@Override
	public boolean isContinuousCollision() {
		return false;
	}

	@Override
	public float getGravityScale() {
		return 0;
	}

	@Override
	public float getAngularVelocity() {
		return 0;
	}

	@Override
	public float getMass() {
		return 0;
	}

	@Override
	public void setRawBody(Body body) {
		this.body = body;
	}

	@Override
	public float getFriction() {
		return 0;
	}

	@Override
	public boolean isSensor() {
		return true;
	}

	@Override
	public Vector2f getOffset() {
		return new Vector2f(1f, 1f);
	}

	@Override
	public Vector2f getOrigin() {
		return new Vector2f(getPosition().x, getPosition().y);
	}

	@Override
	public BodyType getBodyType() {
		return BodyType.STATIC;
	}

	@Override
	public Vector2f getScale() {
		return scaler;
	}

	@Override
	public float getRotation() {
		return 0;
	}

	@Override
	public Vector2f getPosition() {
		return position;
	}

	@Override
	public Body getBody() {
		return body;
	}

	@Override
	public void setCustomCallback(Runnable runnable) {
		this.runnable = runnable;
	}

	@Override
	public void reset() {
		
	}

	@Override
	public STATE getState() {
		return null;
	}

	@Override
	public void setState(STATE state) {
		
	}
	
}
