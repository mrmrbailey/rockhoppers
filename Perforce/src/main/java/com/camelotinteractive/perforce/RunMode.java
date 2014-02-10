package com.camelotinteractive.perforce;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum RunMode {
	
	CLOVER("clover", "com.camelotinteractive.perforce.CloverIncludes"),
	CODEREVIEW("review", "com.camelotinteractive.perforce.CodeReview");
	
	private String mode;
	private String clazz;

	private RunMode(String mode, String clazz) {
		this.mode = mode;
		this.clazz = clazz;
	}
	
	public String getMode() {
		return mode;
	}
	
	public String getClassName() {
		return clazz;
	}
	
	private final static Map<String, RunMode> lookup = new HashMap<String, RunMode>();
	static {
		for (RunMode r : EnumSet.allOf(RunMode.class)){
			lookup.put(r.getMode(), r);
		}
	}
	
	public static RunMode getRunMode(String mode) {
		return lookup.get(mode);
	}
}
