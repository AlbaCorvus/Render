package jschimera.loc.pong.domain;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import jschimera.loc.asset.domain.Texture;
import jschimera.loc.render.physics.PhysicalObject;
import jschimera.loc.render.physics.Physics2D.STATE;

public class Paddle extends GameObject{
	
	public Paddle(long uniqueRenderId, int zIndex) {
		super(uniqueRenderId, zIndex);
	}

	@Override
	public void defineContactResponse(PhysicalObject object, Contact contact, Vector2f vector) {
		float momentum = object.getBody().getLinearVelocity().x;
		float verticalMomentum = getBody().getLinearVelocity().y;
		object.getBody()
				.setLinearVelocity(new Vec2(-1*momentum,verticalMomentum*0.2f));
	}

	@Override
	public float getAngularDamping() {
		return 0;
	}

	@Override
	public float getAngularVelocity() {
		return 0;
	}

	@Override
	public float getFriction() {
		return 0.0f;
	}

	@Override
	public float getGravityScale() {
		return 0.0f;
	}

	@Override
	public float getLinearDamping() {
		return 10;
	}

	@Override
	public float getMass() {
		return 0.0f;
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
	public boolean isContinuousCollision() {
		return false;
	}

	@Override
	public boolean isFixedRotation() {
		return true;
	}

	@Override
	public boolean isSensor() {
		return false;
	}

	@Override
	public BodyType getBodyType() {
		return BodyType.DYNAMIC;
	}

	@Override
	public void receiveUpdate() {
		setPosition(new Vector2f(getBody().getPosition().x, getBody().getPosition().y));
		setRedraw(true);
	}

	@Override
	public Texture getTexture() {
		return null;
	}

	@Override
	public void setCustomCallback(Runnable runnable) {
		
	}

	@Override
	public void reset() {
		
	}

	@Override
	public STATE getState() {
		return state;
	}


}
