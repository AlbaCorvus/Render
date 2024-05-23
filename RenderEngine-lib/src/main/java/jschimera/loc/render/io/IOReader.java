package jschimera.loc.render.io;

public interface IOReader<T, V> {

	T read(V value);
	
}
