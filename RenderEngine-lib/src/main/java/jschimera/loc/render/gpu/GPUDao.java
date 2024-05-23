package jschimera.loc.render.gpu;

public interface GPUDao {

	int bindVao();
	
	int bindVbo(float[] gpuDrawingData);
	
	int bindEbo(int[] indices);
	
	
}
