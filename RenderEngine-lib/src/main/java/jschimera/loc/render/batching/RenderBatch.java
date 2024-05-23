package jschimera.loc.render.batching;

import static jschimera.loc.render.constants.RenderingConstants.VERTEX_SIZE;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import jschimera.loc.asset.domain.Shader;
import jschimera.loc.asset.domain.Texture;
import jschimera.loc.render.data.RenderInfo;
import jschimera.loc.render.data.VerticesData;
import jschimera.loc.render.factory.GPUFactory;
import jschimera.loc.render.factory.GPUService;

public class RenderBatch implements Comparable<RenderBatch> {

	private final int zIndex;
	private float[] gpuDrawingData;
	private GPUService gpuService = GPUFactory.getGPUService();
	private List<RenderInfo> renderInfoList = new ArrayList<>();
	private int[] texSlots = { 0, 1, 2, 3, 4, 5, 6, 7 };
	private List<Texture> textures = new ArrayList<>();	
	private int eboID;
	private final VerticesData verticesData;
	private int nextIndexOfGameObject = -1;

	public RenderBatch(int zIndex, int maxSize) {
		this.zIndex = zIndex;
		this.verticesData = new VerticesData(maxSize);
		verticesData.generateVerticesData();
		gpuDrawingData = new float[maxSize * 6 * 5];
	}

	public void bind() {
		gpuService.bindVao();
		gpuService.bindVbo(gpuDrawingData);
		eboID = gpuService.bindEbo(verticesData.getVerticesData());
	}

	@Override
	public int compareTo(RenderBatch o) {
		return Integer.compare(this.zIndex, o.zIndex);
	}

	public int getZIndex() {
		return zIndex;
	}

	public void addRenderInfo(List<RenderInfo> renderInfoList) {
		this.renderInfoList = renderInfoList;
		List<RenderInfo> textureInfo = renderInfoList.stream().filter(info -> info.getTexture() != null).collect(Collectors.toList());
		Map<Integer,List<Texture>> textures = 
				textureInfo.stream().map(RenderInfo::getTexture).collect(Collectors.groupingBy(Texture::getId));
		for(Map.Entry<Integer, List<Texture>> entry : textures.entrySet()) {
			this.textures.add(entry.getValue().get(0));
		}
	}

	private void refreshDrawingData(List<RenderInfo> renderInfoList) {
		renderInfoList.forEach(RenderBatch.this::refreshDrawingData);
	}
	
	private void setRenderInfoGpuIndexForBatch(RenderInfo renderInfo) {
		renderInfo.setPositionInGpuDrawData(renderInfo.getPositionInGpuDrawData() == -1 ? ++nextIndexOfGameObject : renderInfo.getPositionInGpuDrawData());
	}

	private void refreshDrawingData(RenderInfo renderInfo) {
		setRenderInfoGpuIndexForBatch(renderInfo);
		int offset = renderInfo.getPositionInGpuDrawData() * 4 * VERTEX_SIZE;

		Vector4f color = renderInfo.getColor();
		Vector2f[] texCoords = renderInfo.getTextureCoordinates();

		
		int texId = 0;
		if (renderInfo.getTexture() != null) {
			for (int i = 0; i < textures.size(); i++) {
				if (textures.get(i).equals(renderInfo.getTexture())) {
					texId = i + 1;
					break;
				}
			}
		}
		 

		boolean isRotated = renderInfo.getRotation() != 0.0f;
		Matrix4f transformMatrix = new Matrix4f().identity();
		if (isRotated) {
			transformMatrix.translate(renderInfo.getPosition().x, renderInfo.getPosition().y, 0f);
			transformMatrix.rotate((float) Math.toRadians(renderInfo.getRotation()), 0, 0, 1);
			transformMatrix.scale(renderInfo.getScale().x, renderInfo.getScale().y, 1);
		}

		// Add vertices with the appropriate properties
		float xAdd = 1.0f;
		float yAdd = -1.0f;
		for (int i = 0; i < 4; i++) {
			if (i == 1) {
				xAdd = -1.0f;
				yAdd = 1.0f;
			} else if (i == 2) {
				xAdd = -1.0f;
				yAdd = -1.0f;
			} else if (i == 3) {
				yAdd = 1.0f;
				xAdd = 1.0f;
			}

			Vector4f currentPos = new Vector4f(renderInfo.getPosition().x + (xAdd * renderInfo.getScale().x),
					renderInfo.getPosition().y + (yAdd * renderInfo.getScale().y), 0, 1);
			if (isRotated) {
				currentPos = new Vector4f(xAdd, yAdd, 0, 1).mul(transformMatrix);
			}

			// Load position
			gpuDrawingData[offset] = currentPos.x;
			gpuDrawingData[offset + 1] = currentPos.y;

			// Load color
			gpuDrawingData[offset + 2] = color.x;
			gpuDrawingData[offset + 3] = color.y;
			gpuDrawingData[offset + 4] = color.z;
			gpuDrawingData[offset + 5] = color.w;

			// Load texture coordinates
			gpuDrawingData[offset + 6] = texCoords[i].x;
			gpuDrawingData[offset + 7] = texCoords[i].y;

			// Load texture id
			gpuDrawingData[offset + 8] = texId;

			// Load entity id
			gpuDrawingData[offset + 9] = (renderInfo.getUniqueRenderId() + 1L);
			offset += VERTEX_SIZE;
		}
		
		renderInfo.setRedraw(false);

	}

	public void render(Shader shader, Matrix4f projectionMatrix, Matrix4f viewMatrix) {
		List<RenderInfo> dirtyGameObjects = renderInfoList.stream().filter(RenderInfo::isRedraw)
				.collect(Collectors.toList());
		

		if (!dirtyGameObjects.isEmpty()) {
			refreshDrawingData(dirtyGameObjects);
			glBindBuffer(GL_ARRAY_BUFFER, eboID);
			glBufferSubData(GL_ARRAY_BUFFER, 0, gpuDrawingData);
		}
		// Use shader
		shader.use();
		shader.uploadMat4f("uProjection", projectionMatrix);
		shader.uploadMat4f("uView", viewMatrix);
		
		for (int i = 0; i < textures.size(); i++) {
			glActiveTexture(GL_TEXTURE0 + i + 1);
			textures.get(i).bind();
		}
		 
		shader.uploadIntArray("uTextures", texSlots);

		glDrawElements(GL_TRIANGLES, renderInfoList.size() * 6, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glDisableVertexAttribArray(3);
		glDisableVertexAttribArray(4);
		glBindVertexArray(0);

		// gpuAssist.getTextures().forEach(Texture::unbind);
		shader.detach();
	}

}
