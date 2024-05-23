package jschimera.loc.asset.domain;

import java.nio.ByteBuffer;
import java.util.Map;

public class TextureDetails {

	private int height, width, textureId;
	private Map<Integer, FontCharInfo> characterMap;
	private ByteBuffer image;
	
	public ByteBuffer getImage() {
		return image;
	}
	public void setImage(ByteBuffer image) {
		this.image = image;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getTextureId() {
		return textureId;
	}
	public void setTextureId(int textureId) {
		this.textureId = textureId;
	}
	
	public Map<Integer, FontCharInfo> getCharacterMap(){
		return characterMap;
	}
	
	public void setCharacterInfoMap(Map<Integer, FontCharInfo> characterMap){
		this.characterMap = characterMap;
	}
	
	
}
