package OpenGLWrapper.api;

public interface GLWrapperWindow {

	public void poll();
	
	public void clear();
	
	public boolean shouldWindowClose();
	
	public void swapBuffers();
	
	public long getGlWindowPointer();
	
	public void terminate();
	
	public GLWindowProperties getWindowProperties();
	
}
