package jschimera.loc.pong.domain;

import org.jbox2d.dynamics.contacts.Contact;
import org.joml.Vector2f;

import jschimera.loc.render.physics.PhysicalObject;

public class GoalBoundary extends Boundary{

	public GoalBoundary(Vector2f position, Vector2f scaler) {
		super(position, scaler);
	}
	
	@Override
	public void defineContactResponse(PhysicalObject object, Contact contact, Vector2f hitNormal) {
		if(runnable != null) {
			runnable.run();
		}
		object.reset();
	}
	
}
