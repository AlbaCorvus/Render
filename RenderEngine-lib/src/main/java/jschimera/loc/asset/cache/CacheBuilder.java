package jschimera.loc.asset.cache;

public final class CacheBuilder<K,V> {
	
	CacheBuilder() {}

	public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(
	          CacheLoader<? super K1, V1> loader) {
	    return new LocalCache.LocalLoadingCache<K1, V1>(this, loader);
	}
	
	public static CacheBuilder<Object, Object> newBuilder() {
	    return new CacheBuilder<Object, Object>();
	  }
}
