package com.camelotinteractive.perforce.command.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses the fstat perforce output.
 */
public class FstatParser implements Parser {

	private static final String DEPOT_PATTERN = "\\.{3}\\s+?depotFile";

	/**
	 * Takes a line of text and parses it to return a filename.
	 */
	public String parse(String text) {
		Pattern p = Pattern.compile(DEPOT_PATTERN);
		Matcher m = p.matcher(text);
		if (m.find()) {
			return text.substring(m.end() + 1);
		}
		return null;
	}

	/**
	 * Checks if a text string is parsable to produce a filename.
	 * 
	 * @param text a string of text to be checked.
	 * @return true if a filename can be read otherwise false.
	 */
	public boolean isParsable(String text) {
		Pattern depotPattern = Pattern.compile(DEPOT_PATTERN);
		return depotPattern.matcher(text).find();
	}
}
