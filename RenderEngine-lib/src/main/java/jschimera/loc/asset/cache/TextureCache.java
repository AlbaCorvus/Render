package jschimera.loc.asset.cache;

import jschimera.loc.asset.domain.Texture;

public class TextureCache {
	
private LoadingCache<TextureCacheKey, Texture> loadingCache;
	
	public TextureCache() {
		loadingCache = CacheBuilder.newBuilder().build(new CacheLoader<TextureCacheKey,Texture>(){

			@Override
			public Texture load(TextureCacheKey key) throws Exception {
				return loadTexture(key.getKey(),key.getResourcePath());
			}
			
		});
	}
	
	private Texture loadTexture(String key,String resourcePath) {
		return new Texture(key,resourcePath);
	}
	
	public Texture getTexture(String key, String resourcePath) throws Exception{
		return loadingCache.get(new TextureCacheKey(key, resourcePath));
	}
	
	public Texture getTexture(String key) throws Exception{
		return loadingCache.get(new TextureCacheKey(key, ""));
	}
	
	private class TextureCacheKey{
		
		private String key;
		private String resourcePath;
		
		private TextureCacheKey(String key, String resourcePath){
			this.key = key;
			this.resourcePath = resourcePath;
		}
		
		public String getKey() {
			return key;
		}
		
		public String getResourcePath() {
			return resourcePath;
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof TextureCacheKey)) {
				return false;
			}
			TextureCacheKey other = (TextureCacheKey) obj;
			return this.key.equals(other.getKey());
		}
		
		@Override 
		public int hashCode() {
			return key.hashCode() * 13;
		}
		
	}

}