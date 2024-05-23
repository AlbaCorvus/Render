package OpenGLWrapper.api;

import org.joml.Vector4f;

public interface GLWindowProperties {

	public double getWindowWidth();

	public double getWindowHeight();

	public long getWindowPointer();
	
	public Vector4f getColor();
	
	public void setColor(Vector4f color);
	
}
