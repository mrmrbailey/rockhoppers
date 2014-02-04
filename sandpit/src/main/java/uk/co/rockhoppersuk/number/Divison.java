package uk.co.rockhoppersuk.number;

public class Divison {
	
	public boolean isDouble(int total, int singleDie) {
		return (total/2 == singleDie && total % 2 == 0);
	}
	
	public boolean isDoubleInvalid(int total, int singleDie) {
		return (total/2 == singleDie);
	}
}
