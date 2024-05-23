package jschimera.loc.render.factory;

import jschimera.loc.render.gpu.GPUDao;

public class GPUService {
	
	private GPUDao gpuDao;
	
	GPUService(GPUDao gpuDao){
		this.gpuDao = gpuDao;
	}
	
	
	public int bindVao() {
		return gpuDao.bindVao();
	}

	public int bindVbo(float[] gpuDrawingData) {
		return gpuDao.bindVbo(gpuDrawingData);
	}

	public int bindEbo(int[] verticesData) {
		return gpuDao.bindEbo(verticesData);
	}
	
}
