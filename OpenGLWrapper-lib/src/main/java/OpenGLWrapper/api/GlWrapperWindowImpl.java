package OpenGLWrapper.api;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

 class GlWrapperWindowImpl implements GLWrapperWindow{
	
	private final GLWindowProperties windowProperties;
	
	GlWrapperWindowImpl(GLWindowProperties windowProperties){
		this.windowProperties = windowProperties;
	}
	
	public void pollAndClear() {
		pollEvents();
		clearColor();
	}
	
	public void pollEvents() {
		glfwPollEvents();
	}
	
	public void clearColor() {
		glClearColor(windowProperties.getColor().x,windowProperties.getColor().y,windowProperties.getColor().z, windowProperties.getColor().w);
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public boolean shouldWindowClose() {
		return glfwWindowShouldClose(getGlWindowPointer());
	}
	
	public void swapBuffers() {
		glfwSwapBuffers(getGlWindowPointer());
	}
	
	public GLWindowProperties getWindowProperties() {
		return windowProperties;
	}

	@Override
	public void poll() {
		pollEvents();
	}

	@Override
	public void clear() {
		clearColor();
	}

	@Override
	public long getGlWindowPointer() {
		return windowProperties.getWindowPointer();
	}

	@Override
	public void terminate() {
		glfwFreeCallbacks(getGlWindowPointer());
		glfwDestroyWindow(getGlWindowPointer());

		// Terminate GLFW and the free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}


}
