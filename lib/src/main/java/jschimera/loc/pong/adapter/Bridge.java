package jschimera.loc.pong.adapter;

public class Bridge {

	public static boolean checkDependency(final String path) {
		try {
			Class.forName(path);
			return true;
		} catch (Throwable ex) {
			System.out.println("Render not available");
		}
		return false;
	}

}
