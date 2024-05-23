package jschimera.loc.render.thread;

import jschimera.loc.render.Renderer;

public abstract class RController implements SpecialController {

	private final Renderer renderer;
	protected int keyForPause = 32;

	protected RController(Renderer renderer) {
		this.renderer = renderer;
	}
	
	public abstract void listen();

	@Override
	public synchronized void buttonPressStart() {
			renderer.pause = !renderer.pause;
	}

}
