package uk.co.rockhoppersuk.byRef;

public class Thing {
	
	private String name;
	private int value;
	
	public Thing(String aName, int aInt) {
		name = aName;
		value = aInt;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
