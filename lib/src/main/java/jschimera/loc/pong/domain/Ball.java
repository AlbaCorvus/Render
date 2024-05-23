package jschimera.loc.pong.domain;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import jschimera.loc.asset.domain.Texture;
import jschimera.loc.render.physics.PhysicalObject;
import jschimera.loc.render.physics.Physics2D.STATE;

public class Ball extends GameObject{

	public Ball(long uniqueRenderId, int zIndex) {
		super(uniqueRenderId, zIndex);
	}

	@Override
	public void defineContactResponse(PhysicalObject object, Contact contact, Vector2f vec) {
		
	}

	@Override
	public float getAngularDamping() {
		return 0;
	}

	@Override
	public float getAngularVelocity() {
		return 1;
	}

	@Override
	public float getFriction() {
		return 0;
	}

	@Override
	public float getGravityScale() {
		return 0;
	}

	@Override
	public float getLinearDamping() {
		return 0;
	}

	@Override
	public float getMass() {
		return 1;
	}

	@Override
	public Vector2f getOffset() {
		return new Vector2f(0.5f,0.5f);
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
		return false;
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
		if(state != STATE.STANDBY) {
		setPosition(new Vector2f(body.getPosition().x,body.getPosition().y));
		setRedraw(true);
		}
	}
	
	@Override
	public void setRawBody(Body body) {
		this.body = body;
		this.body.applyLinearImpulse(new Vec2(5000000.0f,0.0f), body.getWorldCenter());
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
		this.setPosition(getInitialPosition());
		state = STATE.RESET;
	}

	@Override
	public STATE getState() {
		return state;
	}


}
