package jschimera.loc.asset.cache;


public interface LoadingCache<K, V> {

	public V get(K key) throws Exception;
	
}
