package jschimera.loc.render.physics;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

public class PhysicsListener implements ContactListener{

	@Override
	public void beginContact(Contact contact) {
		PhysicalObject objectA = (PhysicalObject) contact.getFixtureA().getUserData();
		PhysicalObject objectB = (PhysicalObject) contact.getFixtureB().getUserData();
		WorldManifold worldManifold = new WorldManifold();
		contact.getWorldManifold(worldManifold);
		Vector2f aNormal = new Vector2f(worldManifold.normal.x, worldManifold.normal.y);
		Vector2f bNormal = new Vector2f(aNormal).negate();
		objectA.defineContactResponse(objectB, contact, aNormal);
		
		objectB.defineContactResponse(objectA, contact, bNormal);
	}

	@Override
	public void endContact(Contact contact) {
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		
	}

}
