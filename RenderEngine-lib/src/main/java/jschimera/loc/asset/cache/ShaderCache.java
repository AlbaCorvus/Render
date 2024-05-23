package jschimera.loc.asset.cache;

import jschimera.loc.asset.domain.Shader;

public class ShaderCache {

	private LoadingCache<ShaderCacheKey, Shader> loadingCache;
	
	public ShaderCache() {
		loadingCache = CacheBuilder.newBuilder().build(new CacheLoader<ShaderCacheKey,Shader>(){

			@Override
			public Shader load(ShaderCacheKey key) throws Exception {
				return loadShader(key.getKey(),key.getResourcePath());
			}
			
		});
	}
	
	private Shader loadShader(String key,String resourcePath) {
		return new Shader(key,resourcePath);
	}
	
	public Shader getShader(String key, String resourcePath) throws Exception{
		return loadingCache.get(new ShaderCacheKey(key, resourcePath));
	}
	
	public Shader getShader(String key) throws Exception{
		return loadingCache.get(new ShaderCacheKey(key, ""));
	}
	
	private class ShaderCacheKey{
		
		private String key;
		private String resourcePath;
		
		ShaderCacheKey(String key, String resourcePath){
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
			if(!(obj instanceof ShaderCacheKey)) {
				return false;
			}
			ShaderCacheKey other = (ShaderCacheKey) obj;
			return this.key.equals(other.getKey());
		}
		
		@Override 
		public int hashCode() {
			return key.hashCode() * 13;
		}
		
	}
	
}
