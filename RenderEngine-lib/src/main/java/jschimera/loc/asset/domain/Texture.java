package jschimera.loc.asset.domain;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.util.Objects;

import org.joml.Vector2f;

import jschimera.loc.asset.Asset;
import jschimera.loc.render.data.dao.TextureFontDao;

public class Texture extends Asset{
	
    private TextureDetails textureDetails;

	public Texture(String key, String value) {
		super(key, value);
		textureDetails = new TextureFontDao().read(value);
	}
	
	public Texture(int width, int height) {
		textureDetails = new TextureFontDao().getDefault();
		textureDetails.setHeight(height);
		textureDetails.setWidth(width);
		
        // Generate texture on GPU
        textureDetails.setTextureId(glGenTextures());
        glBindTexture(GL_TEXTURE_2D, textureDetails.getTextureId());

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height,
                0, GL_RGB, GL_UNSIGNED_BYTE, 0);
    }
	
	public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureDetails.getTextureId());
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getWidth() {
        return textureDetails.getWidth();
    }
    
    public Vector2f[] getTextCoords(char c){
    	return textureDetails.getCharacterMap().get((int) c).getTextcoords();
    }

    public int getHeight() {
        return textureDetails.getHeight();
    }

    public int getId() {
        return textureDetails.getTextureId();
    }
	
	@Override
	public int hashCode() {
		return Objects.hash(textureDetails.getTextureId());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Texture)) {
			return false;
		}
		Texture other = (Texture) obj;
		return Objects.equals(other.textureDetails.getTextureId(), textureDetails.getTextureId());
		
	}
	
	

}
