package jschimera.loc.render.physics.observe;

public interface Observer<T extends Observable> {
	
	void addObservable(T observable);
	
	void notifyObservables();
}
