package OpenGLWrapper.api;

import org.joml.Vector4f;

public class OpenGlWindowProperties implements GLWindowProperties{
	
	private final long windowPointer;
	private final double windowWidth;
	private final double windowHeight;
	private Vector4f color = new Vector4f(1,1,1,1);
	
	public OpenGlWindowProperties(long windowPointer, double windowWidth, double windowHeight) {
		this.windowPointer = windowPointer;
		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;
	}

	@Override
	public double getWindowWidth() {
		return windowWidth;
	}

	@Override
	public double getWindowHeight() {
		return windowHeight;
	}

	@Override
	public long getWindowPointer() {
		return windowPointer;
	}

	@Override
	public Vector4f getColor() {
		return color;
	}

	@Override
	public void setColor(Vector4f color) {
		this.color = color;
	}
	
	
}
