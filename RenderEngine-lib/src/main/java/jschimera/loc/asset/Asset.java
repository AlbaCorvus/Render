package jschimera.loc.asset;

public class Asset{
	
	protected final String key;
	protected final String value;
	
	public Asset(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	public Asset() {
		key = null;
		value = null;
	}
	
	
	public String getKey(){
		return key;
	}
	
	public String getValue() {
		return value;
	}
	
}
