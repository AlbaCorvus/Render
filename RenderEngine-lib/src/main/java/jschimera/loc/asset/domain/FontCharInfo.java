package jschimera.loc.asset.domain;

import org.joml.Vector2f;

public class FontCharInfo {
	
	private int sourceX;
	private int sourceY;
	private int charWidth;
	private int charHeight;
	
	private Vector2f[] textureCoordinates = new Vector2f[4];

	public FontCharInfo(int sourceX, int sourceY, int charWidth, int charHeight) {
		this.sourceX = sourceX;
		this.sourceY = sourceY;
		this.charWidth = charWidth;
		this.charHeight = charHeight;
	}
	
	public void initialiseTextureCoordinates(int fontWidth, int fontHeight) {
		float x0 = (float) sourceX / (float) fontWidth;
		float x1 = (float) (sourceX + charWidth) / (float) fontWidth;
		float y0 = (float) (sourceY - charHeight) / (float) fontHeight;
		float y1 = (float) (sourceY) / (float) fontHeight;
		
		textureCoordinates[0] = new Vector2f(x1,y1);
		textureCoordinates[1] = new Vector2f(x0,y0);
		textureCoordinates[2] = new Vector2f(x0,y1);
		textureCoordinates[3] = new Vector2f(x1,y0);
		
	}
	
	public Vector2f[] getTextcoords() {
		return textureCoordinates;
	}

	public int getSourceX() {
		return sourceX;
	}

	public int getSourceY() {
		return sourceY;
	}

	public int getCharWidth() {
		return charWidth;
	}

	public int getCharHeight() {
		return charHeight;
	}
	
	

}
