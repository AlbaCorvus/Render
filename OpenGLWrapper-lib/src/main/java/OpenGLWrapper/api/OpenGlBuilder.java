package OpenGLWrapper.api;

import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_MAXIMIZED;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.opengl.GL;

public class OpenGlBuilder {
	
	private OpenGlBuilder() {}
	
	
	public static GLWrapperWindow build(int width, int height, String title) {
		return build(width, height, title, new Vector4f(1,1,1,1));
	}
	
	public static GLWrapperWindow build(int width, int height, String title, Vector4f color) {
		return build(width, height, title, color, null,null,null,null,null);
	}
	
	public static GLWrapperWindow build(int width, int height, String title, Vector4f color, GLFWCursorPosCallbackI cursorCallback,GLFWMouseButtonCallbackI mouseCallback,GLFWScrollCallbackI scrollCallback,GLFWKeyCallbackI keyCallback,GLFWWindowSizeCallbackI resizeCallback) {
		return new OpenGlBuilder().new Builder()
				.initialiseOpenGlWindow(width, height, title)
				.withCallBacks(cursorCallback, mouseCallback, scrollCallback, keyCallback, resizeCallback)
				.withColor(color)
				.withShowWindow()
				.build();
	}
	
	private class Builder{
		
		private GLWrapperWindow window;
		
		private Builder() {
			
		}
		
		private GLWrapperWindow build() {
			GL.createCapabilities();
			glEnable(GL_BLEND);
			glBlendFunc(GL_ONE,GL_ONE_MINUS_SRC_ALPHA);
			return window;
		}
		
		private Builder initialiseOpenGlWindow(int width, int height, String title) {
			long glfwWindowPointer;
			GLFWErrorCallback.createPrint(System.err).set();
			if(!glfwInit()) {
				throw new IllegalStateException("Unable to initialise glfw");
			}
			
			glfwDefaultWindowHints();
			glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
			glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
			glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
			
			glfwWindowPointer = glfwCreateWindow(width, height, title, NULL, NULL);
			if(glfwWindowPointer == NULL) {
				throw new IllegalStateException("Failed to create  GLFW window");
			}
			window = new GlWrapperWindowImpl(new OpenGlWindowProperties(glfwWindowPointer, width, height));
			
			glfwMakeContextCurrent(glfwWindowPointer);
			glfwSwapInterval(1);
			GL.createCapabilities();

	        glEnable(GL_BLEND);
	        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);
			
			glViewport(0, 0, width, height);
			
			return this;
		}
		
		private Builder withColor(Vector4f color) {
			window.getWindowProperties().setColor(color);
			return this;
		}
		
		private Builder withCallBacks(GLFWCursorPosCallbackI cursorPos, GLFWMouseButtonCallbackI mouseButton, GLFWScrollCallbackI scroll, GLFWKeyCallbackI keyCallback, GLFWWindowSizeCallbackI windowSize) {
			glfwSetCursorPosCallback(window.getGlWindowPointer(), cursorPos);
			glfwSetMouseButtonCallback(window.getGlWindowPointer(), mouseButton);
			glfwSetScrollCallback(window.getGlWindowPointer(), scroll);
			glfwSetKeyCallback(window.getGlWindowPointer(), keyCallback);
			glfwSetWindowSizeCallback(window.getGlWindowPointer(), windowSize);
			return this;
		}
		
		private Builder withShowWindow() {
			glfwShowWindow(window.getGlWindowPointer());
			return this;
		}
		
	}
	
}
