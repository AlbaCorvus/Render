package jschimera.loc.render.data;


public class VerticesData {
	
	private final int MAX_SIZE;
	private final int[] verticesData;
	
	public VerticesData(final int MAX_SIZE) {
		this.MAX_SIZE = MAX_SIZE;
		this.verticesData = new int[6 * MAX_SIZE];
	}

	public void generateVerticesData() {
		for(int i =0; i < MAX_SIZE; i++) {
			int offset = i * 6;
			int valueOfVertices = i * 4;
			
			verticesData[offset] = valueOfVertices + 2;
			verticesData[offset + 1] = valueOfVertices + 1;
			verticesData[offset + 2] = valueOfVertices + 0;
			
			verticesData[offset + 3] = valueOfVertices + 0;
			verticesData[offset + 4] = valueOfVertices + 1;
			verticesData[offset + 5] = valueOfVertices + 3;
		}
	}
	
	public int[] getVerticesData() {
		return verticesData;
	}
	
}
