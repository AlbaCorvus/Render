package jschimera.loc.asset;

import java.util.ResourceBundle;

import jschimera.loc.asset.cache.ShaderCache;
import jschimera.loc.asset.cache.TextureCache;
import jschimera.loc.asset.domain.Shader;
import jschimera.loc.asset.domain.Texture;

public class AssetManager {
	
	private static AssetManager instance;

	private static final String SHADER = "shaders";
	private static final String TEXTURE = "textures";

	private final ShaderCache shaderCache = new ShaderCache();
	private final TextureCache textureCache = new TextureCache();

	public void loadAllResources() {
		ResourceBundle shaderResource = ResourceBundle.getBundle(SHADER);
		shaderResource.keySet().stream().forEach(k -> {
			getShader(k, shaderResource.getString(k));
		});
		ResourceBundle textureResource = ResourceBundle.getBundle(TEXTURE);
		textureResource.keySet().stream().forEach(k -> {
			getTexture(k, textureResource.getString(k));
		});

	}
	
	private AssetManager() {
		
	}
	
	public static synchronized AssetManager build() {
		if(instance == null) {
			instance = new AssetManager();
		}
		return instance;
	}

	

	public Texture getTexture(String key){
		try {
			return textureCache.getTexture(key);
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		
	}
	
	public Shader getShader(String key){
		try {
			return shaderCache.getShader(key);
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		
	}
	
	private Shader getShader(String key, String value){
		try {
			return shaderCache.getShader(key, value);
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		
	}
	
	private Texture getTexture(String key, String value){
		try {
			return textureCache.getTexture(key, value);
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		
	}
	
}
