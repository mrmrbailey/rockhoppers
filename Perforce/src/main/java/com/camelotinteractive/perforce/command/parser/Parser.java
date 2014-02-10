package com.camelotinteractive.perforce.command.parser;

public interface Parser {
	
	
	/**
	 * Checks if a text string is parsable.
	 * @param text a string of text to be checked.
	 * @return true if parsable otherwise false.
	 */
	public boolean isParsable (String text);
	/**
	 * Parses a text string to return a usable value.
	 * @param text a string of text to be parsed.
	 * @return a parsed value from the text string. 
	 */
	public String parse(String text);

}
