package jschimera.loc.asset.cache;

import jschimera.loc.asset.domain.Shader;

public class FontCache {

private LoadingCache<FontCacheKey, Shader> loadingCache;
	
	public FontCache() {
		loadingCache = CacheBuilder.newBuilder().build(new CacheLoader<FontCacheKey,Shader>(){

			@Override
			public Shader load(FontCacheKey key) throws Exception {
				return loadShader(key.getKey(),key.getResourcePath());
			}
			
		});
	}
	
	private Shader loadShader(String key,String resourcePath) {
		return new Shader(key,resourcePath);
	}
	
	public Shader getShader(String key, String resourcePath) throws Exception{
		return loadingCache.get(new FontCacheKey(key, resourcePath));
	}
	
	public Shader getShader(String key) throws Exception{
		return loadingCache.get(new FontCacheKey(key, ""));
	}
	
	private class FontCacheKey{
		
		private String key;
		private String resourcePath;
		
		FontCacheKey(String key, String resourcePath){
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
			if(!(obj instanceof FontCacheKey)) {
				return false;
			}
			FontCacheKey other = (FontCacheKey) obj;
			return this.key.equals(other.getKey());
		}
		
		@Override 
		public int hashCode() {
			return key.hashCode() * 13;
		}
		
	}
}
