package jschimera.loc.pong.domain;

import org.joml.Vector2f;
import org.joml.Vector4f;

import jschimera.loc.asset.AssetManager;
import jschimera.loc.asset.domain.Texture;
import jschimera.loc.render.data.RenderInfo;

public class Text implements RenderInfo{

	private Texture texture;
	private final long uniqueRenderId;
	private final int zIndex;
	private int positionOfObjectInGpuBuffer = -1;
	private boolean reDraw = true;
	private Vector4f color = new Vector4f(1,1,1,1);
	private Vector2f position = new Vector2f(120,275);
	private Vector2f scale = new Vector2f(5,10);
	private char textValue;
	
	public Text(long uniqueRenderId, int zIndex, char textValue) {
		this.uniqueRenderId = uniqueRenderId;
		this.zIndex = zIndex;
		this.textValue = textValue;
		texture = AssetManager.build().getTexture("ARIAL");
	}
	
	public void setValue(char c) {
		this.textValue = c;
	}
	
	public char getValue() {
		return this.textValue;
	}
	
	@Override
	public Vector4f getColor() {
		return color;
	}

	@Override
	public Vector2f[] getTextureCoordinates() {
		return texture.getTextCoords(textValue);
	}
	
	@Override
	public int getPositionInGpuDrawData() {
		return positionOfObjectInGpuBuffer;
	}

	@Override
	public void setPositionInGpuDrawData(int position) {
		this.positionOfObjectInGpuBuffer = position;
	}

	@Override
	public float getRotation() {
		return 0;
	}

	@Override
	public Vector2f getPosition() {
		return position;
	}
	
	public void setPosition(Vector2f position) {
		this.position = position;
	}

	@Override
	public Vector2f getScale() {
		return scale;
	}

	@Override
	public long getUniqueRenderId() {
		return uniqueRenderId;
	}

	@Override
	public int getZindex() {
		return zIndex;
	}

	@Override
	public boolean isRedraw() {
		return reDraw;
	}

	@Override
	public void setRedraw(boolean redraw) {
		this.reDraw = redraw;
	}

	@Override
	public Texture getTexture() {
		return texture;
	}

	
}
