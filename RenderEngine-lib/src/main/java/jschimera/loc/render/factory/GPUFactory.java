package jschimera.loc.render.factory;

import jschimera.loc.render.gpu.GPUDaoImpl;

public class GPUFactory {

	private GPUFactory() {
		
	}
	
	public static GPUService getGPUService() {
		return new GPUService(new GPUDaoImpl());
	}
	
}
