package uk.co.rockhoppersuk.byRef;

import java.util.HashMap;
import java.util.Map;

public class Catalog {
	
	private Map<String, Thing> myMap;
	
	public Catalog() {
		refresh();
	}
	
	public void refresh() {
		Map<String, Thing> aMap =  new HashMap<String, Thing>();
		
		aMap.put("a", new Thing("a", 1));
		aMap.put("b", new Thing("b", 2));
		
		myMap = aMap;
	}
	
	public Thing getThing(String key){
		return myMap.get(key);
	}	

}
