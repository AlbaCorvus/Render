package jschimera.loc.pong.window;

import OpenGLWrapper.api.GLWrapperWindow;
import OpenGLWrapper.api.OpenGlBuilder;

public class OpenGlWindow {
	
	private GLWrapperWindow openGl;
	private int width, height;
	private String title;
	
	public OpenGlWindow(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public void init() {
		openGl = OpenGlBuilder.build(width, height, title);
	}
	
	public boolean windowShouldClose() {
		return openGl.shouldWindowClose();
	}
	
	public void end() {
		openGl.terminate();
	}
	
}
