package jschimera.loc.asset.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

class LocalCache<K, V>{
	
	final CacheLoader<? super K, V> defaultLoader;
	final Map<K,V> resources = new HashMap<>();
	
	LocalCache(
		      CacheBuilder<? super K, ? super V> builder, CacheLoader<? super K, V> loader) {
		defaultLoader = loader;
	}
	
	
	
	V get(K key, CacheLoader<? super K, V> loader){
		return resources.computeIfAbsent(key, k -> {
			try {
				return loader.load(k);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		});
	}
	
	V getOrLoad(K key) {
	    return get(key, defaultLoader);
	  }
	
	
	
	
	static class LocalLoadingCache<K, V> extends LocalCache<K,V> implements LoadingCache<K, V> {

  LocalLoadingCache(CacheBuilder<? super K, ? super V> builder,
      CacheLoader<? super K, V> loader) {
	  super(builder, loader);
  }

  // LoadingCache methods

  @Override
  public V get(K key) throws ExecutionException {
    return this.getOrLoad(key);
  }
  

  // Serialization Support

  private static final long serialVersionUID = 1;





}
}
