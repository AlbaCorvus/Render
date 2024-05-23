package jschimera.loc.render.gpu;
import static org.lwjgl.opengl.GL30.*;
import static jschimera.loc.render.constants.RenderingConstants.*;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;


public class GPUDaoImpl implements GPUDao{

	@Override
	public int bindVao() {
		int vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		checkGLError();
		return vaoId;
	}

	@Override
	public int bindVbo(float[] gpuDrawingData) {
		int vboId = glGenBuffers();
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(gpuDrawingData.length);
		vertexBuffer.put(gpuDrawingData).flip();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);
		return vboId;
	}

	@Override
	public int bindEbo(int[] indices) {
		int eboId = glGenBuffers();
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

		// Enable the buffer attribute pointers
		glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
		glEnableVertexAttribArray(0);

		glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
		glEnableVertexAttribArray(1);

		glVertexAttribPointer(2, TEX_COORDS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_COORDS_OFFSET);
		glEnableVertexAttribArray(2);

		glVertexAttribPointer(3, TEX_ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, TEX_ID_OFFSET);
		glEnableVertexAttribArray(3);

		glVertexAttribPointer(4, ENTITY_ID_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES,ENTITY_ID_OFFSET); 
		glEnableVertexAttribArray(4);
		
		return eboId;
	}
	
	private void checkGLError() {
	    int error = glGetError();
	    if (error != GL_NO_ERROR && error != 1281) {
	        System.err.println("OpenGL Error: " + error);
	    }
	}

}
