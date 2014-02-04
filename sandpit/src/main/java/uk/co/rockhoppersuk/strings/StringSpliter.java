package uk.co.rockhoppersuk.strings;

import java.util.Arrays;
import java.util.List;

public class StringSpliter {
	
	public List<String> getList(String str) {
		return Arrays.asList(str.split(","));
	}

}
