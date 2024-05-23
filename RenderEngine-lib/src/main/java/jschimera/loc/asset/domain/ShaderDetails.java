package jschimera.loc.asset.domain;

public class ShaderDetails {

	private final String vertexSource;
	private final String fragmentSource;
	
	public ShaderDetails(String vertexSrc, String fragmentSrc) {
		this.vertexSource = vertexSrc;
		this.fragmentSource = fragmentSrc;
	}

	public String getVertexSource() {
		return vertexSource;
	}

	public String getFragmentSource() {
		return fragmentSource;
	}
	
}
