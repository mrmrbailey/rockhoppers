package uk.co.rockhoppersuk.arrays;

import java.util.Arrays;

public class BaseClass {
	
	private Integer[] numbersChosen = new Integer[7];
	
	public BaseClass() {
		Arrays.fill(numbersChosen,0);
	}
	
    public void setNumbersChosen(Integer[] numbersChosen) {
        this.numbersChosen = numbersChosen;
    }
    
    @Override
    public String toString() {
    	return Integer.toString(numbersChosen.length);
    }
}