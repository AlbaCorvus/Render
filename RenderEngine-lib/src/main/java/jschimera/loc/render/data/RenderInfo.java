package jschimera.loc.render.data;

import org.joml.Vector2f;
import org.joml.Vector4f;

import jschimera.loc.asset.domain.Texture;

public interface RenderInfo {

	Vector4f getColor();
	
	Vector2f[] getTextureCoordinates();
	
	int getPositionInGpuDrawData();
	
	void setPositionInGpuDrawData(int position);
	
	float getRotation();
	
	Vector2f getPosition();
	
	Vector2f getScale();
	
	long getUniqueRenderId();
	
	int getZindex();
	
	boolean isRedraw();
	
	void setRedraw(boolean redraw);
	
	Texture getTexture();
	
}
