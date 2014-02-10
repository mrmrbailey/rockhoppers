package com.camelotinteractive.perforce.command;

public enum Commands {
	
	P4("p4"),
	FSTAT("fstat"),
	FIXES("fixes"),
	CHANGES("changes");
	
	private String command;
	private Commands(String command){
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}
}
