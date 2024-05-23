package jschimera.loc.pong.domain;

import jschimera.loc.pong.listener.KeyListener;
import jschimera.loc.render.Renderer;
import jschimera.loc.render.thread.RController;

public class GameController extends RController{

	public GameController(Renderer renderer) {
		super(renderer);
	}
	
	public void listen() {
		if (KeyListener.isKeyPressed(keyForPause)) {
			buttonPressStart();
		}
	}

}
