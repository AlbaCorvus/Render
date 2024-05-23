package jschimera.loc.pong.domain;

import org.jbox2d.common.Vec2;

import jschimera.loc.pong.listener.KeyListener;
import jschimera.loc.render.Renderer;
import jschimera.loc.render.thread.Controller;

public class Player1 implements Controller {

	private final GameObject gameObject;

	public Player1(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	@Override
	public void listen() {
		if (KeyListener.isKeyPressed(83)) {
			keyPressDown();
		}
		if (KeyListener.isKeyPressed(87)) {
			keyPressUp();
		}
		
	}

	@Override
	public void keyPressUp() {
		gameObject.getBody().applyLinearImpulse(new Vec2(0.0f, 100000.0f), gameObject.getBody().getWorldCenter());
		gameObject.setRedraw(true);
	}

	@Override
	public void keyPressDown() {
		gameObject.getBody().applyLinearImpulse(new Vec2(0.0f, -100000.0f), gameObject.getBody().getWorldCenter());
		gameObject.setRedraw(true);
	}

	@Override
	public void keyPressLeft() {
		gameObject.getPosition().x -= 2;
		gameObject.setRedraw(true);
	}

	@Override
	public void keyPressRight() {
		gameObject.getPosition().x += 2;
		gameObject.setRedraw(true);
	}


}
