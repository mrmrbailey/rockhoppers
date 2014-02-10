package com.camelotinteractive.perforce;

import java.util.List;

public interface Perforce {
	
	public void doCommand(String[] args);
	
	public List<String> getOutput();
	
}
