package jschimera.loc.render;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWKeyCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;
import org.lwjgl.glfw.GLFWScrollCallbackI;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;

import OpenGLWrapper.api.GLWrapperWindow;
import OpenGLWrapper.api.OpenGlBuilder;
import jschimera.loc.asset.AssetManager;
import jschimera.loc.asset.domain.Shader;
import jschimera.loc.render.batching.RenderBatch;
import jschimera.loc.render.data.RenderInfo;
import jschimera.loc.render.physics.PhysicalObject;
import jschimera.loc.render.physics.Physics2D;
import jschimera.loc.render.thread.Controller;
import jschimera.loc.render.thread.RController;
import jschimera.loc.render.view.VirtualCamera;

public class Renderer {

	private List<RenderInfo> renderInfoObjects;
	private List<PhysicalObject> renderObjectsWithPhysics;
	private List<Controller> controllers = new ArrayList<>();
	private List<RController> rControllers = new ArrayList<>();
	private Shader shader;
	private List<RenderBatch> renderBatches;
	private GLWrapperWindow window;
	private VirtualCamera camera;
	private Object lock;
	public boolean pause = false;

	public Renderer() {
		this.renderInfoObjects = new ArrayList<>();
		this.renderObjectsWithPhysics = new ArrayList<>();
		this.renderBatches = new ArrayList<>();
	}

	public void init(GLFWCursorPosCallbackI cursorCallback, GLFWMouseButtonCallbackI mouseCallback,
			GLFWScrollCallbackI scrollCallback, GLFWKeyCallbackI keyCallback, GLFWWindowSizeCallbackI resizeCallback) {
		window = OpenGlBuilder.build(1920, 1080, "Test", new Vector4f(0, 0, 0, 0), cursorCallback, mouseCallback,
				scrollCallback, keyCallback, resizeCallback);
		cacheAssets();
		initCamera();
		loadShaders();
	}

	private void initCamera() {
		camera = new VirtualCamera(new Vector2f(-250, 0));
	}

	private void cacheAssets() {
		AssetManager.build().loadAllResources();
	}

	private void loadShaders() {
		shader = AssetManager.build().getShader("DEFAULT");
		shader.compile();
	}

	public void addAllRenderInfoObjects(List<RenderInfo> renderInfoList) {
		renderInfoList.forEach(Renderer.this::addRenderInfoObject);
	}

	public void addRenderInfoObject(RenderInfo renderInfoObject) {
		this.renderInfoObjects.add(renderInfoObject);
		Stream.of(renderInfoObject).filter(PhysicalObject.class::isInstance).map(PhysicalObject.class::cast)
				.forEach(renderObjectsWithPhysics::add);
	}

	public void addPhysicsObject(PhysicalObject physicalObject) {
		this.renderObjectsWithPhysics.add(physicalObject);
	}

	public void addController(Controller controller) {
		this.controllers.add(controller);
	}

	public void addRController(RController pauseController) {
		this.rControllers.add(pauseController);
	}

	public void assembleBatches() {
		Map<Integer, List<RenderInfo>> batchReadyInfo = renderInfoObjects.stream()
				.collect(Collectors.groupingBy(RenderInfo::getZindex, Collectors.toList()));
		for (Map.Entry<Integer, List<RenderInfo>> entry : batchReadyInfo.entrySet()) {
			RenderBatch renderBatch = renderBatches.stream().filter(batch -> batch.getZIndex() == entry.getKey())
					.findAny().orElseGet(() -> new RenderBatch(entry.getKey(), 500));
			if (!renderBatches.contains(renderBatch)) {
				renderBatch.addRenderInfo(entry.getValue());
				renderBatches.add(renderBatch);
			}
		}
		Collections.sort(renderBatches);
	}

	private void renderBatches() {
		renderBatches.forEach(batch -> {
			batch.bind();
			batch.render(shader, camera.getProjectionMatrix(), camera.getViewMatrix());
		});
	}

	public void render() {

		float beginTime = (float) glfwGetTime();
		float endTime;
		float dt = -1.0f;
		Physics2D physics2d = new Physics2D();
		assembleBatches();
		renderObjectsWithPhysics.forEach(physics2d::addPhysicalObject);

		while (!window.shouldWindowClose()) {
			rControllers.forEach(RController::listen);
			window.poll();
			window.clear();
			if (!pause) {
				controllers.forEach(Controller::listen);
				renderBatches();
				physics2d.update(dt);
				endTime = (float) glfwGetTime();
				dt = endTime - beginTime;
				beginTime = endTime;
			}
			window.swapBuffers();

		}
		window.terminate();

	}

	public void setLock(Object lock) {
		this.lock = lock;
	}

	public Object getLock() {
		return lock;
	}

}
